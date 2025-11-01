<%-- 
    Document   : erorr
    Created on : Oct 31, 2025, 11:57:08 PM
    Author     : NgKaitou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width,initial-scale=1" />
        <title>404 — Page not found</title>
        <style>
            :root{
                --bg:#f7fbff;
                --card:#ffffff;
                --accent:#2563eb;
                --muted:#6b7280;
                --shadow: 0 8px 30px rgba(15,23,42,0.06);
                --radius:18px;
                font-family: Inter, ui-sans-serif, system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial;
            }
            *{
                box-sizing:border-box
            }
            body{
                margin:0;
                min-height:100vh;
                display:flex;
                align-items:center;
                justify-content:center;
                background:linear-gradient(180deg,var(--bg),#eef6ff);
                color:#0f172a;
                padding:32px;
            }

            .card{
                width:100%;
                max-width:980px;
                background:var(--card);
                border-radius:var(--radius);
                box-shadow:var(--shadow);
                display:flex;
                gap:32px;
                align-items:center;
                padding:36px;
            }

            /* Left: Illustration */
            .illustration{
                flex:0 0 320px;
                display:flex;
                align-items:center;
                justify-content:center;
                padding:12px;
            }
            .illustration svg{
                width:100%;
                max-width:260px;
                height:auto;
                display:block;
            }

            /* Right: content */
            .content{
                flex:1;
                min-width:0;
            }
            .code{
                font-weight:800;
                font-size:72px;
                line-height:1;
                color:var(--accent);
                margin:0 0 8px 0;
            }
            .title{
                margin:0;
                font-size:20px;
                font-weight:600;
            }
            .desc{
                margin:12px 0 20px 0;
                color:var(--muted);
                font-size:15px;
            }

            .actions{
                display:flex;
                gap:12px;
                flex-wrap:wrap;
            }
            .btn{
                display:inline-flex;
                align-items:center;
                gap:10px;
                padding:10px 16px;
                border-radius:10px;
                font-weight:600;
                text-decoration:none;
                cursor:pointer;
                border:0;
            }
            .btn-primary{
                background:var(--accent);
                color:white;
                box-shadow: 0 6px 18px rgba(37,99,235,0.18);
            }
            .btn-ghost{
                background:transparent;
                color:var(--accent);
                border:1px solid rgba(37,99,235,0.12);
            }

            /* small note */
            .hint{
                margin-top:14px;
                color:var(--muted);
                font-size:13px
            }

            /* Responsive */
            @media (max-width:820px){
                .card{
                    flex-direction:column;
                    padding:26px;
                    gap:18px;
                }
                .illustration{
                    flex-basis:auto
                }
                .code{
                    font-size:56px
                }
            }
            @media (max-width:420px){
                .card{
                    padding:18px;
                    border-radius:14px
                }
                .code{
                    font-size:44px
                }
                .title{
                    font-size:18px
                }
            }
        </style>
    </head>
    <body>
        <main class="card" role="main" aria-labelledby="errTitle">
            <div class="illustration" aria-hidden="true">
                <!-- Friendly SVG illustration (no external asset required) -->
                <svg viewBox="0 0 600 400" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Illustration">
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

            <div class="content">
                <p class="code">404</p>
                <h1 id="errTitle" class="title">Rất tiếc — trang bạn đang tìm không tồn tại.</h1>
                <p class="desc">Liên kết có thể đã bị hỏng hoặc trang đã bị xóa. Hãy quay lại trang chủ hoặc thử tìm kiếm nội dung bạn cần.</p>

                <div class="actions">
                    <a class="btn btn-primary" href="/" title="Quay về trang chủ">🏠 Quay về trang chủ</a>
                    <button class="btn btn-ghost" onclick="goBack()">⬅️ Quay lại</button>
                </div>

                <p class="hint">Nếu bạn nhập địa chỉ thủ công, vui lòng kiểm tra lại chính tả. Hoặc <a href="/contact">liên hệ hỗ trợ</a> nếu bạn cho rằng đây là lỗi của hệ thống.</p>
            </div>
        </main>

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

