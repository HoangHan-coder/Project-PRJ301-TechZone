/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Accounts;
import model.DetailOrder;
import model.Orderlist;
import model.Orders;
import dto.OrderItemDTO;

/**
 *
 * @author letan
 */
public class OderListDAO extends DBContext {

    public List<Orderlist> getAll() {
        try {
            String sql = "SELECT o.OrderId, o.OrderCode, a.FullName,o.TotalAmount,o.PaymentStatus,o.Status FROM Orders o\n"
                    + "JOIN Accounts a ON a.AccountId = o.AccountId";
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
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Orders getOrderInfoById(int orderId) {
        try {
            String sql = "SELECT o.OrderId, o.OrderCode, o.OrderTime, o.PaymentMethod, "
                    + "o.TotalAmount, o.PaymentStatus, o.Status, o.ShippingAddress, "
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
                // Gắn thông tin account
                Accounts acc = new Accounts();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setFullName(rs.getString("FullName"));
                acc.setPhone(rs.getString("Phone"));
                acc.setEmail(rs.getString("Email"));
                order.setAccountId(acc.getAccountId());
                return order;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<OrderItemDTO> getProductsByOrderId(int orderId) {
        try {
            List<OrderItemDTO> list = new ArrayList<>();
            String sql = "SELECT p.ProductId, p.ProductName, p.ProductPrice, p.LinkImg, "
                    + "r.Quantity, r.UnitPrice, (r.Quantity * r.UnitPrice) AS Total "
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
                p.setProductPrice(rs.getBigDecimal("ProductPrice"));
                p.setLinkImg(rs.getString("LinkImg"));
                p.setQuantity(rs.getInt("Quantity"));
                // Có thể thêm field phụ nếu bạn muốn hiển thị UnitPrice và Total
                list.add(p);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Accounts getAccountByOrderId(int orderId) {
        try {
            String sql = "SELECT a.AccountId, a.FullName, a.Email, a.Phone "
                    + "FROM Accounts a "
                    + "JOIN Orders o ON o.AccountId = a.AccountId "
                    + "WHERE o.OrderId = ?";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Accounts acc = new Accounts();
                acc.setAccountId(rs.getInt("AccountId"));
                acc.setFullName(rs.getString("FullName"));
                acc.setEmail(rs.getString("Email"));
                acc.setPhone(rs.getString("Phone"));
                return acc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int updateProccessing(int id, String status) {
        try {
            String sql = "UPDATE Orders \n"
                    + "   SET Status = ?\n"
                    + " WHERE OrderId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, status.toUpperCase());
            st.setInt(2, id);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

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
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

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
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

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
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Orderlist> getPage(int page, int totalpage) {
        try {
            int index = (page - 1) * 12;
            List<Orderlist> list = new ArrayList<>();
            String sql = "SELECT o.*, a.Fullname, a.Email\n"
                    + "FROM Orders o\n"
                    + "JOIN Accounts a ON o.AccountId = a.AccountId\n"
                    + "ORDER BY o.OrderId\n" +
            "OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY";
            PreparedStatement st = this.getConnection().prepareCall(sql);
            st.setInt(1, index);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Orderlist order = new Orderlist(rs.getInt("OrderId"), rs.getString("OrderCode"), rs.getString("FullName"), rs.getDouble("TotalAmount"),
                        rs.getString("PaymentStatus"), rs.getString("Status"));
                list.add(order);

            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(OderListDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }
}
