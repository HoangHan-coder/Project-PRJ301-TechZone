package controller;

import dao.FeedBackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.AccountUsers;

/**
 * Handles creation of product feedback by authenticated users.
 *
 * <p>URL mapping: <code>/feedback-user</code>. This servlet validates the
 * incoming request parameters, ensures that the user has purchased the
 * product before allowing feedback, and persists the feedback using
 * {@link dao.FeedBackDAO}.</p>
 */
@WebServlet(name = "FeedbackServlet", urlPatterns = {"/feedback-user"})
public class FeedbackServlet extends HttpServlet {

    /**
     * Forwards to product detail page when accessed via GET (no feedback creation).
     *
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to the product detail page (no action performed here)
        request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp");
        // Note: Missing forward/redirect is intentional per original code; no changes made
    }

    /**
     * Handles POST requests to create a feedback entry for a product.
     *
     * <p>Validates required parameters, ensures the user has an eligible order
     * for the product, then inserts the feedback and redirects back to the
     * product detail page.</p>
     *
     * @param request HTTP request containing productId, message, and rating
     * @param response HTTP response used for redirection
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedBackDAO dao = new FeedBackDAO(); // DAO used to validate order and insert feedback
        String productId_raw = request.getParameter("productId"); // product identifier (string)
        String message = request.getParameter("message"); // feedback content
        String rating_raw = request.getParameter("rating"); // rating score (string)

        // Validate required parameters; if missing, set session message and redirect back
        if (productId_raw == null || rating_raw == null || productId_raw.isEmpty() || rating_raw.isEmpty()
                || message == null || message.isEmpty()) {
            request.getSession().setAttribute("msgee", "Thiếu thông tin đánh giá. Vui lòng nhập đầy đủ các mục, xin cảm ơn!");
            response.sendRedirect("products?action=detail&id=" + productId_raw);
            return;
        }

        int productId = Integer.parseInt(productId_raw); // parse productId to int
        int rating = Integer.parseInt(rating_raw); // parse rating to int

        HttpSession session = request.getSession(false); // get existing session
        AccountUsers acc = (AccountUsers) session.getAttribute("account"); // current user from session
        int accountId = acc.getId(); // get account id

        Integer orderId = dao.getOrderIdByAccountAndProduct(accountId, productId); // ensure user purchased product

        if (orderId == null) { // no eligible order found
            request.getSession().setAttribute("msg", "Bạn chưa mua đơn hàng nên không thể FEEDBACK vui lòng đặt hàng để trải nghiệm");
            response.sendRedirect("products?action=detail&id=" + productId); // redirect back to detail
            return;
        }

        dao.addFeedback(accountId, productId, orderId, message, rating); // persist feedback

        response.sendRedirect("products?action=detail&id=" + productId); // back to product detail
    }
}