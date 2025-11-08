<%-- 
    Document   : feedback-detail
    Created on : Oct 26, 2025, 6:38:42 PM
    Author     : letan
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Chi tiết Phản hồi</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                font-family: "Segoe UI", Tahoma, sans-serif;
                background: #f8fafc;
                margin: 0;
                padding: 0;
            }

            .container {
                max-width: 900px;
                margin: 50px auto;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
                padding: 35px 40px;
            }

            h1 {
                text-align: center;
                color: #1e3a8a;
                font-size: 26px;
                margin-bottom: 30px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .feedback-header {
                display: flex;
                align-items: center;
                gap: 20px;
                margin-bottom: 25px;
                border-bottom: 2px solid #e5e7eb;
                padding-bottom: 15px;
            }

            .user-avatar {
                width: 80px;
                height: 80px;
                border-radius: 50%;
                object-fit: cover;
                border: 3px solid #2563eb;
            }

            .user-info {
                flex: 1;
            }

            .user-info h2 {
                margin: 0;
                font-size: 22px;
                color: #111827;
            }

            .user-info p {
                margin: 4px 0;
                color: #6b7280;
            }

            .feedback-info {
                background: #f9fafb;
                border-radius: 10px;
                padding: 20px;
                margin-bottom: 25px;
            }

            .feedback-info h3 {
                color: #2563eb;
                margin-bottom: 10px;
                font-size: 18px;
            }

            .info-item {
                margin: 10px 0;
                font-size: 15px;
                color: #374151;
            }

            .info-item span {
                font-weight: 600;
                color: #111827;
            }

            .rating {
                display: flex;
                gap: 4px;
                margin-top: 5px;
            }

            .rating i {
                color: #fbbf24;
            }

            .feedback-message {
                background: #eff6ff;
                border-left: 4px solid #2563eb;
                padding: 20px;
                border-radius: 8px;
                color: #1e3a8a;
                line-height: 1.6;
                font-size: 15px;
                margin-bottom: 25px;
            }

            /* Form phản hồi của admin */
            .admin-response {
                background: #f9fafb;
                border: 1px solid #dbeafe;
                border-radius: 10px;
                padding: 20px;
                margin-bottom: 25px;
            }

            .admin-response h3 {
                color: #1e3a8a;
                font-size: 18px;
                margin-bottom: 10px;
            }

            .admin-response textarea {
                width: 100%;
                min-height: 120px;
                resize: vertical;
                padding: 12px 14px;
                font-size: 15px;
                border-radius: 8px;
                border: 1px solid #cbd5e1;
                outline: none;
                transition: 0.25s;
            }

            .admin-response textarea:focus {
                border-color: #2563eb;
                box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.15);
            }

            .admin-response button {
                margin-top: 12px;
                background: #2563eb;
                color: white;
                border: none;
                border-radius: 8px;
                padding: 10px 20px;
                font-size: 15px;
                cursor: pointer;
                font-weight: 600;
                transition: all 0.25s ease;
            }

            .admin-response button:hover {
                background: #1e40af;
                box-shadow: 0 3px 6px rgba(37, 99, 235, 0.3);
            }

            .actions {
                text-align: center;
                display: flex;
                justify-content: center;
                gap: 15px;
            }

            .btn {
                padding: 10px 18px;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-weight: 600;
                font-size: 15px;
                display: inline-flex;
                align-items: center;
                gap: 8px;
                transition: all 0.25s ease;
            }

            .btn-respond {
                background-color: #22c55e;
                color: white;
            }

            .btn-respond:hover {
                background-color: #15803d;
                box-shadow: 0 3px 6px rgba(34, 197, 94, 0.3);
            }

            .btn-delete {
                background-color: #ef4444;
                color: white;
            }

            .btn-delete:hover {
                background-color: #b91c1c;
                box-shadow: 0 3px 6px rgba(239, 68, 68, 0.3);
            }

            @media (max-width: 600px) {
                .feedback-header {
                    flex-direction: column;
                    text-align: center;
                }
            }
        </style>
    </head>

    <body>
        <div class="container-fluid">
            <div>
                <%@include file="../../includes/slide-bar-admin.jsp" %>
            </div>
            <div style="margin: 0 0 50px 280px;">
                <div class="container">
                    <h1>Chi tiết Phản hồi</h1>

                    <div class="feedback-info">
                        <h3>Thông tin phản hồi</h3>
                        <div class="info-item"><span>Sản phẩm:</span> ${data.product.productName}</div>
                        <div class="info-item"><span>Ngày gửi:</span> ${data.createAt}</div>

                        <div class="info-item">
                            <span>Đánh giá:</span>
                            <div class="rating">
                                <c:forEach var="i" begin="1" end="5">
                                    <c:choose>
                                        <c:when test="${i <= data.rating}">
                                            <i class="fa-solid fa-star"></i>
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fa-regular fa-star"></i>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="feedback-message">${data.message}
                    </div>

                    <!-- Form phản hồi của admin -->
                    <div class="admin-response">
                        <h3>Phản hồi của Admin</h3>
                        <form method="POST" action="${pageContext.request.contextPath}/admin/feedback">
                            <textarea name="adminReply" placeholder="Nhập nội dung phản hồi cho khách hàng...">${data.responseMessage}</textarea>
                            <input type="hidden" name="id" value="${data.feedbackId}" />
                            <button type="submit">
                                <i class="fa-solid fa-paper-plane"></i> Gửi phản hồi
                            </button>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
