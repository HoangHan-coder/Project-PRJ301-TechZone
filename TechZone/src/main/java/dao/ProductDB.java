/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Product;

/**
 *
 * @author letan
 */
public class ProductDB {

    protected Connection connect;

    public ProductDB() {
        try {
            this.connect = DBUntils.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        if (connect != null) {
            try {
                PreparedStatement st = connect.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                ObjectMapper mapper = new ObjectMapper();
                while (rs.next()) {
                    Product a = new Product(
                            rs.getInt("productId"), 
                            rs.getString("linkImg"), 
                            rs.getString("productName"),
                            rs.getBigDecimal("productPrice"), 
                            mapper.readValue(rs.getString("productAttributes"), new TypeReference<Map<String, String>>() {}), 
                            rs.getInt("categoryId"),
                            rs.getBoolean("isDeleted"), 
                            rs.getObject("timeCreate",LocalDateTime.class),
                            rs.getInt("quantity")
                    );
                    list.add(a);
                }
            } catch (SQLException e) {
                System.out.println(e);
            } catch (IOException ex) {
                Logger.getLogger(ProductDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

}
