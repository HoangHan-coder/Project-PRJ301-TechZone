/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Order;
import model.OrderCore;
import model.Voucher;

/**
 * DAO for order management (query, create).
 *
 * <p>Provides APIs to retrieve orders by user/status, fetch an order core by id,
 * generate new orders, and utility helpers.</p>
 */
public class OrderDAO extends db.DBContext {

    /**
     * Retrieves orders for a username filtered by status (LIKE).
     *
     * @param username account username
     * @param statusOrder status filter (LIKE)
     * @return list of {@link Order}
     */
    public List<Order> getOrderByUser(String username, String statusOrder) {
        List<Order> list = new ArrayList<>();
        try {

            String query = "SELECT Orders.orderId, Accounts.accountId, Orders.OrderCode, Orders.OrderTime, Orders.TotalAmount, Orders.ShippingFee, Orders.Status, Orders.ShippingAddress, Orders.PaymentMethod, Orders.PaymentStatus, Orders.VoucherId, Orders.IsDeleted\n"
                    + "FROM     Accounts INNER JOIN\n"
                    + "                  Orders ON Accounts.AccountId = Orders.AccountId\n"
                    + "WHERE  Accounts.Username = ? AND Orders.IsDeleted = 0 AND Orders.Status LIKE ? ;";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, "%" + statusOrder + "%"); // apply LIKE filter
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int accountId = rs.getInt("accountId");
                String orderCode = rs.getString("orderCode");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();
                double totalAmount = rs.getDouble("totalAmount");
                double shippingFee = rs.getDouble("shippingFee");
                String status = rs.getString("status");
                String shippingAddress = rs.getString("shippingAddress");
                String paymentMethod = rs.getString("paymentMethod");
                String paymentStatus = rs.getString("paymentStatus");
                Integer voucherId = rs.getInt("voucherId");
                boolean isDeleted = rs.getBoolean("IsDeleted");
                Order order = new Order(orderId, accountId, orderCode, orderTime, totalAmount, shippingFee, status, shippingAddress, paymentMethod, paymentStatus, voucherId, isDeleted); // map row to Order

                list.add(order);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Retrieves orders by status substring (LIKE) for admin list.
     *
     * @param statusOrder status filter
     * @return list of {@link Order}
     */
    public List<Order> getOrderByStatus(String statusOrder) {
        if (statusOrder == null || statusOrder.isEmpty()) {
            statusOrder = "";
        }
        List<Order> list = new ArrayList<>();
        try {

            String query = "SELECT * FROM Orders where Status like ?";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setString(1, "%" + statusOrder + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int accountId = rs.getInt("accountId");
                String orderCode = rs.getString("orderCode");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();
                double totalAmount = rs.getDouble("totalAmount");
                double shippingFee = rs.getDouble("shippingFee");
                String status = rs.getString("status");
                String shippingAddress = rs.getString("shippingAddress");
                String paymentMethod = rs.getString("paymentMethod");
                String paymentStatus = rs.getString("paymentStatus");
                Integer voucherId = rs.getInt("voucherId");
                boolean isDeleted = rs.getBoolean("isDelete");
                Order order = new Order(orderId, accountId, orderCode, orderTime, totalAmount, shippingFee, status, shippingAddress, paymentMethod, paymentStatus, voucherId, isDeleted); // map row to Order

                list.add(order);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Retrieves core order details by order id including voucher and account.
     *
     * @param orderIdRaw order id
     * @return {@link OrderCore} or null
     */
    public OrderCore getOrderByOrderId(int orderIdRaw) {
        try {

            String query = "SELECT * FROM Orders where orderId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setInt(1, orderIdRaw);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int accountId = rs.getInt("accountId");
                String orderCode = rs.getString("orderCode");
                LocalDateTime orderTime = rs.getTimestamp("orderTime").toLocalDateTime();
                BigDecimal totalAmount = rs.getBigDecimal("totalAmount");
                BigDecimal shippingFee = rs.getBigDecimal("shippingFee");
                String status = rs.getString("status");
                String shippingAddress = rs.getString("shippingAddress");
                String paymentMethod = rs.getString("paymentMethod");
                String paymentStatus = rs.getString("paymentStatus");
                int voucherId = rs.getInt("voucherId");
                boolean isDeleted = rs.getBoolean("isDeleted");
                VoucherDAO voucherDAO = new VoucherDAO(); // fetch voucher detail
                Voucher v = voucherDAO.getByVoucherId(voucherId);
                AccountDAO accountDAO = new AccountDAO(); // fetch account detail
                Account a = accountDAO.getById(accountId);
                return new OrderCore(orderId, a, orderCode, orderTime, totalAmount, shippingFee, status, shippingAddress, paymentMethod, paymentStatus, v, isDeleted);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns the maximum OrderId in Orders table.
     * @return max id or 0
     */
    public int maxId() {
        try {
            String sql = "select MAX(OrderId) from Orders";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Creates a new order with default status PROCESSING and PaymentStatus Unpaid.
     *
     * @param AccountId account id
     * @param TotalAmount total amount
     * @param ShippingFee shipping fee
     * @param ShippingAddress shipping address string
     * @param PaymentMethod payment method label
     * @param VoucherId voucher id or 0 for null
     * @return affected rows (1 on success)
     */
    public int createOrder(int AccountId, double TotalAmount, double ShippingFee, String ShippingAddress, String PaymentMethod, Integer VoucherId) {
        String orderCode = "ORD" + System.currentTimeMillis();
        String sql = "INSERT INTO Orders (AccountId, OrderCode, TotalAmount, ShippingFee, Status, ShippingAddress, PaymentMethod, PaymentStatus, VoucherId)\n"
                + "VALUES (?, ?, ?, ?, N'PROCESSING', ?, ?, N'Unpaid', ?);";
        try {
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, AccountId);
            ps.setString(2, orderCode);
            ps.setBigDecimal(3, BigDecimal.valueOf(TotalAmount));
            ps.setBigDecimal(4, BigDecimal.valueOf(ShippingFee));
            ps.setString(5, ShippingAddress);
            ps.setString(6, PaymentMethod);
            if (VoucherId == 0) {
                ps.setNull(7, java.sql.Types.INTEGER); // no voucher applied
            } else {
                ps.setInt(7, VoucherId); // bind voucher id
            }
            return ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
