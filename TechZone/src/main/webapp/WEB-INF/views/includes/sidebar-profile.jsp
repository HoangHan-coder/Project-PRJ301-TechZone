<%-- 
    Document   : sidebar-profile
    Created on : Oct 25, 2025, 12:06:38 PM
    Author     : acer
--%>
<%@page import="model.AccountUsers"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<!-- saved from url=(0049)https://bootstrapmade.com/content/demo/NiceAdmin/ -->
<html lang="en">
  
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>GROUP4</title>
  <meta name="robots" content="noindex, nofollow">
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="https://bootstrapmade.com/content/demo/NiceAdmin/assets/img/favicon.png" rel="icon">
  <link href="https://bootstrapmade.com/content/demo/NiceAdmin/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com/" rel="preconnect">
  <link href="assets/css/css" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="assets/css/bootstrap.min.css" rel="stylesheet">
  <link href="assets/css/bootstrap-icons.css" rel="stylesheet">
  <link href="assets/css/boxicons.min.css" rel="stylesheet">
  <link href="assets/css/quill.snow.css" rel="stylesheet">
  <link href="assets/css/quill.bubble.css" rel="stylesheet">
  <link href="assets/css/remixicon.css" rel="stylesheet">
  <link href="assets/css/style.css" rel="stylesheet">

  <!-- Template Main CSS File -->
  <link href="assets/css/style(1).css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">

  <!-- =======================================================
  * Template Name: NiceAdmin
  * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
  * Updated: Apr 7 2025 with Bootstrap v5.3.5
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->
<!-- </head> -->
<style>
  .nav-item{
    list-style: none;
  }
</style>
<body>
<%
  String activeProfile = (String)request.getAttribute("active profile");
  String activeVoucher = (String)request.getAttribute("active voucher");
 String activeAccount_profile = (String)request.getAttribute("active account_profile");
 AccountUsers user = (AccountUsers) session.getAttribute("account");
  boolean actPro = false;
  boolean actVou = false;
  boolean actAcc = false;
    if(activeProfile != null){
        actPro = true;
      }
      
      if(activeVoucher != null){
      actVou = true;
      }
      
      if(activeAccount_profile != null){
      actAcc = true;
      }
  
  %>
  <!-- ======= Header ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
      <a href="profile.html" class="logo d-flex align-items-center">
        <img src="./logo(1).png" alt="">
        <span class="d-none d-lg-block">FPT</span>
      </a>
      
    </div><!-- End Logo -->

    

        <li class="nav-item dropdown pe-3">

          <a class="nav-link nav-profile d-flex align-items-center pe-0" href="https://bootstrapmade.com/content/demo/NiceAdmin/#" data-bs-toggle="dropdown">
            <!-- <img src="./profile-img.jpg" alt="Profile" class="rounded-circle"> -->
            <span class="d-none d-md-block dropdown-toggle ps-2"><%= user.getUsername() %></span>
          </a><!-- End Profile Iamge Icon -->

          <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
            <li class="dropdown-header">
              <h6><%= user.getUsername() %></h6>
<!--              <span>Web Designer</span>-->
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="#">
                <i class="bi bi-person"></i>
                <span>My Profile</span>
              </a>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="account.html">
                <i class="bi bi-gear"></i>
                <span>Account Settings</span>
              </a>
            </li>
            <li>
              <hr class="dropdown-divider">
            </li>

           
            <li>
              <hr class="dropdown-divider">
            </li>

            <li>
              <a class="dropdown-item d-flex align-items-center" href="">
                <i class="bi bi-box-arrow-right"></i>
                <span>Sign Out</span>
              </a>
            </li>

          </ul><!-- End Profile Dropdown Items -->
        </li><!-- End Profile Nav -->

      </ul>
    </nav><!-- End Icons Navigation -->

  </header><!-- End Header -->
  
  <!-- ======= Sidebar ======= -->
  <aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

      <li class="nav-item">
          <a class="nav-link <%= (actPro)? "":"collapsed"%>" href="${pageContext.request.contextPath}/profile">
          <i class="bi bi-grid"></i>
          <span>Thong bao</span>
        </a>
      </li><!-- End Dashboard Nav -->

      <li class="nav-item">
        <a class="nav-link <%= (actVou)? "":"collapsed"%>" data-bs-target="#components-nav" data-bs-toggle="collapse" href="${pageContext.request.contextPath}/profile?action=voucher">
           <i class="bi bi-backpack4-fill"></i><span>voucher</span> 
        </a>
        
      </li><!-- End Components Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-journal-text"></i><span>Don mua</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="forms-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="#">
              <i class="bi bi-circle"></i><span>Ngay Dat</span>
            </a>
          </li>
          <li>
            <a href="#">
              <i class="bi bi-circle"></i><span>So Luong Don Mua</span>
            </a>
          </li>
          
        </ul>
      </li><!-- End Forms Nav -->

      

      <li class="nav-heading">Pages</li>

      <li class="nav-item">
        <a class="nav-link <%= (actAcc)? "":"collapsed"%>" href="${pageContext.request.contextPath}/profile?action=setting">
          <i class="bi bi-person"></i>
          <span>Profile</span>
        </a>
      </li><!-- End Profile Page Nav -->

      

      <!-- <li class="nav-item">
        <a class="nav-link collapsed" href="#">
          <i class="bi bi-envelope"></i>
          <span>Contact</span>
        </a>
      </li> -->

      <li class="nav-item">
        <a class="nav-link collapsed" href="#">
          <i class="bi bi-box-arrow-in-right"></i>
          <span>Log out</span>
        </a>
      </li>

    </ul>

  </aside><!-- End Sidebar-->
