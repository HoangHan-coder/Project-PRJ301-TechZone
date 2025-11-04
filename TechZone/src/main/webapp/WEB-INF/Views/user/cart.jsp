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

        <style>
            /* Màu chủ đạo: Tím (#5c3cc4) theo kiểu Gizmos */
            :root {
                --color-primary: #0044cc;
                --color-primary-dark: #4a30a1;
                --color-secondary: #0044cc; /* Giữ lại màu xanh shopee cho các chi tiết nhỏ nếu cần */
            }

            .mau-chu-chinh {
                color: var(--color-primary);
            }
            .nut-nen-chinh {
                background-color: var(--color-primary);
                color: white;
                border-color: var(--color-primary);
            }
            .nut-nen-chinh:hover {
                background-color: var(--color-primary-dark);
                border-color: var(--color-primary-dark);
                color: white;
            }
            .o-tim-kiem-placeholder::placeholder {
                font-size: 14px;
            }

            /* Cải thiện giao diện bảng sản phẩm */
            .tieu-de-bang-san-pham {
                background-color: #f8f9fa !important; /* Màu xám nhạt cho tiêu đề */
                border-bottom: 2px solid #e9ecef !important;
                font-size: 14px;
                color: #6c757d;
            }
            .product-row {
                border-radius: 0.25rem !important; /* Bo góc nhẹ */
                border: 1px solid #dee2e6 !important;
                margin-bottom: 15px !important;
            }
            .product-row:hover {
                box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075) !important;
            }
            .item-total-price {
                color: var(--color-primary) !important; /* Tổng tiền dùng màu chủ đạo */
                font-weight: bold;
            }

            /* Kiểu dáng cho phần quản lý số lượng */
            .input-group-sm > .form-control, .input-group-sm > .btn {
                padding: 0.25rem 0.5rem;
                font-size: 0.875rem;
                border-radius: 0.2rem;
            }
            .input-quantity {
                max-width: 45px !important; /* Thu nhỏ ô số lượng */
                text-align: center;
                border-left: 0;
                border-right: 0;
            }
            .btn-quantity-minus, .btn-quantity-plus {
                color: #6c757d;
                border-color: #ced4da;
            }

            /* Thanh toán cuối trang */
            .thanh-thanh-toan {
                background-color: #fff;
                border-top: 1px solid #dee2e6;
                position: sticky;
                bottom: 0;
                z-index: 1000;
                box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.05);
            }
            .dong-voucher-shopee, .dong-coin-shopee {
                border-bottom: 1px solid #f8f9fa;
                font-size: 14px;
            }
            .dong-coin-shopee > div > span:last-child {
                color: var(--color-primary); /* Màu tím cho số tiền Coin/Voucher */
                font-weight: bold;
            }
            .text-danger.fw-bold.fs-5 {
                color: var(--color-primary) !important; /* Màu tím cho Tổng cộng */
            }

            /* Nút Mua Hàng */
            #btn-mua-hang {
                background-color: var(--color-primary) !important;
                padding: 0.5rem 2.5rem; /* Tăng kích thước nút */
                font-size: 1.1rem;
            }
        </style>
    </head>
    <body>
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

        <div class="container-fluid d-flex align-items-center px-5 py-3">
            <div class="d-flex align-items-center text-decoration-none">
                <span class="fs-5 fw-bold mau-chu-chinh me-2"><i class="bi bi-heart-fill"></i></span>
                <span class="fs-5 text-dark">| TechZone Flagship Store </span>
            </div>
        </div>

        <%-- THÊM ID CHO JS: cart-items-container --%>
        <div class="container-fluid px-5" id="cart-items-container">
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
                                    <%-- THÊM CLASS VÀ DATA CHO JS/Servlet --%>
                                    
                                </div>
                           

                            </div>
                        </div>
                    </div>
                </form>
                                    <a href="${pageContext.request.contextPath}/cartitem?action=delete&cartItemId=${cartItem.cartItemId}" class="text-danger text-decoration-none btn-delete-item" 
                                      >Xóa</a>
            </c:forEach>
        </div>

        <div class="cart-container container-fluid px-5">
            <div class="bg-white rounded shadow-sm my-3 p-3">
                <div class="d-flex justify-content-between align-items-center py-2 dong-voucher-shopee"> 
                    <div class="d-flex justify-content-center align-items-center">
                        <i class="bi bi-ticket-perforated-fill mau-chu-chinh me-2 fs-5"></i>
                        <span class="text-dark fw-bold"> TechZone Voucher </span>
                    </div>

                </div>
                <div class="d-flex justify-content-between align-items-center py-2 dong-coin-shopee"> 
                    <div class="d-flex align-items-center">
                        <i class="bi bi-coin me-2 text-warning fs-5"></i>

                    </div>
                    <div>
                        <span class="me-2 text-muted">Bạn chưa chọn sản phẩm</span> 
                        <span class="mau-chu-chinh">-0đ </span> 
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-between align-items-center p-3 thanh-thanh-toan"> 
                <div class="d-flex align-items-center">
                    <div class="form-check me-3">
                        <%-- THÊM ID CHO JS: chon-tat-ca-footer --%>
                        <input class="form-check-input" type="checkbox" id="chon-tat-ca-footer"> 
                        <label class="form-check-lable" for="chon-tat-ca-footer"> 
                            Chọn Tất Cả
                        </label>
                    </div>
                    <a href="#" class="text-muted text-decoration-none me-3">Xóa</a>

                </div>
                <div class="d-flex align-items-center">
                    <span class="me-3">
                        Tổng cộng (<span id="tong-san-pham">0</span> Sản phẩm): 
                        <%-- THÊM ID CHO JS --%>
                        <span class="text-danger fw-bold fs-5" id="tong-tien">0<sup>đ</sup> 
                        </span>
                    </span>
                    <%-- THÊM ID CHO JS: btn-mua-hang --%>
                    <button class="btn nut-nen-chinh fw-bold" 
                            data-bs-toggle="modal" data-bs-target="#modal-dia-chi" id="btn-mua-hang" disabled>
                        Mua Hàng
                    </button>
                </div>
            </div>      
        </div>




        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/cart/cart-script.js"></script>
        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
    </body>
</html>