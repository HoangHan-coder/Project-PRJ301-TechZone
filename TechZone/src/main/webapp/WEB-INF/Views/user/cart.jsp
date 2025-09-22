<%-- 
    Document   : cart
    Created on : Sep 15, 2025, 4:26:22 PM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Responsive -->
        <title>Giỏ Hàng</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Icons (cho icon search) -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

        <!--  để  màu Shopee -->
        <style>
            .text-shopee {
                color: #ee4d2d;
            } /* Màu cam Shopee */
            .btn-shopee {
                background-color: #ee4d2d;
                color: white;
            } /* Nút màu Shopee */
            .search-input::placeholder {
                font-size: 14px;
            } /* Placeholder nhỏ hơn */
        </style>
    </head>
    <body>
        <!-- Thanh điều hướng (Navbar) -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light py-2">
            <div class="container-fluid d-flex justify-content-between align-items-center px-5">       
                <!-- Logo Shopee -->
                <a href="#" class="navbar-logo d-flex align-items-center text-decoration-none">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTc6KyYQqBpxn8XpymfjPsDdDzYn-OLlQ8Bg&s"
                         alt="Shopee Logo" height="40" class="me-2">
                    <div class="d-flex align-items-end">
                        <span class="fs-4 fw-bold text-shopee">Shopee</span>
                        <span class="ms-2 fs-5 text-dark">| Giỏ Hàng</span>
                    </div>
                </a>
                <!-- Ô tìm kiếm -->
                <form class="d-flex ms-auto search-form" style="width: 50%;">
                    <div class="input-group">
                        <input type="text" class="form-control search-input"
                               placeholder="SĂN DEAL BIODERMA CHÍNH HÃNG">
                        <button class="btn btn-shopee" type="submit">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                </form>
            </div>
        </nav>
        <!-- Tiêu đề giỏ hàng -->
        <div class="container-fluid my-4">
            <div class="row align-items-center bg-white py-3 border rounded shadow-sm cart-header">
                <div class="col-6 d-flex align-items-center">
                    <input type="checkbox" class="me-3">
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
        <div class="container-fluid d-flex justify-content-between align-items-center px-5 py-3">
            <a href="#" class="logo d-flex align-items-center text-decoration-none">
                <input type="checkbox" class="me-3">
                <div class="d-flex align-items-end">
                    <span class="fs-4 fw-bold text-shopee" style="color: #ee4d2d;">Yêu thích</span>
                    <span class="ms-2 fs-5 text-dark">| Apple Flagship Store </span>
                </div>
            </a>
        </div>

        <div class="d-flex align-items-center justify-content-between p-3 border">
            <div class="d-flex align-items-center">
                <div class="form-check me-3">
                    <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                </div>
                <img src="https://i.ebayimg.com/images/g/AE0AAeSwgoJowuFO/s-l1600.webp" alt="APPLE 2025 iPHONE 17 PRO MAX 6.9 256GB/512GB/1TB/2TB UNLOCKED (A3526 DUAL SIM)" height="80" class="border me-3">
                <div class="flex-grow-1 ms-3">
                    <p class="mb-1 fw-bold">Điện thoại Apple iPhone 17 Pro 512GB</p>
                    <span class="badge text-bg-warning text-dark">Hàng đặt trước</span>
                </div>
                <div class="d-flex align-items-center mt-2">
                    <span class="text-muted me-2">Phân loại hàng:</span>
                    <div class="dropdown">
                        <button class="btn btn-sm btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"> Cam Vữ Trụ</button>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Xanh Biển </a></li>
                            <li><a class="dropdown-item" href="#">Đen </a></li>
                        </ul>
                    </div>
                </div>
                <div class="d-flex align-items-center justify-content-between flex-grow-1 ms-5">
                    <div class="text-end me-5">
                        <p class="mb-0 text-muted text-decoration-line-through"> 63.490.000đ</p>     
                    </div>
                </div>
                <div class="input-group input-group-sm me-5" style="width: 120px;">
                    <button class="btn btn-outline-secondary" type="button">-</button>
                    <input id="qty" name="qty" type="number" class="form-control text-center" value="2" min="1" max="99">
                    <button class="btn btn-outline-secondary" type="button">+</button>
                </div>
                <div class="text-end me-5">
                    <p class="mb-0 text-danger fw-bold fs-5">86.980.000đ</p>
                </div>
                <div class="text-end">
                    <a hrel="#" class="text-decoration-none text-muted">Xóa</a>
                    <a href="#" class="d-block text-danger text-decoration-none mt-1">Tìm sản phẩm tương tự</a>
                </div>
            </div>
        </div>
        <div class="d-flex align-items-center p-3 border-bottom">
            <div class="d-flex align-items-center">
                <img src="https://deo.shopeemobile.com/shopee/shopee-pcmall-live-sg/cart/293cb84a6429a3426672.svg" alt="Voucher Icon" height="20" class="me-2">
                <span class="text-danger me-2">Voucher giảm đến 500k</span>
                <a href="#" class="text-primary text-decoration-none">Xem thêm voucher</a>
            </div>
        </div>
        <div class="d-flex align-items-center p-3 border-bottom">
            <div class="d-flex align-items-center">
                <img src="https://down-vn.img.susercontent.com/file/sg-11134270-7rfid-m30kymxdm90kc6.webp" alt="Voucher Icon" height="20" class="me-2">
                <span class="text-danger me-2">Giảm 500.000đ phí vận chuyển đơn tối thiểu 0đ</span>
                <a href="#" class="text-primary text-decoration-none">Tìm hiểu thêm</a>
            </div>
        </div>
    </body>
</html>
