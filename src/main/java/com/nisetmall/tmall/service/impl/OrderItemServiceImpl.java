package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.OrderItemMapper;
import com.nisetmall.tmall.pojo.Order;
import com.nisetmall.tmall.pojo.OrderItem;
import com.nisetmall.tmall.pojo.OrderItemExample;
import com.nisetmall.tmall.pojo.Product;
import com.nisetmall.tmall.service.OrderItemService;
import com.nisetmall.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    public void setProduct(List<OrderItem> ois) {
        for (OrderItem oi : ois) {
            setProduct(oi);
        }
    }

    private void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        //给订单项目里面的 private product 赋值
        oi.setProduct(p);
    }

    //Order 和 OrderItem 的一对多关系
    @Override
    public void fill(Order o) {
        //根据订单 ID 查询所有的订单项目
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        //将所有订单项目，打包为 List
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        //根据订单项目，设置 product 属性（为了看到该订单下的产品图片）
        setProduct(ois);

        //计算订单金额和订单总数
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            //Promote Price：促销价，现价
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);

        //为订单填充订单项目的信息
        o.setOrderItems(ois);
    }

    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void add(OrderItem c) {
        orderItemMapper.insert(c);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem c) {
        orderItemMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem result = orderItemMapper.selectByPrimaryKey(id);
        setProduct(result);
        return result;
    }

    /**
     * 获取产品销量
     *
     * @param pid
     * @return
     */
    @Override
    public int getSaleCount(int pid) {
        //获取和该产品 id 相等的所有订单项目
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois = orderItemMapper.selectByExample(example);

        //计数
        int count = 0;

        //遍历所有订单项目，根据订单项目的数量，计算总数
        for (OrderItem oi : ois) {
            count += oi.getNumber();
        }

        return count;
    }

    /**
     * @param uid 根据用户 ID
     * @return 获取订单里的项目
     */
    @Override
    public List<OrderItem> listByUser(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> result = orderItemMapper.selectByExample(example);
        setProduct(result);
        return result;
    }

}
