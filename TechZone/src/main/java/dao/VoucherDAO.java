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
    private final String IMG_PATH = "assets/images/vouchers/voucher.png";

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
    
    public int createVoucher(Voucher voucher){
        if(voucher.isExpired()) {
            voucher.setStatus("EXPIRED");
        } else if(voucher.isNotStart()) {
            voucher.setStatus("UPCOMING");
        } else {
            voucher.setStatus("ACTIVE");
        }
        
        if(voucher.getMaxUsage() <= 0) {
            voucher.setStatus("DISABLED");
        }
        
        String sql = "INSERT INTO Vouchers (ImgPath, Code, DiscountValue, DiscountType, StartDate, EndDate, Status, MinOrderValue, MaxUsage) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement  statement = this.connect.prepareStatement(sql);
            statement.setString(1, IMG_PATH);
            statement.setString(2, voucher.getCode());
            statement.setBigDecimal(3, voucher.getDiscountValue());
            statement.setString(4, voucher.getDiscountType());
            statement.setTimestamp(5, voucher.getStartDate());
            statement.setTimestamp(6, voucher.getEndDate());
            statement.setString(7, voucher.getStatus());
            statement.setBigDecimal(8, voucher.getMinOrderValue());
            statement.setInt(9, voucher.getMaxUsage());
            
            return statement.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;
    }
            
    public int deleteVoucher(int id) {
         
        try {
            String sql = "UPDATE Vouchers SET Status='DISABLED' WHERE VoucherId = ?";
            PreparedStatement  statement = this.connect.prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
}
