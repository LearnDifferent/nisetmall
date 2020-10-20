package com.nisetmall.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.Product;
import com.nisetmall.tmall.service.CategoryService;
import com.nisetmall.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @RequestMapping("/admin_product_list")
    public ModelAndView list(@RequestParam("cid") int cid
            , @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("admin/listProduct");

        PageHelper.startPage(pageNum, 5);

        List<Product> ps = productService.list(cid);
        Category c = categoryService.get(cid);

        PageInfo pageInfo = new PageInfo(ps, 4);

        mav.addObject("ps", ps);
        mav.addObject("c", c);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam"
                , "admin_product_list?cid=" + cid + "&");

        return mav;
    }

    @RequestMapping("/admin_product_add")
    public String add(Product p) {

        productService.add(p);
        return "redirect:admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_delete")
    public String delete(int id) {

        Product p = productService.get(id);
        productService.delete(id);

        return "redirect:admin_product_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_product_edit")
    public String edit(Model model, int id) {

        Product p = productService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);

        return "admin/editProduct";
    }

    @RequestMapping("/admin_product_update")
    public String update(Product p) {

        productService.update(p);
        return "redirect:admin_product_list?cid=" + p.getCid();
    }
}
