package com.nisetmall.tmall.controller;

import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
public class ForeLoginController {

    @Autowired
    UserService userService;

    /**
     * 用于注册
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/foreregister")
    public String register(Model model, User user) {

        String name = user.getName();
        //Turn special characters into HTML character references
        //防止一些用户名为：<script>alert('content')</script> 的内容
        name = HtmlUtils.htmlEscape(name);
        //把不会出问题的名字重新赋予用户
        user.setName(name);

        boolean exist = userService.isExist(name);

        if (exist) {
            String m = "Username already in use! Please change your username.";
            model.addAttribute("msg", m);
            //保证作用域不会添加失败了的 "user"，因为跳转到的"fore/register"有写地方会用到"user"
            model.addAttribute("user", null);

            return "fore/register";
        }
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    /**
     * 用于登陆
     *
     * @param name
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/forelogin")
    public String login(@RequestParam("name") String name
            , @RequestParam("password") String password
            , Model model, HttpSession session) {

        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "Username and password do not match!");
            return "fore/login";
        }

        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    /**
     * 用于 modal.jsp，即弹出小窗口的登陆
     *
     * @param name
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 用于登出
     *
     * @param session
     * @return
     */
    @RequestMapping("/forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    /**
     * 用于判断是否已经登陆
     *
     * @param session
     * @return
     */
    @RequestMapping("/forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        //获取 session 里的 "user"
        User user = (User) session.getAttribute("user");
        //判断 user 是否存在
        if (null != user)
            //存在的话，返回 success
            return "success";
        //不存在返回 fail
        return "fail";
    }

}
