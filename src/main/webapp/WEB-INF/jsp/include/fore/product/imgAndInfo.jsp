<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>

    $(function () {
        var stock = ${p.stock};
        $(".productNumberSetting").keyup(function () {
            var num = $(".productNumberSetting").val();
            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            if (num > stock)
                num = stock;
            $(".productNumberSetting").val(num);
        });

        $(".increaseNumber").click(function () {
            var num = $(".productNumberSetting").val();
            num++;
            if (num > stock)
                num = stock;
            $(".productNumberSetting").val(num);
        });
        $(".decreaseNumber").click(function () {
            var num = $(".productNumberSetting").val();
            --num;
            if (num <= 0)
                num = 1;
            $(".productNumberSetting").val(num);
        });

        <!--点击加入购物车按钮后判断是否登陆-->
        $(".addCartButton").removeAttr("disabled");
        $(".addCartLink").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {
                        var pid = ${p.id};
                        var num = $(".productNumberSetting").val();
                        var addCartpage = "foreaddCart";
                        $.get(
                            addCartpage,
                            {"pid": pid, "num": num},
                            function (result) {
                                if ("success" == result) {
                                    $(".addCartButton").html("Added to Cart!");
                                    $(".addCartButton").attr("disabled", "disabled");
                                    $(".addCartButton").css("background-color", "lightgray")
                                    $(".addCartButton").css("border-color", "lightgray")
                                    $(".addCartButton").css("color", "black")

                                } else {

                                }
                            }
                        );
                    } else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });
        <!--点击购买按钮的时候判断是否登陆-->
        $(".buyLink").click(function () {
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {
                        var num = $(".productNumberSetting").val();
                        location.href = $(".buyLink").attr("href") + "&num=" + num;
                    } else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });

        <!--原页面有 footer.jsp，而 footer.jsp 里有 modal.jsp，下面这个是 model.jsp 的按钮-->
        $("button.loginSubmitButton").click(function () {
            var name = $("#name").val();
            var password = $("#password").val();

            if (0 == name.length || 0 == password.length) {
                $("span.errorMessage").html("Please Enter Username / Password!");
                $("div.loginErrorMessageDiv").show();
                return false;
            }

            var page = "foreloginAjax";
            $.get(
                page,
                {"name": name, "password": password},
                function (result) {
                    if ("success" == result) {
                        location.reload();
                    } else {
                        $("span.errorMessage").html("Username and password do not match!");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );

            return true;
        });

        $("img.smallImage").mouseenter(function () {
            var bigImageURL = $(this).attr("bigImageURL");
            $("img.bigImg").attr("src", bigImageURL);
        });

        $("img.bigImg").load(
            function () {
                $("img.smallImage").each(function () {
                    var bigImageURL = $(this).attr("bigImageURL");
                    img = new Image();
                    img.src = bigImageURL;

                    img.onload = function () {
                        $("div.img4load").append($(img));
                    };
                });
            }
        );
    });

</script>

<div class="imgAndInfo">

    <div class="imgInimgAndInfo">
        <img src="img/productSingle/${p.productImage.id}.jpg" class="bigImg">
        <div class="smallImageDiv">
            <c:forEach items="${p.productSingleImages}" var="pi">
                <img src="img/productSingle_small/${pi.id}.jpg" bigImageURL="img/productSingle/${pi.id}.jpg"
                     class="smallImage">
            </c:forEach>
        </div>
        <div class="img4load hidden"></div>
    </div>


    <div class="infoInimgAndInfo">

        <div class="productTitle">
            ${p.name}
        </div>
        <div class="productSubTitle">
            ${p.subTitle}
        </div>


        <div class="productPrice">
            <div class="juhuasuan">
                <span class="juhuasuanBig">DISCOUNT</span>
                <span>$5 OFF!<span class="juhuasuanTime"> NEXT FRIDAY!</span></span>
            </div>


            <div class="productPriceDiv">
                <div class="gouwujuanDiv"><img height="16px" src="img/site/gouwujuan.png">
                    <span></span>

                </div>
                <div class="originalDiv">
                    <span class="originalPriceDesc">List price</span>
                    <span class="originalPriceYuan">¥</span>
                    <span class="originalPrice">
                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/>
                    </span>
                </div>

                <div class="promotionDiv">
                    <span class="promotionPriceDesc">NOW</span>
                    <span class="promotionPriceYuan">¥</span>
                    <span class="promotionPrice">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                    </span>
                </div>
            </div>
        </div>

        <div class="productSaleAndReviewNumber">
            <div>Sales <span class="redColor boldWord"> ${p.saleCount }</span></div>
            <div>Customer reviews<span class="redColor boldWord"> ${p.reviewCount}</span></div>
        </div>
        <div class="productNumber">
            <span>
                <span class="productNumberSettingSpan">
                <input class="productNumberSetting" type="text" value="1">
                </span>
                <span class="arrow">
                    <a href="#nowhere" class="increaseNumber">
                    <span class="updown">
                            <img src="img/site/increase.png">
                    </span>
                    </a>
                     
                    <span class="updownMiddle"> </span>
                    <a href="#nowhere" class="decreaseNumber">
                    <span class="updown">
                            <img src="img/site/decrease.png">
                    </span>
                    </a>
                     
                </span>Qty </span>
            <span>   (In Stock: ${p.stock} Qty)</span>
        </div>
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">服务承诺</span>
            <span class="serviceCommitmentLink">
                <a href="#nowhere">正品保证</a>
                <a href="#nowhere">极速退款</a>
                <a href="#nowhere">赠运费险</a>
                <a href="#nowhere">七天无理由退换</a>
            </span>
        </div>

        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}">
                <button class="buyButton">Buy now</button>
            </a>
            <a href="#nowhere" class="addCartLink">
                <button class="addCartButton"><span class="glyphicon glyphicon-shopping-cart"></span>Add to Cart
                </button>
            </a>
        </div>
    </div>

    <div style="clear:both"></div>

</div>