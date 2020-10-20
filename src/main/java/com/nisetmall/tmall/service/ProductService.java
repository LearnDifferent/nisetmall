package com.nisetmall.tmall.service;

import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.Product;

import java.util.List;

public interface ProductService {

    void add(Product p);

    void delete(int id);

    void update(Product p);

    Product get(int id);

    List list(int cid);

    void setProductImage(Product p);

    void fill(List<Category> cs);

    void fill(Category c);

    void fillByRow(List<Category> cs);

    /**
     * 为产品设置销量和评价属性
     *
     * @param p
     */
    void setSaleAndReviewNumber(Product p);

    void setSaleAndReviewNumber(List<Product> ps);

    List<Product> search(String keyword);
}
