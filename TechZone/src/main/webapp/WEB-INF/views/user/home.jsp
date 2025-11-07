<%-- 
    Document   : Home
    Created on : 19 Sept 2025, 16:44:44
    Author     : PC
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home 12 Clone</title>
        <link href="assets/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
              
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
                                <img src="assets/images/accessories/anker_charger_04.jpg" alt="Accessory Category" class="img-fluid mb-3" style="height:180px; object-fit:cover;">
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

                    <!-- top san pham dien thoai -->
                    <c:forEach var="p" items="${listPhonefe}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Top san pham laptop -->
                    <c:forEach var="p" items="${listLapfe}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- top san pham access -->
                    <c:forEach var="p" items="${listAccessoryFe}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </section>

        <!-- Trending Products -->
        <section class="Trending py-5 bg-light">
            <div class="container">
                <h2 class="mb-4 text-center fw-bold">Trending Products</h2>
                <div class="row g-3">

                    <!--  Top sản phẩm smartphone -->
                    <c:forEach var="p" items="${listPhone}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!--  Top sản phẩm laptop -->
                    <c:forEach var="p" items="${listLap}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                    <!--  Top sản phẩm Acces -->
                    <c:forEach var="p" items="${listAccessory}">
                        <div class="col-md-4">
                            <div class="category-card p-4 text-center bg-white shadow-sm rounded-3">
                                <a href="products?action=detail&id=${p.productId}">
                                    <img src="${pageContext.request.contextPath}${p.linkImg}" alt="${p.productName}" class="img-fluid mb-3" style="height:200px;object-fit:contain;">
                                    <h5 class="fw-bold">${p.productName}</h5>
                                    <p class="text-danger mb-0">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </section>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
<!--        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>-->
    </body>

</html>
