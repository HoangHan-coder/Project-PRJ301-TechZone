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
            String query = "SELECT FullName, Email, Phone FROM Accounts "
                    + "ORDER BY AccountID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement statement = this.getConnection().prepareStatement(query);

            int offset = (page - 1) * SIZE;
            statement.setInt(1, offset);
            statement.setInt(2, SIZE);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("fullName");
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                Accounts account = new Accounts(name, email, phone);
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

}
