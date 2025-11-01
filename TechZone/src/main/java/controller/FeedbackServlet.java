package controller;

import dao.FeedBackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import model.Account;
import model.AccountUsers;
import model.Feedback;

@WebServlet(name = "FeedbackServlet", urlPatterns = {"/feedback"})
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         FeedBackDAO dao = new FeedBackDAO();
        String productId_raw = request.getParameter("productId");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String rating_raw = request.getParameter("rating");
        // --- 2. Kiểm tra dữ liệu hợp lệ ---
        if (productId_raw == null || rating_raw == null || productId_raw.isEmpty() || rating_raw.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu thông tin đánh giá.");
            return;
        }

        int productId = Integer.parseInt(productId_raw);
        int rating = Integer.parseInt(rating_raw);

        // --- 3. Lấy thông tin người dùng đang đăng nhập ---
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập
            return;
        }

        AccountUsers acc = (AccountUsers) session.getAttribute("account");
        int accountId = acc.getId();

        // Nếu bạn có logic để lấy orderId thật, thay thế dòng này:
        Integer orderId = dao.getOrderIdByAccountAndProduct(accountId, productId);

        if (orderId == null) {
            request.getSession().setAttribute("msg", "Bạn chưa mua đơn hàng nên không thể FEEDBACK vui lòng đặt hàng để trải nghiệm");
            response.sendRedirect("products?action=detail&id=" + productId);
            return;
        }


        dao.addFeedback(accountId, productId, orderId, message, rating, subject);



        // --- 5. Quay lại trang chi tiết sản phẩm ---
        response.sendRedirect("products?action=detail&id=" + productId);
    }
    
}
