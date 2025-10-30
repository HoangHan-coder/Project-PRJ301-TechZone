/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderItemDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.OrderItem;

/**
 *
 * @author NgKaitou
 */
@WebServlet(name = "Order", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        if (view == null) {
            view = "order-list";
        }

        switch (view) {
            case "order-list":
                getListOrder(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }


    private void getListOrder(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        List<OrderItem> listOrder = orderItemDAO.getOrderItemByUsername("user7");
        request.setAttribute("listOrder", listOrder);
        request.getRequestDispatcher("/WEB-INF/views/user/order/order-list.jsp").forward(request, response);
    }

}
