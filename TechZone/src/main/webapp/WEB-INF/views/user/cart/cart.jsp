<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"> 
        <title>Giỏ Hàng</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    </head>
    <body data-context-path="${pageContext.request.contextPath}">
        <div>
            <jsp:include page="/WEB-INF/views/includes/header.jsp"/>
        </div>


        <div class="container-fluid my-4 px-5">
            <div class="row align-items-center bg-white py-3 border rounded shadow-sm tieu-de-bang-san-pham"> 
                <div class="col-6 d-flex align-items-center">
                    <%-- THÊM ID CHO JS: chon-tat-ca-header --%>
                    <input type="checkbox" id="chon-tat-ca-header" class="me-3">
                    <span class="fw-bold">Sản Phẩm</span>
                </div>
                <div class="col-6">
                    <div class="row text-center text-secondary">
                        <div class="col">Đơn Giá</div>
                        <div class="col">Số Lượng</div>
                        <div class="col">Số Tiền</div>
                        <div class="col">Thao Tác</div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Shop name section -->
        <div class="container-fluid px-5 py-2">
            <div class="d-flex align-items-center text-decoration-none">
                <i class="bi bi-shop me-2 text-primary"></i>
                <span class="fw-semibold">TechZone Store</span>
            </div>
        </div>

        <%-- THÊM ID CHO JS: cart-items-container --%>
        <div class="container-fluid px-5" id="cart-items-container">
            <c:if test="${empty cartItems}">
                <div class="bg-white border rounded shadow-sm py-5 mb-3 text-center text-muted">
                    <i class="bi bi-cart3 fs-1 d-block mb-2"></i>
                    <div class="fs-5">Chưa có sản phẩm trong giỏ hàng</div>
                </div>
            </c:if>
            <c:forEach var="cartItem" items="${cartItems}">  
                <%-- Bọc mỗi dòng sản phẩm trong một Form để gửi yêu cầu cập nhật lên Servlet --%>
                <form action="${pageContext.request.contextPath}/cartitem" method="POST" class="form-cart-item">
                  
                    <div class="row align-items-center bg-white border rounded shadow-sm py-3 mb-3 product-row">

                        <div class="col-6 d-flex align-items-center">
                            <%-- THÊM CLASS VÀ DATA CHO JS --%>
                            <input type="checkbox" class="me-3 cart-item-checkbox" 
                                   data-price="${cartItem.unitPrice}"
                                   data-item-id="${cartItem.cartItemId}">
                            <img src="${cartItem.product.linkImg}" alt="${cartItem.product.productName}"
                                 class="img-fluid me-3 rounded" style="width: 80px; height: 80px; object-fit: cover;">

                            <div>
                                <h6 class="mb-1">${cartItem.product.productName}</h6>
                                <small class="text-secondary">Phân loại: Tên phân loại</small>
                            </div>
                        </div>

                        <div class="col-6">
                            <div class="row text-center align-items-center">

                                <div class="col">${cartItem.unitPrice}₫</div>

                                <div class="col">
                                    <div class="input-group input-group-sm justify-content-center">
                                        <%-- THÊM CLASS cho JS --%>
                                        <button class="btn btn-outline-secondary btn-quantity-minus" type="button">-</button>
                                        <%-- THÊM CLASS, NAME, VÀ DATA cho JS/Servlet --%>
                                        <input type="text" name="quantity" class="form-control text-center input-quantity" 
                                               value="${cartItem.quantity}" 
                                               data-unit-price="${cartItem.unitPrice}">
                                        <%-- THÊM CLASS cho JS --%>
                                        <button class="btn btn-outline-secondary btn-quantity-plus" type="button">+</button>

                                        <%-- INPUT ẨN DÙNG CHO FORM SUBMIT LÊN CARTITEM SERVLET --%>
                                        <input type="hidden" name="action" value="update">
                                        <input type="hidden" name="cartItemId" value="${cartItem.cartItemId}">
                                        <input type="hidden" name="unitPrice" value="${cartItem.unitPrice}">
                                    </div>
                                </div>

                                <%-- THÊM CLASS CHO JS: item-total-price --%>
                                <div class="col fw-bold item-total-price">${cartItem.unitPrice * cartItem.quantity}₫</div>

                                <div class="col">
                                    <%-- Nút xóa từng sản phẩm --%>
                                    <button type="button" class="btn btn-link text-danger text-decoration-none btn-delete-item" data-cart-item-id="${cartItem.cartItemId}">Xóa</button>
                                </div>
                           

                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>

        <div class="cart-container container-fluid px-5">
            

            <form id="checkout-form" method="get" action="${pageContext.request.contextPath}/order">
            <div class="d-flex justify-content-between align-items-center p-3 thanh-thanh-toan"> 
                <div class="d-flex align-items-center">
                    <div class="form-check me-3">
                        <%-- THÊM ID CHO JS: chon-tat-ca-footer --%>
                        <input class="form-check-input" type="checkbox" id="chon-tat-ca-footer"> 
                        <label class="form-check-lable" for="chon-tat-ca-footer"> 
                            Chọn Tất Cả
                        </label>
                    </div>
                    <a href="#" id="btn-delete-selected" class="text-muted text-decoration-none me-3" onclick="return false;">Xóa</a>

                </div>
                <div class="d-flex align-items-center">
                    <span class="me-3">
                        Tổng cộng (<span id="tong-san-pham">0</span> Sản phẩm): 
                        <%-- THÊM ID CHO JS --%>
                        <span class="text-danger fw-bold fs-5" id="tong-tien">0<sup>đ</sup> 
                        </span>
                    </span>
                    <%-- THÊM ID CHO JS: btn-mua-hang --%>
                    <div id="selected-items-inputs"></div>
                    <button type="button" class="btn nut-nen-chinh fw-bold" id="btn-mua-hang" disabled>
                        Mua Hàng
                    </button>
                </div>
            </div>
            </form>
        </div>
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/cart/cart-script.js"></script>
        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
    </body>
</html>