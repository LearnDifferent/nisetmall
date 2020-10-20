package com.nisetmall.tmall.service;

import com.nisetmall.tmall.pojo.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getList();

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}
