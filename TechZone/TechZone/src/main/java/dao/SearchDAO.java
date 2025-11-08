package dao;

import db.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 * DAO chuyên xử lý tìm kiếm sản phẩm (search có phân trang)
 */
public class SearchDAO extends DBContext {

    /**
     * Lấy danh sách sản phẩm theo từ khóa (phân trang)
     *
     * @param keyword từ khóa tìm kiếm
     * @param page số trang hiện tại
     * @param pageSize số sản phẩm trên mỗi trang
     * @return danh sách sản phẩm phù hợp
     */
    public List<Product> searchProducts(String keyword, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        String sql = "SELECT * FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0 ORDER BY ProductId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (keyword == null) {
                keyword = "";
            }
            ps.setString(1, "%" + keyword + "%");

            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Đếm tổng số sản phẩm khớp với từ khóa
     *
     * @param keyword từ khóa tìm kiếm
     * @return tổng số dòng kết quả
     */
    public int countProductsByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0";
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (keyword == null) {
                keyword = "";
            }
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔹 Hàm ánh xạ ResultSet -> Product
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
        return p;
    }

    public List<Product> getFilterBrand(int categoryId, String brand) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CategoryId = ? AND IsDeleted = 0";
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
}
