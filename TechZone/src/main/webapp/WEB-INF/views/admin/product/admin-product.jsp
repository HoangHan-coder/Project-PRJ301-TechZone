<%-- 
    Document   : admin-product
    Created on : Oct 29, 2025, 11:06:40 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../Includes/navbar-admin.jsp" %>

<header>
    <div class="d-flex mb-3" style="transform: translateX(300px);">
            
            <select class="form-select" style="max-width:150px;">
                <option selected>time</option>
                <option value="Admin">newest</option>
                <option value="Customer">currently</option>
            </select>
        
        
            <select class="form-select" style="max-width:150px;">
                <option selected>catorgory</option>
                <option value="Admin">laptop</option>
                <option value="Customer">phone</option>
                <option value="Customer">accessories</option>
            </select>
        
               <select class="form-select" style="max-width:150px;">
                <option selected>branch</option>
                <option value="Admin">MSI</option>
                <option value="Customer">TUF</option>
                <option value="Customer">Acer</option>
                <option value="Customer">Legion</option>
                <option value="Customer">Samsung</option>
                <option value="Customer">Iphone</option>
                <option value="Customer">Oppo</option>
            </select>
        
        
        </div>
</header>

</body>
</html>