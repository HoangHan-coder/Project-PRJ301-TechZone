package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Account;

public class CartDAO extends db.DBContext {

    public int createCart(int accountId) {
        try {
            String query = "INSERT INTO Carts (AccountId, Status, CreatedAt) VALUES (?, 'ACTIVE', GETDATE())";
            PreparedStatement st = this.getConnection().prepareStatement(query);
            st.setInt(1, accountId);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public Cart getLatestCartByAccountId(int accountId) {
        String query = "SELECT TOP 1 * FROM Carts WHERE AccountId = ? ORDER BY CreatedAt DESC";
        try (PreparedStatement st = this.getConnection().prepareStatement(query)) {
            st.setInt(1, accountId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Cart(
                        rs.getInt("CartId"),
                        new Account(accountId, ""),
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cart getOrCreateActiveCart(int accountId) {
        try (Connection conn = this.getConnection()) {

            String select = "SELECT TOP 1 CartId FROM Carts WHERE AccountId = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("CartId"));
            }

            String insert = "INSERT INTO Carts (AccountId, Status, CreatedAt) VALUES (?, 'ACTIVE', GETDATE())";
            PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, accountId);
            int affected = pst.executeUpdate();
            if (affected > 0) {
                ResultSet gk = pst.getGeneratedKeys();
                if (gk.next()) {
                    return new Cart(gk.getInt(1));
                } else {
                    return getLatestCartByAccountId(accountId);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Cart getActiveCartByAccountId(int accountId) {
        String query = "SELECT TOP 1 * FROM Carts WHERE AccountId = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(
                        rs.getInt("CartId"),
                        new Account(accountId, ""),
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean cartIsEmty(int cartId) {
        try {
            String sql = "select * from CartItems where CartId = ? ";
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            return !rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteItem(int cartId) {
        String query = "update Carts set Status = 'DISABLE' where CartId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
