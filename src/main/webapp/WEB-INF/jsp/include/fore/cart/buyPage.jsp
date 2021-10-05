<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<div class="buyPageDiv">
    <form action="forecreateOrder" method="post">

        <div class="buyFlow">
            <img class="pull-left" src="img/site/simpleLogo.png">
            <img class="pull-right" src="img/site/buyflow.png">
            <div style="clear:both"></div>
        </div>
        <div class="address">
            <div class="addressTip">Your Address/div>
                <div>

                    <table class="addressTable">
                        <tr>
                            <td class="firstColumn">Shipping Address<span class="redStar">*</span></td>

                            <td><textarea name="address" placeholder="You can write down a fake address."></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>Postal Code</td>
                            <td><input name="post" placeholder="000000 is OK." type="text"></td>
                        </tr>
                        <tr>
                            <td>Name<span class="redStar">*</span></td>
                            <td><input name="receiver" placeholder="Less than 25 character." type="text"></td>
                        </tr>
                        <tr>
                            <td>Phone:<span class="redStar">*</span></td>
                            <td><input name="mobile" placeholder="11 digits long." type="text"></td>
                        </tr>
                    </table>

                </div>


            </div>
            <div class="productList">
                <div class="productListTip">Review Your Order</div>


                <table class="productListTable">
                    <thead>
                    <tr>
                        <th colspan="2" class="productListTableFirstColumn">
                            <img class="tmallbuy" src="img/site/tmallbuy.png">
                            <a class="marketLink" href="#nowhere">Sold by: NiseTmall</a>
                            <a class="wangwanglink" href="#nowhere"> <span class="wangwangGif"></span> </a>
                        </th>
                        <th>Price</th>
                        <th>Qty</th>
                        <th>Total</th>
                        <th>Delivery method</th>
                    </tr>
                    <tr class="rowborder">
                        <td colspan="2"></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody class="productListTableTbody">
                    <c:forEach items="${ois}" var="oi" varStatus="st">
                        <tr class="orderItemTR">
                            <td class="orderItemFirstTD"><img class="orderItemImg"
                                                              src="img/productSingle_middle/${oi.product.productImage.id}.jpg">
                            </td>
                            <td class="orderItemProductInfo">
                                <a href="foreproduct?pid=${oi.product.id}" class="orderItemProductLink">
                                        ${oi.product.name}
                                </a>


                                <img src="img/site/creditcard.png" title="支持信用卡支付">
                                <img src="img/site/7day.png" title="消费者保障服务,承诺7天退货">
                                <img src="img/site/promise.png" title="消费者保障服务,承诺如实描述">

                            </td>
                            <td>

                                <span class="orderItemProductPrice">￥<fmt:formatNumber type="number"
                                                                                       value="${oi.product.promotePrice}"
                                                                                       minFractionDigits="2"/></span>
                            </td>
                            <td>
                                <span class="orderItemProductNumber">${oi.number}</span>
                            </td>
                            <td><span class="orderItemUnitSum">
						￥<fmt:formatNumber type="number" value="${oi.number*oi.product.promotePrice}"
                                           minFractionDigits="2"/>
						</span></td>
                            <c:if test="${st.count==1}">
                                <td rowspan="5" class="orderItemLastTD">
                                    <label class="orderItemDeliveryLabel">
                                        <input type="radio" value="" checked="checked">
                                        Standard Delivery
                                    </label>

                                    <select class="orderItemDeliverySelect" class="form-control">
                                        <option>Free Delivery</option>
                                    </select>

                                </td>
                            </c:if>

                        </tr>
                    </c:forEach>

                    </tbody>

                </table>
                <div class="orderItemSumDiv">
                    <div class="pull-left">
                        <span class="leaveMessageText">Message to seller:</span>
                        <span>
					<img class="leaveMessageImg" src="img/site/leaveMessage.png">
				</span>
                        <span class="leaveMessageTextareaSpan">
					<textarea name="userMessage" class="leaveMessageTextarea"></textarea>
					<div>
						<span>200 character to go</span>
					</div>
				</span>
                    </div>

                    <span class="pull-right">Order total: ￥<fmt:formatNumber type="number" value="${total}"
                                                                             minFractionDigits="2"/></span>
                </div>


            </div>

            <div class="orderItemTotalSumDiv">
                <div class="pull-right">
                    <span>You should pay：</span>
                    <span class="orderItemTotalSumSpan">￥<fmt:formatNumber type="number" value="${total}"
                                                                           minFractionDigits="2"/></span>
                </div>
            </div>

            <div class="submitOrderDiv">
                <button type="submit" class="submitOrderButton">Place Order</button>
            </div>
        </div>
    </form>
</div>