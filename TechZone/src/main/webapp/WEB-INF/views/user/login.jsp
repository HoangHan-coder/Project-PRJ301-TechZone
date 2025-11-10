<%-- 
    Document   : login
    Created on : Sep 17, 2025, 9:29:21 AM
    Author     : acer
--%>

<!-- Import JSTL core tag library for conditional rendering and other utilities -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Set page content type and language for the JSP page -->
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Gizmos</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap CSS used for responsive layout and components -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>

        <style>
            /* Base page background color */
            body {
                background-color: #f8f9fa;


                .login{
                    /* Container styling for the login box */
                    margin-left: 90px;
                    width: 50%;
                    height: 490px;
                    width: 600px;
                    border-radius: 5px;
                }

                h1{
                    /* Centered title for the login form */
                    text-align: center;
                    margin-top: 20px;
                    margin-bottom: 30px;
                }

                #inputtext{

                    margin-left: 40px;
                    width: 450px;

                    padding: 10px;
                    font-size: 14px;
                    border: 1px solid #ccc;
                    border-radius: 5px;
                }

                .login form label{
                    /* Labels alignment inside the form */
                    margin-left: 20px;
                }
                .button{
                    /* Submit button base styling */
                    margin-left: 40px;
                    margin-top: 20px;
                    width: 450px;

                    padding: 10px;
                    font-size: 14px;
                    border: 1px solid #ccc;
                    border-radius: 5px;

                    background-color: blue;
                    color: white;
                    cursor: pointer;
                }

                .button:hover {
                    /* Hover state for the submit button */
                    background-color: orange;
                }

                .form{
                    /* Layout for the main form and carousel area */
                    display: flex;
                    align-items: center;
                    transform: translateY(50px);

                }

                #myCarousel{
                    /* Carousel container measurements and position */
                    margin-left: 300px;
                    width: 60%;
                    width: 600px;
                    height: 400px;
                    text-align: center;
                    transform: translateX(-150px);
                    border-radius: 5px;
                    left: 70px;
                    /* Shadow removed; adjust here if needed */
                }

                .frame div{
                    /* Frame wrapper for carousel images */
                    width: 600px;
                    height: 380px;
                    overflow: hidden;
                }

                .frame div img{
                    /* Ensure images cover the entire frame area */
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
                header img{
                    /* Logo sizing within header */
                    width: 10%;
                }
                p{
                    /* Paragraph spacing within the form area */
                    margin-left: 40px;
                    margin-top: 50px;
                }
                #lab{
                    /* Label indentation */
                    margin-left: 40px;
                }

                .infor{
                    /* Error alert box width and spacing */
                    margin-left: 40px;
                    width: 450px;

                    padding: 10px;
                    font-size: 14px;
                    border: 1px solid #ccc;
                    border-radius: 5px;
                }

                #buterror{
                    /* Adjust close button position inside alert */
                    transform: translateY(-6px);
                }
            </style>
        </head>
        <body>
            <!-- Header with global navigation bar include -->
            <header> 
                <div>
                    <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
                </div>
            </header>
            <!-- Navbar end -->


            <!-- Hero -->


            <!-- Login form section -->

            <div class="form">
                <div class="login">
                    <h1>LOGIN</h1>
                    <!-- Form posts to the login servlet endpoint -->
                    <form action="http://localhost:8080/TechZone/login" method="POST">
                        <!-- Username field -->
                        <label id="lab" for="user" class="form-label">username</label>
                        <input class="form-control" id="inputtext" type="text" name="username" placeholder="Enter your username" required="please enter your username">
                        <br>
                        <!-- Password field -->
                        <label id="lab" for="password" class="form-label">password</label>
                        <input class="form-control" id="inputtext" type="text" name="password" placeholder="Enter your password" required="please enter your password">
                        <br>
                        <!-- Thẻ reCAPTCHA -->
                        <div style="margin-left: 40px" class="g-recaptcha"  data-sitekey="6Lc7rgcsAAAAALwh6lFkVujVi9Tl9yxKYkCIUjJO"></div>
                            <!-- Display error message when login fails (on ?error=true) -->
                            <c:if test="${not empty param.error}">
                                <div class="alert alert-danger alert-dismissible fade show infor" role="alert">
                                    Username or password is not correct!
                                    <button id="buterror" type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <!-- Submit button -->
                            <input type="submit" value="login" class="button">
                            <br>
                            <!-- Registration link for users without an account -->
                            <p>you do not have account? <a style="text-decoration: none;" href="${pageContext.request.contextPath}/register">Sign Up</a></p>
                        <p>you have forgot password? <a style="text-decoration: none;" href="${pageContext.request.contextPath}/forgetpassword">forget password</a></p>
                    </form>

                </div>


                <!-- Marketing carousel showcasing images beside the form -->
                <div id="myCarousel" class="carousel slide" data-bs-ride="carousel">
                    <!-- Indicators -->
                    <div class="carousel-indicators custom-indicators">
                        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active"></button>
                        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1"></button>
                        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2"></button>
                        <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="3"></button>
                    </div>

                    <!-- Slides -->
                    <div class="carousel-inner frame">
                        <div class="carousel-item active">
                            <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-1.jpg" class="d-block w-100" alt="Ảnh 1">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-2.jpg" class="d-block w-100" alt="Ảnh 2">
                        </div>
                        <div class="carousel-item">
                            <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-3.jpg" class="d-block w-100" alt="Ảnh 3">
                        </div>

                    </div>

                    <!-- Arrows -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon"></span>
                    </button>
                </div>
            </div>
            <!-- Toast hiển thị lỗi -->
            <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 9999">
                    <div id="errorToast" class="toast align-items-center text-bg-danger border-0" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">
                                ⚠️ <strong>Please verify you are not a Robot</strong>
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                        </div>
                    </div>
                </div>

                <!-- Bootstrap JS -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

                <!-- Hiển thị toast khi có lỗi -->
                <c:if test="${error == 'error'}">
                    <script>
                        const toastEl = document.getElementById('errorToast');
                        const toast = new bootstrap.Toast(toastEl, {delay: 3000}); // 3 giây
                        toast.show();
                    </script>
                </c:if>


                <!-- Footer include with site-wide footer contents -->
                <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
                <script>
                    // Inline script block for page-specific JS (currently empty)
                </script>
                <!-- Bootstrap JS bundle provides interactive components like the carousel -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">

                </script>
            </body>
        </html>
