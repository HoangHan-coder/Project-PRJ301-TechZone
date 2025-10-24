package dao;

import db.DBUntils;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;
import model.Product;

public class ProductDAO extends DBUntils {

    // ✅ Lấy tất cả sản phẩm (dành cho user)
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0";

        try (Connection con = DBUntils.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return list;
    }

    // ✅ Lấy sản phẩm theo ID
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product WHERE ProductId = ? AND IsDeleted = 0";

        try (Connection con = DBUntils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Lấy sản phẩm theo CategoryId
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CategoryId = ? AND IsDeleted = 0";

        try (Connection con = DBUntils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ Hàm map dữ liệu từ ResultSet → Product object
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();

        p.setProductId(rs.getInt("ProductId"));
        p.setLinkImg(rs.getString("LinkImg"));
        p.setProductName(rs.getString("ProductName"));
        p.setProductPrice(rs.getDouble("ProductPrice"));
        p.setProductAttributes(rs.getString("ProductAttributes"));
        p.setCategoryId(rs.getInt("CategoryId"));
        p.setStock(rs.getInt("Stock"));
        p.setQuantitySold(rs.getInt("QuantitySold"));
        p.setDescriptionProduct(rs.getString("DescriptionProduct"));
        p.setIsDeleted(rs.getBoolean("IsDeleted"));
        p.setCreatedAt(rs.getTimestamp("CreatedAt"));
        p.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        p.setRowVersion(rs.getBytes("RowVersion"));

        return p;
    }
// ✅ Featured product (sản phẩm mới nhất trong danh mục)

    public List<Product> getTop1(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE categoryId = ? ORDER BY createdAt DESC";

        try (Connection con = DBUntils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// ✅ Trending product (sản phẩm bán chạy nhất trong danh mục)
    public List<Product> getTop1ByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE CategoryId = ? ORDER BY quantitySold DESC";

        try (Connection con = DBUntils.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> getFilterBrand(int categoryId, String brand) {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM product WHERE CategoryId = ?";

    if (brand != null && !brand.isEmpty()) {
        sql += " AND JSON_VALUE(ProductAttributes, '$.brand') = ?";
    }

    try (Connection con = DBUntils.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, categoryId);
        if (brand != null && !brand.isEmpty()) {
            ps.setString(2, brand);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToProduct(rs));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


}
