<%-- 
    Document   : product-delete
    Created on : Nov 3, 2025, 1:39:18 PM
    Author     : acer
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../includes/navbar-admin.jsp" %>
<style>
    .container{
        margin-left: 350px;
        transform: translateY(90px);
        background-color: #e5e7eb;
        box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);
        width: 70%;
        height: 570px;
    }

    .img{
        margin-left: 40px;
        margin-top: 40px;
        width: 90%;
    }

    #contentmain{
        margin-left: 250px;
        margin-top: 40px;
    }

    .button{
        display: flex;
        margin-top: 130px;
        margin-left: 1065px;
        gap: 10px;
    }

</style>
<form action="http://localhost:8080/TechZone/admin/product" method="post">
    <input type="hidden" id="categorypro" name="action" value="deleteproduct">
            <input type="hidden" id="categorypro1" name="productID" value="${productdelete.productId}">
    <div class="container">
        <div class="row">
            <div class="col-9" style="margin-left: 20px;margin-top: 10px;"><h1>Product Detail<h1/>
            </div>
            <div class="col-4"><img class="img" src="${pageContext.request.contextPath}/${productdelete.linkImg}" alt="alt"/></div>
            <div id="contentmain" class="col-4">
                <input class="form-control" placeholder="${productdelete.productName}"><br>
                <input class="form-control" placeholder=" <fmt:formatNumber value="${productdelete.productPrice}" type="number" maxFractionDigits="0"/>"><br>
                <input class="form-control" placeholder="${productdelete.categoryId == 1?"Laptop":productdelete.categoryId == 2?"Phone":"Accessories"}"><br>
                <input class="form-control" placeholder="${productdelete.stock}"><br>
                <input class="form-control" placeholder="${productdelete.stock > 0? "Con Hang":"Het Hang"}"><br>
            </div>
        </div>
    </div>
    <div class="button">
        <button class="btn btn-primary" type="submit">Delete</button>

    </div>

</form>


</body>
</html>
