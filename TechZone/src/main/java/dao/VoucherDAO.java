/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Voucher;

/**
 * DAO for voucher management (CRUD, listing, search, usage tracking).
 * 
 * @author NgKaitou
 */
public class VoucherDAO extends DBContext {

    private final String IMG_PATH = "assets/images/vouchers/voucher.png";

    public VoucherDAO() {

    }

    /**
     * Retrieves all vouchers.
     * @return list of {@link Voucher}
     */
    public List<Voucher> getAllVoucher() {
        List<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers";

        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
    
    /**
     * Retrieves vouchers applicable for an order total (minOrderValue <= totalAmount).
     * @param totalAmount order subtotal/total
     * @return list of {@link Voucher}
     */
    public List<Voucher> getAvailableVoucher(double totalAmount) {
        List<Voucher> listVoucher = new ArrayList<>();
        String sql = "SELECT * FROM Vouchers WHERE minOrderValue <= ?;";

        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setBigDecimal(1, BigDecimal.valueOf(totalAmount));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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

    /**
     * Retrieves a voucher by code.
     * @param voucherCode code value
     * @return {@link Voucher} or null
     */
    public Voucher getByVoucherCode(String voucherCode) {

        try {
            String sql = "SELECT * FROM Vouchers WHERE Code = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, voucherCode);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
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

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    /**
     * Retrieves a voucher by id.
     * @param voucherId identifier
     * @return {@link Voucher} or null
     */
    public Voucher getByVoucherId(int voucherId) {

        try {
            String sql = "SELECT * FROM Vouchers WHERE voucherId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, voucherId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
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

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Creates a new voucher; status is inferred from dates and max usage.
     * @param voucher voucher data
     * @return affected rows (1 on success)
     */
    public int createVoucher(Voucher voucher) {
        if (voucher.isExpired()) {
            voucher.setStatus("EXPIRED");
        } else if (voucher.isNotStart()) {
            voucher.setStatus("UPCOMING");
        } else {
            voucher.setStatus("ACTIVE");
        }

        if (voucher.getMaxUsage() <= 0) {
            voucher.setStatus("DISABLED");
        }

        String sql = "INSERT INTO Vouchers (ImgPath, Code, DiscountValue, DiscountType, StartDate, EndDate, Status, MinOrderValue, MaxUsage) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, IMG_PATH);
            statement.setString(2, voucher.getCode().toUpperCase());
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

    /**
     * Updates voucher values by id.
     * @param voucher updated data
     * @param voucherId target id
     * @return affected rows
     */
    public int updateVoucher(Voucher voucher, int voucherId) {

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
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, voucher.getCode().toUpperCase());
            statement.setBigDecimal(2, voucher.getDiscountValue());
            statement.setString(3, voucher.getDiscountType());
            statement.setTimestamp(4, voucher.getStartDate());
            statement.setTimestamp(5, voucher.getEndDate());
            statement.setString(6, voucher.getStatus());
            statement.setBigDecimal(7, voucher.getMinOrderValue());
            statement.setInt(8, voucher.getMaxUsage());
            statement.setInt(9, voucherId);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    /**
     * Soft-disables a voucher by setting Status = 'DISABLED'.
     * @param id voucher id
     * @return affected rows
     */
    public int deleteVoucher(int id) {

        try {
            String sql = "UPDATE Vouchers SET Status='DISABLED' WHERE VoucherId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Counts vouchers filtered by code keyword.
     * @param keyword code substring
     * @return count
     */
    public int getTotalRow(String keyword) {
        try {
            String sql = "SELECT Count(VoucherId) as totalRow FROM Vouchers WHERE Code LIKE ? ";
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Counts all vouchers.
     * @return total rows
     */
    public int getTotalRow() {
        try {
            String sql = "SELECT Count(VoucherId) as totalRow FROM Vouchers";
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Retrieves paginated vouchers (12 per page).
     * @param page page index (1-based)
     * @return list of {@link Voucher}
     */
    public List<Voucher> getVoucherList(int page) {
        List<Voucher> listVoucher = new ArrayList<>();
        int index = (page - 1) * 12;
        try {
            String sql = "select * from Vouchers order by VoucherId "
                    + "offset ? rows fetch next 12 rows only";
            PreparedStatement pt = this.getConnection().prepareStatement(sql);
            pt.setInt(1, index);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
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

    /**
     * Checks if a voucher code already exists.
     * @param voucherCode code to check
     * @return true if exists
     */
    public boolean voucherCodeExist(String voucherCode) {
        String sql = "SELECT * FROM Vouchers WHERE Code = ?";
        Voucher v = null;
        try {
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setString(1, voucherCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                v = new Voucher(
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
            return v != null;
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Retrieves vouchers filtered by code with pagination.
     * @param keyword code keyword (nullable)
     * @param page page index (1-based)
     * @return list of {@link Voucher}
     */
    public List<Voucher> getByVouCode(String keyword, int page) {
        List<Voucher> listVoucher = new ArrayList<>();
        int index = (page - 1) * 12;
        if (keyword == null) {
            keyword = "";
        }
        try {
            String sql = "select * from Vouchers WHERE Code LIKE ? order by VoucherId "
                    + "offset ? rows fetch next 12 rows only";
            PreparedStatement pt = this.getConnection().prepareStatement(sql);
            pt.setString(1, "%" + keyword + "%");
            pt.setInt(2, index);

            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
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
    
    /**
     * Marks a voucher as used by updating usage counters.
     * @param voucher voucher entity
     * @param totalPrice order total (unused in current SQL)
     * @return affected rows or 0 if not applicable
     */
    public int useVoucher(Voucher voucher, double totalPrice) {
        if (voucher.getMaxUsage() <= 0) return 0;
        String sql = "UPDAte Vouchers set MaxUsage = MaxUsage - 1, CurrentUsage = CurrentUsage + 1 Where VoucherId = ?";
        try {
            
            PreparedStatement statement = this.getConnection().prepareStatement(sql);
            statement.setInt(1, voucher.getVoucherId());
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
