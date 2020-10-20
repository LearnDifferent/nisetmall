# 最新说明

这是以前做过的演示项目，为了美观，只留下一个 final commit。

# NiseTmall / 偽天猫

~~官网：http://nisetmall.top/~~

简介：“偽天猫”是基于Spring、Spring MVC 和 MyBaits 框架的电商系统演示网站。

# 项目结构

```
src
└── main
    ├── java
    │   └── com
    │       └── nisetmall
    │           └── tmall
    │               ├── controller
    │               │   ├── AdminController.java
    │               │   ├── CategoryController.java
    │               │   ├── ForeLoginController.java
    │               │   ├── ForeOperationController.java
    │               │   ├── ForePresentController.java
    │               │   ├── OrderController.java
    │               │   ├── PageController.java
    │               │   ├── ProductController.java
    │               │   ├── ProductImageController.java
    │               │   ├── PropertyController.java
    │               │   ├── PropertyValueController.java
    │               │   └── UserController.java
    │               ├── mapper
    │               │   ├── CategoryMapper.java
    │               │   ├── OrderItemMapper.java
    │               │   ├── OrderMapper.java
    │               │   ├── ProductImageMapper.java
    │               │   ├── ProductMapper.java
    │               │   ├── PropertyMapper.java
    │               │   ├── PropertyValueMapper.java
    │               │   ├── ReviewMapper.java
    │               │   └── UserMapper.java
    │               ├── pojo
    │               │   ├── Category.java
    │               │   ├── CategoryExample.java
    │               │   ├── Order.java
    │               │   ├── OrderExample.java
    │               │   ├── OrderItem.java
    │               │   ├── OrderItemExample.java
    │               │   ├── Product.java
    │               │   ├── ProductExample.java
    │               │   ├── ProductImage.java
    │               │   ├── ProductImageExample.java
    │               │   ├── Property.java
    │               │   ├── PropertyExample.java
    │               │   ├── PropertyValue.java
    │               │   ├── PropertyValueExample.java
    │               │   ├── Review.java
    │               │   ├── ReviewExample.java
    │               │   ├── User.java
    │               │   └── UserExample.java
    │               ├── service
    │               │   ├── CategoryService.java
    │               │   ├── OrderItemService.java
    │               │   ├── OrderService.java
    │               │   ├── ProductImageService.java
    │               │   ├── ProductService.java
    │               │   ├── PropertyService.java
    │               │   ├── PropertyValueService.java
    │               │   ├── ReviewService.java
    │               │   ├── UserService.java
    │               │   └── impl
    │               │       ├── CategoryServiceImpl.java
    │               │       ├── OrderItemServiceImpl.java
    │               │       ├── OrderServiceImpl.java
    │               │       ├── ProductImageServiceImpl.java
    │               │       ├── ProductServiceImpl.java
    │               │       ├── PropertyServiceImpl.java
    │               │       ├── PropertyValueServiceImpl.java
    │               │       ├── ReviewServiceImpl.java
    │               │       └── UserServiceImpl.java
    │               └── util
    │                   ├── AdminInterceptor.java
    │                   ├── ImageUtil.java
    │                   ├── LogInterceptor.java
    │                   ├── MybatisGenerator.java
    │                   ├── OtherInterceptor.java
    │                   ├── OverIsMergeablePlugin.java
    │                   ├── ProductComparator.java
    │                   ├── SysException.java
    │                   └── SysExceptionReslover.java
    └── resources
        ├── applicationContext.xml
        ├── generatorConfig.xml
        ├── log4j.properties
        ├── mapper
        │   ├── CategoryMapper.xml
        │   ├── OrderItemMapper.xml
        │   ├── OrderMapper.xml
        │   ├── ProductImageMapper.xml
        │   ├── ProductMapper.xml
        │   ├── PropertyMapper.xml
        │   ├── PropertyValueMapper.xml
        │   ├── ReviewMapper.xml
        │   └── UserMapper.xml
        └── springMVC.xml
```

# 补充

- 本项目使用了 [MyBatis Generator](https://github.com/mybatis/generator) 生成 pojo、mapper 及其 XML 文件。
- 本项目使用了 [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper) 实现分页功能。
- 注：此 Repo 仅包含后端代码。
