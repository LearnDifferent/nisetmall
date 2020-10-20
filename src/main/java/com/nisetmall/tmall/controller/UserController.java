package com.nisetmall.tmall.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/admin_user_list")
    public ModelAndView list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("admin/listUser");

        PageHelper.startPage(pageNum, 5);

        List<User> users = userService.list();

        PageInfo pageInfo = new PageInfo(users, 4);

        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam","admin_user_list?");
        mav.addObject("us", users);

        return mav;
    }
}
