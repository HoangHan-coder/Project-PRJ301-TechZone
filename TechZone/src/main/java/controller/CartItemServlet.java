package controller;

import dao.CartDAO;
import dao.CartItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import model.AccountUsers;
import model.Cart;
import model.CartItem;

@WebServlet(name = "CartItemServlet", urlPatterns = {"/cartitem"})
public class CartItemServlet extends HttpServlet {

    private boolean isAjax(HttpServletRequest request) {
        String xrw = request.getHeader("X-Requested-With");
        return xrw != null && xrw.equalsIgnoreCase("XMLHttpRequest");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String view = request.getParameter("view");
        HttpSession session = request.getSession(false);
        AccountUsers account = (AccountUsers) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (view == null || view.equals("listCart")) {
            CartItemDAO cartItemDAO = new CartItemDAO();
            List<CartItem> list = cartItemDAO.getListByAccountId(account.getId());
            request.setAttribute("cartItems", list);
            request.getRequestDispatcher("/WEB-INF/views/user/cart/cart.jsp").forward(request, response);
        } else if (view.equals("delete")) {
            String Id = request.getParameter("cartId");
            System.out.println(Id);
            request.setAttribute("cartId", Id);
            request.getRequestDispatcher("/WEB-INF/views/user/cart/confirm.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);

        CartItemDAO cartItemDAO = new CartItemDAO();
        CartDAO cartDAO = new CartDAO();
        HttpSession session = request.getSession(false);
        AccountUsers account = (AccountUsers) session.getAttribute("account");
        switch (action) {
            case "create-cart": {
                try {
                    int accountId = account.getId();
                    int productId = Integer.parseInt(request.getParameter("productId"));
                    double productPrice = Double.parseDouble(request.getParameter("productPrice"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    Cart cart = cartDAO.getActiveCartByAccountId(accountId);
                    if (cart == null) {
                        int created = cartDAO.createCart(accountId);
                        if (created > 0) {
                            cart = cartDAO.getLatestCartByAccountId(accountId);
                        }
                    }

                    if (cart != null) {
                        int cartId = cart.getCartId();
                        CartItem existingItem = cartItemDAO.getCartItem(cartId, productId);
                        if (existingItem != null) {
                            cartItemDAO.increaseQuantity(existingItem.getCartItemId(), quantity);
                        } else {
                            cartItemDAO.createCartItems(cartId, productId, productPrice, quantity);
                        }
                        if (isAjax(request)) {
                            response.setContentType("application/json;charset=UTF-8");
                            try (PrintWriter out = response.getWriter()) {
                                out.write("{\"status\":\"ok\",\"message\":\"Added to cart\"}");
                            }
                        } else {
                            response.sendRedirect("cartitem");
                        }
                    }
                } catch (IOException | NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for cart creation.");
                }
                break;
            }

            // 🟠 Update quantity
            case "update": {
                try {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));

                    boolean updated = cartItemDAO.updateQuantity(cartItemId, quantity);
                    if (updated) {
                        response.sendRedirect("cartitem");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update quantity.");
                    }
                } catch (IOException | NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for update.");
                }
                break;
            }

            // 🔴 Delete entire cart by cartId
            case "delete": {
                try {
                    int cartId = Integer.parseInt(request.getParameter("cartId"));
                    boolean deleted = cartDAO.deleteItem(cartId);
                    System.out.println(deleted);
                    if (deleted) {
                        response.sendRedirect("cartitem");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete item.");
                    }
                } catch (IOException | NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for delete.");
                }

                break;
            }

            // 🟣 Delete a single CartItem by cartItemId (per-item delete)
            case "delete-item": {
                try {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                    boolean ok = cartItemDAO.deleteById(cartItemId);
                    if (isAjax(request)) {
                        response.setContentType("application/json;charset=UTF-8");
                        try (PrintWriter out = response.getWriter()) {
                            if (ok) {
                                out.write("{\"status\":\"ok\"}");
                            } else {
                                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                out.write("{\"status\":\"error\"}");
                            }
                        }
                    } else {
                        if (ok) {
                            response.sendRedirect("cartitem");
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete cart item.");
                        }
                    }
                } catch (IOException | NumberFormatException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for delete-item.");
                }
                break;
            }

            // 🟢 Delete multiple selected CartItems
            case "delete-items": {
                try {
                    String[] ids = request.getParameterValues("selectedCartItemIds");
                    boolean allOk = true;
                    if (ids != null) {
                        for (String s : ids) {
                            try {
                                int id = Integer.parseInt(s);
                                if (!cartItemDAO.deleteById(id)) {
                                    allOk = false;
                                }
                            } catch (NumberFormatException ignore) {
                                allOk = false;
                            }
                        }
                    }
                    if (allOk) {
                        response.sendRedirect("cartitem");
                    } else {
                        response.sendRedirect("cartitem");
                    }
                } catch (IOException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for delete-items.");
                }
                break;
            }

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet quản lý giỏ hàng (Thêm, Cập nhật, Xóa) - dùng logic tránh duplicate items";
    }
}
