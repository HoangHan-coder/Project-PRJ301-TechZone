package dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.*;

/**
 * Data access object for cart items.
 *
 * <p>Provides CRUD operations and helpers for manipulating items in a user's
 * shopping cart.</p>
 */
public class CartItemDAO extends db.DBContext {

    /**
     * Retrieves all ACTIVE cart items for a given account, joining related
     * product, cart, and account data for display.
     *
     * @param accountId account identifier
     * @return list of {@link CartItem}
     */
    public List<CartItem> getListByAccountId(int accountId) {
        List<CartItem> list = new ArrayList<>();
        String query = "SELECT p.ProductId, p.Stock, p.LinkImg, p.ProductName, ci.CartItemId, "
                + "ci.UnitPrice, ci.Quantity, ci.TotalPrice, c.CartId, c.Status, c.CreatedAt, "
                + "a.AccountId, a.Username "
                + "FROM Product p "
                + "JOIN CartItems ci ON p.ProductId = ci.ProductId "
                + "JOIN Carts c ON ci.CartId = c.CartId "
                + "JOIN Accounts a ON c.AccountId = a.AccountId "
                + "WHERE Status = 'ACTIVE' and a.AccountId = ?";

        try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId); // bind account id
            ResultSet rs = ps.executeQuery(); // run query

            while (rs.next()) {
                Product pr = new Product(
                        rs.getInt("ProductId"),
                        rs.getString("LinkImg"),
                        rs.getString("ProductName")
                );
                pr.setStock(rs.getInt("Stock")); // attach stock level
                Account ac = new Account(
                        rs.getInt("AccountId"),
                        rs.getString("Username")
                );
                Cart cart = new Cart(
                        rs.getInt("CartId"),
                        ac,
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                );

                CartItem ci = new CartItem(
                        rs.getInt("CartItemId"),
                        cart,
                        pr,
                        rs.getDouble("UnitPrice"),
                        rs.getInt("Quantity"),
                        rs.getDouble("TotalPrice")
                );
                list.add(ci);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    /**
     * Finds an item by cart id and product id.
     *
     * @param cartId cart identifier
     * @param productId product identifier
     * @return {@link CartItem} with minimal fields, or null
     */
    public CartItem findByCartAndProduct(int cartId, int productId) {
        String query = "SELECT CartItemId, CartId, ProductId, UnitPrice, Quantity, TotalPrice FROM CartItems WHERE CartId = ? AND ProductId = ?";
        try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery(); // execute lookup
            if (rs.next()) {
                CartItem ci = new CartItem();
                ci.setCartItemId(rs.getInt("CartItemId"));
                // use appropriate constructor/setters; set minimal fields here
                ci.setUnitPrice(rs.getDouble("UnitPrice"));
                ci.setQuantity(rs.getInt("Quantity"));
                ci.setTotalPrice(rs.getDouble("TotalPrice"));
                return ci;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Increments quantity for an existing cart item; inserts new if not exists.
     *
     * @param cartId cart identifier
     * @param productId product identifier
     * @param unitPrice unit price to use for insertion
     * @param addQuantity quantity to add
     * @return true on success, false on error
     */
    public boolean incrementQuantityIfExistsOrInsert(int cartId, int productId, double unitPrice, int addQuantity) {
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);

            // 1. Check existence
            String select = "SELECT CartItemId, Quantity FROM CartItems WHERE CartId = ? AND ProductId = ?";
            PreparedStatement psSel = conn.prepareStatement(select);
            psSel.setInt(1, cartId);
            psSel.setInt(2, productId);
            ResultSet rs = psSel.executeQuery();
            if (rs.next()) {
                int cartItemId = rs.getInt("CartItemId");
                int currentQty = rs.getInt("Quantity");
                int newQty = currentQty + addQuantity;

                // If TotalPrice is a computed column then only update Quantity
                String upd = "UPDATE CartItems SET Quantity = ? WHERE CartItemId = ?";
                PreparedStatement psUpd = conn.prepareStatement(upd);
                psUpd.setInt(1, newQty);
                psUpd.setInt(2, cartItemId);
                psUpd.executeUpdate();

            } else {
                // Insert new row
                String insert = "INSERT INTO CartItems (CartId, ProductId, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
                PreparedStatement psIns = conn.prepareStatement(insert);
                psIns.setInt(1, cartId);
                psIns.setInt(2, productId);
                psIns.setDouble(3, unitPrice);
                psIns.setInt(4, addQuantity);
                psIns.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (conn != null) {
                    conn.rollback(); // rollback on failure
                }
            } catch (SQLException e) {
                /* ignore */ }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close(); // release connection
                }
            } catch (SQLException e) {
                /* ignore */ }
        }
    }

    /**
     * Updates quantity for a given cart item id.
     *
     * @param cartItemId cart item identifier
     * @param quantity new quantity value
     * @return true if updated
     */
    public boolean updateQuantity(int cartItemId, int quantity) {
        String query = "UPDATE CartItems SET Quantity = ? WHERE CartItemId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0; // success if row affected
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Deletes a cart item by id.
     *
     * @param cartItemId cart item identifier
     * @return true if deleted
     */
    public boolean deleteById(int cartItemId) {
        String sql = "DELETE FROM CartItems WHERE CartItemId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0; // success if row affected
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Creates a new cart item row.
     *
     * @param cartId cart identifier
     * @param productId product identifier
     * @param unitPrice price per unit
     * @param quantity quantity to insert
     * @return affected rows (1 on success)
     */
    public int createCartItems(int cartId, int productId, double unitPrice, int quantity) {
        String query = "INSERT INTO CartItems (CartId, ProductId, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = this.getConnection().prepareStatement(query)) {
            st.setInt(1, cartId);
            st.setInt(2, productId);
            st.setDouble(3, unitPrice);
            st.setInt(4, quantity);
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Retrieves a cart item by cart and product identifiers.
     *
     * @param cartId cart identifier
     * @param productId product identifier
     * @return {@link CartItem} or null if not found
     */
    public CartItem getCartItem(int cartId, int productId) {
        String query = "SELECT * FROM CartItems WHERE CartId = ? AND ProductId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new CartItem(
                        rs.getInt("CartItemId"),
                        new Cart(cartId),
                        new Product(rs.getInt("ProductId")),
                        rs.getDouble("UnitPrice"),
                        rs.getInt("Quantity"),
                        rs.getDouble("TotalPrice")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Increases quantity for an existing cart item by a delta.
     *
     * @param cartItemId cart item identifier
     * @param addQuantity amount to add
     * @return true if updated
     */
    public boolean increaseQuantity(int cartItemId, int addQuantity) {
        String query = "UPDATE CartItems SET Quantity = Quantity + ? WHERE CartItemId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, addQuantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Retrieves full cart items for a list of cart item ids (e.g., selected for checkout).
     *
     * @param selectedIds list of cartItemId
     * @return list of {@link CartItem}
     */
    public List<CartItem> getListFormCart(List<Integer> selectedIds) {
        List<CartItem> list = new ArrayList<>();
        if (selectedIds == null || selectedIds.isEmpty()) {
            return list; // Return empty if no IDs provided
        }
        try {
            String sql = "SELECT p.ProductId, p.LinkImg, p.ProductName, ci.CartItemId, "
                    + "ci.UnitPrice, ci.Quantity, ci.TotalPrice, c.CartId, c.Status, c.CreatedAt, "
                    + "a.AccountId, a.Username "
                    + "FROM Product p "
                    + "JOIN CartItems ci ON p.ProductId = ci.ProductId "
                    + "JOIN Carts c ON ci.CartId = c.CartId "
                    + "JOIN Accounts a ON c.AccountId = a.AccountId "
                    + "WHERE CartItemId IN ("
                    + selectedIds.stream()
                            .map(id -> "?")
                            .collect(Collectors.joining(", "))
                    + ")";
            PreparedStatement ps = getConnection().prepareStatement(sql);

            for (int i = 0; i < selectedIds.size(); i++) { // bind IDs sequentially
                ps.setInt(i + 1, selectedIds.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product pr = new Product(
                        rs.getInt("ProductId"),
                        rs.getString("LinkImg"),
                        rs.getString("ProductName")
                );
                Account ac = new Account(
                        rs.getInt("AccountId"),
                        rs.getString("Username")
                );
                Cart cart = new Cart(
                        rs.getInt("CartId"),
                        ac,
                        rs.getTimestamp("CreatedAt").toLocalDateTime(),
                        rs.getString("Status")
                );

                CartItem ci = new CartItem(
                        rs.getInt("CartItemId"),
                        cart,
                        pr,
                        rs.getDouble("UnitPrice"),
                        rs.getInt("Quantity"),
                        rs.getDouble("TotalPrice")
                );
                list.add(ci);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
