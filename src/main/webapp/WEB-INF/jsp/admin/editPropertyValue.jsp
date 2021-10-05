<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>


<title>编辑产品属性值</title>

<script>
	$(function () {
		<!--使用 keyup 事件，获取 class="pvValue" 输入框里的值-->
		$("input.pvValue").keyup(function () {
			var parSpan = $(this).parent("span"); <!--这是该输入框的 span-->
			var val = $(this).val(); <!--获取该输入框的 value-->
			var id = $(this).attr("pvid"); <!--这是 property value 的 id-->

			parSpan.css("border","1px solid black");<!--输入框设置为黑色边框-->
			$.post( <!--AJAX 的 post 请求-->
					"admin_propertyValue_update",<!--这是请求路径-->
					{"value":val,"id":id},<!--data-->
					function (responseFromController) { <!--callback-->
						<!--turn green if success or red if failure -->
						if (responseFromController == "success")
							parSpan.css("border","1px solid green");
						else
							parentSpan.css("border","1px solid red");
					} <!--type在输入框里面已经定义了，所以可以不写-->
			);
		})
	})
</script>

<div class="workingArea">
	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		<li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
		<li class="active">${p.name}</li>
		<li class="active">编辑产品属性</li>
	</ol>

	<div class="editPVDiv">
		<c:forEach items="${pvs}" var="pv">
			<div class="eachPV">
				<span class="pvName" >${pv.property.name}</span>
				<span class="pvValue"><input class="pvValue" pvid="${pv.id}" type="text" value="${pv.value}"></span>
			</div>
		</c:forEach>
		<div style="clear:both"></div>
	</div>

</div>

