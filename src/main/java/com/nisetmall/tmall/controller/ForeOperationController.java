package com.nisetmall.tmall.controller;

import com.nisetmall.tmall.pojo.*;
import com.nisetmall.tmall.service.*;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ForeOperationController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    /**
     * 立即购买
     *
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @RequestMapping("/forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        //id of OrderItem
        int oiId = 0;

        //获取产品
        Product p = productService.get(pid);

        //获取用户对象user（后面用 uid 获取订单项目）
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user.getId());

        //是否存在对应的 OrderItem
        boolean found = false;

        for (OrderItem oi : ois) {
            //如果该 product 已经有对应的 OrderItem 了（即"还没有生成订单"，目前在"购物车"内）
            if (oi.getProduct().getId().intValue() == p.getId().intValue()) {
                //将新添加/传进来的 num（数量）加入该 OrderItem 原本设定好的数量
                oi.setNumber(oi.getNumber() + num);
                //更新数据
                orderItemService.update(oi);
                //代表该 product 有对应的 OrderItem
                found = true;
                //获取 OrderItem 的 ID
                oiId = oi.getId();
                break;
            }
        }

        //代表该 product 没有对应的 OrderItem（之前没有被加入购物车，即第一次被添加为 OrderItem）
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
            oiId = oi.getId();
        }
        //跳转到结算页面
        return "redirect:forebuy?oiid=" + oiId;
    }

    /**
     * 结算
     *
     * @param model
     * @param oiid
     * @param session
     * @return
     */
    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {
        //订单项的集合
        List<OrderItem> ois = new ArrayList<>();
        float totalPrice = 0;

        //这里传入的是 String[] oiid，而不是 int
        //因为如果是从购物车页面传送来的数据，会出现有多个 OrderItem 的情况
        for (String strid : oiid) {
            //将每一个 OrderItem 的 ID 转化为 int 类型
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            totalPrice += oi.getProduct().getPromotePrice() * oi.getNumber();
            ois.add(oi);
        }

        session.setAttribute("ois", ois);
        model.addAttribute("total", totalPrice);
        return "fore/buy";
    }

    /**
     * 加入购物车
     *
     * @param pid
     * @param num
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {
        Product p = productService.get(pid);
        User user = (User) session.getAttribute("user");
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId().intValue() == p.getId().intValue()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }

        //返回字符串"success"，表示添加成功
        return "success";
    }

    /**
     * 在购物车页面修改需要的商品数量信息
     *
     * @param model
     * @param session
     * @param pid
     * @param number
     * @return
     */
    @RequestMapping("/forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model
            , HttpSession session, int pid, int number) {

        //判断用户是否登陆
        User user = (User) session.getAttribute("user");
        if (null == user)
            return "fail";

        //获取当前用户，所有的未生成订单的 OrderItem
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId().intValue() == pid) {
                //找到匹配的 OrderIterm 并更新数据
                oi.setNumber(number);
                orderItemService.update(oi);
                break;
            }

        }
        return "success";
    }

    /**
     * 删除购物车内已选择的商品
     *
     * @param model
     * @param session
     * @param oiid
     * @return
     */
    @RequestMapping("/foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Model model, HttpSession session, int oiid) {

        User user = (User) session.getAttribute("user");
        if (null == user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }

    /**
     * 新建订单
     *
     * @param model
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("/forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session) {

        //user information
        User user = (User) session.getAttribute("user");
        //order unique name / order's code
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        //在创建订单之后，会变为等待付款。
        order.setStatus(OrderService.waitPay);
        //来自 ForeController.buy() 的 订单项集合
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        //加入数据库，并计算总额
        float total = orderService.add(order, ois);
        //跳转到 PageController.alipay()
        return "redirect:forealipay?oid=" + order.getId() + "&total=" + total;
    }

    /**
     * 付款后的执行事项
     *
     * @param oid
     * @param total
     * @return
     */
    @RequestMapping("/forepaid")
    public ModelAndView paid(int oid, float total) {

        ModelAndView mav = new ModelAndView("fore/paid");
        Order order = orderService.get(oid);
        //付款后，变为等待发货
        order.setStatus(OrderService.waitDelivery);
        //存储付款日期
        order.setPayDate(new Date());
        //更新订单
        orderService.update(order);
        mav.addObject("o", order);
        return mav;
    }

    /**
     * 确认收货
     *
     * @param oid
     * @return
     */
    @RequestMapping("/foreconfirmPay")
    public ModelAndView confirmPay(int oid) {

        ModelAndView mav = new ModelAndView("fore/confirmPay");
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        mav.addObject("o", o);
        return mav;
    }

    /**
     * 确认收货成功后的操作
     *
     * @param oid
     * @return
     */
    @RequestMapping("/foreorderConfirmed")
    public ModelAndView orderConfirmed(int oid) {

        ModelAndView mav = new ModelAndView("fore/orderConfirmed");
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return mav;
    }

    /**
     * 订单状态变更为 delete（不能真的删除订单）
     *
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("/foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model, int oid) {

        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        //boughtPage.jsp 获取返回字符串 success 后，会隐藏掉当前这行订单数据
        return "success";
    }

    /**
     * 发送评价
     *
     * @param model
     * @param session
     * @param content
     * @param oid
     * @param pid
     * @return
     */
    @RequestMapping("/foredoreview")
    public String toReview(Model model, HttpSession session, String content
            , @RequestParam("oid") int oid, @RequestParam("pid") int pid) {
        Order o = orderService.get(oid);
        //修改订单状态为 finish
        o.setStatus(OrderService.finish);
        orderService.update(o);

        //content 是评价信息，这里要注意转换
        content = HtmlUtils.htmlEscape(content);

        //获取用户信息
        User user = (User) session.getAttribute("user");
        //创建评价并填充献给信息
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());

        reviewService.add(review);

        return "redirect:forereview?oid=" + oid + "&showonly=true";
    }

}