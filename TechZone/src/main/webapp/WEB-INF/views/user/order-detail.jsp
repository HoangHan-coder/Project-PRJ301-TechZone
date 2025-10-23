<%-- 
    Document   : order-detail
    Created on : Sep 20, 2025, 8:34:46 PM
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
        <title>Tech Zone</title>
    </head>

    <body>
        <div>
            <jsp:include page="/WEB-INF/views/user/navbar.jsp"/>
        </div>
        
        
        <div class="container">
            <div class="row">
                <ul class="nav justify-content-between align-items-center shadow-sm p-3 my-3 bg-body-tertiary rounded">
                    <li class="nav-item fs-4 text">
                        <p class="m-0 opacity-75" style="cursor: pointer;"><a name="action" href="http://localhost:8080/TechZone/order?action=order-list" class="text-black" style="text-decoration: none;"><i class="bi bi-chevron-left"></i>Trở lại</a></p>
                    </li>
                    <div class="d-flex justify-content-end gap-4">
                        <li class="nav-item">
                            <p class="m-0">MÃ ĐƠN HÀNG. 2507132YN042CK</p>
                        </li>
                        <li class="nav-item">
                            <p class="m-0">|</p>
                        </li>
                        <li class="nav-item">
                            <p class="m-0">Đơn hàng đã hoàn thành</p>
                        </li>
                    </div>
                </ul>
            </div>
            <div class="row">
                <div class="card">
                    <div class="card-body">

                        <h5 class="card-title">Địa chỉ nhận hàng</h5>
                        <p class="card-text">(+84) 706 622 248</p>
                        <p class="card-text">170/77 Đ. Hoàng Quốc Việt, An Bình, Ninh Kiều, Cần Thơ, Phường An Bình, Quận
                            Ninh Kiều, Cần Thơ</p>
                    </div>
                </div>
                <div class="card my-3">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <p class="m-0">ZIYOULANG</p>
                        <p class="m-0"><i class="bi bi-exclamation-circle"></i></p>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <div class="row g-0" style="height: 240px;">
                                <div class="col-md-2 h-100">
                                    <img src="./image.png" class="img-fluid rounded-start h-100" alt="...">
                                </div>
                                <div class="col-md-10">
                                    <div class="card-body">
                                        <h5 class="card-title">Switch Bàn Phím Cơ Gaming ZiyouLang Công Tắc Phím Attack
                                            Shark Blue Switch/
                                            Red Switch/ Yellow Switch/White Switch 3 Pin</h5>
                                        <p class="card-text d-flex justify-content-between fs-5 text"><span>Phân loại hàng: Combo 5
                                                Xanh Đậm</span><span class="text-danger">60.000₫</span></p>
                                        <p class="card-text fs-5 text-black"><small class="text-body-secondary">X4</small></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td>Tổng tiền hàng</td>
                                <td>56.000₫</td>
                            </tr>
                            <tr>
                                <td>Phí vận chuyển</td>
                                <td>28.700₫</td>
                            </tr>
                            <tr>
                                <td>Giảm giá phí vận chuyển <i class="bi bi-exclamation-circle"></i></td>
                                <td>-28.700₫</td>
                            </tr>
                            <tr>
                                <td>Voucher từ Shopee</td>
                                <td>-25.000₫</td>
                            </tr>
                            <tr>
                                <td>Thành tiền</td>
                                <td class="fs-4 text text-danger">31.000₫</td>
                            </tr>
                            <tr>
                                <td>Phương thức Thanh toán</td>
                                <td>TK Ngân hàng</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
         <jsp:include page="/WEB-INF/views/user/footer.jsp"/>
    </body>

</html>