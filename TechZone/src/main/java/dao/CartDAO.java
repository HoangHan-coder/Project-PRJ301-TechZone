/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;

/**
 *
 * @author admin
 */
public class CartDAO extends db.DBContext {

    public int createCart(int accountId) {
        try {
            String query = "INSERT INTO Carts (AccountId) VALUES (?)";
            PreparedStatement st = this.getConnection().prepareStatement(query);
            st.setInt(1, accountId);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public Cart cartId() {
        try {
            String query = "SELECT MAX(CartId) AS maxid FROM Carts";
            PreparedStatement st = this.getConnection().prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int cartId = rs.getInt("maxid"); // ⚠️ bạn đang gọi sai tên cột, phải là "maxid" chứ không phải "cartId"
                return new Cart(cartId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
