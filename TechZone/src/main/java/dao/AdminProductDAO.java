/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.google.gson.Gson;
import db.DBContext;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Product;

/**
 * DAO for admin product management: listing, filtering/searching, CRUD on Product.
 *
 * <p>Builds product attribute JSON per category and executes SQL queries using
 * {@link DBContext} connections.</p>
 *
 * @author acer
 */
public class AdminProductDAO extends DBContext {

    /**
     * Returns all non-deleted products.
     */
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) { // execute query

            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Maps a ResultSet row to Product entity.
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
     * Filters products by optional category name, brand (from JSON attribute),
     * and sort option.
     */
    public List<Product> filterProducts(String category, String brand, String sort) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0";

        if (category != null && !category.isEmpty()) {
            if (category.equals("all")) {
                category = "";
            } else {
                sql += " AND CategoryId = (select CategoryId from Category where Name = ?)"; // filter by category name
            }

        }
        if (brand != null && !brand.isEmpty()) {
            sql += " AND JSON_VALUE(ProductAttributes, '$.brand') = ?"; // filter by brand in JSON attributes
        }
        if ("newest".equals(sort)) {
            sql += " ORDER BY CreatedAt DESC"; // sort newest first
        }
        System.out.println(sql);
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // prepare final query

            int index = 1;
            if (category != null && !category.isEmpty()) {
                ps.setString(index++, category); // bind category name
            }
            if (brand != null && !brand.isEmpty()) {
                ps.setString(index++, brand); // bind brand
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToProduct(rs));
            }
            System.out.println(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Searches products by name with LIKE, excluding deleted ones.
     */
    public List<Product> getAllProductsSearch(String txt) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + txt + "%"); // bind keyword
            System.out.println(sql);
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

    /**
     * Retrieves a product by id if not deleted.
     */
    public Product getProductById(int id) {
        String sql = "SELECT * FROM Product WHERE ProductId = ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id); // bind id
            ResultSet rs = ps.executeQuery(); // execute

            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates a new product with JSON attributes per category and formatted price.
     */
    public int createProduct(String imgName, String model, String productname, String CategoryID, String brand, String price, String descriptionproduct, String cpu, String ram, String storage, String os, String weight, String stock, String cam, String type, String connectivity, String color, String compatibility) {
        Map<String, String> attributes = new HashMap<>();
        String json = null;
        String img = null;
        if (CategoryID.equals("1")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("cpu", cpu);
            attributes.put("ram", ram);
            attributes.put("storage", storage);
            attributes.put("os", os);
            attributes.put("weight", weight);

            json = toJsonString(attributes); // build JSON for laptop
            img = "assets/images/laptops/" + imgName; // image path
        } else if (CategoryID.equals("2")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("cpu", cpu);
            attributes.put("ram", ram);
            attributes.put("storage", storage);
            attributes.put("cameraRear", cam);
            attributes.put("os", os);
            attributes.put("weight", weight);

            json = toJsonString(attributes); // build JSON for phone
            img = "assets/images/phones/" + imgName; // image path
        } else if (CategoryID.equals("3")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("type", type);
            attributes.put("connectivity", connectivity);
            attributes.put("color", color);
            attributes.put("compatibility", compatibility);
            attributes.put("weight", weight);

            json = toJsonString(attributes); // build JSON for accessory
            img = "assets/images/accessories/" + imgName; // image path
        }
         price = price.replaceAll("[.,]", ""); // normalize price string
        int category = Integer.parseInt(CategoryID);
        double priceparse = Double.parseDouble(price); // numeric price
        int stockparse = Integer.parseInt(stock); // numeric stock

        String sql = "INSERT INTO Product (LinkImg, ProductName, ProductPrice, ProductAttributes, CategoryId, Stock, DescriptionProduct)\n"
                + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, img); // image link
            ps.setString(2, productname); // name
            ps.setBigDecimal(3, BigDecimal.valueOf(priceparse)); // price as BigDecimal
            ps.setString(4, json); // JSON attributes
            ps.setInt(5, category); // category id
            ps.setInt(6, stockparse); // stock
            ps.setString(7, descriptionproduct); // description
            return ps.executeUpdate(); // insert

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * Converts map to JSON string using Gson.
     */
    private String toJsonString(Map<String, String> map) {
        return new Gson().toJson(map);
    }

    /**
     * Updates an existing product by id; if imgName is empty, leaves LinkImg unchanged.
     */
    public int updateProduct(int id, String imgName, String model, String productname, String CategoryID, String brand, String price, String descriptionproduct, String cpu, String ram, String storage, String os, String weight, String stock, String cam, String type, String connectivity, String color, String compatibility) {
        Map<String, String> attributes = new HashMap<>();
        String json = null;
        String img = null;
        if (CategoryID.equals("1")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("cpu", cpu);
            attributes.put("ram", ram);
            attributes.put("storage", storage);
            attributes.put("os", os);
            attributes.put("weight", weight);

            json = toJsonString(attributes);
            img = "assets/images/laptops/" + imgName;
        } else if (CategoryID.equals("2")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("cpu", cpu);
            attributes.put("ram", ram);
            attributes.put("storage", storage);
            attributes.put("cameraRear", cam);
            attributes.put("os", os);
            attributes.put("weight", weight);

            json = toJsonString(attributes);
            img = "assets/images/phones/" + imgName;
        } else if (CategoryID.equals("3")) {
            attributes.put("brand", brand);
            attributes.put("model", model);
            attributes.put("type", type);
            attributes.put("connectivity", connectivity);
            attributes.put("color", color);
            attributes.put("compatibility", compatibility);
            attributes.put("weight", weight);

            json = toJsonString(attributes); // JSON for accessory
            img = "assets/images/accessories/" + imgName; // image path
        }
       
        int category = Integer.parseInt(CategoryID);
        double priceparse = Double.parseDouble(price); // numeric price
        int stockparse = Integer.parseInt(stock); // numeric stock
        if(imgName == null || imgName.isEmpty()){
            String sql = "UPDATE Product SET \n"
                + "ProductName = ?,\n"
                + "ProductPrice = ?,\n"
                + "ProductAttributes = ?,\n"
                + "CategoryId = ?,\n"
                + "Stock = ?,\n"
                + "DescriptionProduct = ?\n"
                + "WHERE ProductId = ?";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            
            ps.setString(1, productname); // name
            ps.setBigDecimal(2, BigDecimal.valueOf(priceparse)); // price
            ps.setString(3, json); // attributes JSON
            ps.setInt(4, category); // category id
            ps.setInt(5, stockparse); // stock
            ps.setString(6, descriptionproduct); // description
            ps.setInt(7, id); // product id
            return ps.executeUpdate(); // update without image
            
             } catch (Exception e) {
            e.printStackTrace();
        }
        } else {
            String sql = "UPDATE Product SET \n"
                + "LinkImg = ?,\n"
                + "ProductName = ?,\n"
                + "ProductPrice = ?,\n"
                + "ProductAttributes = ?,\n"
                + "CategoryId = ?,\n"
                + "Stock = ?,\n"
                + "DescriptionProduct = ?\n"
                + "WHERE ProductId = ?";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, img); // image link
            ps.setString(2, productname); // name
            ps.setBigDecimal(3, BigDecimal.valueOf(priceparse)); // price
            ps.setString(4, json); // attributes JSON
            ps.setInt(5, category); // category id
            ps.setInt(6, stockparse); // stock
            ps.setString(7, descriptionproduct); // description
            ps.setInt(8, id); // product id
            return ps.executeUpdate(); // update with image

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        

        return 0;

    }
    
    /**
     * Soft-deletes a product by setting IsDeleted = true.
     */
    public int deleteProduct(int id) {
        String sql = "update Product set IsDeleted = ? WHERE ProductId = ?";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) { // prepare update
            
            ps.setBoolean(1, true); // mark deleted
            ps.setInt(2, id); // id
            return ps.executeUpdate(); // execute

            

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        
    }

}
