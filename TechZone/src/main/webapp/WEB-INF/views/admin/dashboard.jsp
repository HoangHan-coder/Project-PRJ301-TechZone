<%-- 
    Document   : dashboard
    Created on : Oct 13, 2025, 7:11:10 PM
    Author     : letan
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bảng điều khiển</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f9fafb;
                font-family: "Inter", sans-serif;
            }
            .dashboard-card {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
                padding: 20px 0;
                text-align: center;
                transition: all 0.2s ease-in-out;
            }
            .dashboard-card:hover {
                transform: translateY(-3px);
                box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            }
            .dashboard-card h4 {
                font-size: 28px;
                font-weight: 600;
                color: #000;
            }
            .dashboard-card p {
                margin: 0;
                color: #6b7280;
                font-size: 15px;
            }


            /* Biểu đồ tròn */
            .pie-chart {
                width: 160px;
                height: 160px;
                border-radius: 50%;
                box-shadow: 0 2px 6px rgba(0,0,0,0.1);
                position: relative;
            }
            .pie-chart::after {
                content: "";
                position: absolute;
                width: 80px;
                height: 80px;
                background: #fff;
                border-radius: 50%;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            .legend-color {
                display: inline-block;
                width: 12px;
                height: 12px;
                border-radius: 3px;
                margin-right: 6px;
            }


            /* Giả lập biểu đồ cột */
            .bar-chart {
                display: flex;
                align-items: flex-end;
                justify-content: space-around;
                height: 200px;
                border-left: 2px solid #e5e7eb;
                border-bottom: 2px solid #e5e7eb;
                margin-top: 10px;
            }
            .bar {
                width: 40px;
                border-radius: 6px 6px 0 0;
                background: linear-gradient(to top, #60a5fa, #2563eb);
            }

            /* Bảng sản phẩm */
            .table-box {
                background: #fff;
                border-radius: 12px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
                padding: 20px;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div>
                <%@include file="../includes/slide-bar-admin.jsp" %>

            </div>
            <div style="margin: 0 0 50px 280px;">
                <div class="container mt-5">
                    <h4 class="fw-bold mb-4">Bảng điều khiển</h4>

                    <!-- 4 ô thống kê -->
                    <div class="row g-3 mb-4">
                        <div class="col-md-3 col-sm-6">
                            <div class="dashboard-card">
                                <h4>$${allprice}k</h4>
                                <p>Doanh thu</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="dashboard-card">
                                <h4>${allbill}</h4>
                                <p>Đơn hàng</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="dashboard-card">
                                <h4>${allproduct}</h4>
                                <p>Sản phẩm</p>
                            </div>
                        </div>
                        <div class="col-md-3 col-sm-6">
                            <div class="dashboard-card">
                                <h4>${allaccount}</h4>
                                <p>Người dùng</p>
                            </div>
                        </div>
                    </div>

                    <!-- Hàng biểu đồ -->
                    <div class="row g-3 mb-4">
                        <div class="col-md-6">
                            <div class="chart-box text-center">
                                <div class="d-flex justify-content-between align-items-center mb-3">
                                    <p class="fw-semibold mb-0">Doanh thu theo loại</p>
                                    <p class="text-secondary small mb-0">%</p>
                                </div>

                                <!-- Biểu đồ tròn -->
                                <div class="d-flex flex-column align-items-center">
                                    <div class="pie-chart mb-3" style="background: ${pieGradient};"></div>

                                    <div class="d-flex flex-column align-items-center text-secondary small">
                                        <c:forEach var="i" items="${listtotal}">
                                            <span>
                                                <span class="legend-color" style="background:#3b82f6;"></span> 
                                                ${i.name} - ${i.total}%
                                            </span>
                                        </c:forEach>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <!-- Biểu đồ cột giữ nguyên -->
                        <div class="col-md-6">
                            <div class="chart-box">
                                <div class="d-flex justify-content-between align-items-center">
                                    <p class="fw-semibold mb-0">Trạng thái đơn hàng</p>
                                </div>
                                <div class="bar-chart">
                                    <div class="bar" style="height: ${processing}px;"></div>
                                    <div class="bar" style="height: ${pending}px;"></div>
                                    <div class="bar" style="height: ${completed}px;"></div>
                                    <div class="bar" style="height: ${cancel}px;"></div>
                                </div>
                                <div class="d-flex justify-content-around text-secondary mt-2 small">
                                    <span>Đang xử lý</span>
                                    <span>Đang giao</span>
                                    <span>Hoàn thành</span>
                                    <span>Đã hủy</span>
                                </div>
                            </div>
                        </div>
                    </div>


                    <!-- Bảng sản phẩm bán chạy -->
                    <div class="table-box">
                        <div class="d-flex justify-content-between mb-3">
                            <p class="fw-semibold mb-0">Sản phẩm bán chạy</p>
                            <p class="fw-semibold mb-0">Doanh thu</p>
                        </div>
                        <table class="table mb-0">
                            <thead>
                                <tr class="text-secondary small">
                                    <th>Sản phẩm</th>
                                    <th>Số lượng</th>
                                    <th>Lượt bán</th>
                                    <th>Doanh thu</th>
                                </tr>
                            </thead>
                            <c:forEach var="i" items="${listall}">
                                <tbody>
                                    <tr>
                                        <td>${i.getName()}</td>
                                        <td>${i.sales}</td>
                                        <td>${i.sumquantity}</td>
                                        <td>${i.allprice}đ</td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

