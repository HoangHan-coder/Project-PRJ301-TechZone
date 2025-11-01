<%-- 
    Document   : order-detail
    Created on : Sep 20, 2025, 8:34:46 PM
    Author     : NgKaitou
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <title>Tech Zone</title>
    </head>

    <body>
        <div>
            <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
        </div>
        
        
        <div class="container">
            <div class="row">
                <ul class="nav justify-content-between align-items-center shadow-sm p-3 my-3 bg-body-tertiary rounded">
                    <li class="nav-item fs-4 text">
                        <p class="m-0 opacity-75" style="cursor: pointer;"><a name="action" href="http://localhost:8080/TechZone/order?action=order-list" class="text-black" style="text-decoration: none;"><i class="bi bi-chevron-left"></i>Trở lại</a></p>
                    </li>
                    <div class="d-flex justify-content-end gap-4" >
                        <li class="nav-item">
                            <p class="m-0">MÃ ĐƠN HÀNG: ${orderItem.order.orderCode}</p>
                        </li>
                        <li class="nav-item">
                            <p class="m-0">|</p>
                        </li>
                        <li class="nav-item">
                            <p class="m-0">${orderItem.order.status}</p>
                        </li>
                    </div>
                </ul>
            </div>
            <div class="row">
                <div class="card">
                    <div class="card-body">

                        <h5 class="card-title">Địa chỉ nhận hàng</h5>
                        <p class="card-text">Họ tên: ${orderItem.order.account.fullName}</p>
                        <p class="card-text">Số điện thoại: ${orderItem.order.account.phone}</p>
                        <p class="card-text">Địa chỉ: ${orderItem.order.shippingAddress}</p>
                    </div>
                </div>
                <div class="card my-3">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <p class="m-0">TechZone</p>
                        <p class="m-0"><i class="bi bi-exclamation-circle"></i></p>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <div class="row g-3 align-items-center">
                                <div class="col-auto d-flex align-items-center justify-content-center">
                                    <img src="<c:url value="${orderItem.product.linkImg}"></c:url>" class="img-fluid rounded-start border" alt="..." style="width: 120px; height: 120px; object-fit: contain;">
                                </div>
                                <div class="col">
                                    <div class="card-body p-0">
                                        <p class="card-title fs-5 fw-semibold mb-1 text-truncate">${orderItem.productNameSnapshot}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <small class="text-secondary">x${orderItem.quantity}</small>
                                            <span class="text-danger fw-semibold"><fmt:formatNumber value="${orderItem.unitPrice}" type="number" maxFractionDigits="0"/>₫</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td>Tổng tiền hàng</td>
                                <td><fmt:formatNumber value="${orderItem.totalPrice}" type="number" maxFractionDigits="0"/>₫</td>
                            </tr>
                            <tr>
                                <td>Phí vận chuyển</td>
                                <td><fmt:formatNumber value="${orderItem.order.shippingFee}" type="number" maxFractionDigits="0"/>₫</td>
                            </tr>
                            <tr>
                                <td>Voucher từ TechZone</td>
                                <c:if test="${orderItem.order.voucher.discountValue == null}">
                                    <td>Không có</td>
                                </c:if>
                                <c:if test="${orderItem.order.voucher.discountType == 'PERCENT'}">
                                    <td>${orderItem.order.voucher.discountValue}</td>
                                </c:if>
                                <c:if test="${orderItem.order.voucher.discountType != 'PERCENT'}">
                                    <td><fmt:formatNumber value="${orderItem.order.voucher.discountValue}" type="number" maxFractionDigits="0"/></td>
                                </c:if>
                                
                                
                            </tr>
                            <tr>
                                <td>Thành tiền</td>
                                <td class="fs-4 text text-danger"><fmt:formatNumber value="${orderItem.order.totalAmount}" type="number" maxFractionDigits="0"/>₫</td>
                            </tr>
                            <tr>
                                <td>Phương thức Thanh toán</td>
                                <td>TK Ngân hàng</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
         <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
    </body>

</html>