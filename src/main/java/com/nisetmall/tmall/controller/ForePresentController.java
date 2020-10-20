package com.nisetmall.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisetmall.tmall.pojo.*;
import com.nisetmall.tmall.service.*;
import com.nisetmall.tmall.util.ProductComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class ForePresentController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    /**
     * 用于在首页展示以分类为基础的信息
     *
     * @param model
     * @return
     */
    @RequestMapping("/forehome")
    public String home(Model model) {

        List<Category> cs = categoryService.getList();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    /**
     * 用于展示产品信息
     *
     * @param pid
     * @return
     */
    @RequestMapping("/foreproduct")
    public ModelAndView product(int pid) {

        Product p = productService.get(pid);

        ModelAndView mav = new ModelAndView("fore/product");

        //获取产品图片的信息
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        //并将信息赋予 product
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        //获取该产品的所有属性值
        List<PropertyValue> pvs = propertyValueService.list(p.getId());

        //获取该产品对应的所有的评价
        List<Review> reviews = reviewService.list(p.getId());

        //设置产品的销量和评价数量
        productService.setSaleAndReviewNumber(p);

        mav.addObject("reviews", reviews);
        mav.addObject("p", p);
        mav.addObject("pvs", pvs);

        return mav;
    }

    /**
     * 用于展示分类信息
     *
     * @param cid
     * @param sort    根据条件，对分类进行排序
     * @param pageNum
     * @return
     */
    @RequestMapping("/forecategory")
    public ModelAndView category(int cid, String sort
            , @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        Category c = categoryService.get(cid);

        ModelAndView mav = new ModelAndView("fore/category");

        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());

        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), ProductComparator::reviewCompare);
                    break;
                case "date":
                    Collections.sort(c.getProducts(), ProductComparator::dateCompare);
                    break;

                case "saleCount":
                    Collections.sort(c.getProducts(), ProductComparator::saleCompare);
                    break;

                case "price":
                    Collections.sort(c.getProducts(), ProductComparator::priceCompare);
                    break;

                case "all":
                    Collections.sort(c.getProducts(), ProductComparator::hotCompare);
                    break;
            }
        }

        PageHelper.startPage(pageNum, 8);

        List<Product> products = c.getProducts();

        PageInfo pageInfo = new PageInfo(products, 4);

        mav.addObject("c", c);
        mav.addObject("products", products);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam"
                , "forecategory?cid=" + cid + "&sort=" + sort + "&");
        return mav;
    }

    /**
     * 用于展示搜索信息
     *
     * @param keyword 搜索的关键词
     * @param pageNum
     * @return
     */
    @RequestMapping("/foresearch")
    public ModelAndView search(String keyword
            , @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        ModelAndView mav = new ModelAndView("fore/searchResult");

        PageHelper.startPage(pageNum, 8);
        List<Product> ps = productService.search(keyword);
        productService.setSaleAndReviewNumber(ps);

        PageInfo pageInfo = new PageInfo(ps, 4);
        mav.addObject("ps", ps);
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("pageParam", "foresearch?keyword=" + keyword + "&");

        mav.addObject("keyword", keyword);
        return mav;
    }

    /**
     * 查看购物车
     *
     * @param session
     * @return
     */
    @RequestMapping("/forecart")
    public ModelAndView cart(HttpSession session) {

        ModelAndView mav = new ModelAndView("fore/cart");

        //要登陆后才会进入这里。所以可以直接取session里面的"user"
        User user = (User) session.getAttribute("user");
        //获取这个用户关联的订单项集合
        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        mav.addObject("ois", ois);

        //跳转到购物车页面
        return mav;
    }

    /**
     * 展示已购买 / 订单页面
     *
     * @param session
     * @return
     */
    @RequestMapping("/forebought")
    public ModelAndView bought(HttpSession session) {

        ModelAndView mav = new ModelAndView("fore/bought");

        User user = (User) session.getAttribute("user");
        //找出所有订单状态不是 delete 的订单
        List<Order> os = orderService.list(user.getId(), OrderService.delete);

        //为这些订单填充订单项
        orderItemService.fill(os);

        mav.addObject("os", os);

        return mav;
    }

    /**
     * 在订单（已购买）页面展示评价
     *
     * @param oid
     * @return
     */
    @RequestMapping("/forereview")
    public ModelAndView review(int oid) {

        ModelAndView mav = new ModelAndView("fore/review");

        Order o = orderService.get(oid);
        //为订单填充订单项目
        orderItemService.fill(o);
        //获取第一个订单项目里的产品
        Product p = o.getOrderItems().get(0).getProduct();
        //获取这个产品的评价集合
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);

        //存入产品，订单和评价集合的信息
        mav.addObject("p", p);
        mav.addObject("o", o);
        mav.addObject("reviews", reviews);

        return mav;
    }
}
