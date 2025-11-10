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
import jakarta.servlet.http.HttpSession;
import model.AccountUsers;

/**
 *
 * @author acer
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        AccountUsers accuser = (AccountUsers) session.getAttribute("account");
        
        AuthDAO daoAcc = new AuthDAO();
        if(action == null || action.equals("setting")){
            System.out.println(accuser.getId());
            AccountUsers user = daoAcc.getAccounts(accuser.getId());
            request.setAttribute("userAccountInfo", user);
            request.getRequestDispatcher("/WEB-INF/views/profile/account-profile.jsp").forward(request, response);
        } 
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String action = request.getParameter("action");
        
        AuthDAO daoAcc = new AuthDAO();
        PrintWriter out = response.getWriter();
        if(action.equals("update")){
            String name = request.getParameter("name");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
            int success = daoAcc.updateAccount(name, fullname, email, phone);
            if(success == 1){
                 String json = "{ \"success\": true, \"message\":\"Update successfully!\" }";
            out.print(json);
            } else if(success == 0){
                 String json = "{ \"success\": false, \"message\":\"Update failure!\" }";
            out.print(json);
            } else if(success == 3){
                String json = "{ \"success\": false, \"message\":\"Update failure!, email is duplicated, email cannot duplicated\" }";
            out.print(json);
            } else if(success == 4){
                String json = "{ \"success\": false, \"message\":\"Update failure!, phone is duplicated, email cannot duplicated\" }";
            out.print(json);
            }
            
            
            out.flush();
        } else if (action.equals("updatepassword")){
            String name = request.getParameter("name");
            String password = request.getParameter("password");
            String newpassword = request.getParameter("newpassword");
            int success = daoAcc.updatePassword(name , password, newpassword);
            if(success == 1){
                 String json = "{ \"success\": true, \"message\":\"Update password successfully!\" }";
            out.print(json);
            } else {
                 String json = "{ \"success\": false, \"message\":\"Update password failure!, current password is not correct!\" }";
            out.print(json);
            }
            out.flush();
        }
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
