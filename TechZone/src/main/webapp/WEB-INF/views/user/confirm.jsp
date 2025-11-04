<%-- 
    Document   : confirm
    Created on : Nov 2, 2025, 6:26:21 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
    <jsp:include page="/WEB-INF/views/includes/header.jsp"/>
</div>
    <h1>Ban muon xoa ${requestScope.cartItemId} ?</h1>

<form action="${pageContext.request.contextPath}/cartitem" method="POST" class="form-cart-item">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="cartItemId" value="${cartItemId}">


    <div class="col">
        <%-- THÊM CLASS VÀ DATA CHO JS/Servlet --%>
        <button type="submit">Delete</button>
    </div>




</form>


<div>
    <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
</div>



