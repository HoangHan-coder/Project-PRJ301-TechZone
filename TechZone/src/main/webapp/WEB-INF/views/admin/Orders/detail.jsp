<%-- 
    Document   : detail
    Created on : Oct 20, 2025, 7:42:38 PM
    Author     : letan
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            .modal-overlay {
                display: none; /* Ẩn ban đầu */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            /* Hộp nội dung modal */
            .modal-content {
                background: #fff;
                background-color: white !important;
                border-radius: 12px;
                padding: 25px 30px;
                width: 90%;
                max-width: 420px;
                box-shadow: 0 10px 25px rgba(0,0,0,0.2);
                animation: fadeInUp 0.3s ease;
            }

            /* Tiêu đề */
            .modal-content h3 {
                margin-bottom: 15px;
                color: #333;
                font-weight: 600;
                text-align: center;
            }

            /* Ô nhập */
            .modal-content textarea {
                width: 100%;
                height: 100px;
                padding: 10px 12px;
                border: 1px solid #ccc;
                border-radius: 8px;
                resize: none;
                font-size: 15px;
                outline: none;
                transition: border 0.2s;
            }

            .modal-content textarea:focus {
                border-color: #ff4d4f;
            }

            /* Nút trong modal */
            .modal-buttons {
                display: flex;
                justify-content: space-between;
                margin-top: 18px;
            }

            .btn-cancel, .btn-confirm {
                border: none;
                border-radius: 8px;
                padding: 10px 20px;
                font-size: 15px;
                cursor: pointer;
                transition: background 0.3s;
            }

            .btn-cancel {
                background: #ccc;
                color: #333;
            }

            .btn-cancel:hover {
                background: #b3b3b3;
            }

            .btn-confirm {
                background: #ff4d4f;
                color: white;
            }

            .btn-confirm:hover {
                background: #e63946;
            }

            /* Hiệu ứng hiện modal */
            @keyframes fadeInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
            .summary-box {
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                padding: 25px;
                font-family: "Segoe UI", sans-serif;
            }

            .summary-box h3 {
                text-align: center;
                font-size: 22px;
                margin-bottom: 20px;
                color: #333;
                border-bottom: 2px solid #f0f0f0;
                padding-bottom: 10px;
            }

            .summary-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 16px;
            }

            .summary-table td {
                padding: 10px 0;
            }

            .summary-table td:first-child {
                color: #555;
            }

            .summary-table .value {
                text-align: right;
                font-weight: 500;
            }

            .summary-table .discount {
                color: #d9534f;
            }

            .summary-table .total-row {
                font-weight: bold;
                font-size: 18px;
                border-top: 2px solid #ddd;
            }

            .summary-table .total-row .total {
                color: #28a745;
            }

            .checkout-actions {
                margin-top: 25px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .back-btn {
                text-decoration: none;
                color: #555;
                font-size: 15px;
                transition: color 0.3s;
            }

            .back-btn:hover {
                color: #007bff;
            }

            .pay-btn {
                background: #007bff;
                color: white;
                border: none;
                padding: 10px 25px;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
                transition: background 0.3s;
            }

            .pay-btn:hover {
                background: #0056b3;
            }

        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div>
                <%@include file="../../includes/slide-bar-admin.jsp" %>

            </div>
            <div style="margin: 0 0 50px 280px;">
                <h2>Chi tiết đơn hàng <span style="color:#2563eb;">#${order.orderCode}</span></h2>

                <div class="card">
                    <h3>Thông tin đơn hàng</h3>
                    <div class="info-row"><b>Ngày đặt:</b> ${order.orderTime}</div>
                    <div class="info-row">
                        <b>Trạng thái:</b> 
                        <c:choose>
                            <c:when test="${order.status == 'PROCESSING'}">
                                Đang chờ xử lý
                            </c:when>
                            <c:when test="${order.status == 'PENDING'}">
                                Đang giao hàng
                            </c:when>
                            <c:when test="${order.status == 'COMPLETED'}">
                                Đã giao
                            </c:when>
                            <c:when test="${order.status == 'CANCELED'}">
                                Đã hủy
                            </c:when>
                            <c:otherwise>
                                Không xác định
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="info-row"><b>Phương thức thanh toán:</b> ${order.paymentMethod}</div>   
                    <c:if test="${status != null}">
                        <div class="info-row"><b>Lí do:</b>${status}</div>
                    </c:if>

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
                            <c:set var="i" value="0" scope="page"></c:set>
                            <c:forEach var="p" items="${products}" >
                                <tr>
                                    <td><img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" style="width:100px; height:auto; border-radius:8px;"></td>
                                    <td>${p.productName}</td>
                                    <td>${p.quantity}</td>
                                    <td style="text-align: end"><fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/>đ</td>
                                    <td style="text-align: end"><fmt:formatNumber value="${p.total}" type="number" maxFractionDigits="0"/>đ</td>
                                    <c:set var="i" value="${p.total + i}" scope="page"></c:set>
                                    </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                </div>
                <div class="summary-box card">
                    <h3>Thông tin thanh toán</h3>
                    <table class="summary-table">
                        <tr>
                            <td>Phí vận chuyển:</td>
                            <td class="value">${String.format("%,.0f", 150000.0)}₫</td>
                        </tr>
                        <tr>
                            <td>Voucher giảm giá:</td>
                            <td class="value discount">

                                <c:choose>
                                    <c:when test="${order.voucherId != null}">
                                        -<fmt:formatNumber value="${i + 150000 - totalamount}" type="number" maxFractionDigits="0"/>đ
                                    </c:when>
                                    <c:otherwise>
                                        -0₫
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr class="total-row">
                            <td>Tổng thanh toán:</td>
                            <td style="text-align: end"><fmt:formatNumber value="${totalamount}" type="number" maxFractionDigits="0"/>đ</td>
                        </tr>
                    </table>
                    <div class="actions">
                        <form style="cursor: pointer" method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=pending&id=${order.orderId}">
                            <button style="cursor: pointer" class="btn btn-success"
                                    ${order.status == 'CANCELED' ? 'disabled' : ''}>
                                <i class="fa-solid fa-check"></i> Xác nhận đơn
                            </button>
                        </form>
                        <form style="cursor: pointer" method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=completed&id=${order.orderId}">
                            <button   class="btn btn-info"  ${order.status == 'CANCELED' ? 'disabled' : ''}><i class="fa-solid fa-truck"></i> Giao hàng</button>
                        </form>
                        <button  type="button" class="btn btn-danger" id="cancelBtn"  ${order.status == 'CANCELED' ? 'disabled' : ''}>
                            <i class="fa-solid fa-times"></i> Hủy đơn
                        </button>
                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div id="cancelModal" class="modal-overlay">
                <div class="modal-content">
                    <h3>Lý do hủy đơn</h3>
                    <form method="POST" action="${pageContext.request.contextPath}/admin/order?view=update&type=canceled&id=${order.orderId}" id="cancelForm">
                        <textarea name="cancelReason" placeholder="Nhập lý do hủy đơn..." required></textarea>
                        <div class="modal-buttons">
                            <button type="button" class="btn-cancel" id="closeModal">Đóng</button>
                            <button type="submit" class="btn-confirm">Xác nhận hủy</button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Bảng tổng kết -->

        </div>


    </body>
    <script>
        const modal = document.getElementById('cancelModal');
        const openBtn = document.getElementById('cancelBtn');
        const closeBtn = document.getElementById('closeModal');

        // Mở modal khi ấn "Hủy đơn"
        openBtn.addEventListener('click', () => {
            modal.style.display = 'flex';
        });

        // Đóng modal khi ấn "Đóng"
        closeBtn.addEventListener('click', () => {
            modal.style.display = 'none';
        });

        // Đóng modal khi click ra ngoài
        window.addEventListener('click', (e) => {
            if (e.target === modal) {
                modal.style.display = 'none';
            }
        });
    </script>
</html>

