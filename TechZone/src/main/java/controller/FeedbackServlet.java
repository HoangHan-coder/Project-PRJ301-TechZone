package controller;

import dao.FeedBackDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
<<<<<<< HEAD

import model.AccountUsers;
=======
>>>>>>> main-core

import model.AccountUsers;

@WebServlet(name = "FeedbackServlet", urlPatterns = {"/feedback-user"})
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
<<<<<<< HEAD
        request.getRequestDispatcher("/WEB-INF/views/user/product/product-detail/product-detail.jsp");
=======
        request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
>>>>>>> main-core
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedBackDAO dao = new FeedBackDAO();
        String productId_raw = request.getParameter("productId");
        String message = request.getParameter("message");
        String rating_raw = request.getParameter("rating");

        if (productId_raw == null || rating_raw == null || productId_raw.isEmpty() || rating_raw.isEmpty()
<<<<<<< HEAD
                || subject == null || subject.isEmpty()
=======
>>>>>>> main-core
                || message == null || message.isEmpty()) {
            request.getSession().setAttribute("msgee", "Thiếu thông tin đánh giá. Vui lòng nhập đầy đủ các mục, xin cảm ơn!");
            response.sendRedirect("products?action=detail&id=" + productId_raw);
            return;
        }

        int productId = Integer.parseInt(productId_raw);
        int rating = Integer.parseInt(rating_raw);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        AccountUsers acc = (AccountUsers) session.getAttribute("account");
        int accountId = acc.getId();

        Integer orderId = dao.getOrderIdByAccountAndProduct(accountId, productId);

        if (orderId == null) {
            request.getSession().setAttribute("msg", "Bạn chưa mua đơn hàng nên không thể FEEDBACK vui lòng đặt hàng để trải nghiệm");
            response.sendRedirect("products?action=detail&id=" + productId);
            return;
        }

<<<<<<< HEAD
        dao.addFeedback(accountId, productId, orderId, message, rating, subject);
=======
        dao.addFeedback(accountId, productId, orderId, message, rating);
>>>>>>> main-core

        response.sendRedirect("products?action=detail&id=" + productId);
    }

<<<<<<< HEAD
}
=======
}
>>>>>>> main-core
