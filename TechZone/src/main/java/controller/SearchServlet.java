/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Product;

/**
 *
 * @author PC
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  

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
response.setCharacterEncoding("UTF-8");
response.setContentType("text/html;charset=UTF-8");


        String action = request.getParameter("action");
        ProductDAO dao = new ProductDAO();

        if ("filter".equals(action)) {
            int cateid = Integer.parseInt(request.getParameter("cateid"));
            String brand = request.getParameter("brand");

            List<Product> list;

     
            if (brand == null || brand.trim().isEmpty()) {
                list = dao.getProductsByCategory(cateid);
            } else {
                list = dao.getFilterBrand(cateid, brand);
            }

            request.setAttribute("list", list);

   
            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/filter-result.jsp")
                    .forward(request, response);

        } else if ("search".equals(action)) {
            String txtSearch = request.getParameter("txtSearch");
            List<Product> list = dao.getAllProductsSearch(txtSearch);


            request.setAttribute("list", list);
            request.setAttribute("txtSearch", txtSearch);

            request.getRequestDispatcher("/WEB-INF/views/user/product/product-list/product-filter.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/search");
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