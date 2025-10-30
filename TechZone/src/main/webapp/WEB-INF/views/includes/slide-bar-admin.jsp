<%-- 
    Document   : slide-bar
    Created on : Oct 20, 2025, 8:09:35 AM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<title>Admin</title>
<!-- Bootstrap CSS -->
<link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
    }
    /* Sidebar */
    .sidebar {
        width: 240px;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #212529;
        color: #fff;
        padding: 20px 15px;
    }
    .sidebar h5 {
        margin-bottom: 30px;
    }
    .sidebar .nav-link {
        color: #ccc;
        padding: 8px 12px;
        border-radius: 6px;
    }
    .sidebar .nav-link.active,
    .sidebar .nav-link:hover {
        background-color: #0d6efd;
        color: #fff;
    }
    /* Content */
    .content {
        margin-left: 240px;
        padding: 20px;
        background: #f8f9fa;
        min-height: 100vh;
    }
</style>

<!-- Sidebar -->
<div class="sidebar">
    <h5>Shop</h5>
    <ul class="nav flex-column">
        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/report" class="nav-link"><i class="bi bi-house"></i> Tổng quan</a>
        </li>
        <li class="nav-item mb-2">
            <a href="#" class="nav-link"><i class="bi bi-people"></i> Người dùng</a>
        </li>
        <li class="nav-item mb-2">
            <a href="#" class="nav-link"><i class="bi bi-box"></i> Sản phẩm</a>
        </li>
        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/admin/order?view=list" class="nav-link"><i class="bi bi-cart"></i> Đơn hàng</a>
        </li>
        <li class="nav-item mb-2">
            <a href="${pageContext.request.contextPath}/voucher" class="nav-link"><i class="bi bi-tag"></i> Khuyến mãi</a>
        </li>
        <li class="nav-item mb-2">
            <a href="#" class="nav-link"><i class="bi bi-chat"></i> Phản hồi</a>
        </li>
    </ul>
</div>
