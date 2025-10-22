/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Accounts;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author acer
 */
public class AccountsDAO extends DBUntils{

    public AccountsDAO() throws SQLException {
    }
    
    
    public Accounts login(String username,String password) throws ClassNotFoundException{
        
        try {
            String query = "select Accounts.AccountId, Accounts.Username, Accounts.PasswordHash,Accounts.FullName, Accounts.Email, Accounts.Phone, Roles.RoleName from Accounts\n" +
"join AccountRoles on Accounts.AccountId = AccountRoles.AccountId\n" +
"join Roles on AccountRoles.RoleId = Roles.RoleId\n" +
"where Username = ? and PasswordHash = ?";
         
           PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
          statement.setString(1, username);
          statement.setString(2, this.hashMd5(password));
            System.out.println(this.hashMd5(password));
            ResultSet rs = statement.executeQuery();
            
            if(rs.next()){
                return new Accounts(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("FullName"),rs.getString("Email"),rs.getString("Phone"),rs.getString("RoleName"));
            }
        } catch (SQLException ex) {
            System.getLogger(AccountsDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
