<%-- 
    Document   : export-excel
    Created on : Nov 10, 2025, 11:01:56 AM
    Author     : letan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Báo cáo doanh thu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light p-4">

        <div>
            <div>
                <%@include file="../includes/slide-bar-admin.jsp" %>
            </div>
            <div class="container" style="margin-left:220px !important; width: 1288px">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h3 class="fw-bold text-primary mb-0">
                        <i class="bi bi-bar-chart-fill me-2"></i> Báo cáo doanh thu
                    </h3>

                    <form action="${pageContext.request.contextPath}/admin/export" method="POST">
                        <button type="submit" class="btn btn-success d-flex align-items-center">
                            <i class="bi bi-file-earmark-excel-fill me-2"></i>
                            Xuất file Excel
                        </button>
                    </form>
                </div>


                <div class="card shadow-sm">
                    <div class="card-body">
                        <table class="table table-hover table-bordered mb-0">
                            <thead class="table-secondary text-center">
                                <tr>
                                    <th>Sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Lượt bán</th>
                                    <th>Doanh thu (k)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="i" items="${listall}">
                                    <tr>
                                        <td>${i.name}</td>
                                        <td class="text-center">${i.sales}</td>
                                        <td class="text-center">${i.sumquantity}</td>
                                        <td class="text-end">
                                            <fmt:formatNumber value="${i.allprice}" type="number" maxFractionDigits="0"/>k
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap icon + JS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

