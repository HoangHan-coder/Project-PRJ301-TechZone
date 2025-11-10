/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBContext;
import model.AllCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;

/**
 * DAO providing statistical queries for dashboard metrics.
 * 
 * <p>Includes revenue totals, counts of orders/products/accounts, per-category
 * stats, and sales summaries.</p>
 */
public class StatisticalDAO extends DBContext{

    /**
     * Calculates total revenue from completed orders.
     * @return total amount sum or 0.0 on error
     */
    public double getTotal() {
        try {
            // SQL query to sum total amount from completed orders
            String sql = "SELECT SUM(o.TotalAmount) AS TongTien FROM Orders o WHERE o.Status = 'COMPLETED'";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return total amount from result set
                return rs.getDouble("TongTien");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;

    }

    /**
     * Counts completed orders.
     * @return number of completed orders
     */
    public int getTotalBill() {
        try {
            // SQL query to count completed orders
            String sql = "SELECT COUNT(*) AS TongBill FROM Orders o WHERE o.Status = 'COMPLETED'";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongBill");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Sums stock of all non-deleted products.
     * @return total stock count
     */
    public int getTotalProduct() {
        try {
            // SQL query to sum stock of non-deleted products
            String sql = "SELECT SUM(p.stock) AS TongProduct FROM Product p WHERE p.isDeleted = 'False' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return total stock from result set
                return rs.getInt("TongProduct");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Counts customer accounts.
     * @return number of customers
     */
    public int getTotalAccount() {
        try {
            // SQL query to count customer accounts
            String sql = "SELECT COUNT(*) AS TongProduct FROM Accounts p WHERE p.RoleName = 'Customer'";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongProduct");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Gets product counts grouped by category name.
     * @return list of {@link Category} with name and count
     */
    public List<Category> getTotalCategory() {
        try {
            List<Category> list = new ArrayList<>();
            // SQL query to group product counts by category name
            String sql = "SELECT c.Name,COUNT(*) AS Total FROM Category c JOIN Product p ON c.CategoryId = p.CategoryId\n"
                    + "GROUP BY c.Name";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Create Category object from result set and add to list
                Category category = new Category(rs.getString("Name"), rs.getInt("Total"));
                list.add(category);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Counts orders by COMPLETED status.
     * @return count
     */
    public int getCompleted() {
        try {
            // SQL query to count orders by COMPLETED status
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'COMPLETED' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Counts orders by CANCEL status.
     * @return count
     */
    public int getCancel() {
        try {
            // SQL query to count orders by CANCEL status
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'CANCEL' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Counts orders by COMPLETED status (note: method name suggests PENDING, but query is COMPLETED).
     * @return count
     */
    public int getPending() {
        try {
            // SQL query to count orders by COMPLETED status
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'COMPLETED' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Counts orders by PROCESSING status.
     * @return count
     */
    public int getProcessing() {
        try {
            // SQL query to count orders by PROCESSING status
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'PROCESSING' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                // Return count from result set
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    /**
     * Retrieves aggregated stats per category for completed orders.
     * @return list of {@link AllCategory} rows with stock, sales count, and total revenue
     */
    public List<AllCategory> getAll() {
        try {

            // SQL query to retrieve aggregated stats per category for completed orders
            String sql = "SELECT c.Name AS Name,SUM(p.Stock) AS SOLUONG ,SUM(o.Quantity) AS LUOTBAN, SUM(o.UnitPrice*o.Quantity) AS TotalPrice FROM OrderItems o \n"
                    + "JOIN Product p ON p.ProductId = o.ProductId\n"
                    + "JOIN Category c ON c.CategoryId = p.CategoryId\n"
                    + "JOIN Orders r on r.OrderId = o.OrderId\n"
                    + "where r.Status = 'COMPLETED'\n"
                    + "GROUP BY c.Name ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<AllCategory> list = new ArrayList<>();
            while (rs.next()) {
                // Create AllCategory object from result set and add to list
                AllCategory category = new AllCategory(rs.getString("Name"),rs.getInt("SOLUONG"),
                        rs.getInt("LUOTBAN"), rs.getInt("Totalprice"));
                list.add(category);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
