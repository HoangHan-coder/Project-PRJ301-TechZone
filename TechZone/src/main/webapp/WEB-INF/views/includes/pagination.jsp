<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav aria-label="Page navigation example">
        <ul class="pagination">
            <c:choose>
                <c:when test="${currentPage < 5}">
                    <li class="page-item disabled">
                        <a class="page-link" href="<%= request.getContextPath()%>/${servletPath}?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="<%= request.getContextPath()%>/${servletPath}?page=${currentPage - 1}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
            <c:forEach begin="${startPage}" var="pageNumber" end="${endPage}" step="1">
                <c:choose>
                    <c:when test="${currentPage eq pageNumber}">
                        <li class="page-item active"><a class="page-link" href="<%= request.getContextPath()%>/${servletPath}?page=${pageNumber}">${pageNumber}</a></li>
                        </c:when>
                        <c:otherwise>
                        <li class="page-item"><a class="page-link" href="<%= request.getContextPath()%>/${servletPath}?page=${pageNumber}">${pageNumber}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:choose>

                <c:when test="${currentPage > totalPage-1}">
                    <li class="page-item">
                        <a class="page-link disabled" href="<%= request.getContextPath()%>/${servletPath}?page=${currentPage + 1}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="<%= request.getContextPath()%>/${pageNumber}?page=${currentPage + 1}" aria-label="Next" onclick="increasePage()">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>

        </ul>
    </nav>
