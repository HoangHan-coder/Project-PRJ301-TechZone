<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../includes/slide-bar-admin.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Content -->
<div class="content">
    <form action="${pageContext.request.contextPath}/admin/account" method="POST" id="filterForm">
        <input type="hidden" name="view" value="list">
        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1>Người dùng</h1>
            <a class="text-decoration-none btn btn-primary" href="${pageContext.request.contextPath}/admin/account?view=create">Thêm người dùng mới
            </a>
        </div>

        <!-- Tìm kiếm + Lọc -->
        <div class="d-flex mb-3">
            <input type="text" id="keyword" name="keyword" class="form-control me-2"
                   placeholder="Tìm kiếm Tên tài khoản hoặc Họ và tên"
                   value="${keyword}" 
                   onkeyup="submitSearch()" />
            <select class="form-select" style="max-width:200px;" id="role" name="role"
                    onchange="document.getElementById('filterForm').submit()">
                <option value="">Lọc theo vai trò</option>
                <option value="Admin" <c:if test="${role=='Admin'}">selected</c:if>>Admin</option>
                <option value="Customer" <c:if test="${role=='Customer'}">selected</c:if>>Customer</option>
                </select>
            </div>

            <!-- Bảng -->
            <div id="results">
                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th>Tên tài khoản</th>
                                <th>Họ & Tên</th>
                                <th>Email</th>
                                <th>SDT</th>
                                <th>Vai trò</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="a" items="${accounts}">
                            <tr>
                                <td>${a.userName}</td>
                                <td>${a.fullName}</td>
                                <td>${a.email}</td>
                                <td>${a.phone}</td>
                                <td>${a.roleName}</td>
                                <td class="text-center">
                                    <a class="text-decoration-none" href="${pageContext.request.contextPath}/admin/account?view=update&id=${a.accountId}">
                                        <i class="bi bi-eye me-2" style="cursor: pointer;"></i>
                                    </a>
                                    <button type="button" class="btn btn-link p-0 m-0 text-danger"
                                            onclick="confirmDelete(${a.accountId})">
                                        <i class="bi bi-trash"></i>
                                    </button>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- PHÂN TRANG -->
        <nav>
            <ul class="pagination justify-content-center mt-4">

                <!-- Nút về trang đầu -->
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link"  href="${pageContext.request.contextPath}/admin/account?page=1&keyword=${keyword}&role=${role}">First</a>
                </li>

                <!-- Nút Previous -->
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link"href="${pageContext.request.contextPath}/admin/account?page=${currentPage - 1}&keyword=${keyword}&role=${role}" >Previous</a>
                </li>

                <!-- Nút Next -->
                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/admin/account?page=${currentPage + 1}&keyword=${keyword}&role=${role}"">Next</a>
                </li>

                <!-- Nút trang cuối -->
                <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                    <a class="page-link"  href="${pageContext.request.contextPath}/admin/account?page=${totalPages}&keyword=${keyword}&role=${role}">Last</a>
                </li>
            </ul>
        </nav>
    </form>
</div>

<script>
    function confirmDelete(id) {
        if (confirm("Bạn có chắc chắn muốn xóa Account với ID = " + id + " không?")) {
            const form = document.getElementById("filterForm");
            const inputAction = document.createElement("input");
            const inputId = document.createElement("input");

            inputAction.type = "hidden";
            inputAction.name = "action";
            inputAction.value = "delete";

            inputId.type = "hidden";
            inputId.name = "id";
            inputId.value = id;

            form.appendChild(inputAction);
            form.appendChild(inputId);

            form.submit();
        }
    }
    document.addEventListener("DOMContentLoaded", function () {

        document.getElementById("keyword").addEventListener("keypress", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                document.getElementById("filterForm").submit();
            }
        });


        document.getElementById("role").addEventListener("change", function () {
            document.getElementById("filterForm").submit();
        });
    });
</script>