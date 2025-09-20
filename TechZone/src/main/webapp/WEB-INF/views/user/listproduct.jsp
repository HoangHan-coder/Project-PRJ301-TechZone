<%-- 
    Document   : listproduct
    Created on : Sep 17, 2025, 7:41:00 AM
    Author     : letan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="assets/css/list.css"/>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="box-1">
            <div class="row" >
                <div class="col-md-3 inner-1">
                    <div class="filter-box">
                        <h3><i class="fa fa-filter"></i> BỘ LỌC TÌM KIẾM</h3>

                        <!-- Nơi Bán -->
                        <div class="filter-group">
                            <h4>Loại: </h4>
                            <div class="box-2"><label><input type="radio" name="shipping" ></label><p>Điện thoại thông minh</p></div>
                            <label><input type="radio" name="shipping" > Máy tính xách tay</label>
                            <label><input type="radio" name="shipping" > Máy tính bảng</label>
                            <label><input type="radio" name="shipping" > Máy tính để bàn</label>                            
                            <label><input type="radio" name="shipping" > Đồng hồ thông minh</label>
                            <label><input type="radio" name="shipping" > Tivi thông minh</label>
                            <label><input type="radio" name="shipping" > Tai nghe</label>
                        </div>

                        <!-- Đơn Vị Vận Chuyển -->
                        <div class="filter-group">
                            <h4>Đơn Vị Vận Chuyển</h4>
                            <label><input type="checkbox"> Nhanh</label>
                            <label><input type="checkbox"> Tiết Kiệm</label>
                        </div>

                        <!-- Thương Hiệu -->
                        <div class="filter-group">
                            <h4>Thương Hiệu</h4>
                            <label><input type="checkbox"> Baseus</label>
                            <label><input type="checkbox"> GOOJODOQ</label>
                            <label><input type="checkbox"> Basefast</label>
                            <label><input type="checkbox"> S P</label>
                            <a href="#" class="more">Thêm ▾</a>
                        </div>
                    </div>

                </div>
                <div class="col-md-9">
                    <div class="sort-bar">
                        <span class="sort-label">Sắp xếp theo</span>
                        <button class="sort-btn active">Phổ Biến</button>
                        <button class="sort-btn">Mới Nhất</button>
                        <button class="sort-btn">Bán Chạy</button>

                        <select class="sort-select">
                            <option value="asc">Giá: Thấp đến Cao</option>
                            <option value="desc">Giá: Cao đến Thấp</option>
                        </select>

                        <div class="pagination">
                            <span class="page-info">1/7</span>
                            <button class="page-btn" disabled>&lt;</button>
                            <button class="page-btn">&gt;</button>
                        </div>
                    </div>
                    <div class="box-4">
                        <c:forEach items="${list}" var="i">
                            <div class="card">
                                <div class="category">3D Printers</div>
                                <div class="icons">
                                    <span><i class="fa-regular fa-heart"></i></span>
                                    <span><i class="fa-solid fa-arrow-up-right-from-square"></i></span>
                                </div>
                                <img src="${i.linkImg}" alt="Computer Case">
                                <div class="title">${i.productName}</div>
                                <div class="price">$${i.productPrice}</div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
