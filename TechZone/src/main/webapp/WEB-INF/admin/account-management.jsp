<%-- 
    Document   : account-management
    Created on : Sep 26, 2025
    Author     : pc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin</title>
        <!-- Bootstrap CSS -->
        <link href="http://localhost:8080/TechZone/assets/css/bootstrap.min.css" rel="stylesheet">
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
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <h5>Shop</h5>
            <ul class="nav flex-column">
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link"><i class="bi bi-house"></i> Tổng quan</a>
                </li>
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link active"><i class="bi bi-people"></i> Người dùng</a>
                </li>
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link"><i class="bi bi-box"></i> Sản phẩm</a>
                </li>
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link"><i class="bi bi-cart"></i> Đơn hàng</a>
                </li>
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link"><i class="bi bi-tag"></i> Khuyến mãi</a>
                </li>
                <li class="nav-item mb-2">
                    <a href="#" class="nav-link"><i class="bi bi-chat"></i> Phản hồi</a>
                </li>
            </ul>
        </div>

        <!-- Content -->
        <div class="content">
            <!-- Header -->
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h1>Người dùng</h1>
                <button class="btn btn-primary">Thêm người dùng mới</button>
            </div>

            <!-- Tìm kiếm + Lọc -->
            <div class="d-flex mb-3">
                <input type="text" class="form-control me-2" placeholder="Tìm kiếm...">
                <select class="form-select" style="max-width:200px;">
                    <option selected>Lọc theo vai trò</option>
                    <option value="Admin">Admin</option>
                    <option value="Staff">Staff</option>
                    <option value="Customer">Customer</option>
                </select>
            </div>

            <!-- Bảng -->
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>Tên</th>
                            <th>Email</th>
                            <th>SDT</th>
                            <th>Vai trò</th>
                            <th class="text-center">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Phạm Thị D</td>
                            <td>d@gmail.com</td>
                            <td>0903456789</td>
                            <td>Staff</td>
                            <td class="text-center">
                                <i class="bi bi-eye me-2"></i>
                                <i class="bi bi-trash text-danger"></i>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>
