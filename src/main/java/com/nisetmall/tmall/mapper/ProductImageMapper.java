package com.nisetmall.tmall.mapper;

import com.nisetmall.tmall.pojo.ProductImage;
import com.nisetmall.tmall.pojo.ProductImageExample;
import java.util.List;

public interface ProductImageMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}