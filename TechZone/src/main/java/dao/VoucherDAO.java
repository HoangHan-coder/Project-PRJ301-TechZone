/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import db.DBContext;
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
public class VoucherDAO extends DBContext{
    private final String IMG_PATH = "assets/images/vouchers/voucher.png";

    public VoucherDAO() {
        
    }
    
    
    public List<Voucher> getAllVoucher() {
        List<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers";
               
        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
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
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listVoucher;
    }
    
    public Voucher getByVoucherCode(String voucherCode) {
        
        try {
            String sql = "SELECT * FROM Vouchers WHERE Code = ?";
            PreparedStatement  statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, voucherCode);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                return new Voucher(
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
                );
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
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
            PreparedStatement  statement = this.getConnection().prepareStatement(sql);
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
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;
    }
    
    public int updateVoucher(Voucher voucher, int voucherId){
        
        
        String sql = "UPDATE Vouchers SET "
                + " Code = ?,"
                + " DiscountValue = ?,"
                + " DiscountType = ?,"
                + " StartDate = ?,"
                + " EndDate = ?,"
                + " Status = ?,"
                + " MinOrderValue = ?,"
                + " MaxUsage = ?"
                + " WHERE VoucherId = ?";
        try {
            PreparedStatement  statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, voucher.getCode());
            statement.setBigDecimal(2, voucher.getDiscountValue());
            statement.setString(3, voucher.getDiscountType());
            statement.setTimestamp(4, voucher.getStartDate());
            statement.setTimestamp(5, voucher.getEndDate());
            statement.setString(6, voucher.getStatus());
            statement.setBigDecimal(7, voucher.getMinOrderValue());
            statement.setInt(8, voucher.getMaxUsage());
            statement.setInt(9, voucherId);
            return statement.executeUpdate();            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return 0;
    }
            
    public int deleteVoucher(int id) {
         
        try {
            String sql = "UPDATE Vouchers SET Status='DISABLED' WHERE VoucherId = ?";
            PreparedStatement  statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int getTotalRow() {
        try {
            String sql = "SELECT Count(VoucherId) as totalRow FROM Vouchers";
            PreparedStatement  statement = this.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public List<Voucher> getVoucherList(int page) {
        List<Voucher> listVoucher = new ArrayList<>();
        int index = (page - 1) * 12;
        try {
            String sql = "select * from Vouchers order by VoucherId "
                    + "offset ? rows fetch next 12 rows only";
            PreparedStatement pt = this.getConnection().prepareStatement(sql);
            pt.setInt(1, index);
            ResultSet rs = pt.executeQuery();
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
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listVoucher;
    }
}
