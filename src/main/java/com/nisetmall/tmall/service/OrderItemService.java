package com.nisetmall.tmall.service;

import com.nisetmall.tmall.pojo.Order;
import com.nisetmall.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    void add(OrderItem c);
    void delete(int id);
    void update(OrderItem c);
    OrderItem get(int id);
    List list();
    void fill(List<Order> os);
    void fill(Order o);
    int getSaleCount(int  pid);
    List<OrderItem> listByUser(int uid);
}
