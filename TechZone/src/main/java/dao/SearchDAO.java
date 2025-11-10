package dao;

import db.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 * DAO for handling product search (with pagination).
 *
 * <p>Provides methods to search by keyword with pagination, count total results
 * by keyword, and filter products by brand within a category.</p>
 */
public class SearchDAO extends DBContext {

    /**
     * Retrieves a paginated list of products by keyword.
     *
     * @param keyword search keyword
     * @param page current page number
     * @param pageSize items per page
     * @return list of matching products
     */
    public List<Product> searchProducts(String keyword, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        int offset = (page - 1) * pageSize; // compute starting offset for page

        String sql = "SELECT * FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0 ORDER BY ProductId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            if (keyword == null) {
                keyword = ""; // default to empty string to make LIKE work
            }
            ps.setString(1, "%" + keyword + "%"); // parameter for LIKE on product name

            ps.setInt(2, offset); // starting position
            ps.setInt(3, pageSize); // records per page

            ResultSet rs = ps.executeQuery(); // execute query
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs)); // map each row to Product and add to list
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Counts total number of products matching the keyword.
     *
     * @param keyword search keyword
     * @return total number of results
     */
    public int countProductsByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0";
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            if (keyword == null) {
                keyword = ""; // default to empty string to make LIKE work
            }
            ps.setString(1, "%" + keyword + "%"); // parameter for LIKE
            ResultSet rs = ps.executeQuery(); // execute query
            if (rs.next()) {
                return rs.getInt(1); // return count
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔹 ResultSet -> Product mapping function
    /**
     * Maps a ResultSet row to a Product object.
     *
     * @param rs ResultSet pointing at the record to map
     * @return Product object populated from the row
     * @throws SQLException on column access error
     */
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

    /**
     * Filters products by category and optionally by brand in JSON attributes.
     *
     * @param categoryId category id
     * @param brand brand to filter (can be empty or null)
     * @return list of matching products
     */
    public List<Product> getFilterBrand(int categoryId, String brand) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CategoryId = ? AND IsDeleted = 0";
        if (brand != null && !brand.isEmpty()) {
            sql += " AND JSON_VALUE(ProductAttributes, '$.brand') = ?";
        }

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            ps.setInt(1, categoryId); // category parameter
            if (brand != null && !brand.isEmpty()) {
                ps.setString(2, brand); // brand parameter
            }

            ResultSet rs = ps.executeQuery(); // execute query
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs)); // map and add to list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
