/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
 *
 * @author letan
 */
public class StatisticalDAO extends DBContext{

    public double getTotal() {
        try {
            String sql = "SELECT SUM(o.TotalAmount) AS TongTien FROM Orders o WHERE o.Status = 'COMPLETED'";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getDouble("TongTien");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;

    }

    public int getTotalBill() {
        try {
            String sql = "SELECT COUNT(*) AS TongBill FROM Orders o WHERE o.Status = 'COMPLETED'";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongBill");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public int getTotalProduct() {
        try {
            String sql = "SELECT COUNT(*) AS TongProduct FROM Product p WHERE p.isDeleted = 'False' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongProduct");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public int getTotalAccount() {
        try {
            String sql = "SELECT COUNT(*) AS TongProduct FROM AccountRoles p WHERE p.RoleId = 2 ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongProduct");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public List<Category> getTotalCategory() {
        try {
            List<Category> list = new ArrayList<>();
            String sql = "SELECT c.Name,COUNT(*) AS Total FROM Category c JOIN Product p ON c.CategoryId = p.CategoryId\n"
                    + "GROUP BY c.Name";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getString("Name"), rs.getInt("Total"));
                list.add(category);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getCompleted() {
        try {
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'COMPLETED' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }
    public int getCancel() {
        try {
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'CANCEL' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public int getPending() {
        try {
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'COMPLETED' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public int getProcessing() {
        try {
            String sql = "SELECT COUNT(*) AS TongStatus FROM Orders p WHERE p.Status = 'PROCESSING' ";
            PreparedStatement st = this.getConnection().prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return rs.getInt("TongStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StatisticalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public List<AllCategory> getAll() {
        try {

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
