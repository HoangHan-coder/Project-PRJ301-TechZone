/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

<<<<<<< HEAD
import dao.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
=======
import java.io.IOException;
>>>>>>> origin/main-core
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
<<<<<<< HEAD
import java.util.List;
import model.Order;

/**
 *
 * @author admin
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OrderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OrderServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

=======

/**
 *
 * @author NgKaitou
 */
@WebServlet(name = "Order", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

>>>>>>> origin/main-core
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
<<<<<<< HEAD
        
        OrderDAO order = new OrderDAO();
        List<Order> list = order.getAll();
        request.setAttribute("orderList", list);       
       request.getRequestDispatcher("/WEB-INF/views/user/cart/order.jsp").forward(request, response);
       
       
=======
        String action = request.getParameter("action");
        if (action == null) {
            action = "order-list";
        }

        if (action.equalsIgnoreCase("order-detail")) {
            request.getRequestDispatcher("/WEB-INF/views/user/order-detail.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("order-list")) {
            request.getRequestDispatcher("/WEB-INF/views/user/order-list.jsp").forward(request, response);
        }
>>>>>>> origin/main-core
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
<<<<<<< HEAD
        processRequest(request, response);
=======

>>>>>>> origin/main-core
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
