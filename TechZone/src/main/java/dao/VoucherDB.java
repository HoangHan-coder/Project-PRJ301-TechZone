/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
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
public class VoucherDB extends DBContext{
    protected Connection connect;

    public VoucherDB() {
        connect = this.getConnection();
    }
    
    
    public List<Voucher> getAllVoucher() {
        List<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers";
               
        try {
            PreparedStatement ps = connect.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                listVoucher.add(new Voucher(
                        rs.getString("imgPath"),
                        rs.getString("code"),
                        rs.getString("discountValue"),
                        rs.getTimestamp("startDate"),
                        rs.getTimestamp("endDate"),
                        rs.getString("status"),
                        rs.getString("condition"),
                        rs.getString("maxUsage"),
                        rs.getString("currentUsage")
                ));
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listVoucher;
    }
    
}
