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
        .text-shopee { color: #ee4d2d; } /* Màu cam Shopee */
        .btn-shopee { background-color: #ee4d2d; color: white; } /* Nút màu Shopee */
        .search-input::placeholder { font-size: 14px; } /* Placeholder nhỏ hơn */
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
<nav class="navbar navbar-light bg-light">
  <div class="container-fluid d-flex justify-content-between align-items-center px-5">
    <a href="#" class="logo d-flex align-items-center text-decoration-none">
        <input type="checkbox" class="me-3">
      <div class="d-flex align-items-end">
        <span class="fs-4 fw-bold text-shopee" style="color: #ee4d2d;">Yêu thích</span>
        <span class="ms-2 fs-5 text-dark" style="color: #ee4d2d;">| FACTORY.STUDIO</span>
      </div>
    </body>
</html>
