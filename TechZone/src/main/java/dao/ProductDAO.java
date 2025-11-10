package dao;

import db.DBContext;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

/**
 * Data Access Object for products.
 *
 * <p>Provides read and update operations for the <code>Product</code> table,
 * including pagination, category filtering, and stock updates.</p>
 */
public class ProductDAO extends DBContext {

    /**
     * Retrieves products paginated by 12 items per page.
     *
     * @param page 1-based page index
     * @return list of products for the requested page
     */
    public List<Product> getAllProducts(int page) {
        List<Product> list = new ArrayList<>();
        int index = (page - 1) * 12; // compute offset for pagination
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0 order by productId "
                + "offset ? rows fetch next 12 rows only";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement
            ps.setInt(1, index); // bind offset parameter
            ps.setInt(1, index); // duplicated bind (kept as-is)
            try (ResultSet rs = ps.executeQuery()) { // execute and iterate result set
                while (rs.next()) {
                    list.add(mapResultSetToProduct(rs)); // map each row to Product entity
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Retrieves a single product by its id.
     *
     * @param id product identifier
     * @return product instance or null if not found/deleted
     */
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product WHERE ProductId = ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            ps.setInt(1, id); // bind product id
            ResultSet rs = ps.executeQuery(); // execute select

            if (rs.next()) {
                return mapResultSetToProduct(rs); // map row to Product entity
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all products by category id.
     * 
     * @param categoryId category identifier
     * @return list of products in the category
     */
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE CategoryId = ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            ps.setInt(1, categoryId); // bind category id
            ResultSet rs = ps.executeQuery(); // execute select

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs)); // map and add to list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Maps a result set row to a Product entity.
     *
     * @param rs result set positioned at a row
     * @return populated Product instance
     * @throws SQLException if a column access error occurs
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
     * Retrieves the latest created product for a given category.
     *
     * @param categoryId category identifier
     * @return list containing at most one product
     */
    public List<Product> getTop1(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE IsDeleted = 0 AND categoryId = ? ORDER BY createdAt DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            ps.setInt(1, categoryId); // bind category id
            ResultSet rs = ps.executeQuery(); // execute select
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs)); // map and add to list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves the best selling product for a given category.
     *
     * @param categoryId category identifier
     * @return list containing at most one product
     */
    public List<Product> getTop1ByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 1 * FROM Product WHERE IsDeleted = 0 AND CategoryId = ? ORDER BY quantitySold DESC";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement

            ps.setInt(1, categoryId); // bind category id
            ResultSet rs = ps.executeQuery(); // execute select
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs)); // map and add to list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Decrements stock and increments quantity sold by the given amount for a product.
     *
     * @param x delta to adjust stock and quantity sold
     * @param id product id to update
     * @return number of affected rows
     */
    public int updateProductStock(int x, int id) {
        String sql = "update Product Set Stock = Stock - ? , QuantitySold = QuantitySold + ? where Productid = ?";
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // open connection and prepare statement
            ps.setInt(1, x); // bind decrement for stock
            ps.setInt(2, x); // bind increment for quantity sold
            ps.setInt(3, id); // bind product id
            return ps.executeUpdate(); // execute update
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    /**
     * Counts all non-deleted products.
     *
     * @return total number of rows in Product where IsDeleted = 0
     */
    public int getTotalRow() {
        try {
            String sql = "SELECT Count(ProductId) as totalRow FROM Product Where IsDeleted = 0";
            PreparedStatement statement = this.getConnection().prepareStatement(sql); // prepare count query
            ResultSet rs = statement.executeQuery(); // execute
            while (rs.next()) {
                return rs.getInt(1); // return count
            }

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
