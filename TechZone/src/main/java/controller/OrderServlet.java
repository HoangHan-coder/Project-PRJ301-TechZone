/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.OrderDAO;
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
            List<Voucher> vouchers = voucherDAO.getAvailableVoucher(productPrice*quantity);
            String error = (String) request.getSession().getAttribute("error");
            if(error != null) {
                request.getSession().removeAttribute("error");
                request.setAttribute("error", error);
            }
            request.setAttribute("product", product);
            request.setAttribute("quantity", quantity);
            request.setAttribute("vouchers", vouchers);
            request.getRequestDispatcher("/WEB-INF/views/user/order/check-out.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response);
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shippingAddress = request.getParameter("shippingAddress");
        String[] productIds = request.getParameterValues("productId");
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] quantities = request.getParameterValues("quantity");

        List<OrderItem> orderItems = new ArrayList<>();

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                Product p = new Product();
                p.setProductId(Integer.parseInt(productIds[i]));
                p.setProductName(productNames[i]);
                p.setProductPrice(Double.parseDouble(productPrices[i]));
                OrderItem item = new OrderItem();
                item.setProduct(p);
                item.setQuantity(Integer.parseInt(quantities[i]));
                orderItems.add(item);
            }
        }
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        String paymentMethod = request.getParameter("paymentMethod");
        double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        int accountId = Integer.parseInt(request.getParameter("accountId"));

        OrderDAO orderDAO = new OrderDAO();
        int resultOrder = orderDAO.createOrder(accountId, totalAmount, shippingFee, shippingAddress, paymentMethod, voucherId);
        if(voucherId != 0 ) {
            VoucherDAO voucherDAO = new VoucherDAO();
            Voucher voucher = voucherDAO.getByVoucherId(voucherId);
            voucherDAO.useVoucher(voucher, totalAmount);
        }
        
        if (resultOrder != 1) {
            String errors = "Tạo đơn thất bại!";
            request.getSession().setAttribute("errors", errors);
            response.sendRedirect(getServletContext().getContextPath() + "/order?view=check-out");
            return;
        }
        int OrderId = orderDAO.maxId();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        for (OrderItem orderItem : orderItems) {
            int resultOrderItem = orderItemDAO.createOrderItem(OrderId, orderItem.getProduct().getProductId(), orderItem.getProduct().getProductName(), orderItem.getProduct().getProductPrice(), orderItem.getQuantity());
            if (resultOrderItem != 1) {
                String error = "Tạo đơn thất bại!";
                request.getSession().setAttribute("error", error);
                response.sendRedirect(getServletContext().getContextPath() + "/order?view=check-out");
                return;
            }
        }
        response.sendRedirect(getServletContext().getContextPath() + "/order");
    }

}
