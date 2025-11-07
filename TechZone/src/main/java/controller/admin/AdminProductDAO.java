/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.admin;

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
 *
 * @author acer
 */
public class AdminProductDAO extends DBContext {

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

    public List<Product> filterProducts(String category, String brand, String sort) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE IsDeleted = 0";

        if (category != null && !category.isEmpty()) {
            if (category.equals("all")) {
                category = "";
            } else {
                sql += " AND CategoryId = (select CategoryId from Category where Name = ?)";
            }

        }
        if (brand != null && !brand.isEmpty()) {
            sql += " AND JSON_VALUE(ProductAttributes, '$.brand') = ?";
        }
        if ("newest".equals(sort)) {
            sql += " ORDER BY CreatedAt DESC";
        }
        System.out.println(sql);
        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            int index = 1;
            if (category != null && !category.isEmpty()) {
                ps.setString(index++, category);
            }
            if (brand != null && !brand.isEmpty()) {
                ps.setString(index++, brand);
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

    public List<Product> getAllProductsSearch(String txt) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE ProductName LIKE ? AND IsDeleted = 0";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + txt + "%");
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

            json = toJsonString(attributes);
            img = "assets/images/accessories/" + imgName;
        }
         price = price.replaceAll("[.,]", "");
        int category = Integer.parseInt(CategoryID);
        double priceparse = Double.parseDouble(price);
        int stockparse = Integer.parseInt(stock);

        String sql = "INSERT INTO Product (LinkImg, ProductName, ProductPrice, ProductAttributes, CategoryId, Stock, DescriptionProduct)\n"
                + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, img);
            ps.setString(2, productname);
            ps.setBigDecimal(3, BigDecimal.valueOf(priceparse));
            ps.setString(4, json);
            ps.setInt(5, category);
            ps.setInt(6, stockparse);
            ps.setString(7, descriptionproduct);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;

    }

    private String toJsonString(Map<String, String> map) {
        return new Gson().toJson(map);
    }

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

            json = toJsonString(attributes);
            img = "assets/images/accessories/" + imgName;
        }
       
        int category = Integer.parseInt(CategoryID);
        double priceparse = Double.parseDouble(price);
        int stockparse = Integer.parseInt(stock);
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

            
            ps.setString(1, productname);
            ps.setBigDecimal(2, BigDecimal.valueOf(priceparse));
            ps.setString(3, json);
            ps.setInt(4, category);
            ps.setInt(5, stockparse);
            ps.setString(6, descriptionproduct);
            ps.setInt(7, id);
            return ps.executeUpdate();
            
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

            ps.setString(1, img);
            ps.setString(2, productname);
            ps.setBigDecimal(3, BigDecimal.valueOf(priceparse));
            ps.setString(4, json);
            ps.setInt(5, category);
            ps.setInt(6, stockparse);
            ps.setString(7, descriptionproduct);
            ps.setInt(8, id);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        

        return 0;

    }
    
    public int deleteProduct(int id) {
        String sql = "update Product set IsDeleted = ? WHERE ProductId = ?";

        try (Connection con = this.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setBoolean(1, true);
            ps.setInt(2, id);
            return ps.executeUpdate();

            

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        
    }

}
