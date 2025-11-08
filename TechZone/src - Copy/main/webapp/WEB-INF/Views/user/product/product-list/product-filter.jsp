<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="assets/css/list.css"/>
        <title>Danh sách sản phẩm</title>
    </head>
    <body>
        <div class="container-fluid box-1 mt-3">
            <div class="row">
                <!-- CỘT LỌC -->
                <div class="col-md-3 inner-1">
                    <div class="filter-box">
                        <h3><i class="fa fa-filter"></i> BỘ LỌC TÌM KIẾM</h3>

                        <form id="filterForm">
                            <input type="hidden" name="action" value="filter">

                            <!-- Loại sản phẩm -->
                            <div class="filter-group">
                                <h3>Loại:</h3>
                                <label><input type="radio" name="cateid" value="2"> Điện thoại</label><br>
                                <label><input type="radio" name="cateid" value="1"> Laptop</label><br>
                                <label><input type="radio" name="cateid" value="3"> Linh kiện</label><br>
                            </div>

                            <!-- Thương hiệu -->
                            <div class="filter-group ">
                                <h3>Thương hiệu:</h3>
                                <h4 style="color: red"> Máy Tính Xách Tay <h4>
                                        <label><input type="radio" name="brand" value="MSI"> MSI</label><br>
                                        <label><input type="radio" name="brand" value="TUF"> TUF</label><br>
                                        <label><input type="radio" name="brand" value="ACER"> ACER</label><br>
                                        <label><input type="radio" name="brand" value="LEGION"> LEGION</label><br>
                                        <h4 style="color: red"> Điện Thoại Thông Minh <h4>
                                                <label><input type="radio" name="brand" value="SAMSUNG"> SAMSUNG</label><br>
                                                <label><input type="radio" name="brand" value="IPHONE"> IPHONE</label><br>
                                                <label><input type="radio" name="brand" value="OPPO"> OPPO</label><br>
                                                <h4 style="color: red"> LINH KIỆN<h4>
                                                        <label><input type="radio" name="brand" value="WD"> WD</label><br>
                                                        <label><input type="radio" name="brand" value="SONY"> SONY</label><br>
                                                        <label><input type="radio" name="brand" value="LOGITECH"> LOGITECH</label><br>
                                                        <label><input type="radio" name="brand" value="ANKER"> ANKER</label><br>
                                                        <label><input type="radio" name="brand" value="SEAGATE"> SEAGATE</label><br>
                                                        <label><input type="radio" name="brand" value="BASEUS"> BASEUS</label><br>
                                                        </div>
                                                        </form>
                                                        </div>
                                                        </div>

                                                        <!-- CỘT DANH SÁCH SẢN PHẨM -->
                                                        <div class="col-md-9">
                                                            <div id="product-list" class="row mt-3">
                                                                <c:forEach items="${list}" var="i">
                                                                    <div class="col-md-4 mb-4">
                                                                        <div class="card h-100 text-center shadow-sm">
                                                                            <img src="${i.linkImg}" class="card-img-top p-2" alt="${i.productName}">
                                                                            <div class="card-body">
                                                                                <h5 class="card-title">${i.productName}</h5>
                                                                                <p class="card-text text-danger font-weight-bold"><fmt:formatNumber value="${i.productPrice}" type="number" maxFractionDigits="0"/>VND</p>
                                                                                 <a href="products?action=detail&id=${i.productId}" class="btn btn-primary">View Detail</a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                        </div>
                                                        </div>

                                                        <!-- JQuery + Ajax -->
                                                        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                                                        <script>
                                                            $(function () {
                                                                $('#filterForm input').on('change', function () {
                                                                    let formData = $('#filterForm').serialize();
                                                                    $.ajax({
                                                                        url: '${pageContext.request.contextPath}/products',
                                                                        type: 'POST',
                                                                        data: formData,
                                                                        success: function (data) {
                                                                            $('#product-list').html(data); // cập nhật HTML danh sách
                                                                        },
                                                                        error: function (xhr, status, error) {
                                                                            console.error("Lỗi Ajax:", error);
                                                                            $('#product-list').html("<p class='text-danger'>Lỗi tải dữ liệu.</p>");
                                                                        }
                                                                    });
                                                                });
                                                            });


                                                        </script>

                                                        </body>
                                                        </html>
