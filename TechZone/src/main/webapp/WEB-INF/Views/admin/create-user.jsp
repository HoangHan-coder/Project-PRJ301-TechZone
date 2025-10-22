<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../Includes/navbar-admin.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Admin - Thêm tài khoản</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


</head>
<body>

    <!-- Content -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <h3 class="mb-4">Thêm tài khoản</h3>
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin?action=create" method="post"> 
                                <div class="mb-3">
                                    <label class="form-label">AccountId</label>
                                    <input type="text" id="acountId" class="form-control"
                                           value="${nextId}" readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Username</label>
                                    <input type="text" class="form-control" name="userName" >
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Password</label>
                                    <input type="text" class="form-control" name="passWord">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Họ và tên</label>
                                    <input type="text" name="fullName" class="form-control" >
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" name ="email" class="form-control">
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="text" name="phone" class="form-control">
                                    </div>
                                </div>
                                <div class="d-flex gap-2">
                                    <button type="submit" id="saveBtn" class="btn btn-success">Lưu</button>
                                    <button type="reset" id="cancelBtn" class="btn btn-secondary" >Hủy</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


</body>
</html>
