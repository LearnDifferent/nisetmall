package com.nisetmall.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.service.CategoryService;
import com.nisetmall.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/admin_category_list")
    public ModelAndView getList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("admin/listCategory");

        PageHelper.startPage(pageNum, 5);

        List<Category> cs = categoryService.getList();

        PageInfo pageInfo = new PageInfo(cs, 4);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("cs", cs);
        mav.addObject("pageParam", "admin_category_list?");
        return mav;
    }

    @RequestMapping("/admin_category_add")
    public String add(@RequestParam("image") MultipartFile image
            , Category category, HttpSession session) throws IOException {

        categoryService.add(category);

        //图片存放到：tmall_ssm/target/tmall_ssm/img/category
        File imageFile = new File(session.getServletContext()
                .getRealPath("img/category"), category.getId() + ".jpg");

        imageFile.getParentFile().mkdirs();

        image.transferTo(imageFile);

        //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg
        BufferedImage img = ImageUtil.change2jpg(imageFile);
        ImageIO.write(img, "jpg", imageFile);

        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
        System.out.println("in it alread.");

        categoryService.delete(id);

        System.out.println("delete success");

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();

        System.out.println("file deleted");

        return "redirect:/admin_category_list";
    }

    @RequestMapping("/admin_category_edit")
    public String edit(int id, Model model) throws IOException {
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    @RequestMapping("/admin_category_update")
    public String update(Category c, HttpSession session
            , @RequestParam("image") MultipartFile image) throws IOException {

        categoryService.update(c);

        if (null != image && !image.isEmpty()) {
            File imageFile = new File(session.getServletContext()
                    .getRealPath("img/category"), c.getId() + ".jpg");
            image.transferTo(imageFile);
            BufferedImage img = ImageUtil.change2jpg(imageFile);
            ImageIO.write(img, "jpg", imageFile);
        }
        return "redirect:/admin_category_list";
    }

}
