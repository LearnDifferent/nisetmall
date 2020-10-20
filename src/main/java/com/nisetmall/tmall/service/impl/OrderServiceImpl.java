package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.OrderMapper;
import com.nisetmall.tmall.pojo.Order;
import com.nisetmall.tmall.pojo.OrderExample;
import com.nisetmall.tmall.pojo.OrderItem;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.OrderItemService;
import com.nisetmall.tmall.service.OrderService;
import com.nisetmall.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    public void setUser(Order o) {
        int uid = o.getUid();
        User u = userService.get(uid);
        o.setUser(u);
    }

    public void setUser(List<Order> os) {
        for (Order o : os)
            setUser(o);
    }

    @Override
    public void add(Order o) {
        orderMapper.insert(o);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order o, List<OrderItem> ois) {
        //金额
        float total = 0;
        //添加 order（实际上就是调用 mapper 里面的 insert 将记录添加到数据库）
        add(o);

        for (OrderItem oi : ois) {
            //让这个 OrderItem 有自己的 Order ID
            oi.setOid(o.getId());
            orderItemService.update(oi);
            //计算金额
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order o) {
        orderMapper.updateByPrimaryKeySelective(o);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }

    /**
     * 用于展示订单
     *
     * @param uid
     * @param excludedStatus 排除的订单状态（为了排除 delete 状态的订单）
     * @return
     */
    @Override
    public List list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

}
