package com.nisetmall.tmall.service.impl;

import com.nisetmall.tmall.mapper.ProductMapper;
import com.nisetmall.tmall.pojo.Category;
import com.nisetmall.tmall.pojo.Product;
import com.nisetmall.tmall.pojo.ProductExample;
import com.nisetmall.tmall.pojo.ProductImage;
import com.nisetmall.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Override
    public void add(Product p) {
        productMapper.insert(p);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Product p) {
        productMapper.updateByPrimaryKeySelective(p);
    }

    @Override
    public Product get(int id) {
        Product p = productMapper.selectByPrimaryKey(id);
        setProductImage(p);
        setCategory(p);
        return p;
    }

    public void setCategory(List<Product> ps) {
        for (Product p : ps) {
            setCategory(p);
        }
    }

    public void setCategory(Product p) {
        int cid = p.getCid();
        Category c = categoryService.get(cid);
        p.setCategory(c);
    }

    @Override
    public List list(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List productList = productMapper.selectByExample(example);

        setProductImage(productList);
        setCategory(productList);
        return productList;
    }

    @Override
    public void setProductImage(Product p) {

        List pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            //取出第一个作为图片
            ProductImage productImage = (ProductImage) pis.get(0);
            p.setProductImage(productImage);
        }
    }

    public void setProductImage(List<Product> ps) {
        for (Product p : ps) {
            this.setProductImage(p);
        }
    }

    //分类里的各个产品
    @Override
    public void fill(Category c) {
        List<Product> ps = list(c.getId());
        c.setProducts(ps);
    }

    @Override
    public void fill(List<Category> cs) {
        for (Category c : cs) {
            fill(c);
        }
    }

    //为了方便页面显示，将分类下的产品集合，拆成多个
    @Override
    public void fillByRow(List<Category> cs) {
        //按照 8 个为一行
        int eachRowNum = 8;

        //遍历 Category 集合中的每一个 Category
        for (Category c : cs) {

            //取出当前 Category 的所有 products
            List<Product> products = c.getProducts();

            //创建一个集合容器，用于存放所有"按照 eachRowNum 整合的 products 集合"
            List<List<Product>> productsByRow = new ArrayList<>();

            //按照 eachRowNum 为一组来递增，整合该 category 的所有 products
            for (int i = 0; i < products.size(); i += eachRowNum) {

                //如果递增后超出了所有 products 的数量，则将该次递增后的参数修改为 products 的最大数量
                int endNum = i + eachRowNum;
                if (endNum > products.size()) {
                    endNum = products.size();
                }

                //将每一组整合好的 products 列表，存入之前准备好的容器
                List<Product> productEachRow = products.subList(i, endNum);
                productsByRow.add(productEachRow);
            }

            //在该 Category 中 productsByRow
            c.setProductsByRow(productsByRow);
        }
    }

    /**
     * 为产品设置销量和评价数据
     *
     * @param p
     */
    @Override
    public void setSaleAndReviewNumber(Product p) {
        int saleCount = orderItemService.getSaleCount(p.getId());
        p.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(p.getId());
        p.setReviewCount(reviewCount);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        //模糊查询
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");

        List result = productMapper.selectByExample(example);

        setProductImage(result);
        setCategory(result);
        return result;
    }


}
