/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for account-related database operations.
 *
 * <p>Provides CRUD operations, pagination and filtering utilities for the
 * <code>Accounts</code> table, as well as helper utilities like hashing.</p>
 *
 * @author pc
 */
public class AccountDAO extends DBContext {

    /**
     * Retrieves a paginated list of accounts filtered by optional keyword and role.
     *
     * <p>Returns a list of accounts matching the specified filters, ordered by
     * AccountId and paginated according to the provided page index and size.</p>
     *
     * @param page the 1-based page index to retrieve
     * @param keyword optional search keyword to match username or full name (case-insensitive)
     * @param role optional role name to filter by
     * @param pageSize number of records per page
     * @return list of matching {@link Account} objects for the requested page
     */
    public List<Account> filterAccounts(int page, String keyword, String role, int pageSize) {
        List<Account> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT AccountId, Username, PasswordHash, FullName, Email, Phone, RoleName,"
                + " IsDeleted, CreatedAt, UpdatedAt FROM Accounts WHERE IsDeleted = 0"
        );

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (LOWER(Username) LIKE ? OR LOWER(FullName) LIKE ?)");
        }
        if (role != null && !role.trim().isEmpty()) {
            sql.append(" AND RoleName = ?");
        }
        sql.append(" ORDER BY AccountId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql.toString());
            int index = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword.trim().toLowerCase() + "%"; // chuyển về chữ thường
                ps.setString(index++, kw);
                ps.setString(index++, kw);
            }
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(index++, role);
            }

            ps.setInt(index++, (page - 1) * pageSize);
            ps.setInt(index, pageSize);

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

    /**
     * Counts total number of accounts that match the given filters.
     *
     * @param keyword optional search keyword to match username or full name (case-insensitive)
     * @param role optional role name to filter by
     * @return total row count of accounts matching the filters
     */
    public int getTotalPages(String keyword, String role) {
        int totalRows = 0;
        try {
            StringBuilder sql = new StringBuilder("SELECT COUNT(AccountId) FROM Accounts WHERE IsDeleted = 0");
            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (LOWER(Username) LIKE ? OR LOWER(FullName) LIKE ?)");
            }
            if (role != null && !role.trim().isEmpty()) {
                sql.append(" AND RoleName = ?");
            }

            PreparedStatement ps = this.getConnection().prepareStatement(sql.toString());
            int index = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String kw = "%" + keyword.trim().toLowerCase() + "%";
                ps.setString(index++, kw);
                ps.setString(index++, kw);
            }
            if (role != null && !role.trim().isEmpty()) {
                ps.setString(index++, role);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalRows = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalRows;
    }

    /**
     * Computes the next AccountId by selecting the current maximum and adding one.
     *
     * @return next available AccountId, or 1 if none found or on error
     */
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

    /**
     * Retrieves a single account by its unique identifier.
     *
     * @param id the AccountId to look up
     * @return the {@link Account} if found, otherwise {@code null}
     */
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

    /**
     * Updates an existing account's editable fields.
     *
     * @param account the account entity containing updated values
     * @return number of affected rows (0 if none)
     */
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

    /**
     * Soft deletes an account by setting its IsDeleted flag to 1.
     *
     * @param id the AccountId to soft delete
     * @return number of affected rows (0 if none)
     */
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

    /**
     * Inserts a new account record.
     *
     * @param account the account entity to insert
     * @return number of affected rows (0 if failed)
     */
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

    /**
     * Checks whether a username already exists.
     *
     * @param username the username to check
     * @return {@code true} if the username exists, otherwise {@code false}
     */
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
    
    /**
     * Checks whether an email is already used by another account (excluding a given account).
     *
     * @param gmail the email to check
     * @param excludeAccountId the AccountId to exclude from the check
     * @return {@code true} if another account uses the email, otherwise {@code false}
     */
    public boolean existsGmail(String gmail, int excludeAccountId) {
        String sql = "SELECT 1 FROM Accounts WHERE  Email = ? AND AccountId <> ? AND IsDeleted = 0";
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, gmail);
            stmt.setInt(2, excludeAccountId);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); 
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Checks whether a phone number is already used by another account (excluding a given account).
     *
     * @param gmail the phone number to check
     * @param excludeAccountId the AccountId to exclude from the check
     * @return {@code true} if another account uses the phone, otherwise {@code false}
     */
    public boolean existsPhone(String gmail, int excludeAccountId) {
        String sql = "SELECT 1 FROM Accounts WHERE Phone = ? AND AccountId <> ? AND IsDeleted = 0";
        try (PreparedStatement stmt = this.getConnection().prepareStatement(sql)) {
            stmt.setString(1, gmail);
            stmt.setInt(2, excludeAccountId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Generates an MD5 hexadecimal hash for the provided input string.
     *
     * @param raw the input string to hash
     * @return lowercase hexadecimal MD5 string, or empty string on error
     */
    public String hashMd5(String raw) {
       
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

}
