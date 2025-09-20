<%-- 
    Document   : product-detail
    Created on : Sep 20, 2025, 2:59:49 PM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <title>Document</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            .tab-container {
                display: flex;
                border: 1px solid #ddd;
                border-radius: 8px;
                overflow: hidden;
                width: fit-content;
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
                background-color: #2563eb; /* xanh */
                color: white;
            }
            .detail, .des, .review {
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

            ::-webkit-scrollbar-track {
                background: #f1f1f1;    
                border-radius: 10px;
            }

            ::-webkit-scrollbar-thumb {
                background: #2563eb;   
                border-radius: 10px;
            }

            ::-webkit-scrollbar-thumb:hover {
                background: #1e40af;    
            }

        </style>

    </head>

    <body>
        <div class="container-fluid">
            <div class="row w-100">
                <div class="col-md-5 mx-4 my-5 border rounded">
                    <img src="./image.png" class="rounded " style="width: 100%;" alt="...">
                </div>
                <div class="col-md-6 my-5">
                    <h1>Led 4K Smart TV Vantablack Expo GSM-8547366J</h1>
                    <p class="fs-5 text">4.9 <i class="bi bi-star-fill"></i> | <span>Lượt bán: 20K</span></p>
                    <p class="fs-3 text mb-4 mt-2">$990</p>
                    <p>Alienum phaedrum torquatos nec eu, vis detraxit periculis ex, nihil expete mei. Mei an consequat an.
                        Eius lorem tincidunt vix at, vel pertinax sensibus id, error epicurei mea et. Qui purto zril
                        laoreet. Ex error omnium interpretaris pro.</p>
                    <div class="d-flex align-items-center w-100">
                        <p class="fs-4 text me-5">Số lượng: </p>
                        <div class="d-flex align-items-center my-5" style="width: 100px;height: 60px;">
                            <input type="text" class=" text-center me-2" id="quantity" value="1"
                                   style="height: 60px; width: 60px; border-radius: 5px; border: .8px solid rgb(195, 190, 190);">
                            <div class="mb-2">
                                <button class="btn border-0 pt-5 pb-0 mt-2" type="button" onclick="increase()"><i
                                        class="bi bi-chevron-up fs-4 text"></i></button>
                                <button class="btn border-0 pb-5 pt-0" type="button" onclick="decrease()"><i
                                        class="bi bi-chevron-down fs-4 text"></i></button>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary btn-lg me-3">Thêm vào giỏ hàng</button>
                        <button type="button" class="btn btn-secondary btn-lg">Thanh toán</button>
                    </div>
                </div>
            </div>
            <div class="tab-container">
                <div class="tab detailProduct active" onclick="handleDetail(this)">Chi tiết sản phẩm</div>
                <div class="tab despriceProduct" onclick="handleDetail(this)">Miêu tả sản phẩm</div>
                <div class="tab reviewProduct" onclick="handleDetail(this)">Đánh giá sản phẩm</div>
            </div>
            <div class="row w-100 detail">
                <table class="table table-bordered m-4 w-100">
                    <tbody>
                        <tr>
                            <td>CPU</td>
                            <td>Intel Core i7-12700H</td>
                        </tr>
                        <tr>
                            <td>RAM</td>
                            <td>16GB DDR5 4800MHz</td>
                        </tr>
                        <tr>
                            <td>Ổ cứng</td>
                            <td>1TB SSD NVMe</td>
                        </tr>
                        <tr>
                            <td>Card đồ họa</td>
                            <td>NVIDIA GeForce RTX 4060 8GB</td>
                        </tr>
                        <tr>
                            <td>Màn hình</td>
                            <td>15.6" QHD 165Hz IPS</td>
                        </tr>
                        <tr>
                            <td>Hệ điều hành</td>
                            <td>Windows 11 Home</td>
                        </tr>
                        <tr>
                            <td>Cổng kết nối</td>
                            <td>USB-C, HDMI, LAN, 3.5mm</td>
                        </tr>
                        <tr>
                            <td>Pin</td>
                            <td>90Wh, sạc 230W</td>
                        </tr>
                        <tr>
                            <td>Trọng lượng</td>
                            <td>2.1 kg</td>
                        </tr>
                    </tbody>

                </table>
            </div>
            <div class="des">
                <p>Alienum phaedrum torquatos nec eu, vis detraxit periculis ex, nihil expete mei. Mei an consequat an.
                    Eius lorem tincidunt vix at, vel pertinax sensibus id, error epicurei mea et. Qui purto zril
                    laoreet. Ex error omnium interpretaris pro</p>
            </div>
            <div class="row w-100 review">
                <form class="mx-3">
                    <p>Alienum phaedrum torquatos nec eu, vis detraxit periculis ex, nihil expete mei. Mei an consequat an.
                        Eius lorem tincidunt vix at, vel pertinax sensibus id, error epicurei mea et. Qui purto zril
                        laoreet. Ex error omnium interpretaris pro</p>
                    <div class="rating my-3">
                        <i class="bi bi-star star" data-value="1"></i>
                        <i class="bi bi-star star" data-value="2"></i>
                        <i class="bi bi-star star" data-value="3"></i>
                        <i class="bi bi-star star" data-value="4"></i>
                        <i class="bi bi-star star" data-value="5"></i>
                    </div>
                    <div class="form-floating mb-4">
                        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2"
                                  style="height: 100px"></textarea>
                        <label for="floatingTextarea2">Comments</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Đánh giá</button>
                </form>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const firstTab = document.querySelector(".tab");
                if (firstTab) {
                    handleDetail(firstTab);
                }
            });
            function handleDetail(a) {
                const sections = {
                    "tab detailProduct": document.querySelector(".detail"),
                    "tab despriceProduct": document.querySelector(".des"),
                    "tab reviewProduct": document.querySelector(".review")
                };

                Object.values(sections).forEach(el => el.style.display = "none");

                document.querySelectorAll(".tab").forEach(tab => tab.classList.remove("active"));

                if (sections[a.className]) {
                    sections[a.className].style.display = "block";
                }

                a.classList.add("active");
            }
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

                    document.getElementById("ratingValue").value = value;
                });
            });

        </script>
    </body>

</html>
