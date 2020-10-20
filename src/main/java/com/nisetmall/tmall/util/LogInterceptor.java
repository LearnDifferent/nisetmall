package com.nisetmall.tmall.util;

import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.CategoryService;
import com.nisetmall.tmall.service.OrderItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

//用于拦截购买，加入购物车，查看购物车，查看订单等页面，使其发生前先进入登陆页面
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {


        //不需要登陆也能访问的路径
        String[] noNeedAuthPage = new String[]{
                "/forehome",
                "/forecheckLogin",
                "/foreregister",
                "/foreloginAjax",
                "/forelogin",
                "/foreproduct",
                "/forecategory",
                "/foresearch"
        };

        //去掉 "/tmall" 后的 uri 地址。示例："/forehome"
        String uriWithoutContextPath = StringUtils.remove(request.getRequestURI()
                , request.getContextPath());

        if(!Arrays.asList(noNeedAuthPage).contains(uriWithoutContextPath)){
            User user =(User) request.getSession().getAttribute("user");
            if(null==user){
                response.sendRedirect("loginPage");
                return false;
            }
        }

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}