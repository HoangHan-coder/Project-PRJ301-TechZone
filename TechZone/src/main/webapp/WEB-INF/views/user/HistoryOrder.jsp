<%-- 
    Document   : HistoryOrder
    Created on : Sep 18, 2025, 9:03:02 AM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TechZone</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        .history-order {
            display: block;
            margin: 0 auto;
            width: 1000px;
            margin-top: 24px;
            margin-bottom: 12px;
        }

        .nav-underline {
            box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
            height: 52px;
        }


        .nav-item a {
            color: #000;
            line-height: 2.2;
        }

        .nav-item {
            width: 142px;
            text-align: center;
        }
    </style>
</head>

<body>
    <div class="history-order">
        <div class="container-fluid">
            <ul class="nav nav-underline">
                <li class="nav-item">
                    <a class="nav-link" href="#">Tất cả</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Chờ xác Nhận</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Vận chuyển</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="#">Chờ giao hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="#">Hoàn thành</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="#">Đã hủy</a>
                </li>
            </ul>

            <div class="input-group my-3">
                <span class="input-group-text" id="basic-addon1"><i class="bi bi-search"></i></span>
                <input type="text" class="form-control"
                    placeholder="Bạn có thể tìm kiếm theo tên Shop, ID đơn hàng hoặc Tên Sản phẩm" aria-label="Username"
                    aria-describedby="basic-addon1">
            </div>

            <div class="card my-3" style="width: 100%;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <p class="fw-bold m-2">Cooler_Master_Store</p>
                        <p class="m-2"><span class="text-success"> Giao hàng thành công</span><span
                                class="mx-2">|</span><span class="text-uppercase text-danger">Hoàn thành</span></p>
                    </li>
                    <li class="list-group-item">
                        <div class="row g-0">
                            <div class="col-md-2">
                                <img src="https://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m0sg8p9yk2y5ef_tn"
                                    class="img-fluid rounded-start" alt="...">
                            </div>
                            <div class="col-md-10">
                                <div class="card-body">
                                    <h5 class="card-title">Đế Tản Nhiệt Laptop Cooler Master Notepal L1, Chống ồn, giữ
                                        Cho
                                        Laptop Mát Mẻ, Tương thích 17 Inch | Bảo Hành 2 Năm</h5>
                                    <p class="card-text position-relative">x1<span
                                            class="position-absolute  top-0 end-0">379.000₫</span></p>
                                    </p>
                                </div>
                    </li>
                    <li class="list-group-item d-flex flex-column align-items-end">
                        <p class="mb-2">Thành tiền: <span class="fs-3 text text-danger">315.649₫</span></p>
                        <div class="m-2">
                            <button type="button" style="width: 150px;" class="btn btn-primary me-2">Mua lại</button>
                            <button type="button" class="btn btn-light ">Liên hệ người bán</button>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="card my-3" style="width: 100%;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <p class="fw-bold m-2">Cooler_Master_Store</p>
                        <p class="m-2"><span class="text-success"> Giao hàng thành công</span><span
                                class="mx-2">|</span><span class="text-uppercase text-danger">Hoàn thành</span></p>
                    </li>
                    <li class="list-group-item">
                        <div class="row g-0">
                            <div class="col-md-2">
                                <img src="https://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m0sg8p9yk2y5ef_tn"
                                    class="img-fluid rounded-start" alt="...">
                            </div>
                            <div class="col-md-10">
                                <div class="card-body">
                                    <h5 class="card-title">Đế Tản Nhiệt Laptop Cooler Master Notepal L1, Chống ồn, giữ
                                        Cho
                                        Laptop Mát Mẻ, Tương thích 17 Inch | Bảo Hành 2 Năm</h5>
                                    <p class="card-text position-relative">x1<span
                                            class="position-absolute  top-0 end-0">379.000₫</span></p>
                                    </p>
                                </div>
                    </li>
                    <li class="list-group-item d-flex flex-column align-items-end">
                        <p class="mb-2">Thành tiền: <span class="fs-3 text text-danger">315.649₫</span></p>
                        <div class="m-2">
                            <button type="button" style="width: 150px;" class="btn btn-primary me-2">Mua lại</button>
                            <button type="button" class="btn btn-light ">Liên hệ người bán</button>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="card my-3" style="width: 100%;">
                <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between">
                        <p class="fw-bold m-2">Cooler_Master_Store</p>
                        <p class="m-2"><span class="text-success"> Giao hàng thành công</span><span
                                class="mx-2">|</span><span class="text-uppercase text-danger">Hoàn thành</span></p>
                    </li>
                    <li class="list-group-item">
                        <div class="row g-0">
                            <div class="col-md-2">
                                <img src="https://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m0sg8p9yk2y5ef_tn"
                                    class="img-fluid rounded-start" alt="...">
                            </div>
                            <div class="col-md-10">
                                <div class="card-body">
                                    <h5 class="card-title">Đế Tản Nhiệt Laptop Cooler Master Notepal L1, Chống ồn, giữ
                                        Cho
                                        Laptop Mát Mẻ, Tương thích 17 Inch | Bảo Hành 2 Năm</h5>
                                    <p class="card-text position-relative">x1<span
                                            class="position-absolute  top-0 end-0">379.000₫</span></p>
                                    </p>
                                </div>
                    </li>
                    <li class="list-group-item d-flex flex-column align-items-end">
                        <p class="mb-2">Thành tiền: <span class="fs-3 text text-danger">315.649₫</span></p>
                        <div class="m-2">
                            <button type="button" style="width: 150px;" class="btn btn-primary me-2">Mua lại</button>
                            <button type="button" class="btn btn-light ">Liên hệ người bán</button>
                        </div>
                    </li>
                </ul>
            </div>


        </div>


    </div>
</body>

</html>
