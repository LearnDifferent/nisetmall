package com.nisetmall.tmall.mapper;

import com.nisetmall.tmall.pojo.Order;
import com.nisetmall.tmall.pojo.OrderExample;
import java.util.List;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}