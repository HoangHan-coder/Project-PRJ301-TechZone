<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/includes/navbar.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Accessory List</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center mb-4">🔌 Accessories</h2>
            <div class="row">
                <c:forEach var="p" items="${accessoryList}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100 text-center">
                            <img src="${p.image}" class="card-img-top" alt="${p.name}" style="height:200px; object-fit:cover;">
                            <div class="card-body">
                                <h5 class="card-title">${p.name}</h5>
                                <p class="card-text text-danger fw-bold">${p.price} VND</p>
                                <a href="products?view=detail&id=${p.id}" class="btn btn-primary">View Detail</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>
