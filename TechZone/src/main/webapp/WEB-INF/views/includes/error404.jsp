<%-- 
    Document   : error404
    Created on : Nov 5, 2025, 9:17:21 PM
    Author     : letan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>404 - Trang không tồn tại</title>

    </head>
    <body>
        <%@include file="navbar.jsp" %>
        <div class="container-fluid m-5">
            <div>
                <h1>404</h1>
                <p>Trang bạn yêu cầu không tồn tại hoặc đã bị xóa.</p>
                <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">🏠 Quay lại trang chủ</a>
                <button class="btn btn-primary" type="button" onclick="history.back(); return false;">Quay lại</button>
            </div>
        </div>
        <%@include file="footer.jsp" %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
