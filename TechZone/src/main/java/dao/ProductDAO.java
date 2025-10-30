package dao;

import db.DBContext;
import java.sql.*;
import java.util.*;
import model.Product;

public class ProductDAO extends DBContext {

// lay het
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// lay theo productid
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product WHERE ProductId = ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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
// lay theo category

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CategoryId = ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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

// ham tach tao ra doi tuong
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

// MOI them vao database
    public List<Product> getTop1(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE categoryId = ? ORDER BY createdAt DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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

// SO LUONG BAN NHIU NHAT
    public List<Product> getTop1ByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE CategoryId = ? ORDER BY quantitySold DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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

// LOC SAN PHAM
    public List<Product> getFilterBrand(int categoryId, String brand) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE CategoryId = ?";

        if (brand != null && !brand.isEmpty()) {
            sql += " AND JSON_VALUE(ProductAttributes, '$.brand') = ?";
        }

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

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

    public List<Product> getAllProductsSearch(String txt) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + txt + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
