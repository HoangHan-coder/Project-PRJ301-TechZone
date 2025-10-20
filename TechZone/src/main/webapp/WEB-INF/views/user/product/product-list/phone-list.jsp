<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/includes/navbar.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Phone List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">📱 Smartphones</h2>

    
        <c:if test="${empty list}">
            <p class="text-center text-muted">No products found.</p>
        </c:if>

        <div class="row">
           <c:forEach var="p" items="${list}">
    <div class="col-md-4 mb-4">
        <div class="card h-100 text-center shadow-sm">
            <img src="${p.linkImg}" class="card-img-top" alt="${p.productName}" style="height:400px; object-fit:cover;">
            <div class="card-body">
                <h5 class="card-title">${p.productName}</h5>
                <p class="card-text text-danger fw-bold">${p.productPrice} VND</p>          
                <a href="products?action=detail&id=${p.productId}" class="btn btn-primary">View Detail</a>
            </div>
        </div>
    </div>
</c:forEach>
        </div>
    </div>
</body>
</html>
