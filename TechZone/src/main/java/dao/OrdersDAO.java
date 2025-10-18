/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.DTODashboard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letan
 */
public class OrdersDAO {
    protected Connection connect;

    public OrdersDAO() {
        try {
            connect = DBUntils.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public double getTotal() {
        try {
            String sql = "SELECT SUM(o.TotalAmount) AS TongTien FROM Orders o WHERE o.Status = 'COMPLETED'";
            PreparedStatement st = connect.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                return rs.getDouble("TongTien");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
                
    }
    
}
