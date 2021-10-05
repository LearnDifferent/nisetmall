<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<nav class="top">
    <a href="forehome">
        <span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-home redColor"></span>
        NiseTmall Homepage
    </a>

    <span>Welcome to NiseTmall</span>

    <c:if test="${!empty user}">
        <a href="loginPage">${user.name}</a>
        <a href="forelogout">Exit</a>
    </c:if>

    <c:if test="${empty user}">
        <a href="loginPage">Hello, Sign in</a>
        <a href="registerPage">New customer? Start here.</a>
    </c:if>


    <span class="pull-right">
			<a href="forebought">My Orders</a>
			<a href="forecart">
			<span style="color:#C40000;margin:0px" class=" glyphicon glyphicon-shopping-cart redColor"></span>
			Cart <c:if test="${cartTotalItemNumber != null}">
                <strong>${cartTotalItemNumber}</strong>Qty
            </c:if>

			</a>
		</span>


</nav>



