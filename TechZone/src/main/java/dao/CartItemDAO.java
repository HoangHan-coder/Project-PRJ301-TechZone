package dao;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

public class CartItemDAO extends db.DBContext {

    // Lấy danh sách sản phẩm trong giỏ hàng theo AccountId (giữ nguyên)
    public List<CartItem> getListByAccountId(int accountId) {
        List<CartItem> list = new ArrayList<>();
        String query = "SELECT p.ProductId, p.LinkImg, p.ProductName, ci.CartItemId, "
                + "ci.UnitPrice, ci.Quantity, ci.TotalPrice, c.CartId, c.Status, c.CreatedAt, "
                + "a.AccountId, a.Username "
                + "FROM Product p "
                + "JOIN CartItems ci ON p.ProductId = ci.ProductId "
                + "JOIN Carts c ON ci.CartId = c.CartId "
                + "JOIN Accounts a ON c.AccountId = a.AccountId "
                + "WHERE Status = 'ACTIVE' and a.AccountId = ?"; // <-------------------------------------------------

        try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product pr = new Product(
                        rs.getString("LinkImg"),
                        rs.getString("ProductName"),
                        rs.getInt("ProductId")
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

    // Tìm CartItem theo cartId + productId
    public CartItem findByCartAndProduct(int cartId, int productId) {
        String query = "SELECT CartItemId, CartId, ProductId, UnitPrice, Quantity, TotalPrice FROM CartItems WHERE CartId = ? AND ProductId = ?";
        try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CartItem ci = new CartItem();
                ci.setCartItemId(rs.getInt("CartItemId"));
                // bạn có constructor/setter phù hợp, ở đây set minimal fields
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

    // Nếu đã có thì tăng quantity lên (quantity + addQuantity). Nếu chưa có thì insert.
    // Trả về true nếu thành công.
    public boolean incrementQuantityIfExistsOrInsert(int cartId, int productId, double unitPrice, int addQuantity) {
        Connection conn = null;
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);

            // 1. Kiểm tra tồn tại
            String select = "SELECT CartItemId, Quantity FROM CartItems WHERE CartId = ? AND ProductId = ?";
            PreparedStatement psSel = conn.prepareStatement(select);
            psSel.setInt(1, cartId);
            psSel.setInt(2, productId);
            ResultSet rs = psSel.executeQuery();
            if (rs.next()) {
                int cartItemId = rs.getInt("CartItemId");
                int currentQty = rs.getInt("Quantity");
                int newQty = currentQty + addQuantity;

                // Nếu TotalPrice là computed column thì chỉ update Quantity
                String upd = "UPDATE CartItems SET Quantity = ? WHERE CartItemId = ?";
                PreparedStatement psUpd = conn.prepareStatement(upd);
                psUpd.setInt(1, newQty);
                psUpd.setInt(2, cartItemId);
                psUpd.executeUpdate();

            } else {
                // Insert mới
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
                    conn.rollback();
                }
            } catch (SQLException e) {
                /* ignore */ }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                /* ignore */ }
        }
    }

    // Cập nhật số lượng (bỏ cập nhật TotalPrice nếu nó là computed)
    public boolean updateQuantity(int cartItemId, int quantity) {
        String query = "UPDATE CartItems SET Quantity = ? WHERE CartItemId = ?";
        try (PreparedStatement ps = this.getConnection().prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CartItemDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // Xóa sản phẩm


    // Thêm sản phẩm (giữ để tương thích, nhưng khuyến nghị dùng incrementQuantityIfExistsOrInsert)
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
    // 🟣 Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa

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

// 🟢 Tăng số lượng nếu đã có sản phẩm
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

}

    