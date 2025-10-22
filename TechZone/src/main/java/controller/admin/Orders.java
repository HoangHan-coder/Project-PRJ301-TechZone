/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.OderListDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import model.Accounts;
import model.OrderList;
import model.Product;
import model.DetailOrder;
import dto.OrderItemDTO;

/**
 *
 * @author letan
 */
@WebServlet(name = "Orders", urlPatterns = {"/admin/order"})
public class Orders extends HttpServlet {

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
            out.println("<title>Servlet Orders</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Orders at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        String view = request.getParameter("view");
        OderListDAO order = new OderListDAO();
        if(view==null) view="list";
        
        switch (view) {
            case "list":
                List<OrderList> list = order.getAll();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/WEB-INF/views/admin/Orders/list.jsp").forward(request, response);
                break;
            case "detail":
                String id = request.getParameter("id");
                model.Orders orders = order.getOrderInfoById(Integer.parseInt(id));
                List<OrderItemDTO> products = order.getProductsByOrderId(Integer.parseInt(id));
                Accounts account = order.getAccountByOrderId(Integer.parseInt(id));
                request.setAttribute("account", account);
                request.setAttribute("order", orders);
                request.setAttribute("products", products);
                BigDecimal totalAmount = BigDecimal.ZERO;
                for (Product p : products) {
                    BigDecimal lineTotal = BigDecimal.valueOf(p.getProductPrice()).multiply(BigDecimal.valueOf(p.getStock()));
                    totalAmount = totalAmount.add(lineTotal);
                }
                request.setAttribute("totalamount", totalAmount);
                request.getRequestDispatcher("/WEB-INF/views/admin/Orders/detail.jsp").forward(request, response);
                break;

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
        String view = request.getParameter("view");
        OderListDAO order = new OderListDAO();
        System.out.println(view);
        if (view.equals("update")) {
            String type = request.getParameter("type");
            String id = request.getParameter("id");
            switch (type) {
                case "processing":
                    order.updateProccessing(Integer.parseInt(id), type);
                    break;
                case "pending":
                    order.updatePending(Integer.parseInt(id), type);
                    break;
                case "cancel":
                    order.updateCancel(Integer.parseInt(id), type);
                    break;
                case "delete":
                       System.out.println("Dele");
                    order.updateDelete(Integer.parseInt(id), "True");
                    break;
                default:
                    break;

            }
            response.sendRedirect(request.getContextPath() + "/admin/order?view=list");
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
