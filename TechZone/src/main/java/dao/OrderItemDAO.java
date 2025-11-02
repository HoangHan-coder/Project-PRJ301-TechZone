/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
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
import model.OrderCore;
import model.OrderItem;
import model.Product;
import model.Voucher;

/**
 *
 * @author NgKaitou
 */
public class OrderItemDAO extends DBContext {

    public List<OrderItem> getOrderItemByUsername(String username) {
        List<OrderItem> listOrderItems = new ArrayList<>();
        String sql = "SELECT Accounts.Username, Accounts.FullName, Accounts.Phone, Accounts.IsDeleted AS accIsDeaded, Product.LinkImg, Accounts.AccountId, OrderItems.OrderItemId, OrderItems.UnitPrice, OrderItems.ProductNameSnapshot, \n"
                + "                  OrderItems.Quantity, OrderItems.TotalPrice, Orders.OrderId, Orders.OrderCode, Orders.OrderTime, Orders.TotalAmount, Orders.ShippingFee, Orders.ShippingAddress, Orders.Status AS OrderStatus, Orders.PaymentMethod, \n"
                + "                  Orders.PaymentStatus, Orders.IsDeleted AS orderIsDeaded, Product.ProductId, Vouchers.VoucherId, Vouchers.Code AS VoucherCode, Vouchers.DiscountValue, Vouchers.DiscountType, Vouchers.Status AS VoucherStatus\n"
                + "FROM     Accounts INNER JOIN\n"
                + "                  Orders ON Accounts.AccountId = Orders.AccountId INNER JOIN\n"
                + "                  OrderItems ON Orders.OrderId = OrderItems.OrderId INNER JOIN\n"
                + "                  Product ON OrderItems.ProductId = Product.ProductId LEFT JOIN\n"
                + "                  Vouchers ON Orders.VoucherId = Vouchers.VoucherId\n"
                + "WHERE Accounts.Username = ?  AND Orders.IsDeleted = 0;";

        try {
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("FullName"), rs.getString("Phone"), rs.getBoolean("accIsDeaded"));
                Product product = new Product();
                product.setProductId(rs.getInt("ProductId"));
                product.setLinkImg(rs.getString("LinkImg"));
                Voucher voucher = new Voucher(rs.getInt("VoucherId"), rs.getString("VoucherCode"), rs.getBigDecimal("DiscountValue"),
                        rs.getString("DiscountType"), rs.getString("VoucherStatus"));
                OrderCore order = new OrderCore(rs.getInt("OrderId"), account, rs.getString("OrderCode"),
                        rs.getTimestamp("OrderTime").toLocalDateTime(), rs.getBigDecimal("TotalAmount"),
                        rs.getBigDecimal("ShippingFee"), rs.getString("OrderStatus"),
                        rs.getString("ShippingAddress"), rs.getString("PaymentMethod"),
                        rs.getString("PaymentStatus"), voucher, rs.getBoolean("orderIsDeaded"));
                OrderItem orderItem = new OrderItem(rs.getInt("OrderItemId"), order, product, rs.getString("ProductNameSnapshot"), rs.getBigDecimal("UnitPrice"), rs.getInt("Quantity"), rs.getBigDecimal("TotalPrice"));
                listOrderItems.add(orderItem);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listOrderItems;
    }

    public OrderItem getById(int id) {
        String sql = "SELECT Accounts.AccountId, Accounts.Username, Accounts.FullName, Accounts.Phone, Accounts.IsDeleted as accountIsDeleted, OrderItems.OrderItemId, OrderItems.ProductNameSnapshot, OrderItems.UnitPrice, OrderItems.Quantity, OrderItems.TotalPrice, Orders.OrderId , Orders.OrderCode, \n"
                + "                  Orders.OrderTime, Orders.TotalAmount, Orders.ShippingFee, Orders.Status AS orderStatus, Orders.ShippingAddress, Orders.PaymentMethod, Orders.PaymentStatus, Orders.IsDeleted AS orderIsDead, Vouchers.VoucherId, Vouchers.Code AS voucherCode, Vouchers.DiscountValue, \n"
                + "                  Vouchers.DiscountType, Vouchers.Status AS voucherStatus, Product.LinkImg\n"
                + "FROM     Accounts INNER JOIN\n"
                + "                  Orders ON Accounts.AccountId = Orders.AccountId INNER JOIN\n"
                + "                  OrderItems ON Orders.OrderId = OrderItems.OrderId INNER JOIN\n"
                + "                  Product ON OrderItems.ProductId = Product.ProductId LEFT JOIN\n"
                + "                  Vouchers ON Orders.VoucherId = Vouchers.VoucherId\n"
                + "WHERE Accounts.IsDeleted = 0 AND  Orders.IsDeleted = 0 AND OrderItems.OrderItemId = ?;";

        try {
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                
                Account account = new Account(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("FullName"), rs.getString("Phone"), rs.getBoolean("accountIsDeleted"));
                Product product = new Product();
                product.setLinkImg(rs.getString("LinkImg"));
                Voucher voucher = new Voucher(rs.getInt("VoucherId"), rs.getString("voucherCode"), rs.getBigDecimal("DiscountValue"), rs.getString("DiscountType"), rs.getString("voucherStatus"));
                OrderCore order = new OrderCore(rs.getInt("OrderId"), account, rs.getString("OrderCode"), rs.getTimestamp("OrderTime").toLocalDateTime(), rs.getBigDecimal("TotalAmount"), rs.getBigDecimal("ShippingFee"), rs.getString("orderStatus"), rs.getString("ShippingAddress"), rs.getString("PaymentMethod"), rs.getString("PaymentStatus"), voucher, rs.getBoolean("orderIsDead"));
                OrderItem orderItem = new OrderItem(rs.getInt("OrderItemId"), order, product, rs.getString("ProductNameSnapshot"), rs.getBigDecimal("UnitPrice"), rs.getInt("Quantity"), rs.getBigDecimal("TotalPrice"));

                return orderItem;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
