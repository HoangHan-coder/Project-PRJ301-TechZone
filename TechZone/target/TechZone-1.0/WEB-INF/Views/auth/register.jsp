<%-- 
    Document   : register
    Created on : Sep 17, 2025, 6:31:40 PM
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
                .login{
                    box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);
                    margin-left: 90px;
                    width: 50%;
                    height: 590px;
                    width: 600px;

                }

                h1{
                    text-align: center;
                    margin-top: 20px;
                    margin-bottom: 30px;
                }

                #inputtext{
                    margin-bottom: 40px;
                    margin-left: 30px;
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
                    margin-left: 80px;
                    margin-top: 10px;
                    width: 370px;

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
                    transform: translateY(20px);

                }

                #myCarousel{
                    margin-left: 300px;
                    width: 60%;
                    width: 600px;
                    height: 400px;
                    text-align: center;
                    transform: translateX(-150px);

                    left: 70px;
                    box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);
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
                    margin-left: 115px;
                    margin-top: 70px;
                }


            </style>
        </head>
        <body>
            <header>
                <img src="https://img.freepik.com/free-vector/tech-computer-logo-template_23-2149204144.jpg?w=2000" alt="alt"/>
            </header>
            <!-- Navbar -->


            <!-- Hero -->


            <!-- form login -->

            <div class="form">
                <div class="login">
                    <h1>REGISTER</h1>
                    <form>
                        <label for="user" class="form-label">username</label>
                        <input class="form-control" id="inputtext" type="text" name="name" placeholder="Enter your username" required="please enter your username">
                        <br>
                        <label for="password" class="form-label">password</label>
                        <input class="form-control" id="inputtext" type="text" name="password" placeholder="Enter your password" required="please enter your password">
                        <br>

                        <label for="user" class="form-label">phone</label>
                        <input class="form-control" id="inputtext" type="text" name="phone" placeholder="Enter your phone" required="please enter your phone">
                        <br>
                        <input type="submit" value="register" class="button">

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
                            <img src="https://www.studytienganh.vn/upload/2021/06/106879.jpg" class="d-block w-100" alt="Ảnh 1">
                        </div>
                        <div class="carousel-item">
                            <img src="https://hanoicomputercdn.com/media/product/75551_ban_phim_co_gaming_co_day_zifriend_za98_white_blue_blue_switch_1.jpg" class="d-block w-100" alt="Ảnh 2">
                        </div>
                        <div class="carousel-item">
                            <img src="https://www.phucanh.vn/media/news/2312_laptop-moi-cua-lenovo-1.jpg" class="d-block w-100" alt="Ảnh 3">
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

            <script>

            </script>
            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>
    </html>
