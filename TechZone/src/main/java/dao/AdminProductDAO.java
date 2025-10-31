/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;

/**
 *
 * @author acer
 */
public class AdminProductDAO extends DBContext{
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
    StringBuilder sql = new StringBuilder("SELECT * FROM Product WHERE IsDeleted = 0");

    if (category != null && !category.isEmpty()) {
        if(category.equals("all")){
            sql.append("");
            category = "";
        } else {
            sql.append(" AND CategoryId = (select CategoryId from Category where Name = '?')");
        }
        
    }
    if (brand != null && !brand.isEmpty()) {
        sql.append(" AND JSON_VALUE(ProductAttributes, '$.brand') = ?");
    }
    if ("newest".equals(sort)) {
        sql.append(" ORDER BY created_at DESC");
    } 

    try (Connection con = this.getConnection();
         PreparedStatement ps = con.prepareStatement(sql.toString())) {

        int index = 1;
        if (category != null && !category.isEmpty()) ps.setString(index++, category);
        if (brand != null && !brand.isEmpty()) ps.setString(index++, brand);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToProduct(rs));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}
