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

/**
 * DAO for feedback management (create, query, search, update, soft-delete).
 */
public class FeedBackDAO extends DBContext {

    /**
     * Counts all feedback rows.
     *
     * @return total number of feedback entries
     */
    public int getAll() {
        try {
            String sql = "SELECT COUNT(a.FeedbackId) as result FROM FeedBack a";
            // Prepare statement for SQL query
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

    /**
     * Counts public feedback matching product name keyword.
     *
     * @param text search keyword
     * @return count of matched rows
     */
    public int getAllKeyword(String text) {
        try {
            String sql = "SELECT COUNT(o.FeedbackId) AS result "
                    + "FROM FeedBack o "
                    + "JOIN Accounts a ON o.AccountId = a.AccountId "
                    + "JOIN Product p ON p.ProductId = o.ProductId "
                    + "JOIN Orders r ON r.OrderId = o.OrderId "
                    + "WHERE p.ProductName LIKE ? "
                    + "AND IsPublic = 1\n";
            // Prepare statement for SQL query
            PreparedStatement st = this.getConnection().prepareCall(sql);
            st.setString(1, '%' + text + '%');
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("result");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Counts public feedback by rating value.
     *
     * @param rating rating value
     * @return count of matched rows
     */
    public int getAllRating(int rating) {
        try {
            String sql = "SELECT COUNT(o.FeedbackId) AS result "
                    + "FROM FeedBack o\n"
                    + "JOIN Accounts a ON o.AccountId = a.AccountId\n"
                    + "JOIN Product p ON p.ProductId = o.ProductId\n"
                    + "JOIN Orders r ON r.OrderId = o.OrderId\n"
                    + "WHERE o.Rating=? AND o.IsPublic = 1\n";
            // Prepare statement for SQL query
            PreparedStatement st = this.getConnection().prepareCall(sql);
            st.setInt(1, rating);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("result");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Counts public feedback by rating and keyword.
     *
     * @param rating rating value
     * @param text product name keyword
     * @return count of matched rows
     */
    public int getAllRatingAndKeyword(int rating, String text) {
        try {
            String sql = "SELECT COUNT(o.FeedbackId) AS result "
                    + "FROM FeedBack o "
                    + "JOIN Accounts a ON o.AccountId = a.AccountId "
                    + "JOIN Product p ON p.ProductId = o.ProductId "
                    + "JOIN Orders r ON r.OrderId = o.OrderId "
                    + "WHERE (p.ProductName LIKE ? AND o.Rating LIKE ?) AND IsPublic = 1 ";
            // Prepare statement for SQL query
            PreparedStatement st = this.getConnection().prepareCall(sql);
            st.setString(1, text);
            st.setInt(2, rating);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("result");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Retrieves a page of public feedback entries.
     *
     * @param page page number (1-based)
     * @param totalpage unused but preserved for compatibility
     * @return list of feedback
     */
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
            // Prepare statement for SQL query
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

    /**
     * Gets feedback detail by id (includes product name and timestamps).
     *
     * @param id feedback id
     * @return {@link Feedback} or null
     */
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

    /**
     * Updates the admin response text of a feedback.
     *
     * @param id feedback id
     * @param text response message
     * @return affected rows
     */
    public int updateFeedBack(int id, String text) {
        try {
            String sql = "UPDATE FeedBack SET ResponseMessage = ?, ResponseAt = ? WHERE FeedbackId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, text);
            st.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
            st.setInt(3, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Retrieves public feedback list by rating with pagination.
     *
     * @param page page number
     * @param totalpage unused but preserved for compatibility
     * @param rating rating value to filter
     * @return list of feedback
     */
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
            // Prepare statement for SQL query
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

    /**
     * Retrieves public feedback list by keyword with pagination.
     *
     * @param page page number
     * @param totalpage unused
     * @param keyword search term for account full name or product name
     * @return list of feedback
     */
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
            // Prepare statement for SQL query
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

    /**
     * Searches public feedback by keyword and rating with pagination.
     *
     * @param page page number
     * @param totalpage unused
     * @param keyword product keyword
     * @param rating rating
     * @return list of feedback
     */
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
                    + "WHERE (p.ProductName LIKE ? AND o.Rating LIKE ?) AND IsPublic = 1 "
                    + "ORDER BY o.FeedbackId "
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
            // Prepare statement for SQL query
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, '%' + keyword + '%');
            st.setInt(2, rating);
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

    /**
     * Soft-deletes a feedback (set IsPublic to 0).
     *
     * @param id feedback id
     * @return affected rows
     */
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

    /**
     * Finds an order id by account and product to validate purchase before
     * feedback.
     *
     * @param accountId account id
     * @param productId product id
     * @return order id or null
     */
    public Integer getOrderIdByAccountAndProduct(int accountId, int productId) {
        String sql = "SELECT TOP 1 od.OrderId FROM OrderItems od JOIN Orders o ON od.OrderId = o.OrderId WHERE o.Status = 'COMPLETED' AND o.AccountId = ? AND od.ProductId = ? ";
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

    /**
     * Gets all feedback for a product ordered by creation time (desc).
     *
     * @param productId product id
     * @return list of feedback
     */
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

    /**
     * Inserts a new feedback row.
     *
     * @param accountId account id
     * @param productId product id
     * @param orderId order id to link
     * @param message feedback text
     * @param rating rating value
     */
    public void addFeedback(int accountId, int productId, int orderId, String message, int rating) {
        try {
            String sql = "INSERT INTO Feedback (AccountId, ProductId, OrderId, Message, Rating, IsPublic, Status, CreatedAt) VALUES (?, ?, ?, ?,?, 1, 'Pending', GETDATE())";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setInt(2, productId);
            ps.setInt(3, orderId);
            ps.setString(4, message);
            ps.setInt(5, rating);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
