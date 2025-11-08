/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Order;

/**
 *
 * @author admin
 */
public class OrderDAO extends db.DBContext {

    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        try {

            String query = "SELECT * FROM Orders";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
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
                Order order = new Order(orderId, accountId, orderCode, orderTime, totalAmount, shippingFee, status, shippingAddress, paymentMethod, paymentStatus, voucherId, isDeleted);

                list.add(order);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
