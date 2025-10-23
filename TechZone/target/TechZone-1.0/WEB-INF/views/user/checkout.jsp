<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Chi Tiết Đơn Hàng - Ứng Dụng Mua Sắm</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

        <style>
            /* Định nghĩa biến màu (Việt hóa) */
            :root {
                --mau-chinh: #007bff; /* Màu chủ đạo: Xanh dương */
                --nen-nhat: #e6f2ff; /* Màu nền nhạt */
                --mau-nhan: #0044cc; /* Màu nhấn: Xanh đậm */
                --mau-vien: #dee2e6;
                --nen-trang: #f5f5f5;
            }

            body {
                background-color: var(--nen-trang);
            }

            /* --- Thanh Điều Hướng Trên Cùng (Top Navbar) --- */
            .thanh-tren {
                background-color: var(--mau-chinh);
                font-size: 0.85rem;
                padding-top: 0;
                padding-bottom: 0;
            }
            .thanh-tren .nav-link {
                color: rgba(255, 255, 255, 0.9);
                padding: 0.2rem 0.6rem;
            }
            .thanh-tren .nav-link:hover {
                color: white;
            }
            .thanh-tren .border-end {
                border-color: rgba(255, 255, 255, 0.5) !important;
            }
            
            /* --- Thanh Tìm Kiếm Chính (Main Navbar) --- */
            .thanh-tim-kiem-chinh {
                background-color: var(--mau-chinh);
                padding: 1rem 0; 
            }
            .app-logo {
                height: 40px; 
            }
            .vung-tim-kiem {
                flex-grow: 1;
                max-width: 600px; 
                margin-left: 20px;
                position: relative;
            }
            .o-tim-kiem {
                border-radius: 2px !important;
                border: none !important;
                height: 40px; 
                font-size: 0.9rem;
                padding-right: 50px;
            }
            .o-tim-kiem::placeholder {
                 font-size: 14px;
            }
            .nut-tim-kiem {
                position: absolute;
                right: 0;
                top: 0;
                background-color: white;
                color: var(--mau-chinh); 
                border: none !important;
                border-radius: 0 2px 2px 0 !important;
                height: 40px;
                width: 50px;
                display: flex;
                align-items: center;
                justify-content: center;
                padding: 0;
                transition: background-color 0.2s;
            }
            .bieu-tuong-gio-hang {
                font-size: 2rem; 
                color: white;
                margin-left: 30px;
            }

            /* Các thẻ gợi ý tìm kiếm */
            .goi-y-tim-kiem {
                color: rgba(255, 255, 255, 0.8);
                font-size: 0.75rem;
                margin-top: 5px;
            }
            .goi-y-tim-kiem a {
                color: inherit;
                text-decoration: none;
                margin-right: 15px;
            }
            
            /* --- Nội Dung Chính --- */
            .khung-noi-dung-chinh {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px 0;
                display: flex;
            }
            .thanh-ben {
                flex: 0 0 220px; 
                margin-right: 20px;
                background-color: white;
                padding: 15px;
                border-radius: 5px;
                box-shadow: 0 1px 2px rgba(0,0,0,.1);
                height: fit-content;
            }
            .khung-chi-tiet-don {
                flex-grow: 1;
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 1px 2px rgba(0,0,0,.1);
            }
            
            /* Menu Thanh Bên */
            .menu-thanh-ben ul {
                list-style: none;
                padding: 0;
            }
            .menu-thanh-ben li a {
                display: flex;
                align-items: center;
                padding: 10px 5px;
                color: #555;
                text-decoration: none;
                border-radius: 3px;
                transition: background-color 0.2s, color 0.2s;
            }
            .menu-thanh-ben li a:hover, .menu-thanh-ben li.active a {
                color: var(--mau-chinh);
                background-color: var(--nen-nhat);
                font-weight: bold;
            }
            .menu-thanh-ben li a i {
                margin-right: 10px;
                font-size: 1.1em;
            }

            /* --- Dòng Thời Gian Đơn Hàng (Timeline) --- */
            .dong-thoi-gian-don {
                display: flex;
                justify-content: space-between;
                align-items: flex-start;
                padding: 20px 0;
                margin-bottom: 20px;
            }

            .muc-thoi-gian {
                flex: 1;
                text-align: center;
                position: relative;
            }
            
            /* Dòng kết nối */
            .muc-thoi-gian:not(:last-child)::before {
                content: '';
                position: absolute;
                top: 30px; 
                left: 50%;
                width: 100%;
                height: 4px;
                background-color: var(--mau-vien);
                z-index: 0; 
            }
            
            /* Dòng kết nối đã hoàn thành (màu chính) */
            .muc-thoi-gian.completed:not(:last-child)::before {
                background-color: var(--mau-chinh);
            }

            .khung-bieu-tuong {
                width: 60px;
                height: 60px;
                border-radius: 50%;
                background-color: var(--mau-vien);
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 1.8rem;
                margin: 0 auto 10px;
                position: relative;
                z-index: 1; 
                color: white;
                border: 2px solid var(--mau-vien);
                transition: background-color 0.3s, border-color 0.3s;
            }

            .muc-thoi-gian.completed .khung-bieu-tuong,
            .muc-thoi-gian.active .khung-bieu-tuong {
                background-color: var(--mau-chinh);
                border-color: var(--mau-chinh);
            }
            
            .muc-thoi-gian.active .khung-bieu-tuong i {
                animation: pulse-icon 1.5s infinite;
            }

            @keyframes pulse-icon {
                0% { transform: scale(1); opacity: 1; }
                50% { transform: scale(1.1); opacity: 0.9; }
                100% { transform: scale(1); opacity: 1; }
            }

            .chu-moc-thoi-gian {
                font-size: 0.95rem;
                color: #333;
                font-weight: 500;
            }
            .muc-thoi-gian.active .chu-moc-thoi-gian {
                font-weight: bold;
                color: var(--mau-chinh);
            }
            .ngay-gio-moc {
                font-size: 0.8rem;
                color: #888;
                height: 20px;
            }

            /* --- Hộp Thông Tin & Nút Hành Động --- */
            .hop-hanh-dong-info {
                background-color: var(--nen-nhat);
                padding: 15px 20px;
                margin-top: 20px;
                border-radius: 5px;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .chu-info {
                font-size: 0.95rem;
                color: #555;
            }
            .chu-info strong {
                font-weight: bold;
                color: var(--mau-nhan);
            }

            .nut-chinh { /* Nút "Đã Nhận Hàng" */
                background-color: var(--mau-chinh);
                border-color: var(--mau-chinh);
                color: white;
                font-weight: bold;
                padding: 8px 30px;
                font-size: 1rem;
                border-radius: 3px;
                transition: background-color 0.2s;
            }
            .nut-chinh:hover {
                background-color: #0056b3; 
                border-color: #0056b3;
            }
            .nut-vien-phu { /* Nút "Yêu Cầu Hủy", "Liên Hệ Người Bán" */
                color: #555;
                background-color: white;
                border: 1px solid var(--mau-vien);
                font-weight: normal;
                padding: 8px 15px;
                border-radius: 3px;
                transition: border-color 0.2s, background-color 0.2s, color 0.2s;
            }
            .nut-vien-phu:hover {
                border-color: var(--mau-chinh);
                color: var(--mau-chinh);
                background-color: var(--nen-nhat);
            }

            /* Classes thay thế */
            .chu-nhan {
                color: var(--mau-nhan);
            }
            
            /* Phần chi tiết sản phẩm và tổng kết */
            .product-detail-box {
                padding: 15px 0;
                border-bottom: 1px solid #eee;
            }
            .product-row {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
            }
            .product-image {
                width: 80px;
                height: 80px;
                object-fit: contain;
                border: 1px solid #eee;
                margin-right: 15px;
            }
            .product-info {
                flex-grow: 1;
            }
            .product-name {
                font-size: 1rem;
                color: #333;
                line-height: 1.4;
            }
            .product-classification {
                font-size: 0.85rem;
                color: #888;
                margin-top: 5px;
            }
            .price-summary {
                margin-top: 20px;
                padding-top: 15px;
                border-top: 1px solid #eee;
            }
            .price-row {
                display: flex;
                justify-content: space-between;
                padding: 8px 0;
                font-size: 0.95rem;
            }
            .price-row strong {
                font-weight: bold;
            }
            .total-payment-row {
                background-color: #fff8f5; /* Màu nền cam nhạt của Shopee, có thể đổi thành xanh nhạt nếu muốn */
                background-color: var(--light-main-color); /* Dùng màu xanh nhạt theo yêu cầu */
                padding: 15px 20px;
                margin: 20px -20px -20px -20px; /* Mở rộng ra mép order-content */
                border-radius: 0 0 3px 3px;
                font-size: 1.1rem;
                font-weight: bold;
                color: var(--main-color); /* Màu chữ xanh */
            }
            .original-price {
                text-decoration: line-through;
                color: #aaa;
                font-size: 0.85rem;
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
        
        <nav class="navbar navbar-expand-lg thanh-tren">
            <div class="container-fluid px-5">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="#">Kênh Người Bán</a></li>
                    <li class="nav-item border-end border-light"></li>
                    <li class="nav-item"><a class="nav-link" href="#">Tải Ứng dụng</a></li>
                    <li class="nav-item border-end border-light"></li>
                    <li class="nav-item"><a class="nav-link" href="#">Kết nối <i class="fab fa-facebook-f ms-1"></i> <i class="fab fa-instagram ms-1"></i></a></li>
                </ul>

                <ul class="navbar-nav mb-2 mb-lg-0">
                    <li class="nav-item"><a class="nav-link" href="#"><i class="far fa-bell me-1"></i> Thông Báo</a></li>
                    <li class="nav-item"><a class="nav-link" href="#"><i class="far fa-question-circle me-1"></i> Hỗ Trợ</a></li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownLang" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-globe me-1"></i> Tiếng Việt
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownLang">
                            <li><a class="dropdown-item" href="#">English</a></li>
                            <li><a class="dropdown-item" href="#">Tiếng Việt</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownUser" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="far fa-user-circle me-1"></i> giyuu222
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownUser">
                            <li><a class="dropdown-item" href="#">Tài khoản của tôi</a></li>
                            <li><a class="dropdown-item" href="#">Đơn mua</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#">Đăng xuất</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>

        <nav class="navbar navbar-expand-lg thanh-tim-kiem-chinh">
            <div class="container-fluid d-flex align-items-center px-5">
                <a href="#" class="navbar-brand">
                    <h3 class="text-white fw-bold m-0" style="color:white !important;">APP LOGO</h3>
                </a>
                
                <form class="d-flex vung-tim-kiem" role="search">
                    <input class="form-control o-tim-kiem" type="search" placeholder="Puma ưu đãi đến 50%" aria-label="Search">
                    <button class="btn nut-tim-kiem" type="submit">
                        <i class="bi bi-search"></i>
                    </button>
                </form>
                
                <a href="#" class="nav-link bieu-tuong-gio-hang">
                    <i class="fas fa-shopping-cart"></i>
                </a>
            </div>
        </nav>

        <div class="thanh-tim-kiem-chinh py-1">
            <div class="container-fluid px-5 goi-y-tim-kiem">
                <a href="#">Bàn phím cơ</a>
                <a href="#">Tai nghe bluetooth</a>
                <a href="#">Ốp điện thoại</a>
                <a href="#">Laptop gaming</a>
                <a href="#">Chuột không dây</a>
                <a href="#">Đèn bàn làm việc</a>
            </div>
        </div>

        <div class="khung-noi-dung-chinh">
            <div class="thanh-ben">
                <div class="sidebar-profile">
                    <div class="sidebar-profile-avatar"></div>
                    <div>
                        <div class="fw-bold">giyuu222</div>
                        <a href="#" class="text-decoration-none text-muted" style="font-size: 0.9em;"><i class="bi bi-pencil me-1"></i>Sửa Hồ Sơ</a>
                    </div>
                </div>
                <div class="menu-thanh-ben">
                    <ul>
                        <li><a href="#"><i class="far fa-bell"></i> Thông Báo</a></li>
                        <li><a href="#"><i class="far fa-user"></i> Tài Khoản Của Tôi</a></li>
                        <li class="active"><a href="#"><i class="fas fa-clipboard-list"></i> Đơn Mua</a></li>
                        <li><a href="#"><i class="fas fa-ticket-alt"></i> Kho Voucher</a></li>
                        <li><a href="#"><i class="fas fa-coins"></i> Điểm Thưởng</a></li>
                    </ul>
                </div>
            </div>

            <div class="khung-chi-tiet-don">
                <div class="order-header">
                    <div>
                        <a href="#" class="text-decoration-none text-muted me-3"><i class="fas fa-chevron-left me-1"></i> TRỞ LẠI</a>
                        <span class="order-id chu-nhan">MÃ ĐƠN HÀNG: 251020HAGT4XVJ</span>
                    </div>
                    <div class="order-status chu-nhan">ĐƠN HÀNG ĐANG GIAO ĐẾN BẠN</div>
                </div>

                <div class="dong-thoi-gian-don">
                    <div class="muc-thoi-gian completed">
                        <div class="khung-bieu-tuong"><i class="bi bi-clipboard-check"></i></div>
                        <div class="chu-moc-thoi-gian">Đơn Hàng Đã Đặt</div>
                        <div class="ngay-gio-moc">00:50 20-10-2025</div>
                    </div>
                    <div class="muc-thoi-gian completed">
                        <div class="khung-bieu-tuong"><i class="bi bi-currency-dollar"></i></div>
                        <div class="chu-moc-thoi-gian">Đơn Hàng Đã Thanh Toán</div>
                        <div class="ngay-gio-moc">00:50 20-10-2025</div>
                    </div>
                    <div class="muc-thoi-gian completed">
                        <div class="khung-bieu-tuong"><i class="bi bi-truck"></i></div>
                        <div class="chu-moc-thoi-gian">Đã Giao Cho ĐVVC</div>
                        <div class="ngay-gio-moc">11:35 20-10-2025</div>
                    </div>
                    <div class="muc-thoi-gian active">
                        <div class="khung-bieu-tuong"><i class="bi bi-box-seam"></i></div>
                        <div class="chu-moc-thoi-gian">Chờ Giao Hàng</div>
                        <div class="ngay-gio-moc"></div>
                    </div>
                    <div class="muc-thoi-gian pending">
                        <div class="khung-bieu-tuong" style="background-color: var(--mau-vien); border-color: var(--mau-vien); color: #a0a0a0;"><i class="bi bi-star"></i></div>
                        <div class="chu-moc-thoi-gian">Đánh Giá</div>
                        <div class="ngay-gio-moc"></div>
                    </div>
                </div>
                
                <hr style="border-color: #eee; margin-top: 25px;">

                <div class="hop-hanh-dong-info">
                    <div class="chu-info">
                        <div>
                            Ngày nhận hàng dự kiến: <strong>21-10-2025</strong>
                        </div>
                        <div class="mt-1">
                            <i class="bi bi-clock me-1 chu-nhan"></i> Giao nhanh: Nhận Voucher 15.000đ nếu đơn hàng được giao sau ngày 23-10-2025. 
                            <a href="#" class="text-decoration-none chu-nhan" style="font-weight: bold;">Xem thêm</a>
                        </div>
                    </div>
                    
                    <div>
                        <button class="btn nut-chinh">Đã Nhận Hàng</button>
                    </div>
                </div>
                
                <hr style="border-color: #eee; margin-top: 15px;">

                <div class="d-flex justify-content-end gap-3">
                    <button class="btn nut-vien-phu">Yêu Cầu Hủy</button>
                    <button class="btn nut-vien-phu">Liên Hệ Người Bán</button>
                </div>
                
                <hr style="border: none; border-top: 5px dashed #f0f0f0; margin-top: 25px;">

            </div>
        </div>
    
    <hr style="border: none; border-top: 5px dashed #f0f0f0; margin-top: 25px;">
        <div class="product-detail-box">
        
        <div class="product-row">
            <img src="URL_HINH_ANH_LAPTOP_DE_KE" alt="Giá đỡ Laptop" class="product-image">
            <div class="product-info">
                <div class="product-name">Giá đỡ LAPTOP, ĐIỆN THOẠI, MÁY TÍNH bảng nhôm có thể điều chỉnh được độ cao, để kệ laptop nhôm</div>
                <div class="product-classification">Phân loại hàng: NHÔM CHỮ X (11-16IN) x1</div>
            </div>
            <div class="product-price text-end">
                <span class="original-price">155.000₫</span>
                <span class="fw-bold text-dark">69.000₫</span>
            </div>
        </div>
        
        <div class="price-summary">
            
            <div class="price-row">
                <span class="text-muted">Tổng tiền hàng</span>
                <span class="text-dark">69.000₫</span>
            </div>
            
            <div class="price-row">
                <span class="text-muted">Phí vận chuyển</span>
                <span class="text-dark">28.700₫</span>
            </div>
            
            <div class="price-row">
                <span class="text-muted">Giảm giá phí vận chuyển <i class="bi bi-info-circle-fill text-muted"></i></span>
                <span class="text-dark">-28.700₫</span>
            </div>
            
            <div class="price-row">
                <span class="text-muted">Voucher từ Shopee</span>
                <span class="text-dark">-30.000₫</span>
            </div>
            
        </div>
    </div>
    
    <div class="total-payment-row d-flex justify-content-end align-items-center">
        <span class="me-3">Thành tiền</span>
        <span style="font-size: 1.5rem;">39.000₫</span>
    </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>