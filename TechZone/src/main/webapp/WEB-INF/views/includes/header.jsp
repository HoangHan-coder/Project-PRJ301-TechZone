<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style>
    .input-group .form-select,
    .input-group .form-control,
    .input-group .btn {
        border: none !important;
        outline: none !important;
        box-shadow: none !important;
    }

    .input-group .form-select:focus,
    .input-group .form-control:focus,
    .input-group .btn:focus {
        border: none !important;
        outline: none !important;
        box-shadow: none !important;
    }
</style>

<!-- Top Navbar -->
<div class="bg-light border-bottom">
    <div class="container d-flex justify-content-between align-items-center py-2">
        <!-- Logo + Menu -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light w-100">
            <a class="navbar-brand fw-bold fs-2" href="Home.jsp" >Gizmos<span style="color: orange">.</span></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" 
                    data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false" 
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse my-navbar" id="mainNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><a class="nav-link active fw-bold" href="Home.jsp">Home</a></li>
                    <li class="nav-item"><a class="nav-link fw-bold" href="#">Pages</a></li>
                    <li class="nav-item"><a class="nav-link fw-bold" href="Shop.jsp">Shop</a></li>
                    <li class="nav-item"><a class="nav-link fw-bold" href="#">Blog</a></li>
                    <li class="nav-item"><a class="nav-link fw-bold" href="#">Landing</a></li>
                </ul>
            </div>
            <!-- Contact Info -->
            <div class="d-none d-lg-flex align-items-center">
                <i class="bi bi-headset me-2 fs-2"></i>
                <div>
                    <div class="fw-bold fs-4">+0916973161</div>
                    <small class="text-muted">KhangPD.CE191105@gmail.com</small>
                </div>
            </div>
        </nav>
    </div>
</div>

<!-- Bottom Navbar -->
<!-- Navbar Search Section -->
<nav class="navbar navbar-expand-lg" style="background-color: #285dde; height: 70px"> 
    <div class="container-fluid d-flex justify-content-between align-items-center">

        <!-- Nút bấm Shop By Categories -->
        <div class="dropdown">
            <!-- Nút bấm -->
            <a class="d-flex align-items-center text-white fw-bold me-3 text-decoration-none dropdown-toggle" 
               href="#" id="categoriesDropdown" role="button" 
               data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-list fs-4 me-2"></i>
                <span>Shop By Categories</span>
            </a>

            <!-- Menu xổ xuống -->
            <ul class="dropdown-menu" aria-labelledby="categoriesDropdown">
                <li><a class="dropdown-item" href="#">Weekly Bestsellers</a></li>
                <li><a class="dropdown-item" href="#">Featured Products</a></li>
                <li><a class="dropdown-item" href="#">New Arrivals</a></li>
            </ul>
        </div>
        <!-- Search Bar -->
        <form class="d-flex justify-content-center mx-3 flex-grow-1 my-0" role="search">
            <div class="input-group" style="max-width: 600px; width: 100%;">
                <select class="form-select border-0 border-end" style="max-width: 180px;">
                    <option selected>All Categories</option>
                    <option value="1">Electronics</option>
                    <option value="2">Fashion</option>
                    <option value="3">Home</option>
                </select>
                <input class="form-control border-0" type="search" placeholder="Tìm kiếm sản phẩm..." aria-label="Search">
                <button class="btn btn-light" type="submit">
                    <i class="bi bi-search"></i>
                </button>
            </div>
        </form>

        <!-- Icons -->
        <div class="d-flex align-items-center text-white">
            <a href="#" class="text-white mx-2 fs-5"><i class="bi bi-arrow-left-right"></i></a>
            <a href="#" class="text-white mx-2 fs-5"><i class="bi bi-person"></i></a>
            <a href="#" class="text-white mx-2 fs-5"><i class="bi bi-heart"></i></a>

            <!-- Cart with badge -->
            <div class="position-relative mx-2">
                <a href="http://localhost:8080/TechZone/order" class="text-white fs-5"><i class="bi bi-cart"></i></a>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    0
                </span>
            </div>

            <!-- Price -->
            <span class="ms-2 fw-bold">$0</span>
        </div>

    </div>
</nav>

<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>