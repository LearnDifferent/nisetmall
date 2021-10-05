<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="searchProducts">

    <c:forEach items="${ps}" var="p">
    <div class="productUnit" price="${p.promotePrice}">
        <a href="foreproduct?pid=${p.id}">
            <img class="productImage" src="img/productSingle/${p.productImage.id}.jpg">
        </a>
        <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}"
                                                      minFractionDigits="2"/></span>
        <a class="productLink" href="foreproduct?pid=${p.id}">
                ${fn:substring(p.name, 0, 50)}
        </a>

        <a class="tmallLink" href="foreproduct?pid=${p.id}">NiseTmall</a>

        <div class="show1 productInfo">
            <span class="monthDeal ">Sales: <span class="productDealNumber">${p.saleCount}</span></span>
            <span class="productReview">Reviews: <span class="productReviewNumber">${p.reviewCount}</span></span>
            <span class="wangwang"><img src="img/site/wangwang.png"></span>
        </div>

    </div>
    </c:forEach>
    <c:if test="${empty ps}">
    <div class="noMatch">Your search did not match any products.
        <div>
            </c:if>

            <div style="clear:both"></div>
        </div>
    </div>