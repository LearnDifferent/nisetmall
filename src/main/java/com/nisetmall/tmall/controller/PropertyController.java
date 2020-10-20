package com.nisetmall.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.Property;
import com.nisetmall.tmall.service.CategoryService;
import com.nisetmall.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PropertyController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    PropertyService propertyService;

    @RequestMapping("/admin_property_add")
    public String add(Property p) {
        propertyService.add(p);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_property_delete")
    public String delete(int id) {
        Property p = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_property_edit")
    public String edit(Model model, int id) {
        Property p = propertyService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    @RequestMapping("/admin_property_update")
    public String update(Property p) {
        propertyService.update(p);
        return "redirect:admin_property_list?cid=" + p.getCid();
    }

    @RequestMapping("/admin_property_list")
    public ModelAndView getList(@RequestParam("cid") int cid
            , @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("admin/listProperty");

        PageHelper.startPage(pageNum, 5);

        List<Property> properties = propertyService.list(cid);
        Category c = categoryService.get(cid);

        PageInfo pageInfo = new PageInfo(properties, 4);


        mav.addObject("ps", properties);
        mav.addObject("c", c);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam"
                , "admin_property_list?cid=" + c.getId() + "&");

        return mav;
    }
}
