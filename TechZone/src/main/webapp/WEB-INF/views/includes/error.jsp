    <%-- 
    Document   : erorr
    Created on : Oct 31, 2025, 11:57:08 PM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>404 — Page not found</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body class="bg-light">
            <%@include file="navbar.jsp" %>
        <main class="container py-5" role="main" aria-labelledby="errTitle">
            <div class="row justify-content-center">
                <div class="col-12 col-lg-10">
                    <div class="card shadow-lg border-0 rounded-4 p-4 p-md-5">
                        <div class="row g-4 align-items-center">
                            <div class="col-12 col-md-5" aria-hidden="true">
                                <div class="d-flex justify-content-center">
                                    <svg viewBox="0 0 600 400" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Illustration" class="img-fluid" style="max-width:260px;">
                                        <defs>
                                            <linearGradient id="g1" x1="0" x2="1">
                                                <stop offset="0" stop-color="#cfe8ff"/>
                                                <stop offset="1" stop-color="#eef6ff"/>
                                            </linearGradient>
                                        </defs>
                                        <rect rx="20" width="600" height="400" fill="url(#g1)"/>
                                        <g transform="translate(120,70)">
                                            <g transform="scale(0.9)">
                                                <circle cx="160" cy="120" r="70" fill="#fff" stroke="#e6f0ff" stroke-width="10"/>
                                                <path d="M110 170 q50 40 100 0" fill="none" stroke="#cbdffd" stroke-width="12" stroke-linecap="round"/>
                                                <text x="135" y="125" font-size="46" fill="#1e3a8a" font-weight="700">404</text>
                                            </g>
                                        </g>
                                    </svg>
                                </div>
                            </div>
                            <div class="col-12 col-md-7">
                                <p class="display-4 fw-bold text-primary mb-1">404</p>
                                <h1 id="errTitle" class="h4 fw-semibold mb-2">Rất tiếc — trang bạn đang tìm không tồn tại.</h1>
                                <p class="text-secondary mb-4">Liên kết có thể đã bị hỏng hoặc trang đã bị xóa. Hãy quay lại trang chủ hoặc thử tìm kiếm nội dung bạn cần.</p>
                                <div class="d-flex flex-wrap gap-2">
                                    <a class="btn btn-primary" href="/" title="Quay về trang chủ">🏠 Quay về trang chủ</a>
                                    <button class="btn btn-outline-primary" onclick="goBack()">⬅️ Quay lại</button>
                                </div>
                                <p class="small text-secondary mt-3">Nếu bạn nhập địa chỉ thủ công, vui lòng kiểm tra lại chính tả. Hoặc <a href="/contact">liên hệ hỗ trợ</a> nếu bạn cho rằng đây là lỗi của hệ thống.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <%@include file="footer.jsp" %>
        <script>
            function goBack() {
                if (document.referrer)
                    history.back();
                else
                    window.location.href = '/';
            }
        </script>
    </body>
</html>

