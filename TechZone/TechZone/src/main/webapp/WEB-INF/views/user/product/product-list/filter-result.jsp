<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${empty list}">
        <div class="col-12 text-center mt-4 text-danger" style="font-size: 20px;">
            <p>Không tìm thấy sản phẩm nào phù hợp.</p>
        </div>
    </c:when>
    <c:otherwise>
        <!-- ✅ Bắt đầu dòng grid -->
        <div class="row justify-content-center">
            <c:forEach items="${list}" var="i">
                <div class="col-lg-3 col-md-4 col-sm-6 mb-4">
                    <div class="card h-100 text-center shadow-sm border-0">
                        <img src="${pageContext.request.contextPath}${i.linkImg}" 
                             class="card-img-top p-3" alt="${i.productName}" style="height:250px; object-fit:contain;">
                        <div class="card-body">
                            <h6 class="card-title">${i.productName}</h6>
                            <p class="card-text text-danger fw-bold mb-2">
                                <fmt:formatNumber value="${i.productPrice}" type="number" maxFractionDigits="0"/> VND
                            </p>
                            <a href="products?action=detail&id=${i.productId}" class="btn btn-outline-primary btn-sm">
                                View Detail
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <!-- ✅ Kết thúc dòng grid -->

        <!-- Phân trang -->
        <c:if test="${totalPages > 1}">
            <div class="col-12 mt-4">
                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <!-- Nếu đang ở chế độ tìm kiếm (txtSearch có giá trị), dùng link full-page để giữ tham số search -->
                                <c:choose>
                                    <c:when test="${not empty txtSearch}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/search?action=search&txtSearch=${txtSearch}&page=${i}">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Mặc định: filter qua AJAX -->
                                        <a class="page-link page-btn" href="#" data-page="${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </c:if>
    </c:otherwise>
</c:choose>
