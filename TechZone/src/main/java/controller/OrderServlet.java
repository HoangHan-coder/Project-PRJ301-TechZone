/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CartDAO;
import dao.CartItemDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.OrderListDAO;
import dao.ProductDAO;
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
import model.CartItem;
import model.Order;
import model.OrderCore;
import model.OrderItem;
import model.Product;
import model.ResponseOrder;
import model.Voucher;

/**
 *
 * @author NgKaitou
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
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
            case "check-out-cart":
                getCheckOutFromCart(request, response);
                break;
            default:
                getListOrder(request, response);
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
        String orderStatus = request.getParameter("orderStatus");
        System.out.println("orderStatus: " + orderStatus);
        AccountUsers username = (AccountUsers) request.getSession().getAttribute("account");
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        OrderDAO orderDAO = new OrderDAO();
        System.out.println(username.getUsername());
        if (orderStatus == null) {
            orderStatus = "";
        }
        List<Order> orders = orderDAO.getOrderByUser(username.getUsername(), orderStatus);
        List<OrderItem> listOrder = orderItemDAO.getOrderItemfilterByStatus(username.getUsername(), orderStatus);
        System.out.println(listOrder.size());
        request.setAttribute("orders", orders.reversed());
        request.setAttribute("listOrder", listOrder);
        request.getRequestDispatcher("/WEB-INF/views/user/order/order-list.jsp").forward(request, response);
    }

    private void getOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            OrderItemDAO orderItemDAO = new OrderItemDAO();
            OrderListDAO orderListDAO = new OrderListDAO();
            List<OrderItem> orderItems = orderItemDAO.getByOrderId(orderId);
            OrderDAO orderDAO = new OrderDAO();
            OrderCore order = orderDAO.getOrderByOrderId(orderId);
            System.out.println("=== DEBUG createOrderItem parameters ===");
            System.out.println("OrderId: " + order.getOrderId());
            ResponseOrder responseOrder = orderListDAO.getResponse(orderId);
            request.setAttribute("responseOrder", responseOrder);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/views/user/order/order-detail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            System.out.println("id order error");
        }
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
            double totalAmount = productPrice * quantity;
            Product product = new Product();
            product.setProductId(productId);
            product.setLinkImg(productImg);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            System.out.println(productPrice * quantity);
            List<Voucher> vouchers = voucherDAO.getAvailableVoucher(totalAmount);
            String error = (String) request.getSession().getAttribute("error");
            if (error != null) {
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
        String cartId = request.getParameter("cartId");
        String[] cartItemIds = request.getParameterValues("cartItemId");

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
                ProductDAO productDAO = new ProductDAO();
                productDAO.updateProductStock(item.getQuantity(), p.getProductId());
                if(cartItemIds[i] != null) {
                    CartItemDAO cartItemDAO = new CartItemDAO();
                    cartItemDAO.deleteById(Integer.parseInt(cartItemIds[i]));
                }
            }
            if(cartId != null) {
                CartDAO cartDAO = new CartDAO();
                if(cartDAO.cartIsEmty(Integer.parseInt(cartId))) {
                    cartDAO.deleteItem(Integer.parseInt(cartId));
                }
            }
        }
        int voucherId = Integer.parseInt(request.getParameter("voucherId"));
        String paymentMethod = request.getParameter("paymentMethod");
        double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        int accountId = Integer.parseInt(request.getParameter("accountId"));

        OrderDAO orderDAO = new OrderDAO();
        int resultOrder = orderDAO.createOrder(accountId, totalAmount, shippingFee, shippingAddress, paymentMethod, voucherId);
        if (voucherId != 0) {
            VoucherDAO voucherDAO = new VoucherDAO();
            Voucher voucher = voucherDAO.getByVoucherId(voucherId);
            int usedVoucher = voucherDAO.useVoucher(voucher, totalAmount);
            System.out.println(usedVoucher == 1 ? "used voucher" : "don't use voucher");
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
        System.out.println("______________________________________________________________________________________________________--");
        response.sendRedirect(getServletContext().getContextPath() + "/order");
    }

    private void getCheckOutFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cartItemIds = request.getParameterValues("cartItemIds");
        List<Integer> listCartItemId = new ArrayList<>();
        double totalAmount = 0;
        for (String cartItemIdRaw : cartItemIds) {
            try {
                int cartItemId = Integer.parseInt(cartItemIdRaw);
                //int quantity = Integer.parseInt(request.getParameter("quantity-" + cartItemId));
                listCartItemId.add(cartItemId);
            } catch (NumberFormatException ex) {
                request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response);
                return;
            }
        }
        CartItemDAO cartItemDAO = new CartItemDAO();
        VoucherDAO voucherDAO = new VoucherDAO();
        List<CartItem> cartItems = cartItemDAO.getListFormCart(listCartItemId);
       
        for (CartItem cartItem : cartItems) {
            totalAmount += cartItem.getTotalPrice();
        }

        List<Voucher> vouchers = voucherDAO.getAvailableVoucher(totalAmount);
        String error = (String) request.getSession().getAttribute("error");
        if (error != null) {
            request.getSession().removeAttribute("error");
            request.setAttribute("error", error);
        }
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("vouchers", vouchers);
        request.getRequestDispatcher("/WEB-INF/views/user/order/check-out.jsp").forward(request, response);
    }

}
