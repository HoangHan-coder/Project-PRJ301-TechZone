<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Bộ lọc sản phẩm</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                height: 250px;
                object-fit: contain;
            }
            .card:hover {
                transform: scale(1.03);
                transition: 0.2s;
            }
            #product-list img {
                transition: transform 0.2s;
            }

            #product-list img:hover {
                transform: scale(1.05);
            }
        </style>
    </head>
    <body>

        <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

        <div class="container-fluid mt-3">
            <div class="row">
                <!-- Bộ lọc -->
                <div class="col-md-3">
                    <div class="filter-box">
                        <h3><i class="fa fa-filter"></i> Bộ Lọc Tìm Kiếm</h3>
                        <form id="filterForm">
                            <input type="hidden" name="action" value="filter"/>

                            <div class="filter-group">
                                <h3>Loại sản phẩm</h3>
                                <label><input type="radio" name="cateid" value="1"> Laptop</label><br>
                                <label><input type="radio" name="cateid" value="2"> Điện thoại</label><br>
                                <label><input type="radio" name="cateid" value="3"> Linh kiện</label>
                            </div>

                            <div class="filter-group">
                                <h3>Thương hiệu</h3>
                                <details>
                                    <summary><b>Laptop</b></summary>
                                    <label><input type="radio" name="brand" value="MSI"> MSI</label><br>
                                    <label><input type="radio" name="brand" value="TUF"> TUF</label><br>
                                    <label><input type="radio" name="brand" value="ACER"> ACER</label><br>
                                    <label><input type="radio" name="brand" value="LEGION"> LEGION</label>
                                </details>
                                <details>
                                    <summary><b>Điện thoại</b></summary>
                                    <label><input type="radio" name="brand" value="SAMSUNG"> SAMSUNG</label><br>
                                    <label><input type="radio" name="brand" value="IPHONE"> IPHONE</label><br>
                                    <label><input type="radio" name="brand" value="OPPO"> OPPO</label>
                                </details>
                                <details>
                                    <summary><b>Linh Kiện</b></summary>
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

                <!-- Danh sách sản phẩm -->
                <div class="col-md-9">
                    <div id="product-list" class="row mt-3">
                        <!-- Nội dung sản phẩm sẽ load bằng AJAX hoặc hiển thị khi có dữ liệu từ server -->
                        <c:if test="${requestScope.list ne null || (not empty param.action and param.action eq 'search')}">
                            <jsp:include page="/WEB-INF/views/user/product/product-list/filter-result.jsp"/>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>

        <!-- JS -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script>
            $(function () {

                // Khi thay đổi bộ lọc → load lại trang 1
                $('#filterForm input').on('change', function () {
                    loadProducts(1);
                });

                // Hàm load sản phẩm bằng AJAX
                function loadProducts(page) {
                    let formData = $('#filterForm').serialize() + '&page=' + page;
                    $.ajax({
                        url: '${pageContext.request.contextPath}/search',
                        type: 'POST',
                        data: formData,
                        success: function (data) {
                            $('#product-list').html(data);
                        },
                        error: function () {
                            $('#product-list').html("<p class='text-center text-danger mt-4'>Vui lòng chọn loại sản phẩm.</p>");
                        }
                    });
                }

                // Khi click vào nút phân trang
                $(document).on('click', '.page-btn', function (e) {
                    e.preventDefault();
                    let page = $(this).data('page');
                    loadProducts(page);
                });
            });
        </script>

        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
           <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
