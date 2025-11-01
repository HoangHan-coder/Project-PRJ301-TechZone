package dao;

import db.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Feedback;
import model.Product;

public class FeedBackDAO extends DBContext {

    public int getAll() {
        try {
            String sql = "SELECT COUNT(a.FeedbackId) as result FROM FeedBack a";

            PreparedStatement st = this.getConnection().prepareCall(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("result");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Feedback> getAllPage(int page, int totalpage) {
        try {
            int index = (page - 1) * 12;
            List<Feedback> list = new ArrayList<>();
            String sql = "SELECT a.Fullname, p.ProductName, o.FeedbackId, o.Message, o.Rating, o.isPublic, o.Status, o.ResponseAt, o.ResponseMessage "
                    + "FROM FeedBack o\n"
                    + "JOIN Accounts a ON o.AccountId = a.AccountId\n"
                    + "JOIN Product p ON p.ProductId = o.ProductId\n"
                    + "JOIN Orders r ON r.OrderId = o.OrderId\n"
                    + " WHERE IsPublic = 1\n"
                    + "ORDER BY o.FeedbackId\n"
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";

            PreparedStatement st = this.getConnection().prepareCall(sql);
            st.setInt(1, index);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString("Fullname"));
                Product product = new Product(rs.getString("ProductName"));
                Feedback feedback = new Feedback(account, product, rs.getInt("FeedbackId"), rs.getString("Message"),
                        rs.getInt("Rating"), rs.getBoolean("isPublic"), rs.getString("Status"), rs.getString("ResponseMessage"),
                        rs.getTimestamp("ResponseAt"));
                list.add(feedback);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Feedback getId(int id) {
        try {
            String sql = "SELECT f.FeedbackId, p.ProductName,f.createdAt,f.Rating,f.Message,f.ResponseMessage FROM FeedBack f JOIN Product p ON p.productid = f.productid WHERE f.FeedBackId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("ProductName"));
                Feedback feedback = new Feedback(rs.getInt("FeedbackId"), product, rs.getString("Message"), rs.getInt("Rating"), rs.getTimestamp("createdAt"), rs.getString("ResponseMessage"));
                return feedback;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int updateFeedBack(int id, String text) {
        try {
            String sql = "UPDATE FeedBack SET ResponseMessage = ? WHERE FeedbackId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, text);
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Feedback> getRating(int page, int totalpage, int rating) {
        try {
            int index = (page - 1) * 10;
            List<Feedback> list = new ArrayList<>();
            String sql = "SELECT a.Fullname, p.ProductName, o.FeedbackId, o.Message, o.Rating, o.isPublic, o.Status, o.ResponseAt, o.ResponseMessage "
                    + "FROM FeedBack o\n"
                    + "JOIN Accounts a ON o.AccountId = a.AccountId\n"
                    + "JOIN Product p ON p.ProductId = o.ProductId\n"
                    + "JOIN Orders r ON r.OrderId = o.OrderId\n"
                    + "WHERE o.Rating=? AND o.IsPublic = 1\n"
                    + "ORDER BY o.FeedbackId\n"
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";

            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, rating);
            st.setInt(2, index);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString("Fullname"));
                Product product = new Product(rs.getString("ProductName"));
                Feedback feedback = new Feedback(account, product, rs.getInt("FeedbackId"), rs.getString("Message"),
                        rs.getInt("Rating"), rs.getBoolean("isPublic"), rs.getString("Status"), rs.getString("ResponseMessage"),
                        rs.getTimestamp("ResponseAt"));
                list.add(feedback);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Feedback> getByKeyword(int page, int totalpage, String keyword) {
        List<Feedback> list = new ArrayList<>();
        try {
            int index = (page - 1) * 10;
            String sql = "SELECT a.Fullname, p.ProductName, o.FeedbackId, o.Message, o.Rating, "
                    + "o.isPublic, o.Status, o.ResponseAt, o.ResponseMessage "
                    + "FROM FeedBack o "
                    + "JOIN Accounts a ON o.AccountId = a.AccountId "
                    + "JOIN Product p ON p.ProductId = o.ProductId "
                    + "JOIN Orders r ON r.OrderId = o.OrderId "
                    + "WHERE a.Fullname LIKE ? OR p.ProductName LIKE ? "
                    + "AND IsPublic = 1\n"
                    + "ORDER BY o.FeedbackId "
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            String like = "%" + keyword + "%";
            st.setString(1, like);
            st.setString(2, like);
            st.setInt(3, index);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString("Fullname"));
                Product product = new Product(rs.getString("ProductName"));
                Feedback feedback = new Feedback(
                        account,
                        product,
                        rs.getInt("FeedbackId"),
                        rs.getString("Message"),
                        rs.getInt("Rating"),
                        rs.getBoolean("isPublic"),
                        rs.getString("Status"),
                        rs.getString("ResponseMessage"),
                        rs.getTimestamp("ResponseAt")
                );
                list.add(feedback);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Feedback> searchFeedback(int page, int totalpage, String keyword, int rating) {
        List<Feedback> list = new ArrayList<>();
        try {
            int index = (page - 1) * 10;
            // SQL có điều kiện lọc linh hoạt theo rating
            String sql = "SELECT a.Fullname, p.ProductName, o.FeedbackId, o.Message, o.Rating, "
                    + "o.isPublic, o.Status, o.ResponseAt, o.ResponseMessage "
                    + "FROM FeedBack o "
                    + "JOIN Accounts a ON o.AccountId = a.AccountId "
                    + "JOIN Product p ON p.ProductId = o.ProductId "
                    + "JOIN Orders r ON r.OrderId = o.OrderId "
                    + "WHERE (a.Fullname LIKE ? OR p.ProductName LIKE ?) AND IsPublic = 1 ";

            // Nếu rating > 0 thì thêm điều kiện lọc sao
            if (rating > 0) {
                sql += "AND o.Rating = ? ";
            }

            sql += "ORDER BY o.FeedbackId "
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";

            PreparedStatement st = this.getConnection().prepareStatement(sql);

            String like = "%" + keyword + "%";
            st.setString(1, like);
            st.setString(2, like);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getString("Fullname"));
                Product product = new Product(rs.getString("ProductName"));
                Feedback feedback = new Feedback(
                        account,
                        product,
                        rs.getInt("FeedbackId"),
                        rs.getString("Message"),
                        rs.getInt("Rating"),
                        rs.getBoolean("isPublic"),
                        rs.getString("Status"), rs.getString("ResponseMessage"),
                        rs.getTimestamp("ResponseAt")
                );
                list.add(feedback);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int getUpdateDelete(int id) {
        try {
            String sql = "UPDATE Feedback SET IsPublic = 0 WHERE FeedBackId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

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
        String sql = "SELECT f.*, a.fullName "
                + "FROM Feedback f JOIN Accounts a ON f.accountId = a.accountId "
                + "WHERE f.productId = ? ORDER BY f.createdAt DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback();
                fb.setFeedbackId(rs.getInt("feedbackId"));
                Product p = new Product();
                p.setProductId(rs.getInt("productId"));
                fb.setProduct(p);

                Account acc = new Account();
                acc.setAccountId(rs.getInt("accountId"));
                acc.setFullName(rs.getString("fullName"));
                fb.setAccount(acc);

                fb.setSubject(rs.getString("subject"));
                fb.setMessage(rs.getString("message"));
                fb.setRating(rs.getInt("rating"));
                fb.setCreatedAt(rs.getTimestamp("createdAt"));
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
