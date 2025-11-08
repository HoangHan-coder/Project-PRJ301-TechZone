<%-- 
    Document   : voucher-management-admin
    Created on : Oct 20, 2025, 10:12:06 AM
    Author     : NgKaitou
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TechZone</title>
    </head>
    <body>

        <div class="container-fluid">
            <div>
                <%@include file="../../includes/slide-bar-admin.jsp" %>
            </div>
            <div style="margin: 100px 0 50px 280px;">
                <h1><strong>Danh sách voucher</strong></h1>
                <div class="my-5">
                    <c:if test="${not empty sessionScope.createErr}">
                        <p class="text-danger my-2">${sessionScope.createErr}</p>
                    </c:if>
                    <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/admin/voucher?action=create" method="post" >
                        <div class="col-md-12 position-relative">
                            <label for="vocherCode" class="form-label">Mã voucher</label>
                            <input type="text" class="form-control" id="vocherCode" name="vocherCode"  required>
                            <c:if test="${not empty requestScope.errorMap.vouCodExist}">
                                <p class="text-danger my-2">${requestScope.errorMap.vouCodExist}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="discountValue" class="form-label">Giá trị giảm</label>
                            <input type="text" class="form-control" id="discountValue" name="discountValue" required>
                            <c:choose>
                                <c:when test="${not empty requestScope.errorMap.disValErrPos}">
                                    <p class="text-danger my-2">${requestScope.errorMap.disValErrPos}</p>
                                </c:when>
                                <c:when test="${not empty requestScope.errorMap.disValErrNumFmt}">
                                    <p class="text-danger my-2">${requestScope.errorMap.disValErrNumFmt}</p>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="discountType" class="form-label">Loại giảm giá</label>
                            <select class="form-select" id="discountType" name="discountType" required>
                                <option selected value="FIXED">FIXED(VND)</option>
                                <option value="PERCENT">PERCENT(%)</option>
                            </select>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="minOrderValue" class="form-label">Điều kiện</label>
                            <input type="text" class="form-control" id="minOrderValue" name="minOrderValue">
                           <c:choose>
                                <c:when test="${not empty requestScope.errorMap.minOrdErrPos}">
                                    <p class="text-danger my-2">${requestScope.errorMap.minOrdErrPos}</p>
                                </c:when>
                                <c:when test="${not empty requestScope.errorMap.minOrdErrNumFmt}">
                                    <p class="text-danger my-2">${requestScope.errorMap.minOrdErrNumFmt}</p>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="startDate" class="form-label">Ngày bắt đầu</label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" required>
                            <c:if test="${not empty requestScope.errorMap.startDateErr}">
                                <p class="text-danger my-2">${requestScope.errorMap.startDateErr}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="endDate" class="form-label">Ngày kết thúc</label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" required>
                            <c:if test="${not empty requestScope.errorMap.endDateErr}">
                                <p class="text-danger my-2">${requestScope.errorMap.endDateErr}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="maxUsage" class="form-label">Số lượng</label>
                            <input type="number" class="form-control" id="maxUsage" name="maxUsage" required>
                            <c:choose>
                                <c:when test="${not empty requestScope.errorMap.maxUsageErrPos}">
                                    <p class="text-danger my-2">${requestScope.errorMap.maxUsageErrPos}</p>
                                </c:when>
                                <c:when test="${not empty requestScope.errorMap.maxUsageErrNumFmt}">
                                    <p class="text-danger my-2">${requestScope.errorMap.maxUsageErrNumFmt}</p>
                                </c:when>
                            </c:choose>
                        </div>
                        <div class="col-12">
                            <button class="btn btn-primary me-3" type="submit">Thêm voucher</button>
                            <a class="btn btn-primary" href="#" onclick="history.back(); return false;">Quay lại</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
