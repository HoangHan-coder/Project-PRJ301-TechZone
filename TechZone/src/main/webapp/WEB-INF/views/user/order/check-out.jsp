<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thanh toán - TechZone</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .container-checkout {
                width: 100%;
                margin: 0;
                padding: 16px;
            }
            .card + .card {
                margin-top: 16px;
            }
            .product-img {
                width: 80px;
                height: 80px;
                object-fit: contain;
            }
            .summary-row {
                display:flex;
                justify-content: space-between;
                padding: 6px 0;
            }
            .summary-total {
                font-size: 1.25rem;
                font-weight: 600;
                color: #dc3545;
            }
            .label-muted {
                color:#6c757d;
            }
            .sidebar-sticky {
                position: sticky;
                top: 180px;
            }
        </style>
    </head>
    <body>
        <div class="">
            <jsp:include page="/WEB-INF/views/includes/header.jsp"/>
        </div>

        <div class="container-checkout">   
            <div class="row g-3">
                <div class="col-12 col-lg-12">
                    <div class="row w-100" style="margin-left: 0px">
                        <ul class="nav justify-content-between align-items-center shadow-sm p-3 my-2 bg-body-tertiary rounded">
                            <li class="nav-item fs-5">
                                <a href="javascript:history.back()" class="text-black text-decoration-none"><i class="bi bi-chevron-left"></i>Trở lại</a>
                            </li>
                            <li class="nav-item">
                                <span class="fw-semibold">Thanh toán</span>
                            </li>
                            <li class="nav-item">
                                <span class="text-secondary">TechZone</span>
                            </li>
                        </ul>
                    </div>
                    <div class="row g-3">

                        <div class="">
                            <!-- Địa chỉ nhận hàng -->
                            <div class="card" style="margin-top: 100px;">
                                <div class="card-body">
                                    <h5 class="card-title mb-3">Địa chỉ nhận hàng</h5>
                                    <c:url value="/order" var="placeOrderUrl">
                                        <c:param name="action" value="check-out"/>
                                    </c:url>
                                    <form id="checkoutForm" method="post" action="${placeOrderUrl}">
                                        <div class="row g-3">
                                            <div class="col-md-6">
                                                <label class="form-label">Họ và tên</label>
                                                <input type="text" name="fullName" class="form-control" value="${sessionScope.account != null ? sessionScope.account.fullname : ''}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label class="form-label">Số điện thoại</label>
                                                <input type="tel" name="phone" class="form-control" value="${sessionScope.account == null ? '' : sessionScope.account.phone}" readonly>
                                            </div>
                                            <div class="col-12">
                                                <label class="form-label">Địa chỉ</label>
                                                <input type="text" name="shippingAddress" class="form-control" id="address" placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành" required>
                                                <small id="addressError" class="text-danger"></small>
                                            </div>
                                        </div>

                                        <!-- Hàng hóa -->
                                        <div class="card mt-3">
                                            <div class="card-header d-flex justify-content-between align-items-center">
                                                <span>TechZone Store</span>
                                                <span class="text-secondary"><i class="bi bi-exclamation-circle"></i></span>
                                            </div>
                                            <div class="card-body">
                                                <!-- Từ giỏ hàng -->
                                                <%--
                                                <c:if test="${not empty cartItems}">
                                                    <c:set var="subtotal" value="0"/>
                                                    <c:forEach var="item" items="${cartItems}">
                                                        <div class="row g-3 align-items-center py-2 border-bottom">
                                                            <div class="col-auto">
                                                                <img class="product-img border rounded" src="${pageContext.request.contextPath}${item.product.linkImg}" alt="${item.product.productName}">
                                                            </div>
                                                            <div class="col">
                                                                <p class="mb-1 fw-semibold">${item.product.productName}</p>
                                                                <small class="text-secondary">x${item.quantity}</small>
                                                            </div>
                                                            <div class="col-auto text-danger fw-semibold">
                                                                ${item.unitPrice * item.quantity}₫
                                                            </div>
                                                        </div>
                                                        <c:set var="subtotal" value="${subtotal + (item.unitPrice * item.quantity)}"/>
                                                    </c:forEach>
                                                </c:if>
                                                --%>
                                                <!-- Từ trang chi tiết sản phẩm -->
                                                <c:if test="${not empty product}">
                                                    <c:set var="subtotal" value="${product.productPrice * quantity}"/>
                                                    <div class="row g-3 align-items-center py-2 border-bottom">
                                                        <div class="col-auto">
                                                            <img class="product-img border rounded" src="${pageContext.request.contextPath}${product.linkImg}" alt="${product.productName}">
                                                        </div>
                                                        <div class="col">
                                                            <p class="mb-1 fw-semibold">${product.productName}</p>
                                                            <small class="text-secondary">x${quantity}</small>
                                                        </div>
                                                        <div class="col-auto text-danger fw-semibold">
                                                            <fmt:formatNumber value="${product.productPrice * quantity}" type="number" maxFractionDigits="0"/>₫
                                                        </div>
                                                    </div>
                                                    <!-- Truyền ẩn cho phía server nếu cần -->
                                                    <input type="hidden" name="productId" value="${product.productId}">
                                                    <input type="hidden" name="quantity" value="${quantity}">
                                                </c:if>
                                            </div>
                                        </div>

                                        <!-- Voucher -->
                                        <div class="card mt-3">
                                            <div class="card-body">
                                                <h6 class="mb-3">Voucher</h6>
                                                <div class="row g-3 align-items-end">
                                                    <div class="col-md-6">
                                                        <label class="form-label">Chọn voucher</label>
                                                        <select class="form-select" name="voucherId" id="selectVoucher">
                                                            <option value="0">-- Không dùng voucher --</option>
                                                            <c:if test="${not empty vouchers}">
                                                                <c:forEach var="v" items="${vouchers}">
                                                                    <option 
                                                                        value="${v.voucherId}"
                                                                        data-type="${v.discountType}" data-value="${v.discountValue}">

                                                                        <c:if test="${v.discountType == 'PERCENT'}">
                                                                            ${v.code} - giảm <fmt:formatNumber value="${v.discountValue}" maxFractionDigits="0"/>%
                                                                        </c:if>
                                                                        <c:if test="${v.discountType != 'PERCENT'}">
                                                                            ${v.code} - giảm <fmt:formatNumber value="${v.discountValue}" type="number" maxFractionDigits="0"/>₫
                                                                        </c:if>
                                                                    </option>
                                                                </c:forEach>
                                                            </c:if>
                                                        </select>

                                                    </div>
                                                    <div class="col-md-2">
                                                        <button class="btn btn-outline-primary" type="button" id="btnVoucher">Áp dụng</button>
                                                    </div>
                                                </div>
                                                <div class="mt-2 small text-muted">
                                                    Giảm giá hiện tại: <span class="text-success" id="discountValue">-<fmt:formatNumber value="${empty voucherDiscount ? 0 : voucherDiscount}" type="number" maxFractionDigits="0"/>₫</span>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Phương thức thanh toán -->
                                        <div class="card mt-3">
                                            <div class="card-body">
                                                <h6 class="mb-3">Phương thức thanh toán</h6>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="paymentMethod" id="pmCOD" value="COD" checked>
                                                    <label class="form-check-label" for="pmCOD">Thanh toán khi nhận hàng (COD)</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="paymentMethod" id="pmBANK" value="BANK">
                                                    <label class="form-check-label" for="pmBANK">Tài khoản ngân hàng</label>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Tổng kết đơn hàng -->
                                        <c:set var="shippingFee" value="${150000}"/>
                                        <c:set var="voucherDiscount" value="${empty voucherDiscount ? 0 : voucherDiscount}"/>
                                        <c:set var="orderTotal" value="${(empty subtotal ? 0 : subtotal) + shippingFee - voucherDiscount}"/>
                                        <div class="card mt-3">
                                            <div class="card-body">
                                                <h6 class="mb-3">Tổng kết</h6>
                                                <div class="summary-row">
                                                    <span class="label-muted">Tổng tiền hàng</span>
                                                    <p><span id="subtotalVal"><fmt:formatNumber value="${empty subtotal ? 0 : subtotal}" type="number" maxFractionDigits="0"/></span><span>₫</span></p>
                                                </div>
                                                <div class="summary-row">
                                                    <span class="label-muted">Phí vận chuyển</span>
                                                    <span><fmt:formatNumber value="${shippingFee}" type="number" maxFractionDigits="0"/>₫</span>
                                                </div>
                                                <div class="summary-row">
                                                    <span class="label-muted">Voucher TechZone</span>
                                                    <span class="text-success" id="voucherVal">-<fmt:formatNumber value="${voucherDiscount}" type="number" maxFractionDigits="0"/>₫</span>
                                                </div>
                                                <hr/>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <span>Thành tiền</span>
                                                    <span class="summary-total" id="summaryTotal"><fmt:formatNumber value="${orderTotal}" type="number" maxFractionDigits="0"/>₫</span>
                                                </div>
                                            </div>
                                        </div>
                                        <input type="hidden" name="accountId" value="${sessionScope.account.id}"/>       
                                        <div class="d-flex justify-content-end mt-3">
                                            <button type="submit" class="btn btn-primary px-4">Đặt hàng</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
        <script src="assets/js/order/order-script.js"></script>
        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
    </body>

</html>
