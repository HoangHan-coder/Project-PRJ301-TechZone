<%-- 
    Document   : update-voucher
    Created on : Oct 21, 2025, 10:54:26 PM
    Author     : NgKaitou
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <div class="container-fluid">
            <div>
                <%@include file="../../includes/slide-bar-admin.jsp" %>
            </div>
            <div style="margin: 100px 0 50px 280px;">
                <h1><strong>Chỉnh sửa voucher</strong></h1>
                <div class="my-5">
                    <form class="row g-3 needs-validation" action="${pageContext.request.contextPath}/voucher?action=update" method="post" >
                        <div class="col-md-12 position-relative">
                            <label for="vocherCode" class="form-label">Mã voucher</label>
                            <input type="text" class="form-control" id="vocherCode" name="vocherCode"  value="${voucher.code}">
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="discountValue" class="form-label">Giá trị giảm</label>
                            <input type="text" class="form-control" id="discountValue" name="discountValue" value="${voucher.discountValue}">
                            <c:if test="${not empty requestScope.errors.discountValueError}">
                                <p class="text-danger my-2">${requestScope.errors.discountValueError}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="discountType" class="form-label">Loại giảm giá</label>
                            <select class="form-select" id="discountType" name="discountType">
                                <option ${voucher.discountType eq 'FIXED' ? 'selected' : ''} value="FIXED">FIXED(VND)</option>
                                <option ${voucher.discountType eq 'PERCENT' ? 'selected' : ''} value="PERCENT">PERCENT(%)</option>
                            </select>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="minOrderValue" class="form-label">Điều kiện</label>
                            <input type="text" class="form-control" id="minOrderValue" name="minOrderValue" value="${voucher.minOrderValue}">
                            <c:if test="${not empty requestScope.errors.minOrderValueError}">
                                <p class="text-danger my-2">${requestScope.errors.minOrderValueError}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="startDate" class="form-label">Ngày bắt đầu</label>
                            <input type="datetime-local" class="form-control" id="startDate" name="startDate" value="<fmt:formatDate value='${voucher.startDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>">
                            <c:if test="${not empty requestScope.errors.startDateError}">
                                <p class="text-danger my-2">${requestScope.errors.startDateError}</p>
                            </c:if>
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="endDate" class="form-label">Ngày kết thúc</label>
                            <input type="datetime-local" class="form-control" id="endDate" name="endDate" value="<fmt:formatDate value='${voucher.endDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>">
                        </div>
                        <div class="col-md-4 position-relative">
                            <label for="maxUsage" class="form-label">Số lượng</label>
                            <input type="text" class="form-control" id="maxUsage" name="maxUsage" value="${voucher.maxUsage}">
                            <c:if test="${not empty requestScope.errors.maxUsage}">
                                <p class="text-danger my-2">${requestScope.errors.maxUsage}</p>
                            </c:if>
                        </div>
                        <div class="col-12">
                            <button class="btn btn-primary me-3" type="submit">Lưu voucher</button>
                            <a class="btn btn-primary" href="#" onclick="history.back(); return false;">Quay lại</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
