package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.ReviewMapper;
import com.nisetmall.tmall.pojo.Review;
import com.nisetmall.tmall.pojo.ReviewExample;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.service.ReviewService;
import com.nisetmall.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    UserService userService;

    /**
     * 添加评价
     * 因为添加评论和修改订单状态都有
     *
     * @param c
     */
    @Override
    @Transactional(propagation= Propagation.REQUIRED, rollbackForClassName = {"Exception"})
    public void add(Review c) {
        reviewMapper.insert(c);
    }

    /**
     * 删除评价
     *
     * @param id 该评价的 id
     */
    @Override
    public void delete(int id) {
        reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Review c) {
        reviewMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Review get(int id) {
        return reviewMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取该产品的所有评价
     *
     * @param pid
     * @return
     */
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");

        List<Review> result = reviewMapper.selectByExample(example);
        setUser(result);
        return result;
    }

    /**
     * 根据该产品评价的 List 的大小，计算总评价数
     *
     * @param pid
     * @return
     */
    @Override
    public int getCount(int pid) {
        return list(pid).size();
    }

    private void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }

    public void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }
}
