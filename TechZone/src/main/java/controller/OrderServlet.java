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
 * Handles order listing, details, checkout flows, and order creation.
 *
 * <p>GET routes by view param: order-list, order-detail, check-out, check-out-cart.
 * POST handles order creation.</p>
 *
 * @author NgKaitou
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {


    /**
     * Routes GET requests to specific order views based on 'view' parameter.
     *
     * @param request incoming HTTP request
     * @param response HTTP response for forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view"); // which view to render
        if (view == null) {
            view = "order-list"; // default view
        }

        switch (view) {
            case "order-list":
                getListOrder(request, response); // list user orders
                break;
            case "order-detail":
                getOrderDetail(request, response); // order detail
                break;
            case "check-out":
                getCheckOut(request, response); // checkout for single product
                break;
            case "check-out-cart":
                getCheckOutFromCart(request, response); // checkout from selected cart items
                break;
            default:
                getListOrder(request, response); // fallback
        }
    }


    /**
     * Handles POST actions such as 'check-out' for creating orders.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // expected: check-out
        switch (action) {
            case "check-out":
                createOrder(request, response); // create new order
                break;
            default:
                request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response); // unknown action
        }
    }

    /**
     * Loads order list for current user, supports optional status filter.
     */
    private void getListOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderStatus = request.getParameter("orderStatus"); // filter by status
        System.out.println("orderStatus: " + orderStatus);
        AccountUsers username = (AccountUsers) request.getSession().getAttribute("account"); // current user
        OrderItemDAO orderItemDAO = new OrderItemDAO(); // DAO for order items
        OrderDAO orderDAO = new OrderDAO(); // DAO for orders
        System.out.println(username.getUsername()); // debug user
        if (orderStatus == null) {
            orderStatus = ""; // default no filter
        }
        List<Order> orders = orderDAO.getOrderByUser(username.getUsername(), orderStatus); // fetch orders
        List<OrderItem> listOrder = orderItemDAO.getOrderItemfilterByStatus(username.getUsername(), orderStatus); // fetch items filtered
        System.out.println(listOrder.size()); // debug size
        request.setAttribute("orders", orders.reversed()); // reverse to show latest first
        request.setAttribute("listOrder", listOrder); // attach list
        request.getRequestDispatcher("/WEB-INF/views/user/order/order-list.jsp").forward(request, response); // forward to JSP
    }

    /**
     * Shows details for a specific order by orderId.
     */
    private void getOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId")); // parse id
            OrderItemDAO orderItemDAO = new OrderItemDAO(); // DAO for items
            OrderListDAO orderListDAO = new OrderListDAO(); // DAO for response model
            List<OrderItem> orderItems = orderItemDAO.getByOrderId(orderId); // fetch items
            OrderDAO orderDAO = new OrderDAO(); // DAO for core order
            OrderCore order = orderDAO.getOrderByOrderId(orderId); // fetch core order
            System.out.println("=== DEBUG createOrderItem parameters ===");
            System.out.println("OrderId: " + order.getOrderId()); // debug
            ResponseOrder responseOrder = orderListDAO.getResponse(orderId); // build response info
            request.setAttribute("responseOrder", responseOrder); // attach response
            request.setAttribute("orderItems", orderItems); // attach items
            request.setAttribute("order", order); // attach order
            request.getRequestDispatcher("/WEB-INF/views/user/order/order-detail.jsp").forward(request, response); // forward
        } catch (NumberFormatException ex) {
            System.out.println("id order error"); // invalid id
        }
    }

    /**
     * Prepares checkout view for a single product (not from cart).
     */
    private void getCheckOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            VoucherDAO voucherDAO = new VoucherDAO(); // DAO for vouchers

            int productId = Integer.parseInt(request.getParameter("productId")); // parse values from request
            String productName = request.getParameter("productName");
            String productImg = request.getParameter("productImg");
            double productPrice = Double.parseDouble(request.getParameter("productPrice"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double totalAmount = productPrice * quantity; // compute total
            Product product = new Product(); // create product view model
            product.setProductId(productId);
            product.setLinkImg(productImg);
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            System.out.println(productPrice * quantity);
            List<Voucher> vouchers = voucherDAO.getAvailableVoucher(totalAmount); // list vouchers by total
            String error = (String) request.getSession().getAttribute("error"); // check session error
            if (error != null) {
                request.getSession().removeAttribute("error"); // clear once
                request.setAttribute("error", error); // pass to view
            }
            request.setAttribute("product", product); // attach product
            request.setAttribute("quantity", quantity); // attach quantity
            request.setAttribute("vouchers", vouchers); // attach vouchers
            request.getRequestDispatcher("/WEB-INF/views/user/order/check-out.jsp").forward(request, response); // forward to checkout
        } catch (NumberFormatException ex) {
            request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response); // invalid params
        }
    }

    /**
     * Creates a new order from posted items (single or from cart), updates stock,
     * and applies voucher usage when present.
     */
    private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shippingAddress = request.getParameter("shippingAddress");
        String[] productIds = request.getParameterValues("productId"); // arrays of posted products
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("productPrice");
        String[] quantities = request.getParameterValues("quantity");
        String cartId = request.getParameter("cartId");
        String[] cartItemIds = request.getParameterValues("cartItemId");

        List<OrderItem> orderItems = new ArrayList<>(); // collected items

        if (productIds != null) {
            for (int i = 0; i < productIds.length; i++) {
                Product p = new Product(); // build product snapshot
                p.setProductId(Integer.parseInt(productIds[i]));
                p.setProductName(productNames[i]);
                p.setProductPrice(Double.parseDouble(productPrices[i]));
                OrderItem item = new OrderItem(); // build order item
                item.setProduct(p);
                item.setQuantity(Integer.parseInt(quantities[i]));
                orderItems.add(item); // add to list
                ProductDAO productDAO = new ProductDAO(); // update stock and sold
                productDAO.updateProductStock(item.getQuantity(), p.getProductId());
                if(cartItemIds != null) {
                    CartItemDAO cartItemDAO = new CartItemDAO(); // remove checked-out cart items
                    cartItemDAO.deleteById(Integer.parseInt(cartItemIds[i]));
                }
            }
            if(cartId != null) {
                CartDAO cartDAO = new CartDAO(); // if cart now empty, delete it
                if(cartDAO.cartIsEmty(Integer.parseInt(cartId))) {
                    cartDAO.deleteItem(Integer.parseInt(cartId));
                }
            }
        }
        int voucherId = Integer.parseInt(request.getParameter("voucherId")); // 0 means no voucher
        String paymentMethod = request.getParameter("paymentMethod"); // payment method
        double shippingFee = Double.parseDouble(request.getParameter("shippingFee")); // shipping fee
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount")); // total after discount
        int accountId = Integer.parseInt(request.getParameter("accountId")); // current account id

        OrderDAO orderDAO = new OrderDAO(); // DAO for order
        int resultOrder = orderDAO.createOrder(accountId, totalAmount, shippingFee, shippingAddress, paymentMethod, voucherId); // create order record
        if (voucherId != 0) {
            VoucherDAO voucherDAO = new VoucherDAO(); // mark voucher usage
            Voucher voucher = voucherDAO.getByVoucherId(voucherId);
            int usedVoucher = voucherDAO.useVoucher(voucher, totalAmount);
            System.out.println(usedVoucher == 1 ? "used voucher" : "don't use voucher");
        }

        if (resultOrder != 1) {
            String errors = "Tạo đơn thất bại!"; // order creation failed
            request.getSession().setAttribute("errors", errors);
            response.sendRedirect(getServletContext().getContextPath() + "/order?view=check-out"); // redirect back
            return;
        }
        int OrderId = orderDAO.maxId(); // get created order id
        OrderItemDAO orderItemDAO = new OrderItemDAO(); // DAO for order items
        for (OrderItem orderItem : orderItems) {
            int resultOrderItem = orderItemDAO.createOrderItem(OrderId, orderItem.getProduct().getProductId(), orderItem.getProduct().getProductName(), orderItem.getProduct().getProductPrice(), orderItem.getQuantity()); // insert item
            if (resultOrderItem != 1) {
                String error = "Tạo đơn thất bại!"; // item insert failed
                request.getSession().setAttribute("error", error);
                response.sendRedirect(getServletContext().getContextPath() + "/order?view=check-out"); // redirect back
                return;
            }
        }
        System.out.println("______________________________________________________________________________________________________--"); // debug separator
        response.sendRedirect(getServletContext().getContextPath() + "/order"); // go to orders page
    }

    /**
     * Prepares checkout view using selected cart items.
     */
    private void getCheckOutFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cartItemIds = request.getParameterValues("cartItemIds"); // selected items from form
        List<Integer> listCartItemId = new ArrayList<>(); // parsed ids
        double totalAmount = 0; // sum of selected items
        for (String cartItemIdRaw : cartItemIds) {
            try {
                int cartItemId = Integer.parseInt(cartItemIdRaw); // parse id
                //int quantity = Integer.parseInt(request.getParameter("quantity-" + cartItemId));
                listCartItemId.add(cartItemId); // collect id
            } catch (NumberFormatException ex) {
                request.getRequestDispatcher("/WEB-INF/views/includes/error.jsp").forward(request, response); // invalid id
                return;
            }
        }
        CartItemDAO cartItemDAO = new CartItemDAO(); // DAO to fetch selected items
        VoucherDAO voucherDAO = new VoucherDAO(); // DAO for vouchers
        List<CartItem> cartItems = cartItemDAO.getListFormCart(listCartItemId); // fetch details
       
        for (CartItem cartItem : cartItems) {
            totalAmount += cartItem.getTotalPrice(); // sum prices
        }

        List<Voucher> vouchers = voucherDAO.getAvailableVoucher(totalAmount); // vouchers by total
        String error = (String) request.getSession().getAttribute("error"); // get session error
        if (error != null) {
            request.getSession().removeAttribute("error"); // clear once
            request.setAttribute("error", error); // pass to view
        }
        request.setAttribute("cartItems", cartItems); // attach items
        request.setAttribute("vouchers", vouchers); // attach vouchers
        request.getRequestDispatcher("/WEB-INF/views/user/order/check-out.jsp").forward(request, response); // forward to checkout
    }

}
