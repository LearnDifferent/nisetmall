<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
    $(function () {
        $("input.sortBarPrice").keyup(function () {
            var num = $(this).val();
            if (num.length == 0) {
                $("div.productUnit").show();
                return;
            }

            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            $(this).val(num);


            var begin = $("input.beginPrice").val();
            var end = $("input.endPrice").val();
            if (!isNaN(begin) && !isNaN(end)) {
                console.log(begin);
                console.log(end);
                $("div.productUnit").hide();
                $("div.productUnit").each(function () {
                    var price = $(this).attr("price");
                    price = new Number(price);

                    if (price <= end && price >= begin)
                        $(this).show();
                });
            }

        });
    });
</script>
<div class="categorySortBar">

    <table class="categorySortBarTable categorySortTable">
        <tr>
            <td
                    <c:if test="${'all'==param.sort||empty param.sort}">class="grayColumn"</c:if> ><a
                    href="?cid=${c.id}&sort=all">Featured<span class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td
                    <c:if test="${'review'==param.sort}">class="grayColumn"</c:if> ><a href="?cid=${c.id}&sort=review">Review<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${'date'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=date">Newest<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${'saleCount'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=saleCount">Sale<span
                    class="glyphicon glyphicon-arrow-down"></span></a></td>
            <td <c:if test="${'price'==param.sort}">class="grayColumn"</c:if>><a href="?cid=${c.id}&sort=price">Price:Low to High<span
                    class="glyphicon glyphicon-resize-vertical"></span></a></td>
        </tr>
    </table>


    <table class="categorySortBarTable">
        <tr>
            <td><input class="sortBarPrice beginPrice" type="text" placeholder="From"></td>
            <td class="grayColumn priceMiddleColumn">-</td>
            <td><input class="sortBarPrice endPrice" type="text" placeholder="To"></td>
        </tr>
    </table>

</div>