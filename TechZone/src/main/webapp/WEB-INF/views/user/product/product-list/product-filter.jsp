<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<head>
    <meta charset="UTF-8">
    <title>Search Page</title>


    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .filter-box {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .filter-group h3 {
            font-size: 18px;
            margin-top: 15px;
        }

        .card img {
            height: 300px;
            object-fit: contain;
        }

        .card {
            border-radius: 15px;
            transition: transform 0.2s ease;
        }

        .card:hover {
            transform: scale(1.03);
        }
    </style>

</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
    <div class="container-fluid box-1 mt-3">
        <div class="row">
            <!-- Cot loc -->
            <div class="col-md-3 inner-1">
                <div class="filter-box">
                    <h3><i class="fa fa-filter"></i> Bộ Lọc Tìm Kiếm</h3>

                    <form id="filterForm">
                        <input type="hidden" name="action" value="filter"/>


                        <div class="filter-group">
                            <h3>Loại:</h3>
                            <label><input type="radio" name="cateid" value="2"/> Điện Thoại</label><br>
                            <label><input type="radio" name="cateid" value="1"/> Laptop</label><br>
                            <label><input type="radio" name="cateid" value="3"/> Linh Kiện</label><br>
                        </div>


                        <div class="filter-group">
                            <h3>Thương hiệu:</h3>
                            <details>
                                <summary><strong>Máy Tính Xách Tay</strong></summary>
                                <label><input type="radio" name="brand" value="MSI"/> MSI</label><br>
                                <label><input type="radio" name="brand" value="TUF"/> TUF</label><br>
                                <label><input type="radio" name="brand" value="ACER"/> ACER</label><br>
                                <label><input type="radio" name="brand" value="LEGION"/> LEGION</label><br>
                            </details>

                            <details>
                                <summary><strong>Điện Thoại Thông Minh</strong></summary>
                                <label><input type="radio" name="brand" value="SAMSUNG"/> SAMSUNG</label><br>
                                <label><input type="radio" name="brand" value="IPHONE"/> IPHONE</label><br>
                                <label><input type="radio" name="brand" value="OPPO"/> OPPO</label><br>
                            </details>

                            <details>
                                <summary><strong>Linh Kiện</strong></summary>
                                <label><input type="radio" name="brand" value="WD"/> WD</label><br>
                                <label><input type="radio" name="brand" value="SONY"/> SONY</label><br>
                                <label><input type="radio" name="brand" value="LOGITECH"/> LOGITECH</label><br>
                                <label><input type="radio" name="brand" value="ANKER"/> ANKER</label><br>
                                <label><input type="radio" name="brand" value="SEAGATE"/> SEAGATE</label><br>
                                <label><input type="radio" name="brand" value="BASEUS"/> BASEUS</label><br>
                            </details>
                        </div>
                    </form>
                </div>
            </div>

            <!-- DANH SÁCH San phaM -->
            <div class="col-md-9">
                <div id="product-list" class="row mt-3">
                    <c:forEach items="${list}" var="i">
                        <div class="col-md-4 mb-4">
                            <div class="card h-100 text-center shadow-sm">
                                <img src="${pageContext.request.contextPath}${i.linkImg}" class="card-img-top p-2" alt="${i.productName}">
                                <div class="card-body">
                                    <h5 class="card-title">${i.productName}</h5>
                                    <p class="card-text text-danger font-weight-bold">
                                        <fmt:formatNumber value="${i.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </p>
                                    <a href="products?action=detail&id=${i.productId}" class="btn btn-primary">View Detail</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <!-- AJAX FILTER -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(function () {
            $('#filterForm input').on('change', function () {
                let formData = $('#filterForm').serialize();
                $.ajax({
                    url: '${pageContext.request.contextPath}/search',
                    type: 'POST',
                    data: formData,
                    success: function (data) {
                        $('#product-list').html(data);
                    },
                    error: function (xhr, status, error) {
                        console.error("Lại Ajax:", error);
                        $('#product-list').html("<p class='text-danger text-center' style='font-size:20px; margin-top:30px;'>Vui lòng chọn loại mặt hàng trước khi chọn thương hiệu.</p>");

                    }
                });
            });
        });
    </script>
    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

