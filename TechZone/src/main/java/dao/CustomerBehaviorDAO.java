/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.CustomerBehavior;
import model.Product;

/**
 * DAO for recording and querying customer behavior (e.g., product views).
 *
 * <p>Provides create/update operations and recent behavior-based product
 * recommendations.</p>
 *
 * @author letan
 */
public class CustomerBehaviorDAO extends DBContext {

    /**
     * Inserts a new customer behavior record.
     *
     * @param productcode product identifier
     * @param quantity behavior quantity (e.g., view count increment)
     * @param id account identifier
     * @return rows affected
     */
    public int createBehavior(int productcode, int quantity, int id) {
        try {
            String query = "INSERT INTO [dbo].[CustomerBehavior]\n"
                    + "           ([AccountId]\n"
                    + "           ,[ProductCode]\n"
                    + "           ,[Quantity]\n"
                    + "           ,[ActionTime]\n"
                    + "           ,[IsDeleted])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            Account account = new Account();
            account.setAccountId(id);
            statement.setInt(1, account.getAccountId()); // bind account id
            statement.setInt(2, productcode);
            statement.setInt(3, quantity);
            statement.setBoolean(5, false); // not deleted
            Date now = new Date();
            statement.setTimestamp(4, new Timestamp(now.getTime())); // current timestamp
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerBehaviorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Checks if a behavior record exists for a given product and account.
     *
     * @param id product id
     * @param accountid account id
     * @return a placeholder {@link CustomerBehavior} if exists, otherwise null
     */
    public CustomerBehavior selectBehaviorDAO(int id, int accountid) {
        try {
            String query = "SELECT * FROM CustomerBehavior WHERE ProductCode = ? AND AccountId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, accountid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return new CustomerBehavior(); // existence indicator
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerBehaviorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves the current quantity recorded for a product-account pair.
     *
     * @param id product id
     * @param accountid account id
     * @return quantity value or 0 if absent
     */
    public int selectQuantity(int id, int accountid) {
        try {
            String query = "SELECT Quantity FROM CustomerBehavior WHERE ProductCode = ? AND AccountId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setInt(1, id);
            statement.setInt(2, accountid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return rs.getInt("Quantity");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerBehaviorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Updates quantity and timestamp for a product-account behavior record.
     *
     * @param productcode product id
     * @param quantity new quantity
     * @param accountid account id
     * @return rows affected
     */
    public int updateQuantity(int productcode, int quantity, int accountid) {
        try {
            String query = "UPDATE [dbo].[CustomerBehavior]\n"
                    + "   SET [Quantity] = ?\n"
                    + "      ,[ActionTime] = ?\n"
                    + " WHERE ProductCode = ? AND AccountId = ?";
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            statement.setInt(1, quantity);
            Date now = new Date();
            statement.setTimestamp(2, new Timestamp(now.getTime())); // update timestamp
            statement.setInt(3, productcode);
            statement.setInt(4, accountid);
            return statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerBehaviorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Gets top 3 recent products related to the user's behavior within last 3 days.
     *
     * @param accountid account id
     * @return list of {@link Product}
     */
    public List<Product> getBehavior(int accountid) {
        try {
            List<Product> list = new ArrayList<>();

            String sql = "SELECT TOP 3  \n"
                    + "    p.ProductId,\n"
                    + "    p.ProductName,\n"
                    + "    p.ProductPrice,\n"
                    + "    p.LinkImg \n"
                    + "FROM CustomerBehavior c\n"
                    + "JOIN Product p \n"
                    + "    ON p.ProductId = c.ProductCode\n"
                    + "WHERE c.ActionTime >= DATEADD(DAY, -3, GETDATE()) AND  c.AccountId=?\n"
                    + "ORDER BY c.Quantity DESC;";
            PreparedStatement ps = this.getConnection().prepareStatement(sql);
            ps.setInt(1, accountid); // bind account id
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product products = new Product(rs.getInt("productId"), rs.getString("LinkImg"),
                        rs.getString("ProductName"), rs.getDouble("ProductPrice")); // map to Product
                list.add(products);
            }

            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerBehaviorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
