package controller;

import dao.CartDAO;
import dao.CartItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.AccountUsers;
import model.Cart;
import model.CartItem;

@WebServlet(name = "CartItemServlet", urlPatterns = {"/cartitem"})
public class CartItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String Id = request.getParameter("cartItemId");
        System.out.println("hello mmyy");
        HttpSession session = request.getSession(false);
        AccountUsers account = (AccountUsers) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int accountId = account.getId();
        
        if(action == null || action.equals("listCart")){
            CartItemDAO cartItemDAO = new CartItemDAO();
        List<CartItem> list = cartItemDAO.getListByAccountId(accountId);

        request.setAttribute("cartItems", list);
        request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
        }
        
       else if(action.equals("delete")){
            System.out.println(Id);
        request.setAttribute("cartItemId", Id);
        request.getRequestDispatcher("/WEB-INF/views/user/confirm.jsp").forward(request, response);
        }
        
        
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        AccountUsers account = new AccountUsers();
        
        System.out.println(action);

        int accountId = account.getId();
        CartItemDAO cartItemDAO = new CartItemDAO();
        CartDAO cartDAO = new CartDAO();

        switch (action) {
            // 🟢 Thêm sản phẩm vào giỏ
            case "create-cart": {
                try {
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
                        response.sendRedirect("cartitem");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for cart creation.");
                }
                break;
            }

            // 🟠 Cập nhật số lượng
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
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for update.");
                }
                break;
            }

            // 🔴 Xóa sản phẩm khỏi giỏ
            case "delete": {
                try {
                    int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                   
                    boolean deleted = cartDAO.deleteItem(cartItemId);
                    System.out.println(deleted);
                    if (deleted) {
                        response.sendRedirect("cartitem");
                    } else {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete item.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for delete.");
                } catch (NumberFormatException e) {
                    System.out.println(">>>>>>>>>NUmberEx");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters for delete.");
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
                        