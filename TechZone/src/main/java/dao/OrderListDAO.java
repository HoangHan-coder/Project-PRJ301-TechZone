/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Orderlist;
import model.Orders;
import dto.OrderItemDTO;
import model.Account;
import model.ResponseOrder;

/**
 * DAO for admin order listing and management.
 * 
 * <p>Supports filtering, pagination, retrieving order detail info, updating
 * order statuses, soft delete, cancel reasons, and fetching associated
 * products.</p>
 */
public class OrderListDAO extends DBContext {

    /**
     * Gets all non-deleted orders basic info for listing.
     * @return list of {@link Orderlist}
     */
    public List<Orderlist> getAll() {
        try {
            String sql = "SELECT o.OrderId, o.OrderCode, a.FullName,o.TotalAmount,o.PaymentStatus,o.Status FROM Orders o\n"
                    + "JOIN Accounts a ON a.AccountId = o.AccountId\n"
                    + "WHERE o.IsDeleted = 'False'\n";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Orderlist> list = new ArrayList<>();
            while (rs.next()) {
                Orderlist order = new Orderlist(rs.getInt("OrderId"), rs.getString("OrderCode"), rs.getString("FullName"), rs.getDouble("TotalAmount"),
                        rs.getString("PaymentStatus"), rs.getString("Status"));
                list.add(order);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Counts filtered orders by optional order code and status.
     * @param orderCode order code substring
     * @param status exact status match
     * @return number of matched orders
     */
    public int countFilteredOrders(String orderCode, String status) {
        try {
            int count = 0;
            String sql = "SELECT COUNT(*) "
                    + "FROM Orders o "
                    + "JOIN Accounts a ON a.AccountId = o.AccountId "
                    + "WHERE o.IsDeleted = 'False'";

            if (orderCode != null && !orderCode.trim().isEmpty()) {
                sql += "AND o.OrderCode LIKE ? "; // add order code filter
            }
            if (status != null && !status.trim().isEmpty()) {
                sql += "AND o.Status = ? "; // add exact status filter
            }
            PreparedStatement ps = this.getConnection().prepareStatement(sql);

            int i = 1;
            if (orderCode != null && !orderCode.trim().isEmpty()) {
                ps.setString(i++, "%" + orderCode.trim() + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(i++, status);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Filters orders by optional code and status with pagination.
     * @param orderCode code substring
     * @param status exact status
     * @param page 1-based page index
     * @return list of {@link Orderlist}
     */
    public List<Orderlist> filterOrders(String orderCode, String status, int page) {
        try {
            int index = (page - 1) * 10;
            List<Orderlist> list = new ArrayList<>();

            String sql = "SELECT o.OrderId, o.OrderCode, a.FullName, o.TotalAmount, "
                    + "o.PaymentStatus, o.Status "
                    + "FROM Orders o "
                    + "JOIN Accounts a ON a.AccountId = o.AccountId "
                    + "WHERE o.IsDeleted = 'False' ";

            // Append filter conditions when provided
            if (orderCode != null && !orderCode.trim().isEmpty()) {
                sql += "AND o.OrderCode LIKE ? ";
            }
            if (status != null && !status.trim().isEmpty()) {
                sql += "AND o.Status = ? ";
            }

            // Always include default ORDER BY
            sql += "ORDER BY o.OrderId DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";

            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            int i = 1;

            if (orderCode != null && !orderCode.trim().isEmpty()) {
                ps.setString(i++, "%" + orderCode.trim() + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(i++, status);
            }

            ps.setInt(i++, index);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orderlist order = new Orderlist(
                        rs.getInt("OrderId"),
                        rs.getString("OrderCode"),
                        rs.getString("FullName"),
                        rs.getDouble("TotalAmount"),
                        rs.getString("PaymentStatus"),
                        rs.getString("Status")
                );
                list.add(order);
            }

            return list;
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves basic order info and customer details by order id.
     * @param orderId order id
     * @return {@link Orders} or null
     */
    public Orders getOrderInfoById(int orderId) {
        try {
            String sql = "SELECT o.OrderId, o.OrderCode, o.OrderTime, o.PaymentMethod, "
                    + "o.TotalAmount, o.Voucherid, o.PaymentStatus, o.Status, o.ShippingAddress, "
                    + "a.AccountId, a.FullName, a.Phone, a.Email "
                    + "FROM Orders o "
                    + "JOIN Accounts a ON a.AccountId = o.AccountId "
                    + "WHERE o.OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Orders order = new Orders();
                order.setOrderId(rs.getInt("OrderId"));
                order.setOrderCode(rs.getString("OrderCode"));
                order.setOrderTime(rs.getTimestamp("OrderTime").toLocalDateTime());
                order.setPaymentMethod(rs.getString("PaymentMethod"));
                order.setTotalAmount(rs.getDouble("TotalAmount"));
                order.setPaymentStatus(rs.getString("PaymentStatus"));
                order.setStatus(rs.getString("Status"));
                order.setShippingAddress(rs.getString("ShippingAddress"));
                order.setVoucherId(rs.getInt("Voucherid"));
                // Attach account info
                Account acc = new Account();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setFullName(rs.getString("FullName"));
                acc.setPhone(rs.getString("Phone"));
                acc.setEmail(rs.getString("Email"));
                order.setAccountId(acc.getAccountId());
                return order;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Gets product items (DTO) in an order.
     * @param orderId order id
     * @return list of {@link OrderItemDTO}
     */
    public List<OrderItemDTO> getProductsByOrderId(int orderId) {
        try {
            List<OrderItemDTO> list = new ArrayList<>();
            String sql = "SELECT p.ProductId, p.ProductName, r.UnitPrice, p.LinkImg, "
                    + "r.Quantity, r.UnitPrice, (r.Quantity * r.UnitPrice) AS Total, p.stock "
                    + "FROM OrderItems r "
                    + "JOIN Product p ON p.ProductId = r.ProductId "
                    + "WHERE r.OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, orderId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderItemDTO p = new OrderItemDTO();
                p.setProductId(rs.getInt("ProductId"));
                p.setProductName(rs.getString("ProductName"));
                p.setProductPrice(rs.getBigDecimal("UnitPrice"));
                p.setLinkImg(rs.getString("LinkImg"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setStock(rs.getInt("Stock"));
                // Additional fields (e.g., UnitPrice or Total) can be added if needed for display
                list.add(p);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves account basic info based on order id.
     * @param orderId order id
     * @return {@link Account} or null
     */
    public Account getAccountByOrderId(int orderId) {
        try {
            String sql = "SELECT a.AccountId, a.FullName, a.Email, a.Phone "
                    + "FROM Accounts a "
                    + "JOIN Orders o ON o.AccountId = a.AccountId "
                    + "WHERE o.OrderId = ?";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setFullName(rs.getString("FullName"));
                acc.setEmail(rs.getString("Email"));
                acc.setPhone(rs.getString("Phone"));
                return acc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates order status to the provided status (typically COMPLETED).
     * @param id order id
     * @param status new status (will be upper-cased)
     * @return affected rows
     */
    public int updateCompleted(int id, String status) {
        try {
            String sql = "UPDATE Orders \n"
                    + "   SET Status = ?\n"
                    + " WHERE OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, status.toUpperCase());
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Records a cancel reason for an order.
     * @param text reason text
     * @param id order id
     * @return affected rows
     */
    public int insetCancel(String text, int id) {
        try {
            String sql = "INSERT INTO [dbo].[responseOrder]\n"
                    + "           ([reason]\n"
                    + "           ,[orderId])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, text);
            Orders order = new Orders(id);
            st.setInt(2, order.getOrderId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Updates order status to the provided status (typically PENDING).
     * @param id order id
     * @param status new status
     * @return affected rows
     */
    public int updatePending(int id, String status) {
        try {
            String sql = "UPDATE Orders \n"
                    + "   SET Status = ?\n"
                    + " WHERE OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, status.toUpperCase());
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Updates order status to the provided status (typically CANCEL).
     * @param id order id
     * @param status new status
     * @return affected rows
     */
    public int updateCancel(int id, String status) {
        try {
            String sql = "UPDATE Orders \n"
                    + "   SET Status = ?\n"
                    + " WHERE OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, status.toUpperCase());
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Updates stock of a product by id.
     * @param id product id
     * @param status new stock value
     * @return affected rows
     */
    public int updateStock(int id, int status) {
        try {
            String sql = "UPDATE [dbo].[Product]\n"
                    + "   SET [Stock] = ?\n"
                    + " WHERE ProductId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, status);
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Soft-delete an order by toggling isDeleted flag.
     * @param id order id
     * @param status string flag (e.g., 'True'/'False')
     * @return affected rows
     */
    public int updateDelete(int id, String status) {
        try {
            String sql = "UPDATE Orders \n"
                    + "   SET isDeleted = ?\n"
                    + " WHERE OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Gets the cancel response detail for an order.
     * @param id order id
     * @return {@link ResponseOrder} or null
     */
    public ResponseOrder getResponse(int id) {
        try {
            String sql = "SELECT * FROM responseOrder WHERE orderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Orders order = new Orders(rs.getInt("orderId"));
                ResponseOrder orderCancel = new ResponseOrder(rs.getInt("responseOrderId"), rs.getString("reason"), order, rs.getTime("CreatedAt"));
                return orderCancel;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves a page of non-deleted orders for listing view.
     * @param page page number
     * @param totalpage unused
     * @return list of {@link Orderlist}
     */
    public List<Orderlist> getAllPage(int page, int totalpage) {
        try {
            int index = (page - 1) * 12;
            List<Orderlist> list = new ArrayList<>();
            String sql = "SELECT o.OrderId, o.OrderCode, a.FullName,o.TotalAmount,o.PaymentStatus,o.Status FROM Orders o\n"
                    + "JOIN Accounts a ON a.AccountId = o.AccountId\n"
                    + "WHERE o.IsDeleted = 'False'\n"
                    + "ORDER BY o.OrderId\n"
                    + "OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";

            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, index);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Orderlist order = new Orderlist(rs.getInt("OrderId"), rs.getString("OrderCode"), rs.getString("FullName"), rs.getDouble("TotalAmount"),
                        rs.getString("PaymentStatus"), rs.getString("Status"));
                list.add(order);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(FeedBackDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
