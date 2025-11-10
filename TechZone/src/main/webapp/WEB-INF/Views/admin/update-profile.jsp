<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../includes/navbar-admin.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Admin - Hồ sơ cá nhân</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="content">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <h3 class="mb-4">Hồ sơ cá nhân</h3>
                        <div class="card shadow-sm">
                            <div class="card-body">

                                <!-- Không tìm thấy account -->
                                <c:if test="${account == null}">
                                    <div>Không tìm thấy Account với id <%=request.getParameter("account.accountId")%></div>
                                </c:if>

                                <!-- Nếu có account -->
                                <c:if test="${account != null}">
                                    <form id="accountForm" action="${pageContext.request.contextPath}/admin/account?action=update" method="post">
                                        <input type="hidden" name="id" value="${account.accountId}">

                                        <!-- Username -->
                                        <div class="mb-3">
                                            <label class="form-label">Username</label>
                                            <input type="text" class="form-control" value="${account.userName}" readonly>
                                            <input type="hidden" name="userName" value="${account.userName}">
                                        </div>

                                        <!-- Full name -->
                                        <div class="mb-3">
                                            <label class="form-label">Họ và tên</label>
                                            <input type="text" name="fullName" class="form-control editable" value="${account.fullName}" readonly>
                                            <span class="text-danger" id="fullNameError"></span>
                                            <c:if test="${not empty fullNameError}">
                                                <div class="text-danger">${fullNameError}</div>
                                            </c:if>
                                        </div>

                                        <!-- Email -->
                                        <div class="mb-3">
                                            <label class="form-label">Email</label>
                                            <input type="email" name="email" class="form-control editable" value="${account.email}" readonly>
                                            <span class="text-danger" id="email-error"></span>
                                            <c:if test="${not empty emailError}">
                                                <div class="text-danger">${emailError}</div>
                                            </c:if>
                                        </div>

                                        <!-- Phone -->
                                        <div class="row">
                                            <div class="col-md-6 mb-3">
                                                <label class="form-label">Số điện thoại</label>
                                                <input type="text" name="phone" class="form-control editable" value="${account.phone}" readonly>
                                                <span class="text-danger" id="phone-Error"></span>
                                                <c:if test="${not empty phoneError}">
                                                    <div class="text-danger">${phoneError}</div>
                                                </c:if>
                                            </div>
                                        </div>

                                        <!-- Vai trò -->
                                        <p>Vai trò</p>
                                        <div class="form-check">
                                            <input class="form-check-input role-radio" type="radio" name="role" value="Customer"
                                                   ${account.roleName == 'Customer' ? 'checked' : ''} onclick="return false;">
                                            <label class="form-check-label">Customer</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input role-radio" type="radio" name="role" value="Admin"
                                                   ${account.roleName == 'Admin' ? 'checked' : ''} onclick="return false;">
                                            <label class="form-check-label">Admin</label>
                                        </div>

                                        <!-- Nút thao tác -->
                                        <div class="d-flex gap-2 mt-3">
                                            <button type="button" id="editBtn" class="btn btn-primary" onclick="enableEdit()">Chỉnh sửa</button>
                                            <button type="submit" id="saveBtn" class="btn btn-success d-none">Lưu</button>
                                            <button type="reset" id="cancelBtn" class="btn btn-secondary d-none" onclick="cancelEdit()">Hủy</button>
                                        </div>
                                    </form>
                                </c:if>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                                document.addEventListener("DOMContentLoaded", function () {
                                                    const fullName = document.querySelector("[name='fullName']");
                                                    const email = document.querySelector("[name='email']");
                                                    const phone = document.querySelector("[name='phone']");
                                                    const form = document.getElementById("accountForm");

                                                    // ---- Hàm hiển thị / xóa lỗi ----
                                                    function setError(id, message) {
                                                        document.getElementById(id).textContent = message;
                                                    }
                                                    function clearError(id) {
                                                        document.getElementById(id).textContent = "";
                                                    }

                                                    // ---- Kiểm tra Họ tên ----
                                                    fullName.addEventListener("input", function () {
                                                        if (fullName.value.trim() !== "")
                                                            clearError("fullNameError");
                                                    });
                                                    fullName.addEventListener("blur", function () {
                                                        if (fullName.value.trim() === "")
                                                            setError("fullNameError", "Họ và tên không được để trống");
                                                    });

                                                    // ---- Kiểm tra Email ----
                                                    email.addEventListener("input", function () {
                                                        if (email.value.trim() !== "" && email.value.includes("@"))
                                                            clearError("email-error");
                                                    });
                                                    email.addEventListener("blur", function () {
                                                        if (email.value.trim() === "" || !email.value.includes("@"))
                                                            setError("email-error", "Email không hợp lệ");
                                                    });

                                                    // ---- Kiểm tra SĐT ----
                                                    phone.addEventListener("input", function () {
                                                        const value = phone.value.trim();
                                                        if (value === "") {
                                                            setError("phone-Error", "SĐT không được để trống");
                                                        } else if (!/^[0-9]+$/.test(value)) {
                                                            setError("phone-Error", "SĐT chỉ được chứa chữ số");
                                                        } else {
                                                            clearError("phone-Error");
                                                        }
                                                    });

                                                    phone.addEventListener("blur", function () {
                                                        const value = phone.value.trim();
                                                        if (value === "") {
                                                            setError("phone-Error", "SĐT không được để trống");
                                                        } else if (!/^[0-9]+$/.test(value)) {
                                                            setError("phone-Error", "SĐT chỉ được chứa chữ số");
                                                        } else if (value.length < 10 || value.length > 11) {
                                                            setError("phone-Error", "SĐT phải có 10 hoặc 11 chữ số");
                                                        } else {
                                                            clearError("phone-Error");
                                                        }
                                                    });

                                                    // ---- Validate khi Submit ----
                                                    form.addEventListener("submit", function (e) {
                                                        let valid = true;

                                                        if (fullName.value.trim() === "") {
                                                            setError("fullNameError", "Họ và tên không được để trống");
                                                            valid = false;
                                                        }
                                                        if (email.value.trim() === "" || !email.value.includes("@")) {
                                                            setError("email-error", "Email không hợp lệ");
                                                            valid = false;
                                                        }
                                                        const value = phone.value.trim();
                                                        if (value === "") {
                                                            setError("phone-Error", "SĐT không được để trống");
                                                            valid = false;
                                                        } else if (!/^[0-9]+$/.test(value)) {
                                                            setError("phone-Error", "SĐT chỉ được chứa chữ số");
                                                            valid = false;
                                                        } else if (value.length < 10 || value.length > 11) {
                                                            setError("phone-Error", "SĐT phải có 10 hoặc 11 chữ số");
                                                            valid = false;
                                                        }

                                                        if (!valid)
                                                            e.preventDefault();
                                                    });
                                                });

                                                // ---- Chức năng Chỉnh sửa / Hủy ----
                                                function enableEdit(keepEdit = false) {
                                                    document.querySelectorAll(".editable").forEach(f => f.removeAttribute("readonly"));
                                                    document.querySelectorAll(".role-radio").forEach(r => r.onclick = false);

                                                    if (!keepEdit) {
                                                        document.getElementById("editBtn").classList.add("d-none");
                                                        document.getElementById("saveBtn").classList.remove("d-none");
                                                        document.getElementById("cancelBtn").classList.remove("d-none");
                                                    } else {
                                                        document.getElementById("editBtn").classList.add("d-none");
                                                        document.getElementById("saveBtn").classList.remove("d-none");
                                                }
                                                }

                                                function cancelEdit() {
                                                    document.querySelectorAll(".editable").forEach(f => f.setAttribute("readonly", true));
                                                    document.querySelectorAll(".role-radio").forEach(r => r.onclick = true);
                                                    document.getElementById("editBtn").classList.remove("d-none");
                                                    document.getElementById("saveBtn").classList.add("d-none");
                                                    document.getElementById("cancelBtn").classList.add("d-none");
                                                }

                                                // Giữ trạng thái "chỉnh sửa" nếu form bị lỗi khi reload
                                                window.onload = function () {
            <c:if test="${not empty fullNameError or not empty emailError or not empty phoneError}">
                                                    enableEdit(true);
            </c:if>
                                                }
        </script>
    </body>
</html>