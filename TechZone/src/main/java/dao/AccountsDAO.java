/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.Accounts;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pc
 */
public class AccountsDAO extends DBContext {

    private final int SIZE = 30;
 
    public List<Accounts> getAccounts(int page) {
        try {
            if (page < 1) {
                page = 1; // tránh OFFSET âm
            }

            List<Accounts> list = new ArrayList<>();
            String query = "SELECT AccountId,username, FullName, Email, Phone FROM Accounts "
                    + "WHERE IsDeleted = 0"
                    + "ORDER BY AccountID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement statement = this.getConnection().prepareStatement(query);

            int offset = (page - 1) * SIZE;
            statement.setInt(1, offset);
            statement.setInt(2, SIZE);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int accoutId = rs.getInt("AccountId");
                String userName = rs.getString("username");
                String name = rs.getString("fullName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Accounts account = new Accounts(accoutId, userName, name, email, phone);
                list.add(account);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AccountsDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 1;
        }

    }

    public Accounts getById(int id) {
        try {
            String sql = "SELECT AccountId,username, FullName, email, phone FROM Accounts where AccountId=?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int accoutId = rs.getInt("AccountId");
                String userName = rs.getString("username");
                String name = rs.getString("fullName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Accounts account = new Accounts(accoutId, userName, name, email, phone);
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int update(Accounts account) {
        String sql = "UPDATE Accounts SET fullName = ?, email=?, phone=?\n"
                + " WHERE AccountId = ?";
        try (PreparedStatement st = this.getConnection().prepareStatement(sql)) {
            st.setString(1, account.getFullName());
            st.setString(2, account.getEmail());
            st.setString(3, account.getPhone());
            st.setInt(4, account.getId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int delete(int id) {
        try {
            String sql = "UPDATE Accounts SET IsDeleted = 1 WHERE AccountId = ?";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            return st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public int create(Accounts account) {
        try {
            String query = "insert into Accounts (Username, FullName, Email, PasswordHash,Phone)"
                    + " values(?,?,?,?,?)";
            PreparedStatement st = this.getConnection().prepareStatement(query);
     
            st.setString(1, account.getUserName());
            st.setString(2, account.getFullName());
            st.setString(3, account.getEmail());
            st.setString(4, account.getPassWord());
            st.setString(5, account.getPhone());
            return st.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

}
