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
            <div class="row w-100">
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
            <div class="row w-100">
                <form class="mx-3">
                    <h2>Đánh giá sản phẩm</h2>
                    <div class="d-flex">
                        <input type="range" class="form-range w-25 me-5 my-3" min="0" max="5" value="50" id="range4">
                        <div class=""><output for="range4" id="rangeValue" aria-hidden="true" class="my-3"></output><i
                                class="bi bi-star-fill ms-3"></i></div>
                    </div>
                    <div class="form-floating mb-4">
                        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea2"
                                  style="height: 100px"></textarea>
                        <label for="floatingTextarea2">Comments</label>
                    </div>
                    <button type="submit" class="btn btn-primary">Đánh giá</button>
                </form>
            </div>
            <div class="row w-100">
                <div class="card m-4" style="width: 100%;">
                    <div class="card-body">
                        <h5 class="card-title">Username</h5>
                        <h6 class="card-subtitle mb-2 text-body-secondary">
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                            <i class="bi bi-star-fill"></i>
                        </h6>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the
                            card’s content.</p>
                    </div>
                </div>
            </div>
        </div>
        <script>
            // This is an example script, please modify as needed
            const rangeInput = document.getElementById('range4');
            const rangeOutput = document.getElementById('rangeValue');

            // Set initial value
            rangeOutput.textContent = rangeInput.value;

            rangeInput.addEventListener('input', function () {
                rangeOutput.textContent = this.value;
            });

            function increase() {
                let qty = document.getElementById("quantity");
                qty.value = parseInt(qty.value) + 1;
            }
            function decrease() {
                let qty = document.getElementById("quantity");
                if (parseInt(qty.value) > 1)
                    qty.value = parseInt(qty.value) - 1;
            }
        </script>
    </body>

</html>
