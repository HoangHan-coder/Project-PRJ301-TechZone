/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.AccountUsers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author acer
 */
public class AuthDAO extends DBContext{

    
    
    
    public AccountUsers login(String username,String password) {
        
        try {
            String query = "select Accounts.AccountId, Accounts.Username, Accounts.PasswordHash,Accounts.FullName, Accounts.Email, Accounts.Phone, Accounts.RoleName, Accounts.isDeleted from Accounts\n" +
"where Username = ? and PasswordHash = ?";
         
           PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
          statement.setString(1, username);
          statement.setString(2, this.hashMd5(password));
            System.out.println(this.hashMd5(password));
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                return new AccountUsers(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("FullName"),rs.getString("Email"),rs.getString("Phone"),rs.getString("RoleName"),rs.getBoolean("isDeleted"));
            }
        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public int register(String username, String password, String phone) {
        try {
            String query = "insert into Accounts (Username, FullName, Email, PasswordHash,Phone,isDeleted,RoleName)"
                    + " values(?,?,?,?,?,?,?)";
            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, "unknown");
            statement.setString(3, "unknown");
            statement.setString(4, this.hashMd5(password));
            statement.setString(5, phone);
            statement.setBoolean(6, false);
            statement.setString(7, "Customer");
            
            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
        
        
    }
    
    public int updateAccount(String name, String fullname, String email, String phone){
        
        return 0;
        
    }
}
