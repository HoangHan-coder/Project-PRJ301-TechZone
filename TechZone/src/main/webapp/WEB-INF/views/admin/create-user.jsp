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
                                    <input type="text" class="form-control" name="userName" placeholder="Nhập tên đăng nhập" value="${userName}">
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
        document.addEventListener("DOMContentLoaded", function () {
            const username = document.querySelector("[name='userName']");
            const password = document.querySelector("[name='passWordHarh']");
            const fullName = document.querySelector("[name='fullName']");
            const email = document.querySelector("[name='email']");
            const phone = document.querySelector("[name='phone']");
            const form = document.getElementById("accountForm");

            function clearError(id) {
                document.getElementById(id).textContent = "";
            }
            function setError(id, message) {
                document.getElementById(id).textContent = message;
            }


            username.addEventListener("input", function () {
                if (username.value.trim() !== "")
                    clearError("usernameError");
            });
            username.addEventListener("blur", function () {
                if (username.value.trim() === "")
                    setError("usernameError", "Username không được để trống");
            });


            password.addEventListener("input", function () {
                if (password.value.length >= 8)
                    clearError("passwordError");
            });
            password.addEventListener("blur", function () {
                if (password.value.length < 8)
                    setError("passwordError", "Password phải >= 8 ký tự");
            });


            fullName.addEventListener("input", function () {
                if (fullName.value.trim() !== "")
                    clearError("fullNameError");
            });
            fullName.addEventListener("blur", function () {
                if (fullName.value.trim() === "")
                    setError("fullNameError", "Họ và tên không được để trống");
            });


            email.addEventListener("input", function () {
                if (email.value.trim() !== "" && email.value.includes("@"))
                    clearError("email-error");
            });
            email.addEventListener("blur", function () {
                if (email.value.trim() === "" || !email.value.includes("@"))
                    setError("email-error", "Email không hợp lệ");
            });


            phone.addEventListener("input", function () {
                if (phone.value.trim() !== "" && !isNaN(phone.value))
                    clearError("phone-Error");
                else
                    setError("phone-Error", "SĐT không được để trống");
            });
            phone.addEventListener("blur", function () {
                if (phone.value.trim() === "" || isNaN(phone.value))
                    setError("phone-Error", "SĐT phải là số");
            });


            form.addEventListener("submit", function (e) {
                let valid = true;
                if (username.value.trim() === "") {
                    setError("usernameError", "Username không được để trống");
                    valid = false;
                }
                if (password.value.length < 8) {
                    setError("passwordError", "Password phải >= 8 ký tự");
                    valid = false;
                }
                if (fullName.value.trim() === "") {
                    setError("fullNameError", "Họ và tên không được để trống");
                    valid = false;
                }
                if (email.value.trim() === "" || !email.value.includes("@")) {
                    setError("email-error", "Email không hợp lệ");
                    valid = false;
                }
                if (phone.value.trim() === "" || isNaN(phone.value)) {
                    setError("phone-Error", "SĐT phải là số");
                    valid = false;
                }

                if (!valid)
                    e.preventDefault();
            });
        });
    </script>


</body>
</html>
