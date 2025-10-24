<%-- 
    Document   : login
    Created on : Sep 17, 2025, 9:29:21 AM
    Author     : acer
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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


                .login{
                    /*                    box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);*/
                    margin-left: 90px;
                    width: 50%;
                    height: 490px;
                    width: 600px;
                    border-radius: 5px;
                }

                h1{
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
                    margin-left: 20px;
                }
                .button{
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
                    background-color: orange;
                }

                .form{
                    display: flex;
                    align-items: center;
                    transform: translateY(50px);

                }

                #myCarousel{
                    margin-left: 300px;
                    width: 60%;
                    width: 600px;
                    height: 400px;
                    text-align: center;
                    transform: translateX(-150px);
                    border-radius: 5px;
                    left: 70px;
                    /*                    box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);*/
                }

                .frame div{
                    width: 600px;
                    height: 380px;
                    overflow: hidden;
                }

                .frame div img{
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
                header img{
                    width: 10%;
                }
                p{
                    margin-left: 40px;
                    margin-top: 50px;
                }
                #lab{
                    margin-left: 40px;
                }

            </style>
        </head>
        <body>
            <header>
                <div>
                    <jsp:include page="/WEB-INF/Views/user/navbar.jsp"/>
                </div>
            </header>
            <!-- Navbar -->


            <!-- Hero -->


            <!-- form login -->

            <div class="form">
                <div class="login">
                    <h1>LOGIN</h1>
                    <form action="http://localhost:8080/TechZone/login" method="POST">
                        <label id="lab" for="user" class="form-label">username</label>
                        <input class="form-control" id="inputtext" type="text" name="username" placeholder="Enter your username" required="please enter your username">
                        <br>
                        <label id="lab" for="password" class="form-label">password</label>
                        <input class="form-control" id="inputtext" type="text" name="password" placeholder="Enter your password" required="please enter your password">
                        <br>
                        <input type="submit" value="login" class="button">
                        <br>
                        <p>you do not have account? <a style="text-decoration: none;" href="${pageContext.request.contextPath}/register">Sign Up</a></p>
                    </form>
                </div>


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
                            <img src="assets/headphone.png" class="d-block w-100" alt="Ảnh 1">
                        </div>
                        <div class="carousel-item">
                            <img src="assets/keyboard.png" class="d-block w-100" alt="Ảnh 2">
                        </div>
                        <div class="carousel-item">
                            <img src="assets/laptop.png" class="d-block w-100" alt="Ảnh 3">
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


            <!-- Footer -->
             <jsp:include page="/WEB-INF/Views/user/footer.jsp"/>
            <script>

            </script>
            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
                
            </script>
        </body>
    </html>
