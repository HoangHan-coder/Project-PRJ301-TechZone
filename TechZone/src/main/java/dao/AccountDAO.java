/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class AccountDAO extends DBContext {

    private final int SIZE = 30;

    public List<Account> getAccounts(int page) {
        try {
            if (page < 1) {
                page = 1; // tránh OFFSET âm
            }
            List<Account> list = new ArrayList<>();
            String query = "SELECT AccountId, Username, PasswordHash, FullName, "
                    + "Email, Phone, RoleName, IsDeleted, CreatedAt, UpdatedAt "
                    + "FROM Accounts "
                    + "WHERE IsDeleted = 0 "
                    + "ORDER BY AccountId "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement statement = this.getConnection().prepareStatement(query);
            int offset = (page - 1) * SIZE;
            statement.setInt(1, offset);
            statement.setInt(2, SIZE);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int accountId = rs.getInt("AccountId");
                String userName = rs.getString("Username");
                String name = rs.getString("FullName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String roleName = rs.getString("RoleName");
                boolean isDeleted = rs.getBoolean("IsDeleted");
                Timestamp createdAt = rs.getTimestamp("CreatedAt");
                Timestamp updatedAt = rs.getTimestamp("UpdatedAt");

                Account account = new Account(accountId, userName, name, email, phone, roleName);
                account.setIsDeleted(isDeleted);
                account.setCreatedAt(createdAt.toLocalDateTime());

                list.add(account);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getTotalPages() {
        int totalRows = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Accounts";

            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                totalRows = rs.getInt(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        int totalPage = (int) Math.ceil((double) totalRows / SIZE);
        return totalPage;

    }

    public int getNextId() {
        try {
            String sql = "SELECT MAX(AccountId) FROM Accounts";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 1;
        }

    }

    public Account getById(int id) {
        try {
            String sql = "SELECT AccountId, Username, FullName, Email, Phone, RoleName\n"
                    + "FROM Accounts \n"
                    + "WHERE AccountId = ?\n";

            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int accoutId = rs.getInt("AccountId");
                String userName = rs.getString("username");
                String name = rs.getString("FullName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String roleName = rs.getString("RoleName");
                Account account = new Account(accoutId, userName, name, email, phone, roleName);
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int update(Account account) {
        String sql = "UPDATE Accounts SET fullName = ?, email=?, phone=?, RoleName=?\n"
                + " WHERE AccountId = ?";
        try (PreparedStatement st = this.getConnection().prepareStatement(sql)) {
            st.setString(1, account.getFullName());
            st.setString(2, account.getEmail());
            st.setString(3, account.getPhone());
            st.setString(4, account.getRoleName());
            st.setInt(5, account.getAccountId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int delete(int id) {
        try {
            String sql = "UPDATE Accounts SET IsDeleted = 1 WHERE AccountId = ? AND IsDeleted = 0";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            int result = st.executeUpdate();
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int create(Account account) {

        try {
            String query = "INSERT INTO Accounts ( Username,PasswordHash, FullName, Email, Phone, RoleName)\n"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setString(1, account.getUserName());
            statement.setString(2, account.getPassWordHarh());
            statement.setString(3, account.getFullName());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPhone());
            statement.setString(6, account.getRoleName());

            return statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean existsUsername(String username) {

        try {
            String sql = "SELECT 1 FROM Accounts WHERE Username= ?";

            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String hashMd5(String raw) {
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

    public List<Account> filterAccounts(String keyword, String role) {
        List<Account> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT AccountId, Username, PasswordHash, FullName, Email, Phone, RoleName,"
                + " IsDeleted, CreatedAt, UpdatedAt FROM Accounts WHERE IsDeleted = 0"
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (Username LIKE ? OR FullName LIKE ?)");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND RoleName = ?");
        }
        sql.append(" ORDER BY AccountId");

        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql.toString());
            int index = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(index++, role);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account(
                        rs.getInt("AccountId"),
                        rs.getString("Username"),
                        rs.getString("FullName"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("RoleName")
                );
                a.setIsDeleted(rs.getBoolean("IsDeleted"));
                Timestamp createdAt = rs.getTimestamp("CreatedAt");
                if (createdAt != null) {
                    a.setCreatedAt(createdAt.toLocalDateTime());
                }
                list.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Thêm in lỗi SQL
        }
        return list;
    }

}
