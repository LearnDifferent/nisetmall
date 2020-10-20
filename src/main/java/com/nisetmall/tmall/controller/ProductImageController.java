package com.nisetmall.tmall.controller;

import com.nisetmall.tmall.pojo.Product;
import com.nisetmall.tmall.pojo.ProductImage;
import com.nisetmall.tmall.service.ProductImageService;
import com.nisetmall.tmall.service.ProductService;
import com.nisetmall.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Controller
public class ProductImageController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @RequestMapping("/admin_productImage_list")
    public String list(int pid, Model model) {

        Product p = productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);

        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);

        return "admin/listProductImage";
    }

    @RequestMapping("/admin_productImage_add")
    public String add(ProductImage pi, HttpSession session, @RequestParam("image") MultipartFile image) {

        productImageService.add(pi);

        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if (ProductImageService.type_single.equals(pi.getType())) {

            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
        }

        File imageFile = new File(imageFolder, fileName);
        imageFile.getParentFile().mkdirs();

        try {

            image.transferTo(imageFile);

            //确保图片格式一定是jpg
            BufferedImage img = ImageUtil.change2jpg(imageFile);
            ImageIO.write(img, "jpg", imageFile);

            if (ProductImageService.type_single.equals(pi.getType())) {
                File imageFile_small = new File(imageFolder_small, fileName);
                File imageFile_middle = new File(imageFolder_middle, fileName);

                //使用 ImageUtil.resizeImage 改变图片大小
                ImageUtil.resizeImage(imageFile, 56, 56, imageFile_small);
                ImageUtil.resizeImage(imageFile, 217, 190, imageFile_middle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

    @RequestMapping("/admin_productImage_delete")
    public String delete(int id, HttpSession session) {

        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId() + ".jpg";
        String imageFolder;
        String imageFolder_small;
        String imageFolder_middle;

        if (ProductImageService.type_single.equals(pi.getType())) {

            imageFolder = session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile = new File(imageFolder, fileName);
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();
        } else {
            imageFolder = session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder, fileName);
            imageFile.delete();
        }


        productImageService.delete(id);


        return "redirect:admin_productImage_list?pid=" + pi.getPid();
    }

}
