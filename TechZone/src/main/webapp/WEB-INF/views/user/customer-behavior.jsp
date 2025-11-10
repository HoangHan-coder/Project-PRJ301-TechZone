<%-- 
    Document   : CustomerBehavior
    Created on : Nov 9, 2025
    Author     : letan
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Product Suggestions</title>

        <style>
            :root {
                --bg: #f5f7fb;
                --card: #fff;
                --text: #1f2937;
                --muted: #6b7280;
                --accent: #0b66ff;
                --danger: #e53935;
                --radius: 14px;
                --shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
            }

            * {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                background: var(--bg);
                font-family: "Segoe UI", Roboto, sans-serif;
                color: var(--text);
            }

            /* Accessibility skip link */
            .skip-link {
                position: absolute;
                left: -9999px;
                top: auto;
                width: 1px;
                height: 1px;
                overflow: hidden;
            }

            .skip-link:focus {
                left: 12px;
                top: 12px;
                width: auto;
                height: auto;
                background: #000;
                color: #fff;
                padding: 8px 12px;
                border-radius: 8px;
                z-index: 1000;
            }

            .container {
                padding: 0 16px;
            }

            h2.title {
                text-align: center;
                font-size: 1.8rem;
                margin-bottom: 24px;
                margin-top: 25px;
                font-weight: 700;
            }

            .product-grid {
                display: flex;
                gap: 20px;
                margin-bottom: 40px;
            }

            /* Card */
            .card {
                background: var(--card);
                border-radius: var(--radius);
                box-shadow: var(--shadow);
                overflow: hidden;
                width: 413px;
                display: flex;
                flex-direction: column;
                transition: transform .25s ease, box-shadow .25s ease;
            }

            .card:hover {
                transform: translateY(-6px);
                box-shadow: 0 10px 24px rgba(0, 0, 0, 0.15);
            }

            .card-media {
                text-align: center;
                height: 280px;
                overflow: hidden;
                position: relative;
                background: #f3f4f6;
            }

            .card-media img {
                height: 100%;
                object-fit: cover;
                transition: transform .4s ease;
            }

            .card:hover .card-media img {
                transform: scale(1.05);
            }

            .card-body {
                padding: 16px 18px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                flex: 1;
            }

            .title {
                font-size: 1.05rem;
                font-weight: 600;
                color: var(--text);
                margin-bottom: 10px;
                line-height: 1.3;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
            }

            .price {
                font-weight: 700;
                color: var(--danger);
                font-size: 1.05rem;
            }

            .price-row {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: auto;
            }

            .btn {
                background: var(--accent);
                color: #fff;
                padding: 8px 14px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: 600;
                transition: background .2s;
            }

            .btn:hover {
                background: #054ecb;
            }

            .img-fallback {
                display: flex;
                justify-content: center;
                align-items: center;
                color: var(--muted);
                height: 100%;
                font-size: 2.2rem;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty listcustomer}">
            <div class="container" id="main">
                <a href="#main" class="skip-link">Skip to product section</a>
                <h2 class="title">Recommended Products for You</h2>

                <div class="product-grid">
                    <c:forEach var="p" items="${listcustomer}">
                        <article class="card" role="article" aria-labelledby="title-${p.productId}">
                            <a href="products?action=detail&id=${p.productId}" class="card-media" title="${p.productName}">
                                <c:choose>
                                    <c:when test="${not empty p.linkImg}">
                                        <img src="${pageContext.request.contextPath}/${p.linkImg}" alt="${p.productName}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="img-fallback">?</div>
                                    </c:otherwise>
                                </c:choose>
                            </a>

                            <div class="card-body">
                                <h3 id="title-${p.productId}" class="title">${p.productName}</h3>

                                <div class="price-row">
                                    <div class="price">
                                        <fmt:formatNumber value="${p.productPrice}" type="number" maxFractionDigits="0"/> VND
                                    </div>
                                    <a class="btn" href="products?action=detail&id=${p.productId}">View Details</a>
                                </div>
                            </div>
                        </article>
                    </c:forEach>
                </div>
            </div>
        </c:if>
    </body>
</html>
