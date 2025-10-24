/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import dao.AccountDAO;
import db.DBContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Account;

/**
 *
 * @author pc
 */
public class TestDBContext {

    public static void main(String[] args) {
//        DBContext db = new DBContext();
//        try (Connection conn = db.getConnection()) {
//            if (conn != null && !conn.isClosed()) {
//                System.out.println("Kết nối database thành công!");
//            } else {
//                System.out.println("Kết nối database thất bại!");
//            }
//        } catch (SQLException e) {
//            System.out.println("Lỗi khi kết nối database:");
//            e.printStackTrace();
//        }

        AccountDAO dao = new AccountDAO();
        List<Account> product = dao.getAccounts(0);
        if (product != null && !product.isEmpty()) {
            for (Account a : product) {
                System.out.println(a);
            }
        } else {
            System.out.println("No data or query failed");
        }

    }
}
