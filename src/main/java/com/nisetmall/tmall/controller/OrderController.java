package com.nisetmall.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.Order;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.OrderItemService;
import com.nisetmall.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * 找出所有订单，遍历每个订单下的所有项目，并交由前台展示
     * @param pageNum
     * @return
     */
    @RequestMapping("/admin_order_list")
    public ModelAndView list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("admin/listOrder");

        PageHelper.startPage(pageNum, 5);
        //所有订单的合集
        List<Order> os = orderService.list();

        //每个订单，都要加入，该订单的项目的信息
        orderItemService.fill(os);

        PageInfo pageInfo = new PageInfo(os, 4);

        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam", "admin_order_list?");
        mav.addObject("os", os);

        return mav;
    }

    @RequestMapping("/admin_order_delivery")
    public String delivery(Order o) {

        //修改发货时间
        o.setDeliveryDate(new Date());

        //发货状态设置为 "待收货"
        o.setStatus(OrderService.waitConfirm);

        //更新订单信息
        orderService.update(o);

        //跳转到展示页面
        return "redirect:admin_order_list";
    }
}
