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

//用于后台登陆拦截
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    public boolean preHandle(HttpServletRequest request
            , HttpServletResponse response, Object handler) throws Exception {


        //以 admin 开头，不需要登陆也能访问的路径（的开头）
        String[] noNeedAuthPage = new String[]{
                "/adminLoginCheck",
                "/adminLoginPage"
        };

        //去掉 ContextPat 后，得到的 uri 地址。示例："/adminLoginPage"
        String uriWithoutContextPath = StringUtils.remove(request.getRequestURI()
                , request.getContextPath());

        if (!Arrays.asList(noNeedAuthPage).contains(uriWithoutContextPath)) {
            //之前会验证管理员姓名，如果正确了，会在 request 输出一个 "admin"
            String adminName = (String) request.getSession().getAttribute("admin");
            //只要没有 "admin"，就无法登陆
            if (null == adminName) {
                response.sendRedirect("adminLoginPage");
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