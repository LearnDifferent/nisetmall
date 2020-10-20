package com.nisetmall.tmall.service;

import com.nisetmall.tmall.pojo.User;

import java.util.List;

public interface UserService {

    void add(User u);
    void delete(int id);
    void update(User u);
    User get(int id);
    List<User> list();
    boolean isExist(String name);
    User get(String name, String password);
}
