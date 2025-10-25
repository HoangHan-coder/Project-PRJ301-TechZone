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
import model.Account;
import model.Cart;
import model.CartItem;
import model.Product;

/**
 *
 * @author admin
 */
public class CartItemDAO extends db.DBContext {

    public List<CartItem> getList(String username) {
        
            List<CartItem> list = new ArrayList<>();
        try {
            String query = "SELECT Product.ProductId, Product.LinkImg, Product.ProductName, CartItems.CartItemId, CartItems.UnitPrice, CartItems.Quantity, CartItems.TotalPrice, Carts.CartId, Carts.Status, Carts.CreatedAt, Accounts.AccountId, Accounts.Username\n"
                    + "                     FROM     Product INNER JOIN\n"
                    + "                                       CartItems ON Product.ProductId = CartItems.ProductId INNER JOIN\n"
                    + "                                       Carts ON CartItems.CartId = Carts.CartId INNER JOIN\n"
                    + "                                       Accounts ON Carts.AccountId = Accounts.AccountId Where Accounts.Username = ?";

            PreparedStatement ps = this.getConnection().prepareCall(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
        
            while (rs.next()) {
                int productId = rs.getInt("productId");
                String linkImg = rs.getString("linkImg");
                String productName = rs.getString("productName");
                Product pr = new Product(linkImg, productName, productId);
                int accountId = rs.getInt("accountId");
                String userName = rs.getString("userName");
                Account ac = new Account(accountId, userName);

                int cartId = rs.getInt("cartId");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                String status = rs.getString("status");
                Cart cart = new Cart(cartId, ac, createdAt, status);

                int cartItemId = rs.getInt("cartItemId");
                double unitPrice = rs.getDouble("unitPrice");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("totalPrice");
                CartItem ca = new CartItem(cartItemId, cart, pr, unitPrice, quantity, totalPrice);

                list.add(ca);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return list;
    }
     
}
