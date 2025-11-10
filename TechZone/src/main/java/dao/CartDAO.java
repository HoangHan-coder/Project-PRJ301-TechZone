package dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Account;

/**
 * Data access object for shopping carts.
 *
 * <p>Provides operations to create and fetch carts, including ensuring a single
 * active cart per account.</p>
 */
public class CartDAO extends db.DBContext {

    /**
     * Creates a new ACTIVE cart for the given account.
     *
     * @param accountId account identifier
     * @return rows affected (1 if created)
     */
    public int createCart(int accountId) {
        try {
            String query = "INSERT INTO Carts (AccountId, Status, CreatedAt) VALUES (?, 'ACTIVE', GETDATE())"; // SQL insert
            PreparedStatement st = this.getConnection().prepareStatement(query); // prepare statement
            st.setInt(1, accountId); // bind account id
            return st.executeUpdate(); // execute and return affected rows
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Gets the most recently created cart for an account.
     *
     * @param accountId account identifier
     * @return latest {@link Cart} or null if none
     */
    public Cart getLatestCartByAccountId(int accountId) {
        String query = "SELECT TOP 1 * FROM Carts WHERE AccountId = ? ORDER BY CreatedAt DESC";
        try (PreparedStatement st = this.getConnection().prepareStatement(query)) {
            st.setInt(1, accountId); // bind account id
            ResultSet rs = st.executeQuery(); // execute query
            if (rs.next()) {
                return new Cart(
                        rs.getInt("CartId"),
                        new Account(accountId, ""),
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                ); // map result to Cart model
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves an ACTIVE cart for an account, creating one if not exists.
     *
     * @param accountId account identifier
     * @return active {@link Cart} instance or null on failure
     */
    public Cart getOrCreateActiveCart(int accountId) {
        try (Connection conn = this.getConnection()) {

            String select = "SELECT TOP 1 CartId FROM Carts WHERE AccountId = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC"; // look for active cart
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("CartId")); // return existing
            }

            String insert = "INSERT INTO Carts (AccountId, Status, CreatedAt) VALUES (?, 'ACTIVE', GETDATE())"; // create new active
            PreparedStatement pst = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, accountId);
            int affected = pst.executeUpdate();
            if (affected > 0) {
                ResultSet gk = pst.getGeneratedKeys(); // fetch generated id
                if (gk.next()) {
                    return new Cart(gk.getInt(1)); // return new cart with id
                } else {
                    return getLatestCartByAccountId(accountId); // fallback query
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves the latest ACTIVE cart for an account.
     *
     * @param accountId account identifier
     * @return active cart or null
     */
    public Cart getActiveCartByAccountId(int accountId) {
        String query = "SELECT TOP 1 * FROM Carts WHERE AccountId = ? AND Status = 'ACTIVE' ORDER BY CreatedAt DESC";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, accountId); // bind id
            ResultSet rs = ps.executeQuery(); // run query
            if (rs.next()) {
                return new Cart(
                        rs.getInt("CartId"),
                        new Account(accountId, ""),
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                ); // map to Cart
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Checks whether a cart has any items.
     *
     * @param cartId cart identifier
     * @return true if empty, false otherwise
     */
    public boolean cartIsEmty(int cartId) {
        try {
            String sql = "select * from CartItems where CartId = ? "; // query items for cart
            PreparedStatement ps = getConnection().prepareStatement(sql);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            return !rs.next(); // if no row, cart is empty
        } catch (SQLException ex) {
            Logger.getLogger(CartDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Soft-deletes a cart by setting its status to DISABLE.
     *
     * @param cartId cart identifier
     * @return true if at least one row updated
     */
    public boolean deleteItem(int cartId) {
        String query = "update Carts set Status = 'DISABLE' where CartId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, cartId); // bind id
            return ps.executeUpdate() > 0; // true if updated
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
