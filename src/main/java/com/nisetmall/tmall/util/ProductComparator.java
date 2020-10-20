package com.nisetmall.tmall.util;

import com.nisetmall.tmall.pojo.Product;


//用于排序产品
public class ProductComparator {

    /**
     * 评价数量多的放前面
     *
     * @param p1
     * @param p2
     * @return
     */
    public static int reviewCompare(Product p1, Product p2) {
        return p2.getReviewCount() - p1.getReviewCount();
    }

    /**
     * 创建日期晚的放前面
     *
     * @param p1
     * @param p2
     * @return
     */
    public static int dateCompare(Product p1, Product p2) {
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }

    /**
     * 销量高的放前面
     *
     * @param p1
     * @param p2
     * @return
     */
    public static int saleCompare(Product p1, Product p2) {
        return p2.getSaleCount() - p1.getSaleCount();
    }

    /**
     * 价格低的放前面
     *
     * @param p1
     * @param p2
     * @return
     */
    public static int priceCompare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }

    /**
     * 销量 * 评价 数值大的放前面
     *
     * @param p1
     * @param p2
     * @return
     */
    public static int hotCompare(Product p1, Product p2) {
        return p2.getReviewCount() * p2.getSaleCount() - p1.getReviewCount() * p1.getSaleCount();
    }

}
