<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${empty list}">
    <div class="col-12 text-center text-muted mt-4">
        <p>Không tìm thấy sản phẩm nào phù hợp.</p>
    </div>
</c:if>

<c:forEach items="${list}" var="i">
    <div class="col-md-4 mb-4">
        <div class="card h-100 text-center shadow-sm">
            <img src="${i.linkImg}" class="card-img-top p-2" alt="${i.productName}">
            <div class="card-body">
                <h5 class="card-title">${i.productName}</h5>
                <p class="card-text text-danger font-weight-bold"><fmt:formatNumber value="${i.productPrice}" type="number" maxFractionDigits="0"/>VND</p>
                 <a href="products?action=detail&id=${i.productId}" class="btn btn-primary">View Detail</a>
            </div>
        </div>
    </div>
</c:forEach>
