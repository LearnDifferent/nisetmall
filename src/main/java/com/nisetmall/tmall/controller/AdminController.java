package com.nisetmall.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//用于登陆，配合过滤器
@Controller
public class AdminController {

    @RequestMapping("/adminLoginPage")
    public String registerPage() {
        return "admin/adminLogin";
    }

    @RequestMapping("/adminLoginCheck")
    public String adminLoginPage(@RequestParam(value = "myName", required = false) String name
            , Model model, HttpSession session, HttpServletResponse response) {

        name = HtmlUtils.htmlEscape(name);

        //此处的"XXX"为游客登陆名，网站实际运行时游客登陆名并不是"XXX"，而是我的真实姓名
        if (name.equalsIgnoreCase("XXX") || name.equalsIgnoreCase("XXX")) {
            session.setAttribute("admin", "guest");
            Cookie cookie = new Cookie("showUp", "showUp");
            cookie.setMaxAge(1);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:admin_category_list";
        }

        //此处的"XXX"为管理员登陆名，网站实际运行时管理员登陆名并不是"XXX"
        if (name.equalsIgnoreCase("XXX")) {
            session.setAttribute("admin", "admin");
            Cookie cookie = new Cookie("showUp", "showUp");
            cookie.setMaxAge(1);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:admin_category_list";
        }

        model.addAttribute("msg", "对不起，您的输入有误，请重新尝试。");

        return "admin/adminLogin";
    }

    @RequestMapping("/adminLogout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:adminLoginPage";
    }
}
