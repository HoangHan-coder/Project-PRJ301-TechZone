/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NgKaitou
 */
public class DBContext {

    private static final String DB_NAME = "TechZoneDBv1";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "123";

    public Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true";
            conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
