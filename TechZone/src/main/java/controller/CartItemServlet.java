/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CartDAO;
import dao.CartItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Cart;
import model.CartItem;

/**
 *
 * @author admin
 */
@WebServlet(name = "CartItemServlet", urlPatterns = {"/cartitem"})
public class CartItemServlet extends HttpServlet {


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
        
        CartItemDAO ca = new CartItemDAO();
        List<CartItem> list = ca.getList("user1");
        System.out.println(list.size());
        request.setAttribute("cartItems", list);       
        request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
        
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

    String action = request.getParameter("action");

    if ("create-cart".equals(action)) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String linkImg = request.getParameter("linkImg");
        double productPrice = Double.parseDouble(request.getParameter("productPrice"));
        int accountId = Integer.parseInt(request.getParameter("accountId"));

        CartDAO cartDAO = new CartDAO();
        CartItemDAO cartItemDAO = new CartItemDAO();

        // 1️⃣ Tạo giỏ hàng mới
        int result = cartDAO.createCart(accountId);
        if (result > 0) {
            // 2️⃣ Lấy cartId vừa tạo
            Cart cart = cartDAO.cartId();
            if (cart != null) {
                int cartId = cart.getCartId();

                // 3️⃣ Thêm sản phẩm vào bảng CartItems
                cartItemDAO.createCartItems(cartId, productId, productPrice, 1);

                response.sendRedirect("cartitem");
            }
        }
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
