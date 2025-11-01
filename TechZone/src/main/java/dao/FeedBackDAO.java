package dao;

import db.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Feedback;
import model.Account;
import model.Product;

public class FeedBackDAO extends DBContext {

    public Integer getOrderIdByAccountAndProduct(int accountId, int productId) {
        String sql = "SELECT TOP 1 od.OrderId FROM OrderItems od JOIN Orders o ON od.OrderId = o.OrderId WHERE o.AccountId = ? AND od.ProductId = ? ";
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("OrderId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // không có đơn hàng nào
    }

    public List<Feedback> getFeedbackByProductId(int productId) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT f.*, a.fullName AS feedbackerName, r.fullName AS responderName\n"
                + "FROM Feedback f\n"
                + "JOIN Accounts a ON f.accountId = a.accountId\n"
                + "LEFT JOIN Accounts r ON f.responseByAccountId = r.accountId\n"
                + "WHERE f.productId = ?\n"
                + "ORDER BY f.createdAt DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setFeedbackId(rs.getInt("feedbackId"));

                Product p = new Product();
                p.setProductId(rs.getInt("productId"));
                fb.setProduct(p);

                // Người viết feedback
                Account acc = new Account();
                acc.setAccountId(rs.getInt("accountId"));
                acc.setFullName(rs.getString("feedbackerName"));
                fb.setAccount(acc);

                // Người phản hồi feedback (nếu có)
                int responseById = rs.getInt("responsebyaccountid");
                if (responseById != 0) {
                    Account responder = new Account();
                    responder.setAccountId(responseById);
                    responder.setFullName(rs.getString("responderName"));
                    fb.setResponseBy(responder);
                }

                fb.setSubject(rs.getString("subject"));
                fb.setMessage(rs.getString("message"));
                fb.setRating(rs.getInt("rating"));
                fb.setCreatedAt(rs.getTimestamp("createdAt"));
                fb.setResponseMessage(rs.getString("ResponseMessage"));
                fb.setResponseAt(rs.getTimestamp("ResponseAt"));

                list.add(fb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addFeedback(int accountId, int productId, int orderId, String message, int rating, String subject) {
        try {
            String sql = "INSERT INTO Feedback (AccountId, ProductId, OrderId, Subject, Message, Rating, IsPublic, Status, CreatedAt) VALUES (?, ?, ?, ?, ?,?, 1, 'Pending', GETDATE())";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            ps.setInt(1, accountId);
            ps.setInt(2, productId);
            ps.setInt(3, orderId);
            ps.setString(4, subject);
            ps.setString(5, message);
            ps.setInt(6, rating);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
