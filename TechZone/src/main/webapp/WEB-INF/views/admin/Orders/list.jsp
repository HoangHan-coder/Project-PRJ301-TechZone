<%-- 
    Document   : orders
    Created on : Oct 20, 2025, 7:29:33 PM
    Author     : letan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Bảng Đơn Hàng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                font-family: "Segoe UI", sans-serif;
                background-color: #f9fafb;
                padding: 40px;
            }

            .order-table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                border-radius: 10px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            }

            .order-table th {
                background-color: #d1d5db;
                color: #111827;
                padding: 14px;
                text-align: center;
                font-weight: 700;
                font-size: 16px;
            }

            .order-table td {
                padding: 14px;
                text-align: center;
                border-bottom: 1px solid #e5e7eb;
                color: #374151;
                font-size: 15px;
            }

            .order-table tr:hover {
                background-color: #f3f4f6;
                transition: 0.2s;
            }

            /* Cột hành động */
            .actions i {
                margin: 0 6px;
                cursor: pointer;
                font-size: 18px;
                transition: 0.2s;
            }
            .actions .fa-eye {
                color: #2563eb;
            }

            .actions .fa-trash {
                color: #dc2626;
            }

            .actions i:hover {
                transform: scale(1.1);
            }

            /* Trạng thái */
            .status {
                font-weight: 600;
                padding: 6px 12px;
                border-radius: 20px;
                display: inline-block;
            }

            .paid {
                background: #dcfce7;
                color: #166534;
            }
            .unpaid {
                background: #fee2e2;
                color: #991b1b;
            }
            .shipping {
                background: #fef9c3;
                color: #92400e;
            }
            .delivered {
                background: #dbeafe;
                color: #1e3a8a;
            }
            h2 {
                font-size: 24px;
                font-weight: 700;
                color: #111827;
                margin-bottom: 25px;
            }
            .btn-delete {
                background: none;
                border: none;
                color: red;
                cursor: pointer;
            }
            .btn-delete:hover {
                color: darkred;
            }
            .filter-box {
                display: flex;
                gap: 10px;
                align-items: center;
                background: #fff;
                padding: 15px 20px;
                border-radius: 12px;
                box-shadow: 0 3px 10px rgba(0,0,0,0.1);
                margin-bottom: 20px;
            }
            .filter-box input,
            .filter-box select {
                padding: 10px 12px;
                border: 1px solid #ddd;
                border-radius: 8px;
                outline: none;
                font-size: 14px;
                transition: border 0.3s;
            }
            .filter-box input:focus,
            .filter-box select:focus {
                border-color: #1677ff;
            }
            .filter-box button {
                background: #1677ff;
                color: white;
                border: none;
                padding: 10px 16px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 500;
                transition: background 0.3s;
            }
            .filter-box button:hover {
                background: #005be0;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                border-radius: 10px;
                overflow: hidden;
            }
            th, td {
                padding: 12px 15px;
                border-bottom: 1px solid #eee;
                text-align: left;
            }
            th {
                background: #f8f9fb;
                font-weight: 600;
            }
            tr:hover {
                background-color: #f1f7ff;
            }
            .status {
                padding: 4px 10px;
                border-radius: 8px;
                font-weight: 500;
            }
            .status.COMPLETED {
                background: #e6ffed;
                color: #2b9e47;
            }
            .status.PENDING {
                background: #fff4e5;
                color: #d48806;
            }
            .status.PROCESSING {
                background: #e6f7ff;
                color: #1677ff;
            }
            .status.CANCELED {
                background: #fff1f0;
                color: #cf1322;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <%@include file="../../includes/slide-bar-admin.jsp" %>
        </div>
        <div style="margin: 0 0 50px 280px;">
            <h2> <span style="color:#2563eb;">Danh sách sản phẩm</span></h2>
            <form action="${pageContext.request.contextPath}/admin/order" method="POST">
                <input type="hidden" name="fill" />
                <div class="filter-box">
                    <input type="text" name="orderCode" placeholder="Nhập mã đơn (VD: ORD0001)">
                    <select name="status" id="statusSelect">
                        <option value="">-- Tất cả trạng thái --</option>
                        <option value="COMPLETED">Hoàn thành</option>
                        <option value="PENDING">Đang giao hàng</option>
                        <option value="PROCESSING">Đang chờ xử lý</option>
                        <option value="CANCELED">Đã hủy</option>
                    </select>
                    <button >Lọc</button>
                </div>
            </form>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Mã Đơn</th>
                        <th>Khách hàng</th>
                        <th>Tổng tiền</th>
                        <th>Thanh toán</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${list.size() != 0}">
                        <c:forEach var="i" items="${list}">
                            <tr>
                                <td>${i.ordercode}</td>
                                <td>${i.fullname}</td>
                                <td><fmt:formatNumber value="${i.totalamount}" type="number" maxFractionDigits="0"/>đ</td>
                                <!-- Trạng thái thanh toán -->
                                <td>
                                    <c:choose>
                                        <c:when test="${i.payment == 'Paid'}">
                                            <span class="status paid">Đã thanh toán</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status unpaid">Chưa thanh toán</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- Trạng thái đơn hàng -->
                                <td>
                                    <c:choose>
                                        <c:when test="${i.status == 'PENDING'}">
                                            <span class="status pending">Đang giao hàng</span>
                                        </c:when>
                                        <c:when test="${i.status == 'PROCESSING'}">
                                            <span class="status processing">Đang chờ xử lý</span>
                                        </c:when>
                                        <c:when test="${i.status == 'COMPLETED'}">
                                            <span class="status completed">Đã giao</span>
                                        </c:when>
                                        <c:when test="${i.status == 'CANCELED'}">
                                            <span class="status cancel">Đã hủy</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status unknown">Không xác định</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <td class="actions">
                                    <div style="display: flex; justify-content: center">
                                        <a href="${pageContext.request.contextPath}/admin/order?view=detail&id=${i.orderid}">
                                            <i class="fa-solid fa-eye"></i>
                                        </a>
                                        <form method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=delete&id=${i.orderid}">
                                            <button type="submit" class="btn-delete">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${list.size() == 0}">
                        <tr>
                            <td colspan="7" style="text-align:center; color:#888; font-style:italic; padding:20px;">
                                <i class="fa-solid fa-circle-exclamation" style="color:#ff7b00;"></i>
                                Không tìm thấy phản hồi nào phù hợp
                            </td>
                        </tr>
                    </c:if>

                </tbody>

            </table>
            <div style="margin-top: 20px">
                <c:if test="${list != null}">
                    <%@include file="../../includes/pagination.jsp" %>
                </c:if>
            </div>

        </div>

    </body>
</html>

