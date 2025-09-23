<%-- 
    Document   : list-voucher
    Created on : Sep 18, 2025, 10:50:53 PM
    Author     : NgKaitou
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </head>

    <body>
        <div>
            <jsp:include page="/WEB-INF/views/user/navbar.jsp"/>
        </div>
        <div class="container shadow-lg p-3 mb-5 bg-body-tertiary rounded my-5" style="width: 930px;">
            <ul class="nav d-flex justify-content-between ">
                <li class="nav-item">
                    <a class="nav-link text-black fs-4" href="#">Kho Voucher</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link  d-inline-block" href="#">Tìm thêm voucher</a>
                    <i>|</i>
                    <a class="nav-link  d-inline-block" href="#">Xem lịch sử voucher</a>
                </li>
            </ul>

            <div class="search-voucher bg-secondary-subtle d-flex align-items-center m-4" style="height: 100px;">
                <div class="search-voucher px-4 d-flex gap-4 justify-content-center  ">
                    <label for="input" class="form-label p-1">Mã Voucher</label>
                    <input type="search" class="form-control opacity-50" id="input" placeholder="Nhập mã voucher tại đây"
                           style="width: 500px;">
                    <button type="button" class="btn btn-outline-secondary" style="width: 100px;">Lưu</button>
                </div>
            </div>

            <div class="list-voucher py-4">
                <div class="container">
                    <div class="row">
                        <c:forEach items="${listVoucher}" var="i">
                            <div class="col-md-6">
                            <div class="card mb-3" style="max-width: 540px;">
                                <div class="row g-0">
                                    <div class="col-md-4 d-flex align-items-md-center">
                                        <img src="<%= request.getContextPath() %>/assets/images/vouchers/voucher.jpg" 
                                             class="img-fluid rounded-start" alt="...">
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">${i.code}</h5>
                                            <p class="card-text"> Giảm: ${i.discountValue}</p>
                                            <p class="card-text"> Cho sản phẩn từ ${i.condition} VND</p>
                                            <p class="card-text"><small class="text-body-secondary"> Thời hạn:  ${i.endDate}</small></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
         <jsp:include page="/WEB-INF/views/user/footer.jsp"/>
    </body>

</html>
