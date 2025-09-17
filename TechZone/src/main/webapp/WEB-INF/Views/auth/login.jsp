<%-- 
    Document   : login
    Created on : Sep 17, 2025, 9:29:21 AM
    Author     : acer
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Gizmos</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body {
      background-color: #f8f9fa;
    }
    .navbar {
      background-color: #0d6efd;
    }
    .navbar .nav-link, .navbar .navbar-brand {
      color: white !important;
    }
    .hero {
      text-align: center;
      padding: 80px 20px;
      background-color: #f1f3f6;
    }
    .hero h1 {
      font-size: 2.5rem;
      margin-bottom: 10px;
    }
    .product-card {
      text-align: center;
      padding: 15px;
      border: 1px solid #ddd;
      border-radius: 8px;
      margin: 10px;
      background-color: #fff;
    }
    .product-card img {
      max-width: 100%;
      height: 150px;
      object-fit: contain;
      margin-bottom: 10px;
    }
    footer {
      background-color: #212529;
      color: #bbb;
      padding: 20px;
      text-align: center;
      margin-top: 40px;
    }
    footer a {
      color: #bbb;
      margin: 0 5px;
      text-decoration: none;
    }
    
    .login{
         box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);
         margin-left: 90px;
         width: 600px;
         height: 300px;
         margin-left: 465px;
    }
    
    h1{
        text-align: center;
        margin-bottom: 30px;
    }
    
    .inputtext{
         margin-bottom: 40px;
         margin-left: 30px;
         width: 370px;
    }
    
    .login form label{
         margin-left: 20px;
    }
    .button{
        margin-left: 90px;
        margin-top: 10px;
    }
  </style>
</head>
<body>

  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg">
    <div class="container-fluid">
      <a class="navbar-brand fw-bold" href="#">Gizmos.</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav me-auto">
          <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Pages</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Shop</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Blog</a></li>
          <li class="nav-item"><a class="nav-link" href="#">Landing</a></li>
        </ul>
        <form class="d-flex" role="search">
          <select class="form-select me-2">
            <option selected>All Categories</option>
          </select>
          <input class="form-control me-2" type="search" placeholder="Search for Product...">
          <button class="btn btn-light" type="submit">🔍</button>
        </form>
      </div>
    </div>
  </nav>

  <!-- Hero -->
  <section class="hero">
      <h1>Welcome to Gizmos</h1>
    <p>Find the best gadgets and electronics here.</p>
    <a href="#" class="btn btn-primary">Shop Now</a>
  </section>

  <!-- form login -->
  <div class="login">
      <h1>LOGIN</h1>
      <form>
          <label for="">username</label>
          <input class="inputtext" type="text" name="name">
          <br>
          <label for="">password</label>
          <input style="margin-left: 32px;" class="inputtext" type="text" name="password">
          <br>
          <input type="submit" value="login" class="button">
          <br>
          <p>you do not account? <a href="${pageContext.request.contextPath}/register">Sign Up</a></p>
      </form>
  </div>

  <!-- Footer -->
  <footer>
    <p>© 2025 Gizmos. All rights reserved.</p>
    <a href="#">Privacy Policy</a> | 
    <a href="#">Terms of Service</a>
  </footer>
  <script>
      
      </script>
  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
