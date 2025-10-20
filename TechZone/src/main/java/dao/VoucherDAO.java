/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Voucher;

/**
 *
 * @author NgKaitou
 */
public class VoucherDAO {
    protected Connection connect;

    public VoucherDAO() {
        try {
            connect = DBUntils.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<Voucher> getAllVoucher() {
        List<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers";
               
        try {
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                listVoucher.add(new Voucher(
                        rs.getInt("VoucherId"),
                        rs.getString("imgPath"),
                        rs.getString("code"),
                        rs.getBigDecimal("discountValue"),
                        rs.getString("discountType"),
                        rs.getTimestamp("startDate"),
                        rs.getTimestamp("endDate"),
                        rs.getString("status"),
                        rs.getBigDecimal("minOrderValue"),
                        rs.getInt("maxUsage"),
                        rs.getInt("currentUsage")
                ));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listVoucher;
    }
    
}
