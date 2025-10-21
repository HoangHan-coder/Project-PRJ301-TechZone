<%-- 
    Document   : voucher-management-admin
    Created on : Oct 20, 2025, 10:12:06 AM
    Author     : NgKaitou
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </head>
    <body>

        <div class="container-fluid">
            <div>
                <%@include file="../../includes/slide-bar-admin.jsp" %>
            </div>
            <div style="margin-left: 280px; margin-top: 100px">
                <h1><strong>Danh sách voucher</strong></h1>
                <c:if test="${not empty requestScope.success}">
                    <p class="text-success my-2">${requestScope.success}</p>
                </c:if>
                <c:if test="${not empty requestScope.removeError}">
                    <p class="text-danger my-2">${requestScope.removeError}</p>
                </c:if>
                <div class="float-end my-4 me-3"><a href="${pageContext.request.contextPath}/voucher?view=create" class="btn btn-primary">Thêm voucher mới</a></div>

                <table class="table table-bordered mt-5">
                    <thead>
                        <tr class="table-secondary">
                            <th scope="col">ID</th>
                            <th scope="col">Mã voucher</th>
                            <th scope="col">Giá trị giảm</th>
                            <th scope="col">Ngày bắt đầu</th>
                            <th scope="col">Ngày kết thúc</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Điều kiện</th>
                            <th scope="col">Đã dùng</th>
                            <th scope="col">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="voucher" items="${listVoucher}">
                            <tr>
                                <th scope="row">${voucher.voucherId}</th>
                                <td>${voucher.code}</td>
                                <td>${voucher.getDiscountValueToString()}</td>
                                <td>${voucher.startDate}</td>
                                <td>${voucher.endDate}</td>
                                <td>${voucher.status}</td>
                                <td>${voucher.getMinOrderValueToString()}</td>
                                <td>${voucher.currentUsage}/${voucher.maxUsage}</td>
                                <td class="text-center d-flex gap-3 justify-content-center">
                                    <a href="#" class="text-decoration-none">
                                        <i class="bi bi-eye me-2 text-dark" style="cursor: pointer;"></i>
                                    </a>
                                    <button style="border: none;background-color: #ffffff;"
                                            class="btn btn-danger btn-sm" 
                                            data-bs-toggle="modal" 
                                            data-bs-target="#deleteModal-${voucher.voucherId}">
                                        <i class="bi bi-trash text-danger" style="cursor: pointer;"></i>
                                    </button>


                                </td>
                            </tr>
                            <!-- Modal riêng -->
                        <div class="modal fade" id="deleteModal-${voucher.voucherId}" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header bg-warning-subtle text-dark">
                                        <h5 class="modal-title">
                                            <i class="bi bi-exclamation-triangle-fill me-2"></i> Confirm Deletion
                                        </h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure you want to delete voucher <strong>${voucher.code}</strong>?</p>
                                        <small class="text-muted">This action cannot be undone.</small>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                        <form action="${pageContext.request.contextPath}/voucher?action=remove" method="post">
                                            <input type="hidden" name="voucherId" value="${voucher.voucherId}">
                                            <button type="submit" class="btn btn-danger">Delete</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
        </div>
    </body>

</html>
