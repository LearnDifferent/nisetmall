package com.nisetmall.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//浏览器无法直接访问 WEB-INF 下的网页，所以要使用这个来跳转
@Controller
public class PageController {

    @RequestMapping("/registerPage")
    public String registerPage() {
        return "fore/register";
    }

    @RequestMapping("/registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "fore/login";
    }

    @RequestMapping("/forealipay")
    public String alipay(){
        return "fore/alipay";
    }
}
