<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../Includes/navbar-admin.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Content -->
<div class="content">
    <form action="${pageContext.request.contextPath}/admin" method="post">
        <input type="hidden" name="action" value="user">
        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Người dùng</h1>
            <button class="btn btn-primary">Thêm người dùng mới</button>
        </div>

        <!-- Tìm kiếm + Lọc -->
        <div class="d-flex mb-3">
            <input type="text" class="form-control me-2" placeholder="Tìm kiếm...">
            <select class="form-select" style="max-width:200px;">
                <option selected>Lọc theo vai trò</option>
                <option value="Admin">Admin</option>
                <option value="Staff">Staff</option>
                <option value="Customer">Customer</option>
            </select>
        </div>

        <!-- Bảng -->
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>SDT</th>
                        <th>Vai trò</th>
                        <th class="text-center">Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${accounts}">
                    <tr>
                        <td>${a.fullName}</td>
                        <td>${a.email}</td>
                        <td>${a.phone}</td>
                        <td>Ad</td>
                        <td class="text-center">
                            <a href="${pageContext.request.contextPath}/admin">
                                <i class="bi bi-eye me-2" style="cursor: pointer;"></i>
                            </a>
                            <a href="${pageContext.request.contextPath}/artist?view=create">
                                <i class="bi bi-trash text-danger" style="cursor: pointer;"></i>
                            </a>


                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </form>
</div>

</body>
</html>
