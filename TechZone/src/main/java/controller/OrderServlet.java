/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderItemDAO;
import dao.VoucherDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.AccountUsers;
import model.OrderItem;
import model.Product;
import model.Voucher;

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
            case "order-detail":
                getOrderDetail(request, response);
                break;
            case "check-out":
                getCheckOut(request, response);
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "check-out":
                createOrder(request, response);
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response);
        }
    }

    private void getListOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AccountUsers username = (AccountUsers) request.getSession().getAttribute("account");
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        System.out.println(username.getUsername());
        List<OrderItem> listOrder = orderItemDAO.getOrderItemByUsername(username.getUsername());
        System.out.println(listOrder.size());
        request.setAttribute("listOrder", listOrder);
        request.getRequestDispatcher("/WEB-INF/views/user/order/order-list.jsp").forward(request, response);
    }

    private void getOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderItemId = Integer.parseInt(request.getParameter("orderItemId"));
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        OrderItem orderItem = orderItemDAO.getById(orderItemId);
        System.out.println(orderItem.getOrder().getVoucher().getDiscountValue());
        request.setAttribute("orderItem", orderItem);
        request.getRequestDispatcher("/WEB-INF/views/user/order/order-detail.jsp").forward(request, response);
    }

    private void getCheckOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VoucherDAO voucherDAO = new VoucherDAO();
            List<Voucher> vouchers = voucherDAO.getAllVoucher();
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            String productImg = request.getParameter("productImg");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            Product product = new Product();
            product.setProductId(productId);
            product.setLinkImg(productImg);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            request.setAttribute("product", product);
            request.setAttribute("quantity", quantity);
            request.setAttribute("vouchers", vouchers);
            request.getRequestDispatcher("/WEB-INF/views/user/order/check-out.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response);
    }

}
