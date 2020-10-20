package com.nisetmall.tmall.service;

import com.nisetmall.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    //单个图片
    String type_single = "type_single";

    //图片详情
    String type_detail = "type_detail";

    List list(int pid, String type);
    void add(ProductImage pi);
    void delete(int id);
    void update(ProductImage pi);
    ProductImage get(int id);
}