<%-- 
    Document   : Home
    Created on : 19 Sept 2025, 16:44:44
    Author     : PC
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>TechZone</title>
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            .hero {
                background-color: #f8f9fa;
            }

            .hero h1 {
                font-weight: 700;
            }

            .category-card {
                border: 1px solid #ddd;
                transition: transform 0.3s;
            }

            .category-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }

            .featured-products .card,
            .trending-products .card {
                border: none;
                transition: transform 0.3s, box-shadow 0.3s;
            }

            .featured-products .card:hover,
            .trending-products .card:hover {
                transform: translateY(-5px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.15);
            }

            .newsletter {
                background-size: cover;
                background-position: center;
                background-color: #333;
            }
            .carousel-item img {
                height: 500px;         /* chiều cao ảnh */
                object-fit: cover;     /* cắt ảnh vừa khung */
                width: 100%;
            }
            .carousel-caption {
                bottom: 20px;          /* kéo caption lên gần ảnh hơn */
            }
            .carousel-caption h5,
            .carousel-caption p {
                margin-bottom: 5px;    /* giảm khoảng cách chữ */
            }

            .product-card {
                background: #1c1c1c;
                color: white;
                border-radius: 15px;
                padding: 30px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                height: 250px;
            }
            .product-info h3 {
                font-weight: bold;
            }
            .product-image img {
                max-height: 180px;
            }
            .logo-container {
                display: flex;
                justify-content: space-evenly; /* căn đều các logo */
                align-items: center;
                padding: 20px 0;
                background-color: #f8f8f8;
            }

            .logo-container img {
                max-height: 60px; /* đồng bộ chiều cao logo */
                object-fit: contain;
                transition: transform 0.2s;
            }

            .logo-container img:hover {
                transform: scale(1.1);
            }


            footer {
                background-color:#111;
                color:#fff;
                padding:40px 20px 0;
            }
            .footer-columns {
                display: flex;
                justify-content: space-evenly;
                gap: 40px; /* khoảng cách giữa các cột */
                max-width: 1200px;
                margin: 0 auto 30px;
                text-align: left;
            }
            .footer-column h4 {
                margin-bottom: 15px;
                font-size: 18px;
                font-weight: bold;
            }
            .footer-column ul {
                list-style: none;
                margin: 0;
                padding: 0;
            }
            .footer-column ul li {
                margin-bottom: 8px;
            }
            .footer-column ul li a {
                color: #bbb;
                text-decoration: none;
                transition: color .3s;
            }
            .footer-column ul li a:hover {
                color: #fff;
            }
            .footer-bottom {
                border-top:1px solid #333;
                margin-top:30px;
                padding:20px;
                display:flex;
                flex-wrap:wrap;
                justify-content:space-evenly;
                align-items:center;
                max-width:1200px;
                margin-left:auto;
                margin-right:auto;
            }
            .footer-logo {
                font-size:24px;
                font-weight:bold;
            }
            .footer-bottom-item {
                display:flex;
                align-items:center;
                gap:10px;
                color:#bbb;
            }
            .footer-bottom-item i {
                font-size:20px;
                color:#fff;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

        <!-- Bootstrap Carousel -->
        <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
            <!-- Indicators -->
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="0" class="active"></button>
                <button type="button" data-bs-target="#heroCarousel" data-bs-slide-to="1"></button>
            </div>

            <!-- Slides -->


            <!-- Nút điều hướng -->
            <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <!-- Categories -->
        <section class="categories py-5 bg-light">
            <div class="container">
                <h2 class="mb-4 text-center fw-bold">Shop By Categories</h2>
                <div class="row g-4">

                    <!-- Điện thoại -->
                    <div class="col-md-4">
                        <a href="products?category=phone" class="text-decoration-none text-dark">
                            <div class="category-card p-4 text-center bg-white rounded shadow-sm">
                                <img src="assets/images/phones/iphone_01.jpg" alt="Phone Category" class="img-fluid mb-3" style="height:180px; object-fit:cover;">
                                <h5>Smartphones & Tablets</h5>
                            </div>
                        </a>
                    </div>

                    <!-- Laptop -->
                    <div class="col-md-4">
                        <a href="products?category=laptop" class="text-decoration-none text-dark">
                            <div class="category-card p-4 text-center bg-white rounded shadow-sm">
                                <img src="assets/images/laptops/acer_01.jpg" alt="Laptop Category" class="img-fluid mb-3" style="height:180px; object-fit:cover;">
                                <h5>Laptops</h5>
                            </div>
                        </a>
                    </div>

                    <!-- Linh kiện -->
                    <div class="col-md-4">
                        <a href="products?category=accessory" class="text-decoration-none text-dark">
                            <div class="category-card p-4 text-center bg-white rounded shadow-sm">
                                <img src="assets/images/categories/accessory.jpg" alt="Accessory Category" class="img-fluid mb-3" style="height:180px; object-fit:cover;">
                                <h5>Accessories</h5>
                            </div>
                        </a>
                    </div>

                </div>
            </div>
        </section>




        <!-- Featured Products -->

        <section class="featured py-5 bg-light">
            <div class="container">
                <h2 class="mb-4 text-center fw-bold">Featured Products</h2>
                <div class="row g-3">
                     <c:forEach var="p" items="${listPhonefe}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">${p.productPrice} VND</p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- 🔹 Top sản phẩm laptop -->
                    <c:forEach var="p" items="${listLapfe}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">${p.productPrice} VND</p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="col-md-4">
                        <div class="category-card p-4 text-center bg-white">
                            <img src="#" alt="Category 3" class="img-fluid mb-3">
                            <h5>TV & Audio</h5>
                        </div>
                    </div>
                </div>
            </div>
        </section>


        <!-- Trending Products -->

        <section class="Trending py-5 bg-light">
            <div class="container">
                <h2 class="mb-4 text-center fw-bold">Trending Products</h2>
                <div class="row g-3">
                    <c:forEach var="p" items="${listPhone}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">${p.productPrice} VND</p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- 🔹 Top sản phẩm laptop -->
                    <c:forEach var="p" items="${listLap}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">${p.productPrice} VND</p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="col-md-4">
                        <div class="category-card p-4 text-center bg-white">
                            <img src=" #" alt="Category 3" class="img-fluid mb-3">
                            <h5>TV & Audio</h5>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

</html>
