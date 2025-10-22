<%-- 
    Document   : detail
    Created on : Oct 20, 2025, 7:42:38 PM
    Author     : letan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết đơn hàng</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                font-family: "Segoe UI", sans-serif;
                background-color: #f8fafc;
                color: #1e293b;
                margin: 0;
                padding: 40px;
            }

            h2 {
                font-size: 24px;
                font-weight: 700;
                color: #111827;
                margin-bottom: 25px;
            }

            .card {
                background: white;
                border-radius: 10px;
                box-shadow: 0 3px 6px rgba(0,0,0,0.05);
                padding: 20px;
                margin-bottom: 25px;
                border: 1px solid #e5e7eb;
            }

            .card h3 {
                font-size: 18px;
                font-weight: 600;
                color: #1f2937;
                margin-bottom: 15px;
            }

            .info-row {
                margin-bottom: 8px;
            }

            .info-row b {
                display: inline-block;
                width: 180px;
                color: #374151;
            }

            .product-table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                margin-top: 15px;
            }

            .product-table th {
                background-color: #e2e8f0;
                text-align: center;
                padding: 10px;
                font-weight: 600;
                border-bottom: 2px solid #cbd5e1;
            }

            .product-table td {
                text-align: center;
                padding: 10px;
                border-bottom: 1px solid #e2e8f0;
            }

            .product-table img {
                width: 60px;
                height: 60px;
                object-fit: cover;
                border-radius: 6px;
                border: 1px solid #e5e7eb;
            }

            .total {
                text-align: right;
                font-weight: bold;
                color: #b91c1c;
                margin-top: 15px;
                font-size: 18px;
            }

            .actions {
                display: flex;
                justify-content: center;
                gap: 10px;
                margin-top: 25px;
            }

            .btn {
                padding: 10px 18px;
                border: none;
                border-radius: 8px;
                font-weight: 600;
                cursor: pointer;
                font-size: 15px;
                transition: 0.2s;
                color: white;
            }

            .btn-confirm {
                background-color: #16a34a;
            }

            .btn-ship {
                background-color: #0284c7;
            }

            .btn-cancel {
                background-color: #dc2626;
            }

            .btn:hover {
                opacity: 0.9;
                transform: scale(1.03);
            }

        </style>
    </head>
    <body>

        <h2>Chi tiết đơn hàng <span style="color:#2563eb;">#${order.orderCode}</span></h2>

        <div class="card">
            <h3>Thông tin đơn hàng</h3>
            <div class="info-row"><b>Ngày đặt:</b> ${order.orderTime}</div>
            <div class="info-row">
                <b>Trạng thái:</b> 
                <c:choose>
                    <c:when test="${order.status == 'PROCESSING'}">
                        Đang xử lý
                    </c:when>
                    <c:when test="${order.status == 'PENDING'}">
                        Đang chờ xử lý
                    </c:when>
                    <c:when test="${order.status == 'COMPLETED'}">
                        Đã giao
                    </c:when>
                    <c:when test="${order.status == 'CANCEL'}">
                        Đã hủy
                    </c:when>
                    <c:otherwise>
                        Không xác định
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="info-row"><b>Phương thức thanh toán:</b> ${order.paymentMethod}</div>
        </div>

        <div class="card">
            <h3>Thông tin khách hàng</h3>
            <div class="info-row"><b>Tên:</b> ${account.fullName}</div>
            <div class="info-row"><b>Số điện thoại:</b> ${account.phone}</div>
            <div class="info-row"><b>Địa chỉ giao hàng:</b> ${order.shippingAddress}</div>
        </div>

        <div class="card">
            <h3>Danh sách sản phẩm</h3>
            <table class="product-table">
                <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Giá</th>
                        <th>Tổng</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td><img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" style="width:100px; height:auto;"></td>
                            <td>${p.getProductName()}</td>
                            <td>${p.quantity}</td>
                            <td>${p.productPrice}₫</td>
                            <td>${p.getTotal()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="total">Tổng cộng: ${totalamount}₫</div>
        </div>


        <div class="actions">
            <form method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=processing&id=${order.orderId}">
                <button class="btn btn-confirm"><i class="fa-solid fa-check"></i> Xác nhận đơn</button>
            </form>
            <form method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=pending&id=${order.orderId}">
                <button class="btn btn-ship"><i class="fa-solid fa-truck"></i> Giao hàng</button>
            </form>
            <form method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=cancel&id=${order.orderId}">
                <button class="btn btn-cancel"><i class="fa-solid fa-times"></i> Hủy đơn</button>
            </form>
        </div>

    </body>
</html>

