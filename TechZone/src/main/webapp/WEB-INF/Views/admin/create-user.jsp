<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../includes/navbar-admin.jsp" %>
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
                            <form action="${pageContext.request.contextPath}/admin?action=create" method="post" id="accountForm"> 
                                <div class="mb-3">
                                    <label class="form-label">AccountId</label>
                                    <input type="text" id="accountId" class="form-control"
                                           name="id"
                                           value="${accountId != null ? accountId : nextId}"
                                           readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Username</label>
                                    <input type="text" class="form-control" name="userName" placeholder="Nhập tên đăng nhập"value="${userName}">
                                    <span class="text-danger" id="usernameError"></span>
                                    <c:if test="${not empty usernameError}">
                                        <div class="text-danger">${usernameError}</div>
                                    </c:if>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Password</label>
                                    <input type="text" class="form-control" name="passWordHarh" placeholder="Mật khẩu phải 8 kí tự">
                                    <span class="text-danger" id="passwordError"></span>
                                    <c:if test="${not empty passwordError}">
                                        <div class="text-danger">${passwordError}</div>
                                    </c:if>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Họ và tên</label>
                                    <input type="text" name="fullName" class="form-control" placeholder="Nhập họ và tên" value="${fullName}">
                                    <span class="text-danger" id="fullNameError"></span>
                                    <c:if test="${not empty fullNameError}">
                                        <div class="text-danger">${fullNameError}</div>
                                    </c:if>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" name ="email" class="form-control" placeholder="ví dụ: abc@gmail.com" value="${email}">
                                    <span class="text-danger" id="email-error"></span>
                                    <c:if test="${not empty emailError}">
                                        <div class="text-danger">${emailError}</div>
                                    </c:if>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="text" name="phone" class="form-control" placeholder="Chỉ nhập số" value="${phone}">
                                        <span class="text-danger" id="phone-Error"></span>
                                        <c:if test="${not empty phoneError}">
                                            <div class="text-danger">${phoneError}</div>
                                        </c:if>
                                    </div>
                                </div>
                                <p>Vai trò</p>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="role" id="radioDefault1" value="Admin">
                                    <label class="form-check-label" for="radioDefault1">Admin</label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="role" id="radioDefault2" value="Customer" checked>
                                    <label class="form-check-label" for="radioDefault2">Customer</label>
                                </div>
                                <br>
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
    <script>
        document.getElementById("accountForm").addEventListener("submit", function (e)){
        let valid = true;
        document.querySelectorAll(".text-danger").forEach(el => el.textContext = "");
        const username = document.querySelector("[name='userName']").value.trim();
        const password = document.querySelector("[name='passWordHarh']").value.trim();
        const email = document.querySelector("[name='email']").value.trim();
        const phone = document.querySelector("[name='phone']").value.trim();
        if (username === "") {
        document.getElementById("username-error").textContent = "Username không được để trống";
        valid = false;
        }
        if (password.length < 6) {
        document.getElementById("password-error").textContent = "Password phải >= 6 ký tự";
        valid = false;
        }
        if (email === "" || !email.includes("@")) {
        document.getElementById("email-error").textContent = "Email không hợp lệ";
        valid = false;
        }
        if (phone === "" || isNaN(phone)) {
        document.getElementById("phone-error").textContent = "SĐT phải là số";
        valid = false;
        }

        if (!valid) e.preventDefault();
        });
        }



    </script>


</body>
</html>
