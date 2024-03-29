<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="100"/>
</c:if>

<c:if test="${!empty param.categorycount}">
    <c:set var="categorycount" scope="page" value="${param.categorycount}"/>
</c:if>

<div class="categoryProducts">
    <c:forEach items="${c.products}" var="p" varStatus="stc">
        <c:if test="${stc.count<=categorycount}">
            <div class="productUnit" price="${p.promotePrice}">
                <div class="productUnitFrame">
                    <a href="foreproduct?pid=${p.id}">
                        <img class="productImage" src="img/productSingle_middle/${p.productImage.id}.jpg">
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
                        <span class="wangwang">
					<a class="wangwanglink" href="#nowhere">
						<img src="img/site/wangwang.png">
					</a>
					
					</span>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
    <div style="clear:both"></div>
</div>