<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../Includes/navbar-admin.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Admin - Hồ sơ cá nhân</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">


</head>
<body>

    <!-- Content -->
    <div class="content">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-8">
                    <h3 class="mb-4">Hồ sơ cá nhân</h3>
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <c:if test="${account == null}"> 
                                <div> khong tim thay Account voi id <%=request.getParameter("id")%></div>
                            </c:if>
                            <c:if test ="${account != null}">
                                <form action="${pageContext.request.contextPath}/admin?action=update" method="post">
                                    <div class="mb-3">
                                        <label class="form-label">Username</label>
                                        <input type="text" class="form-control" value="${account.userName}"  readonly>
                                        <input type="hidden" name="id" value="${account.accountId}">
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Họ và tên</label>
                                        <input type="text" name="fullName" class="form-control editable" value="${account.fullName}" readonly>
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Email</label>
                                        <input type="email" name ="email" class="form-control editable" value="${account.email}" readonly>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6 mb-3">
                                            <label class="form-label">Số điện thoại</label>
                                            <input type="text" name="phone" class="form-control editable" value="${account.phone}" readonly>
                                        </div>
                                    </div>
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
                                    <div class="d-flex gap-2">
                                        <button type="button" id="editBtn" class="btn btn-primary" onclick="enableEdit()">Chỉnh sửa thông tin</button>
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
                                            function enableEdit() {
                                                let fields = document.getElementsByClassName("editable");
                                                for (let i = 0; i < fields.length; i++) {
                                                    fields[i].removeAttribute("readonly");
                                                }

                                                document.querySelectorAll('.role-radio').forEach(r => r.onclick = false);
                                                document.getElementById("editBtn").classList.add("d-none");
                                                document.getElementById("saveBtn").classList.remove("d-none");
                                                document.getElementById("cancelBtn").classList.remove("d-none");
                                            }

                                            function cancelEdit() {
                                                let fields = document.getElementsByClassName("editable");
                                                for (let i = 0; i < fields.length; i++) {
                                                    fields[i].setAttribute("readonly", true);
                                                }
                                                document.querySelectorAll('.role-radio').forEach(r => r.onclick = true);
                                                document.getElementById("editBtn").classList.remove("d-none");
                                                document.getElementById("saveBtn").classList.add("d-none");
                                                document.getElementById("cancelBtn").classList.add("d-none");
                                            }

    </script>

</body>
</html>
