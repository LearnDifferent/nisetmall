package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.CategoryMapper;
import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.CategoryExample;
import com.nisetmall.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getList() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id desc");
        return categoryMapper.selectByExample(categoryExample);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
