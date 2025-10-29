<%-- 
    Document   : profile
    Created on : Oct 24, 2025, 5:01:21 PM
    Author     : acer
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../Includes/sidebar-profile.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h1>Profile</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item">Users</li>
                <li class="breadcrumb-item active">Profile</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section profile">
        <div class="row">
            <div class="col-xl-4">

                <div class="card">
                    <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">

                        <!-- <img src="assets/img/profile-img.jpg" alt="Profile" class="rounded-circle"> -->
                        <h2><%= user.getUsername()%></h2>
                        <!--              <h3>Web Designer</h3>-->
                        <div class="social-links mt-2">
                            <a href="#" class="twitter"><i class="bi bi-twitter"></i></a>
                            <a href="#" class="facebook"><i class="bi bi-facebook"></i></a>
                            <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
                            <a href="#" class="linkedin"><i class="bi bi-linkedin"></i></a>
                        </div>
                    </div>
                </div>

            </div>

            <div class="col-xl-8">

                <div class="card">
                    <div class="card-body pt-3">
                        <!-- Bordered Tabs -->
                        <ul class="nav nav-tabs nav-tabs-bordered" role="tablist">

                            <li class="nav-item" role="presentation">
                                <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#profile-overview" aria-selected="true" role="tab">Overview</button>
                            </li>

                            <li class="nav-item" role="presentation">
                                <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-edit" aria-selected="false" role="tab" tabindex="-1">Edit Profile</button>
                            </li>



                            <li class="nav-item" role="presentation">
                                <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-change-password" aria-selected="false" role="tab" tabindex="-1">Change Password</button>
                            </li>

                        </ul>
                        <div class="tab-content pt-2">

                            <div class="tab-pane fade profile-overview active show" id="profile-overview" role="tabpanel">


                                <h5 class="card-title">Profile Details</h5>

                                <div class="row">
                                    <div class="col-lg-3 col-md-4 label ">Full Name</div>
                                    <div class="col-lg-9 col-md-8"><%= user.getFullname()%></div>
                                </div>



                                <div class="row">
                                    <div class="col-lg-3 col-md-4 label">Phone</div>
                                    <div class="col-lg-9 col-md-8"><%= user.getPhone()%></div>
                                </div>

                                <div class="row">
                                    <div class="col-lg-3 col-md-4 label">Email</div>
                                    <div class="col-lg-9 col-md-8"><%= user.getEmail()%></div>
                                </div>

                            </div>

                            <div class="tab-pane fade profile-edit pt-3" id="profile-edit" role="tabpanel">

                                <!-- Profile Edit Form -->
                                <form>


                                    <div class="row mb-3">
                                        <label for="company" class="col-md-4 col-lg-3 col-form-label">UserName</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="username" type="text" class="form-control" id="company" value="<%= user.getUsername()%>" wfd-id="id2" onblur="checkUserName()">
                                            <p id="nameError"> </p>
                                        </div>
                                    </div>

                                    <div class="row mb-3">
                                        <label for="fullName" class="col-md-4 col-lg-3 col-form-label">Full Name</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="fullName" type="text" class="form-control" id="fullName" value="<%= user.getFullname()%>" wfd-id="id1" onblur="checkFullName()">
                                        <p id="fullnameError"> </p>
                                        </div>
                                    </div>



                                    <div class="row mb-3">
                                        <label for="Phone" class="col-md-4 col-lg-3 col-form-label">Phone</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="phone" type="text" class="form-control" id="Phone" value="<%= user.getPhone()%>" wfd-id="id6" onblur="checkPhone()">
                                        <p id="phoneError"> </p>
                                        </div>
                                    </div>

                                    <div class="row mb-3">
                                        <label for="Email" class="col-md-4 col-lg-3 col-form-label">Email</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="email" type="email" class="form-control" id="Email" value="<%= user.getEmail()%>" wfd-id="id7" onblur="checkEmail()">
                                        <p id="emailError"> </p>
                                        </div>
                                    </div>



                                    <div class="text-center">
                                        <button type="button" onclick="HandleSubmit()" class="btn btn-primary">Save Changes</button>
                                    <p id="InfoError"> </p>
                                    </div>
                                        
                                </form><!-- End Profile Edit Form -->

                            </div>





                            <div class="tab-pane fade pt-3" id="profile-change-password" role="tabpanel">
                                <!-- Change Password Form -->
                                <form>

                                    <div class="row mb-3">
                                        <label for="currentPassword" class="col-md-4 col-lg-3 col-form-label">Current Password</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="password" type="password" class="form-control" id="currentPassword" wfd-id="id16">
                                        </div>
                                    </div>

                                    <div class="row mb-3">
                                        <label for="newPassword" class="col-md-4 col-lg-3 col-form-label">New Password</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="newpassword" type="password" class="form-control" id="newPassword" wfd-id="id17">
                                        </div>
                                    </div>

                                    <div class="row mb-3">
                                        <label for="renewPassword" class="col-md-4 col-lg-3 col-form-label">Re-enter New Password</label>
                                        <div class="col-md-8 col-lg-9">
                                            <input name="renewpassword" type="password" class="form-control" id="renewPassword" wfd-id="id18">
                                        </div>
                                    </div>

                                    <div class="text-center">
                                        <button type="submit" class="btn btn-primary">Change Password</button>
                                    </div>
                                </form><!-- End Change Password Form -->

                            </div>

                        </div><!-- End Bordered Tabs -->

                    </div>
                </div>

            </div>
        </div>
    </section>



</main>
<script>
    function checkUserName() {
        const username = document.getElementById("company").value.trim();
        if (username === "") {
            document.getElementById("nameError").innerHTML = "Please enter your username, cannot empty";
        } else {
            document.getElementById("nameError").innerHTML = "";
        }
    }
    
    function checkPhone() {
                const phone = document.getElementById("Phone").value.trim();
                if (phone === "") {
                    document.getElementById("phoneError").innerHTML = "Please enter your phone, cannot empty";
                } else {
                    if(validatePhone()){
                        document.getElementById("phoneError").innerHTML = "";
                    } else {
                        document.getElementById("phoneError").innerHTML = "numberphone is not valid, please enter your phone again such as +84912345678 or 0912345678";
                    }
                    
                }
            }

            function validatePhone() {
                const phone = document.getElementById("Phone").value.trim();

                const regexVN = /^(0|\+84)(3|5|7|8|9)\d{8}$/;
                const regexINT = /^\+[1-9]\d{7,14}$/;

                if (regexVN.test(phone) || regexINT.test(phone)) {

                    return true; 
                } else {

                    return false; 
                }
            }
            
            function validateFullName() {
                const FullName = document.getElementById("fullName").value.trim();
                const regexName = /^[\p{L}]+(?:\s[\p{L}]+)*$/u;
                 if (regexName.test(FullName)) {
                    return true; 
                } else {

                    return false; 
                }
            }
            
            function checkFullName() {
        const fullname = document.getElementById("fullName").value.trim();
        if (fullname === "") {
            document.getElementById("fullnameError").innerHTML = "Please enter your fullname, cannot empty";
        } else {
           if(validateFullName()){
                        document.getElementById("fullnameError").innerHTML = "";
                    } else {
                        document.getElementById("fullnameError").innerHTML = "Full Name is not valid, please enter your Full name again such as Vo Thanh Dat or VoThanhDat";
                    }
        }
    }
    
    function validateEmail() {
                const email = document.getElementById("Email").value.trim();
                const regex = /^[A-Za-z][A-Za-z0-9._]*@[A-Za-z]+\.(com)$/;
                 if (regex.test(email)) {
                    return true; 
                } else {

                    return false; 
                }
            }
            
            
            function checkEmail() {
        const email = document.getElementById("Email").value.trim();
        if (email === "") {
            document.getElementById("emailError").innerHTML = "Please enter your email, cannot empty";
        } else {
           if(validateEmail()){
                        document.getElementById("emailError").innerHTML = "";
                    } else {
                        document.getElementById("emailError").innerHTML = "Email is not valid, please enter your Email again such as ThanhDat@gmail.com";
                    }
        }
    }
    
    
    async function HandleSubmit() {
                const username = document.getElementById("company").value.trim();
                const fullName = document.getElementById("fullName").value.trim();
                const phone = document.getElementById("Phone").value.trim();
                const email = document.getElementById("Email").value.trim();
                if (username !== "" && fullName !== "" && phone !== "" && email !== "") {
                    if (validatePhone()) {
                        if(validateFullName()){
                            if (validateEmail()) {
                            try {
                                const res = await fetch("http://localhost:8080/TechZone/profile", {
                                    method: "POST",
                                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                                    body: `name=\${encodeURIComponent(username)}&fullname=\${encodeURIComponent(fullName)}&phone=\${encodeURIComponent(phone)}&email=\${encodeURIComponent(email)}&action=\${encodeURIComponent("update")}`
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
                                document.getElementById("InfoError").innerHTML = "Error: cannot connect to server.";

                            }
                        } else {
                            document.getElementById("InfoError").innerHTML = "error, email is not valid, it must be a example : thanhdat@gmail.com, cannot be @@@@@, cannot be 1@1";
                        }
                        } else {
                            document.getElementById("InfoError").innerHTML = "Full Name is not valid, please enter your Full name again such as Vo Thanh Dat or VoThanhDat";
                        }
                        
                    } else {
                        document.getElementById("InfoError").innerHTML = "numberphone is not valid, please enter your phone again such as +84912345678 or 0912345678";
                    }


                } else {
                    document.getElementById("InfoError").innerHTML = "error, please, you must enter your information such as email, fullname, phone, username";
                }


            }

</script>

<%@include file="../Includes/footer-profile.jsp" %>

