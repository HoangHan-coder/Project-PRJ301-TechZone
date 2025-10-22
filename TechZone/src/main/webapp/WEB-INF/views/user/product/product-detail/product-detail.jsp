<%-- 
    Document   : product-detail
    Author     : Phung Dinh Khang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${product.productName}</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            * {
                box-sizing: border-box;
            }
            .tab-container {
                display: flex;
                border: 1px solid #ddd;
                border-radius: 8px;
                overflow: hidden;
                width: fit-content;
                margin: 0 auto;
            }

            .tab {
                padding: 12px 24px;
                cursor: pointer;
                border-right: 1px solid #ddd;
                background-color: white;
                color: black;
                font-weight: 500;
                transition: 0.3s;
            }

            .tab:last-child {
                border-right: none;
            }

            .tab.active {
                background-color: #2563eb;
                color: white;
            }

            .detail,
            .review {
                display: none;
            }

            .star {
                font-size: 2rem;
                color: #ccc;
                cursor: pointer;
                transition: color 0.2s;
            }

            .star.active {
                color: gold;
            }

            ::-webkit-scrollbar {
                width: 10px;
                height: 10px;
            }

            ::-webkit-scrollbar-thumb {
                background: #2563eb;
                border-radius: 10px;
            }

            ::-webkit-scrollbar-thumb:hover {
                background: #1e40af;
            }
            .review {
                --bs-gutter-x: 1.5rem;
                --bs-gutter-y: 0;
                display: none;
                flex-wrap: wrap;
                margin-top: calc(-1 * var(--bs-gutter-y));
                margin-right: calc(-.5 * var(--bs-gutter-x));
                margin-left: calc(-.5 * var(--bs-gutter-x));
            }
        </style>
    </head>

    <body>
        <jsp:include page="/WEB-INF/views/includes/navbar.jsp" />

        <div class="container-fluid">
            <div class="row w-100">
                <!-- Hình ảnh sản phẩm -->
                <div class="col-md-5 mx-4 my-5 border rounded">
                    <img src="${product.linkImg}" class="rounded w-100" alt="${product.productName}">
                </div>

                <!-- Thông tin sản phẩm -->
                <div class="col-md-6 my-5">
                    <h1>${product.productName}</h1>
                    <p class="fs-5 text-muted">⭐ 4.9 | Lượt bán: ${product.quantitySold}</p>
                    <p class="fs-3 text-danger mb-4 mt-2">${product.productPrice} VND</p>
                    <p>${product.descriptionProduct}</p>

                    <div class="d-flex align-items-center w-100 mt-4">
                        <p class="fs-5 me-3 mb-0">Số lượng:</p>
                        <div class="d-flex align-items-center" style="width: 100px;">
                            <input type="text" class="form-control text-center" id="quantity" value="1"
                                   style="height: 45px; width: 60px;">
                            <div class="ms-2 d-flex flex-column">
                                <button class="btn border-0 p-0" type="button" onclick="increase()"><i
                                        class="bi bi-chevron-up"></i></button>
                                <button class="btn border-0 p-0" type="button" onclick="decrease()"><i
                                        class="bi bi-chevron-down"></i></button>
                            </div>
                        </div>
                    </div>

                    <div class="mt-4">
                        <button type="button" class="btn btn-primary btn-lg me-3">🛒 Thêm vào giỏ hàng</button>
                        <button type="button" class="btn btn-secondary btn-lg">💳 Thanh toán</button>
                    </div>
                </div>
            </div>

            <!-- Tabs -->
            <div class="tab-container mb-4">
                <div class="tab active" data-target="detail">Chi tiết sản phẩm</div>
                <div class="tab" data-target="review">Đánh giá sản phẩm</div>
            </div>

            <!-- Chi tiết sản phẩm -->
            <div class="row w-100 detail">
                <table class="table table-bordered m-4 w-100">
                    <tbody>
                        <tr><td>Brand</td><td>${product.attributesMap.brand}</td></tr>
                        <tr><td>Model</td><td>${product.attributesMap.model}</td></tr>
                        <tr><td>CPU</td><td>${product.attributesMap.cpu}</td></tr>
                        <tr><td>RAM</td><td>${product.attributesMap.ram}</td></tr>
                        <tr><td>Storage</td><td>${product.attributesMap.storage}</td></tr>
                        <tr><td>OS</td><td>${product.attributesMap.os}</td></tr>
                        <tr><td>Weight</td><td>${product.attributesMap.weight}</td></tr>
                    </tbody>
                </table>
            </div>

            <!-- Đánh giá -->
            <div class="w-100 review">
                <form class="mx-3 mb-5">
                    <p class="my-3 text-muted">Hãy chia sẻ cảm nhận của bạn về sản phẩm này!</p>
                    <div class="rating my-3">
                        <i class="bi bi-star star" data-value="1"></i>
                        <i class="bi bi-star star" data-value="2"></i>
                        <i class="bi bi-star star" data-value="3"></i>
                        <i class="bi bi-star star" data-value="4"></i>
                        <i class="bi bi-star star" data-value="5"></i>
                    </div>
                    <div class="form-floating mb-4">
                        <textarea class="form-control" placeholder="Nhập nhận xét của bạn..." id="floatingTextarea2"
                                  style="height: 100px"></textarea>
                        <label for="floatingTextarea2">Bình luận</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp" />

        <script>
            // Tab chuyển đổi
            document.querySelectorAll(".tab").forEach(tab => {
                tab.addEventListener("click", () => {
                    document.querySelectorAll(".tab").forEach(t => t.classList.remove("active"));
                    tab.classList.add("active");

                    document.querySelectorAll(".detail, .review").forEach(sec => sec.style.display = "none");
                    document.querySelector("." + tab.dataset.target).style.display = "block";
                });
            });

            // Mặc định mở tab đầu tiên
            document.addEventListener("DOMContentLoaded", () => {
                document.querySelector(".detail").style.display = "block";
            });

            // Rating sao
            document.querySelectorAll(".star").forEach(star => {
                star.addEventListener("click", () => {
                    const value = parseInt(star.dataset.value);
                    document.querySelectorAll(".star").forEach(s => {
                        if (parseInt(s.dataset.value) <= value) {
                            s.classList.add("active");
                            s.classList.replace("bi-star", "bi-star-fill");
                        } else {
                            s.classList.remove("active");
                            s.classList.replace("bi-star-fill", "bi-star");
                        }
                    });
                });
            });

            // Nút tăng/giảm số lượng
            function increase() {
                const q = document.getElementById("quantity");
                q.value = parseInt(q.value) + 1;
            }
            function decrease() {
                const q = document.getElementById("quantity");
                if (parseInt(q.value) > 1)
                    q.value = parseInt(q.value) - 1;
            }
        </script>
    </body>
</html>
