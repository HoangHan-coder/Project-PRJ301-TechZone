<%-- 
    Document   : feelbac
    Created on : Oct 26, 2025, 3:23:37 PM
    Author     : letan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Danh sách Phản hồi</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            body {
                font-family: "Segoe UI", Tahoma, sans-serif;
                background: #f4f6f9;
                margin: 0;
                padding: 40px;
                color: #333;
            }

            h1 {
                text-align: center;
                color: #1e3a8a;
                margin-bottom: 30px;
                font-size: 28px;
                font-weight: 700;
                text-transform: uppercase;
            }

            .feedback-container {
                max-width: 1150px;
                margin: 0 auto;
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 4px 14px rgba(0, 0, 0, 0.08);
                padding: 30px 35px;
            }

            /* Bộ lọc */
            .filter-bar {
                display: flex;
                flex-wrap: wrap;
                gap: 12px;
                margin-bottom: 25px;
                justify-content: space-between;
                align-items: center;
            }

            .filter-bar input,
            .filter-bar select {
                padding: 10px 14px;
                border: 1px solid #cbd5e1;
                border-radius: 8px;
                font-size: 15px;
                flex: 1;
                min-width: 200px;
                transition: all 0.25s ease;
            }

            .filter-bar input:focus,
            .filter-bar select:focus {
                border-color: #3b82f6;
                box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
                outline: none;
            }

            .filter-bar button {
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

            .filter-bar button:hover {
                background: #1e40af;
                box-shadow: 0 4px 10px rgba(37, 99, 235, 0.3);
            }

            /* Bảng phản hồi */
            table {
                width: 100%;
                border-collapse: collapse;
                font-size: 15px;
                border-radius: 10px;
            }

            /* Header */
            th {
                background: #1e40af;
                color: white;
                text-align: center;
                padding: 14px 10px;
                font-weight: 600;
                z-index: 1;
            }

            td {
                padding: 12px 10px;
                text-align: center;
                border-bottom: 1px solid #e5e7eb;
                max-width: 300px;
                white-space: nowrap;
                overflow-x: auto;       /* Cho phép cuộn ngang khi dài quá */
                overflow-y: hidden;
                text-overflow: clip;    /* Không hiện dấu ... nữa */
                scrollbar-width: thin;  /* Thanh cuộn mảnh (Firefox) */
            }

            td::-webkit-scrollbar {
                height: 6px; /* Thanh cuộn ngang thấp */
            }

            td::-webkit-scrollbar-thumb {
                background: #9ca3af;
                border-radius: 10px;
            }

            td::-webkit-scrollbar-thumb:hover {
                background: #4b5563;
            }


            /* Khi hover tr */
            tr:hover {
                background: #f1f5f9;
                transition: background 0.25s;
            }


            /* Nút hành động */
            .actions {
                display: flex;
                justify-content: center;
                gap: 10px;
            }

            .action-btn {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: 6px;
                border: none;
                border-radius: 6px;
                padding: 8px 14px;
                font-size: 14px;
                font-weight: 500;
                cursor: pointer;
                transition: all 0.2s ease;
            }

            .btn-response {
                background-color: #22c55e;
                color: white;
            }
            .btn-response:hover {
                background-color: #15803d;
                transform: translateY(-2px);
                box-shadow: 0 3px 6px rgba(34, 197, 94, 0.3);
            }

            .btn-delete {
                background-color: #ef4444;
                color: white;
            }
            .btn-delete:hover {
                background-color: #b91c1c;
                transform: translateY(-2px);
                box-shadow: 0 3px 6px rgba(239, 68, 68, 0.3);
            }


            @media (max-width: 768px) {
                table, thead, tbody, th, td, tr {
                    display: block;
                }
                thead {
                    display: none;
                }
                tr {
                    background: #fff;
                    margin-bottom: 15px;
                    border-radius: 10px;
                    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
                    padding: 10px;
                }
                td {
                    border: none;
                    display: flex;
                    justify-content: space-between;
                    padding: 8px;
                }
                td::before {
                    content: attr(data-label);
                    font-weight: 600;
                    color: #555;
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
                <h1>Danh sách Phản hồi</h1>

                <div class="feedback-container">
                    <div class="filter-bar">
                        <form id="filterForm" action="${pageContext.request.contextPath}/admin/feedback" method="get">
                            <input type="text" name="keyword" placeholder="Tìm theo tên sản phẩm hoặc khách hàng..." />

                            <select name="rating" id="ratingSelect">
                                <option value="">Chọn số sao</option>
                                <option value="5">5 sao</option>
                                <option value="4">4 sao</option>
                                <option value="3">3 sao</option>
                                <option value="2">2 sao</option>
                                <option value="1">1 sao</option>
                            </select>

                            <button type="submit">Tìm kiếm</button>
                        </form>
                    </div>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên khách hàng</th>
                                <th>Sản phẩm</th>
                                <th>Nội dung</th>
                                <th>Số sao</th>
                                <th>Ngày phản hồi</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty list}">
                                <tr>
                                    <td colspan="7" style="text-align:center; color:#888; font-style:italic; padding:20px;">
                                        <i class="fa-solid fa-circle-exclamation" style="color:#ff7b00;"></i>
                                        Không tìm thấy phản hồi nào phù hợp
                                    </td>
                                </tr>
                            </c:if>

                            <c:forEach var="i" items="${list}">
                                <tr>
                                    <td>${i.feedbackId}</td>
                                    <td>${i.account.fullName}</td>
                                    <td>${i.product.productName}</td>
                                    <td>${i.message}</td>
                                    <td>${i.rating}</td>
                                    <td>
                            <fmt:formatDate value="${i.responseAt}" pattern="yyyy-MM-dd" />
                            </td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/admin/feedback?id=${i.feedbackId}">
                                    <button type="button" class="action-btn btn-response">
                                        <i class="fa-solid fa-reply"></i> Phản hồi
                                    </button>
                                </a>
                                <form method="POST" action="${pageContext.request.contextPath}/admin/feedback?type=delete&id=${i.feedbackId}">
                                    <button type="submit" class="action-btn btn-delete">
                                        <i class="fa-solid fa-trash"></i> Xóa
                                    </button>
                                </form>
                            </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>

                        <c:if test="${!empty list}">
                        <%@include file="../../includes/pagination.jsp" %>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>

