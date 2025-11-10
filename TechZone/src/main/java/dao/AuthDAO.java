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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author acer
 */
public class AuthDAO extends DBContext {

    public AccountUsers login(String username, String password) {

        try {
            String query = "select Accounts.AccountId, Accounts.Username, Accounts.PasswordHash,Accounts.FullName, Accounts.Email, Accounts.Phone, Accounts.RoleName, Accounts.isDeleted from Accounts\n"
                    + "where Username = ? and PasswordHash = ? AND IsDeleted = 0";

            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, this.hashMd5(password));
            System.out.println(this.hashMd5(password));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new AccountUsers(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Phone"), rs.getString("RoleName"), rs.getBoolean("isDeleted"));
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
            for (byte b : mess) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public int checkDuplicateUsername(String UserName) {
        int check = 5;
        try {
            String sql = "select Username from Accounts where Username = ? ";

            PreparedStatement state = (PreparedStatement) this.getConnection().prepareStatement(sql);
            state.setString(1, UserName);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                check = 2;
            }

        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return check;
    }

    public int checkDuplicateEmail(String Email) {
        int check = 5;
        try {
            String sql = "select Email from Accounts Email = ?";

            PreparedStatement state = (PreparedStatement) this.getConnection().prepareStatement(sql);

            state.setString(1, Email);

            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                check = 3;
            }

        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return check;
    }

    public int checkDuplicatePhone(String Phone) {
        int check = 5;
        try {
            String sql = "select Phone from Accounts where Phone = ? ";

            PreparedStatement state = (PreparedStatement) this.getConnection().prepareStatement(sql);
            state.setString(1, Phone);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                check = 4;
            }

        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return check;
    }

    public int register(String username, String password, String phone, String email) {
        int checkUserName = checkDuplicateUsername(username);
        int checkEmail = checkDuplicateEmail(email);
        int checkPhone = checkDuplicatePhone(phone);
        if (!(checkUserName == 5)) {
            return checkUserName;
        }

        if (!(checkEmail == 5)) {
            return checkEmail;
        }

        if (!(checkPhone == 5)) {
            return checkPhone;
        }

        try {
            String query = "insert into Accounts (Username, FullName, Email, PasswordHash,Phone,isDeleted,RoleName)"
                    + " values(?,?,?,?,?,?,?)";
            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, "unknown");
            statement.setString(3, email);
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

    public int updateAccount(String name, String fullname, String email, String phone) {
        try {
            String sql = "select Email from Accounts where Username = ? AND Email = ? AND IsDeleted = 0";
            PreparedStatement statementCheckEmail = (PreparedStatement) this.getConnection().prepareStatement(sql);
            statementCheckEmail.setString(1, name);
            statementCheckEmail.setString(2, email);
            ResultSet rs = statementCheckEmail.executeQuery();
            if (!rs.next()) {
                int checkEmail = checkDuplicateEmail(email);
                if (!(checkEmail == 5)) {
                    return checkEmail;
                }
            }

        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);

        }

        try {
            String sql = "select Phone from Accounts where Username = ? AND Phone = ? AND IsDeleted = 0";
            PreparedStatement statementCheckPhone = (PreparedStatement) this.getConnection().prepareStatement(sql);
            statementCheckPhone.setString(1, name);
            statementCheckPhone.setString(2, phone);
            ResultSet rs = statementCheckPhone.executeQuery();
            if (!rs.next()) {
                int checkPhone = checkDuplicatePhone(phone);

                if (!(checkPhone == 5)) {
                    return checkPhone;
                }
            }

        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);

        }

        try {
            String query = "Update Accounts set FullName = ?, Email = ?, Phone = ? where Username = ? ";
            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);

            statement.setString(1, fullname);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, name);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return 0;
        }

    }

    public int updatePassword(String name, String password, String newpassword) {
        try {
            String query = "Update Accounts set PasswordHash = ? where PasswordHash = ? and Username = ?";
            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);

            statement.setString(1, this.hashMd5(newpassword));
            statement.setString(2, this.hashMd5(password));
            statement.setString(3, name);

            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return 0;
        }

    }

    public AccountUsers getAccounts(int id) {
        try {

            String query = "SELECT AccountId, Username, PasswordHash, FullName, Email, Phone, RoleName,isDeleted FROM Accounts WHERE AccountId = ?";

            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new AccountUsers(rs.getInt("AccountId"), rs.getString("Username"), rs.getString("PasswordHash"), rs.getString("FullName"), rs.getString("Email"), rs.getString("Phone"), rs.getString("RoleName"), rs.getBoolean("isDeleted"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(AuthDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int updateForgetPassword(String newpassword, String email) {
        try {
            String query = "Update Accounts set PasswordHash = ? where Email = ?";
            PreparedStatement statement = (PreparedStatement) this.getConnection().prepareStatement(query);

            statement.setString(1, this.hashMd5(newpassword));

            statement.setString(2, email);

            return statement.executeUpdate();
        } catch (SQLException ex) {
            System.getLogger(AuthDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return 0;
        }

    }
}
