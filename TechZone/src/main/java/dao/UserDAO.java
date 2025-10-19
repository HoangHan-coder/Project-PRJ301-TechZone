/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author acer
 */
public class UserDAO extends DBContext{

    public UserDAO() throws SQLException {
    }
    
    
    
    public User login(String username,String password){
        
        try {
            String query = "select id, username, password, fullname, email, phone from users where username = ? and password = ?";
         
           PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
          statement.setString(1, username);
          statement.setString(2, this.hashMd5(password));
            System.out.println(this.hashMd5(password));
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("fullname"), rs.getString("email"), rs.getString("phone"));
            }
        } catch (SQLException ex) {
            System.getLogger(UserDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return null;
        
    }
    
    private String hashMd5(String raw) {
        raw = raw + "h";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mess = md.digest(raw.getBytes());
           
            StringBuilder sb = new StringBuilder();
            for (byte b: mess) {
                sb.append(String.format("%02x", b));
            }
           
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
