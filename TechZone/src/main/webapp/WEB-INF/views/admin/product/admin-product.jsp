<%-- 
    Document   : admin-product
    Created on : Oct 29, 2025, 11:06:40 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../includes/navbar-admin.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<main id="main" class="main">
    <header>
    <div class="d-flex mb-3" style="transform: translateX(300px);">
            
        <select class="form-select" style="max-width:150px;" id="sort">
                <option>time</option>
                <option value="newest">newest</option>
                
            </select>
        
        
        <select class="form-select" style="max-width:150px;" id="category">
                <option>catorgory</option>
                <option value="all">ALL</option>
                <option value="Laptops">laptop</option>
                <option value="Phones">phone</option>
                <option value="Accessories">accessories</option>
            </select>
        
        <select class="form-select" style="max-width:150px;" id="brand">
                <option>branch</option>
                <option value="MSI">MSI</option>
                <option value="TUF">TUF</option>
                <option value="Acer">Acer</option>
                <option value="Legion">Legion</option>
                <option value="Samsung">Samsung</option>
                <option value="iPhone">Iphone</option>
                <option value="Oppo">Oppo</option>
                <option value="Baseus">Baseus</option>
                <option value="Anker">Anker</option>
                <option value="Seagate">Seagate</option>
                <option value="WD">WD</option>
                <option value="Sony">Sony</option>
                <option value="Logitech">Logitech</option>
            </select>
        
        
        </div>
</header>
    <input type="text" name="keyword" class="form-control me-2"
       placeholder="Tìm kiếm sản phẩm"
       style="width: 60%; transform: translateX(260px);" />

<!-- Bảng hiển thị sản phẩm -->
<div class="table-responsive" style="width: 80%; transform: translateX(260px); margin-top: 30px;" id="product-list">
  <table class="table table-bordered table-hover align-middle text-center">
    <thead class="table-light">
      <tr>
        <th>ID</th>
        <th>TÊN</th>
        <th>Ảnh</th>
        <th>Giá</th>
        <th>Danh mục</th>
        <th>Tồn kho</th>
        <th>Trạng thái</th>
        <th>Hành động</th>
      </tr>
    </thead>
    <tbody>
        <c:forEach items="${listproductadmin}" var="li">
      <!-- Hàng mẫu 1 -->
      <tr>
        <td>${li.productId}</td>
        <td>${li.productName}</td>
        <td><img src="${pageContext.request.contextPath}/${li.linkImg}" alt="Ảnh" style="width: 60px; border-radius: 5px;"></td>
        <td>${li.productPrice}</td>
        <td>${li.attributesMap['brand']}</td>
        <td>${li.stock}</td>
        <td><span class="badge bg-${li.stock > 0? "success":"danger"}">${li.stock > 0? "Con Hang":"Het Hang"}</span></td>
        <td>
          <button class="btn btn-outline-primary btn-sm"><i class="bi bi-eye"></i></button>
          <button class="btn btn-outline-danger btn-sm"><i class="bi bi-trash"></i></button>
        </td>
      </tr>

      <!-- Hàng mẫu 2 -->
<!--      <tr>
        <td>2</td>
        <td>Tên sản phẩm 2</td>
        <td><img src="https://via.placeholder.com/60" alt="Ảnh" style="width: 60px; border-radius: 5px;"></td>
        <td>0₫</td>
        <td>Danh mục</td>
        <td>0</td>
        <td><span class="badge bg-danger">Hết hàng</span></td>
        <td>
          <button class="btn btn-outline-primary btn-sm"><i class="bi bi-eye"></i></button>
          <button class="btn btn-outline-danger btn-sm"><i class="bi bi-trash"></i></button>
        </td>
      </tr>-->
        </c:forEach>
    </tbody>
  </table>
</div>

  </main>
<script>
    
    document.addEventListener("DOMContentLoaded", function() {
    const category = document.getElementById("category");
    const brand = document.getElementById("brand");
    const sort = document.getElementById("sort");
    const contextPath = "${pageContext.request.contextPath}";
    [category, brand, sort].forEach(el => el.addEventListener("change", fetchProducts));

    function fetchProducts() {
        const cat = category.value;
        const br = brand.value;
        const srt = sort.value;

        fetch("http://localhost:8080/TechZone/admin", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `category=${cat}&brand=${br}&sort=${srt}&action=${"filter"}`
        })
        .then(response => response.json()) // Nhận dữ liệu JSON
        .then(products => {
            const container = document.getElementById("product-list");
            let html = `
    <table class="table table-bordered table-hover align-middle text-center">
      <thead class="table-light">
        <tr>
          <th>ID</th>
          <th>TÊN</th>
          <th>Ảnh</th>
          <th>Giá</th>
          <th>Danh mục</th>
          <th>Tồn kho</th>
          <th>Trạng thái</th>
          <th>Hành động</th>
        </tr>
      </thead>
      <tbody>
  `;

  if (products.length === 0) {
    html += `<tr><td colspan="8">Không có sản phẩm phù hợp</td></tr>`;
  } else {
    products.forEach(p => {
      const badge = p.stock > 0
        ? `<span class="badge bg-success">Còn hàng</span>`
        : `<span class="badge bg-danger">Hết hàng</span>`;

      html += `
        <tr>
          <td>${p.productId}</td>
          <td>${p.productName}</td>
          <td><img src="${contextPath}${p.linkImg}" alt="Ảnh" style="width:60px;border-radius:5px;"></td>
          <td>${p.productPrice}</td>
          <td>${p.attributesMap['brand']}</td>
          <td>${p.stock}</td>
          <td>${badge}</td>
          <td>
            <button class="btn btn-outline-primary btn-sm"><i class="bi bi-eye"></i></button>
            <button class="btn btn-outline-danger btn-sm"><i class="bi bi-trash"></i></button>
          </td>
        </tr>
      `;
    });
  }

  html += `
      </tbody>
    </table>
  `;

  // ✅ Gắn HTML vào thẻ div
  container.innerHTML = html;
});
}
});
</script>
</body>
</html>