<%-- 
    Document   : product-detail
    Created on : Oct 31, 2025, 8:38:20 PM
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
        margin-top: 5px;
    }
    
    .button{
        display: flex;
        margin-top: 130px;
        margin-left: 1065px;
        gap: 10px;
    }
    
</style>
<div class="container">
    <div class="row">
        <div class="col-9" style="margin-left: 20px;margin-top: 10px;"><h1>Product Detail<h1/>
                </div>
        <div class="col-4"><img class="img" src="${pageContext.request.contextPath}/${productdetail.linkImg}" alt="alt"/></div>
        <div id="contentmain" class="col-4">
            <p>
            <h2>${productdetail.productName}<h2/> 
            </p>
            <br>
            <p>
               Gia: <fmt:formatNumber value="${productdetail.productPrice}" type="number" maxFractionDigits="0"/>
            </p>
            <br>
            <p>
               Danh Muc: ${productdetail.categoryId == 1?"Laptop":productdetail.categoryId == 2?"Phone":"Accessories"}
            </p>
            <br>
            <p>
                Ton Kho: ${productdetail.stock}
            </p>
            <br>
            <p>
                Trang Thai: ${productdetail.stock > 0? "Con Hang":"Het Hang"}
            </p>
        </div>
    </div>
</div>
            <div class="button">
                <a class="btn btn-primary" href="http://localhost:8080/TechZone/admin/product?view=edit&Id=${productdetail.productId}" role="button">Sua San Pham</a>
            <a class="btn btn-primary" href="http://localhost:8080/TechZone/admin/product?view=product" role="button">Quay Lai Trang San Pham</a>
            </div>
            
            
</body>
</html>
