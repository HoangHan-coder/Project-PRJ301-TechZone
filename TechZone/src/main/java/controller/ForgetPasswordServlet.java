/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AuthDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author acer
 */
@WebServlet(name = "ForgetPasswordServlet", urlPatterns = {"/forgetpassword"})
public class ForgetPasswordServlet extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user/forgetpassword.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        PrintWriter out = response.getWriter();
        AuthDAO acc = new AuthDAO();
        int success = acc.updateForgetPassword(password, email);
        if(success == 1){
            String json = "{ \"success\": true, \"message\":\"change forgot password successfully!\" }";
            out.print(json);
        } else {
            String json = "{ \"success\": false, \"message\":\"change forgot password failure, email is not correct!\" }";
            out.print(json);
        }
        out.flush();
        
    }

    
    

}
