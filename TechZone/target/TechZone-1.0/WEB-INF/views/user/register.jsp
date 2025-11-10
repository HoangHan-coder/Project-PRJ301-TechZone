<%-- 
    Document   : register
    Created on : Sep 17, 2025, 6:31:40 PM
    Author     : acer
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en">
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

            #user,#password,#phone,#con-password{

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



            /* ===== Toast thông báo ===== */
            .toast {
                position: relative;
                margin-top: 15px;
                border-radius: 8px;
                padding: 12px 16px;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .error-toast {
                background: rgba(255, 50, 50, 0.15);
                border-left: 4px solid #ff5555;
            }

            .toast-icon {
                font-size: 18px;
            }

            .toast-message {
                flex: 1;
            }

            .toast-close {
                background: none;
                border: none;
                color: #ccc;
                font-size: 20px;
                cursor: pointer;
            }
            .toast {
                position: fixed;
                top: 20px;
                right: 20px;
                border-radius: 8px;
                padding: 12px 16px;
                color: #fff;
                display: flex;
                align-items: center;
                gap: 10px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.4);
                z-index: 9999;
                animation: slideIn 0.4s ease forwards;
                backdrop-filter: blur(6px);
            }

            /* Animation trượt */
            @keyframes slideIn {
                from {
                    opacity: 0;
                    transform: translateX(100%);
                }
                to {
                    opacity: 1;
                    transform: translateX(0);
                }
            }

            /* Kiểu toast lỗi */
            .error-toast {
                background: rgba(255, 50, 50, 0.15);
                border-left: 4px solid #ff5555;
            }

            /* Kiểu toast thành công */
            .success-toast {
                background: rgba(50, 255, 100, 0.15);
                border-left: 4px solid #00ff88;
            }

            .toast-content {
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .toast-icon {
                font-size: 20px;
            }

            .toast-message {
                font-size: 14px;
            }

            .toast-close {
                background: none;
                border: none;
                color: #ccc;
                font-size: 18px;
                cursor: pointer;
                transition: 0.2s;
            }

            .toast-close:hover {
                color: #fff;
                transform: scale(1.2);
            }


        </style>
    </head>
    <body>
        <header>
            <div>
                <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>
            </div>
        </header>
        <div class="form">
            <div class="login">
                <h1>REGISTER</h1>

                <label id="lab" for="user" class="form-label">username</label>
                <input class="form-control" id="user" type="text" name="name" placeholder="Enter your username" required="please enter your username" onblur="checkUserName()">
                <p id="username"></p>
                <br>
                <label id="lab" for="password" class="form-label">password</label>
                <input class="form-control" id="password" type="text" name="password" placeholder="Enter your password" required="please enter your password" onblur="checkPassword()">
                <p id="pass"></p>

                <br>

                <label id="lab" for="password" class="form-label">confirm-password</label>
                <input class="form-control" id="con-password" type="text"placeholder="Enter your password" required="please enter your password" onblur="checkConfirmPassword()">
                <p id="pass1"></p>

                <br> 

                <label id="lab" for="password" class="form-label">Email</label>
                <form style="margin-left: 30px; width: 452px; display: flex; justify-content: space-between"  action="${pageContext.request.contextPath}/gencode" method="post">
                    <input class="form-control" type="email" id="emailInput" value="${empty email ? '' : email}" name="email" placeholder="${empty email ? 'Nhập email' : email}" ${status == 'ok' ? 'readonly' : ''} required>
                    <button class="ms-3 btn btn-primary" style="display: ${status == 'ok' ? 'none' : 'block'} ; width: 100px;"   type="submit">Gửi mã</button>
                </form>

                <c:if test='${status == "success"}'>
                    <label id="lab" for="password" class="form-label">Mã xác nhận</label>
                    <form style="margin-left: 30px; height: 45px; width: 452px; display: flex;" action="gencode" method="post">
                        <input class="form-control" type="number" name="code" placeholder="Nhập mã 6 chữ số" required>
                        <input type="hidden" name="confirm" value="${otp}" required>
                        <input type="hidden" name="email" value="${email}" required>
                        <button class="ms-3 btn btn-primary" style="font-size: 12px; width: 100px" type="submit">Xác nhận</button>
                    </form>
                </c:if>

                <c:if test="${error == 'error' && status != 'ok'}">
                    <div id="errorToast" style="height: 50px" class="toast error-toast">
                        <div class="toast-content">
                            <div class="toast-icon">❌</div>
                            <div class="toast-message">Mã xác nhận không đúng, vui lòng thử lại!</div>
                            <button class="toast-close" onclick="closeToast()">×</button>
                        </div>
                    </div>
                </c:if>

                <br>



                <label id="lab" for="phone" class="form-label">phone</label>
                <input class="form-control" id="phone" type="text" name="phone" placeholder="Enter your phone" required="please enter your phone" onblur="checkPhone()">
                <p id="numberphone"></p>
                <br>
                <input type="button" value="register" class="button" onclick="register()">
                <p id="error"></p>


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
                        <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-1.jpg" class="d-block w-100" alt="Ảnh 1">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-2.jpg" class="d-block w-100" alt="Ảnh 2">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/assets/images/slide-login/slide-login-3.jpg" class="d-block w-100" alt="Ảnh 3">
                    </div>

                </div>

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
                    if (validatePhone()) {
                        document.getElementById("numberphone").innerHTML = "";
                    } else {
                        document.getElementById("numberphone").innerHTML = "numberphone is not valid, please enter your phone again such as +84912345678 or 0912345678";
                    }

                }
            }

            function validatePhone() {
                const phone = document.getElementById("phone").value.trim();

                const regexVN = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
                const regexINT = /^\+[1-9]\d{7,14}$/;

                if (regexVN.test(phone) || regexINT.test(phone)) {

                    return true;
                } else {

                    return false;
                }
            }

            function checkConfirmPassword() {
                const con_pass = document.getElementById("con-password").value;
                const password = document.getElementById("password").value;
                if (con_pass === "") {
                    document.getElementById("pass1").innerHTML = "Please enter your phone, cannot empty";
                } else {
                    if (con_pass === password) {
                        document.getElementById("pass1").innerHTML = "";
                        return true;
                    } else {
                        document.getElementById("pass1").innerHTML = "password is not correct, please enter to confirm password again";
                        return false;
                    }
                }


            }

            function validateEmail() {
                const email = document.getElementById("emailInput").value.trim();
                const regex = /^[A-Za-z][A-Za-z0-9._]*@[A-Za-z]+\.(com)$/;
                if (regex.test(email)) {
                    return true;
                } else {

                    return false;
                }
            }


            async function register() {
                const username = document.getElementById("user").value;
                const password = document.getElementById("password").value;
                const phone = document.getElementById("phone").value;
                const email = document.getElementById("emailInput").value;
                if (username !== "" && password !== "" && phone !== "" && email !== "") {
                    if (validatePhone()) {
                        if (password.length >= 8) {
                            if (checkConfirmPassword()) {
                                if (validateEmail()) {
                                    try {
                                        const res = await fetch("http://localhost:8080/TechZone/register", {
                                            method: "POST",
                                            headers: {"Content-Type": "application/x-www-form-urlencoded"},
                                            body: `name=\${encodeURIComponent(username)}&password=\${encodeURIComponent(password)}&phone=\${encodeURIComponent(phone)}&email=\${encodeURIComponent(email)}`
                                        });

                                        const data = await res.json();

                                        if (data.success) {
                                            alert(data.message + " ,Hello: " + username);


                                            window.location.href = "${pageContext.request.contextPath}/login";
                                        } else {
                                            alert("register failed: " + data.message);
                                        }
                                    } catch (err) {
                                        console.error("Fetch error:", err);
                                        document.getElementById("error").innerHTML = "Error: cannot connect to server.";

                                    }
                                } else {
                                    document.getElementById("error").innerHTML = "email is not valid, example email such as thanhdat123@gmail.com";
                                }
                            } else {
                                document.getElementById("error").innerHTML = "error, confirm password must be equal to password";
                            }

                        } else {
                            document.getElementById("error").innerHTML = "error, password must be greater than or equal 8 character";
                        }
                    } else {
                        document.getElementById("error").innerHTML = "numberphone is not valid, please enter your phone again such as +84912345678 or 0912345678";
                    }


                } else {
                    document.getElementById("error").innerHTML = "error, please, you must enter your information such as email, password, phone";
                }


            }


            window.addEventListener("load", function () {
                const toast = document.getElementById("errorToast");
                if (toast) {
                    toast.classList.add("show");

                    // Tự động ẩn sau 3 giây
                    setTimeout(() => closeToast(), 3000);
                }
            });

            function closeToast() {
                const toast = document.getElementById("errorToast");
                if (toast) {
                    toast.classList.remove("show");
                    // Ẩn hẳn sau khi hết animation
                    setTimeout(() => toast.remove(), 400);
                }
            }
        </script>
        <!--         Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/bootstrap.bundle.min.js"></script>

    </body>
</html>

