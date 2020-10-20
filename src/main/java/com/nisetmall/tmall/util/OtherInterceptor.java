package com.nisetmall.tmall.util;

import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.OrderItem;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.CategoryService;
import com.nisetmall.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderItemService orderItemService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;

    }

    /**
     * 生成视图前加入数据
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response
            , Object handler, ModelAndView modelAndView) throws Exception {

        //用于在搜索栏下面放置分类集合信息
        List<Category> cs = categoryService.getList();
        request.getSession().setAttribute("cs", cs);


        //用于点击图标后，可以跳转到首页
        String contextPath = request.getContextPath();
        request.getSession().setAttribute("contextPath", contextPath);

        //用于显示该用户的购物车里面的数量
        User user = (User) request.getSession().getAttribute("user");
        int cartNum = 0;
        if (null != user) {
            List<OrderItem> ois = orderItemService.listByUser(user.getId());
            for (OrderItem oi : ois) {
                cartNum += oi.getNumber();
            }
        }
        request.getSession().setAttribute("cartTotalItemNumber", cartNum);
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
