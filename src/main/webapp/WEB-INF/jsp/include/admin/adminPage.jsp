<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>


<script>
    $(function () {
        $("ul.pagination li.disabled a").click(function () {
            return false;
        });
    });

</script>

<%
    request.setAttribute("APP_PATH", request.getContextPath());
%>

<div>
    <nav>
        <ul class="pagination">
            <li>
                <a href="${APP_PATH}/${pageParam}pageNum=${pageInfo.firstPage}">&laquo;</a>
            </li>


            <li>
                <a href="${APP_PATH}/${pageParam}pageNum=${pageInfo.pageNum-1}" aria-label="Previous">
                    <span aria-hidden="true">‹</span>
                </a>
            </li>


            <c:forEach items="${pageInfo.navigatepageNums}" var="page_Num">
                <c:if test="${pageInfo.pageNum == page_Num}">
                    <li class="active"><a href="#">${page_Num}</a></li>
                </c:if>
                <c:if test="${pageInfo.pageNum != page_Num}">
                    <li><a href="${APP_PATH}/${pageParam}pageNum=${page_Num}">${page_Num}</a></li>
                </c:if>
            </c:forEach>

            <li>
                <a href="${APP_PATH}/${pageParam}pageNum=${pageInfo.pageNum+1}" aria-label="Next">
                    <span aria-hidden="true">›</span>
                </a>
            </li>
            <li>
                <a href="${APP_PATH}/${pageParam}pageNum=${pageInfo.lastPage}">&raquo;</a>
            </li>
        </ul>
    </nav>
</div>

<div>
    当前为第 ${pageInfo.pageNum} 页，总共 ${pageInfo.pages} 页，共 ${pageInfo.total} 条记录。<br><br>
</div>