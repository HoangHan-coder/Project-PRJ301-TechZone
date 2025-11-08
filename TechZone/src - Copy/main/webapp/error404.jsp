<%-- 
    Document   : error404
    Created on : Nov 5, 2025, 9:17:21 PM
    Author     : letan
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>404 - Trang không tồn tại</title>
    <style>
        body {
            background-color: #f2f2f2;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            text-align: center;
            padding-top: 100px;
        }
        h1 {
            font-size: 80px;
            color: #ff5555;
            margin-bottom: 10px;
        }
        p {
            font-size: 20px;
            color: #555;
        }
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            background-color: #007bff;
            color: white;
            padding: 10px 25px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>404</h1>
    <p>Trang bạn yêu cầu không tồn tại hoặc đã bị xóa.</p>
    <button type="button" onclick="history.back(); return false;">🏠 Quay lại trang chủ</button>
</body>
</html>
