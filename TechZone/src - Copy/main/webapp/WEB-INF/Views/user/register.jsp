<%-- 
    Document   : register
    Created on : Sep 17, 2025, 6:31:40 PM
    Author     : acer
--%>
<!--
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
--><html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Gizmos</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

          
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                background-color: #f8f9fa;

            }
            .login{
                /*                    box-shadow: 0 0 15px 0 rgba(0,0,0,0.5);*/
                margin-left: 90px;
                width: 50%;
                height: 520px;
                width: 600px;

            }

            h1{
                text-align: center;
                margin-top: 20px;
                margin-bottom: 30px;
            }

            #user,#password,#phone{

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
                margin-left: 30px;
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
                margin-left: 115px;
                margin-top: 70px;
            }
            #lab{
                margin-left: 30px;
            }

        </style>
    </head>
    <body>
        <header>
            <div>
                <jsp:include page="/WEB-INF/views/includes/header.jsp"/>
            </div>
        </header>
        <!-- Navbar -->


        <!-- Hero -->


               <!--

-->        <div class="form">
            <div class="login">
                <h1>REGISTER</h1>
                <form>
                    <label id="lab" for="user" class="form-label">username</label>
                    <input class="form-control" id="user" type="text" name="name" placeholder="Enter your username" required="please enter your username" onblur="checkUserName()">
                    <p id="username"></p>
                    <br>
                    <label id="lab" for="password" class="form-label">password</label>
                    <input class="form-control" id="password" type="text" name="password" placeholder="Enter your password" required="please enter your password" onblur="checkPassword()">
                    <p id="pass"></p>

                    <br>

                    <label id="lab" for="phone" class="form-label">phone</label>
                    <input class="form-control" id="phone" type="text" name="phone" placeholder="Enter your phone" required="please enter your phone" onblur="checkPhone()">
                    <p id="numberphone"></p>
                    <br>
                    <input type="button" value="register" class="button" onclick="register()">
                    <p id="error"></p>
                </form>

                </div>
            <div id="myCarousel" class="carousel slide" data-bs-ride="carousel">
<!--                 Indicators -->
                <div class="carousel-indicators custom-indicators">
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="3"></button>
                </div>

<!--                 Slides -->
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

<!--                 Arrows -->
                <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon"></span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon"></span>
                </button>
            </div>



        </div>
        


        <!-- Footer -->
        <jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
        <script>
            function checkUserName() {
                const username = document.getElementById("user").value;
                if (username === "") {
                    document.getElementById("username").innerHTML = "Please enter your username, cannot empty";
                } else {
                    document.getElementById("username").innerHTML = "";
                }
            }

            function checkPassword() {
               const password = document.getElementById("password").value;
                if (password === "") {
                    document.getElementById("pass").innerHTML = "Please enter your password, cannot empty";
                } else {
                    document.getElementById("pass").innerHTML = "";
                }
            }

            function checkPhone() {
                const phone = document.getElementById("phone").value;
                if (phone === "") {
                    document.getElementById("numberphone").innerHTML = "Please enter your phone, cannot empty";
                } else {
                    document.getElementById("numberphone").innerHTML = "";
                }
            }


            async function register() {
                const username = document.getElementById("user").value;
                const password = document.getElementById("password").value;
                const phone = document.getElementById("phone").value;
                if (username !== "" && password !== "" && phone !== "") {
                    if (password.length >= 8) {
                        try {
                            const res = await fetch("http://localhost:8080/TechZone/register", {
                                method: "POST",
                                headers: {"Content-Type": "application/x-www-form-urlencoded"},
                                body: `name=\${encodeURIComponent(username)}&password=\${encodeURIComponent(password)}&phone=\${encodeURIComponent(phone)}`
                            });

                            const data = await res.json();

                            if (data.success) {
                                alert(data.message + " ,Hello: " + username);

                                // ✅ Chuyển hướng sang trang Home (dashboard.html)
                                window.location.href = "${pageContext.request.contextPath}/login";
                            } else {
                                alert("register failed: ");
                            }
                        } catch (err) {
                            console.error("Fetch error:", err);
                            document.getElementById("error").innerHTML = "Error: cannot connect to server.";

                        }
                    } else {
                        document.getElementById("error").innerHTML = "error, password must be greater than or equal 8 character";
                    }

                } else {
                    document.getElementById("error").innerHTML = "error, please, you must enter your information such as email, password, phone";
                }


            }
        </script>
<!--         Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body><!--
--></html>

