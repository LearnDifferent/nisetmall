package com.nisetmall.tmall.util;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跳转到友好的异常错误页面
 * 虽然写了，但是这里没有用到。
 * 如果要用，要去 SpringMVC 那里设置
 * ，并且要在 jsp 里加入${errorMsg}
 */
public class SysExceptionReslover implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse, Object o, Exception e) {

        System.out.println("errorerrorerrorerrorerror");

        SysException exception = null;
        if (e instanceof SysException) { //看看是否属于自己新建的异常类
            exception = (SysException) e; //如果是，就将传入的异常赋值到这里
        } else { //如果没有
            exception = new SysException("Updating…… Please try again later."); //如果不是，就新建一个异常
        }
        ModelAndView mav = new ModelAndView("fore/error");//跳转到error.jsp
        mav.addObject("errorMsg", exception.getMessage());
        return mav;
    }
}

