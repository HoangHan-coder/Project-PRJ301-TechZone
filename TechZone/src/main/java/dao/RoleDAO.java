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
public class RoleDAO extends DBContext {

    public int getRoleIdByName(String roleName) {
        int roleId = -1;
        String sql = "SELECT RoleId FROM Roles WHERE RoleName=?";
        try {
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            st.setString(1, roleName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                roleId = rs.getInt("RoleId");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountsDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleId;
    }
}
