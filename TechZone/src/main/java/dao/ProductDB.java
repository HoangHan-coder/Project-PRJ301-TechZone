/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try {
            PreparedStatement st = connect.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product(rs.getInt("productId"), rs.getString("linkImg"), rs.getString("productName"),
                        rs.getString("productPrice"), rs.getString("productIdDetail"), rs.getString("categoryId"),
                        rs.getString("isDeleted"), rs.getString("timeCreate")
                );
                list.add(a);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

}
