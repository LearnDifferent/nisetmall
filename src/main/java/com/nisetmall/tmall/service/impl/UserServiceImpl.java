package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.UserMapper;
import com.nisetmall.tmall.pojo.User;
import com.nisetmall.tmall.pojo.UserExample;
import com.nisetmall.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User u) {
        userMapper.insert(u);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User u) {
        userMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }

    //判断用户名是否存在
    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> result = userMapper.selectByExample(example);
        if (!result.isEmpty())
            return true;
        return false;
    }

    @Override
    public User get(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> result = userMapper.selectByExample(example);
        if (result.isEmpty())
            return null;
        return result.get(0);
    }
}
