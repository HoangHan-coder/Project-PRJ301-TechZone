/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author letan
 */
public class DBUntils {
    private static final String DB_NAME = "TechZoneDB";
    private static final String DB_USER_NAME = "sa";
    private static final String DB_PASSWORD = "datvtce123";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME+";encrypt=true;trustServerCertificate=true";
        conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        if(conn != null) System.out.println("------------------------------------------------>");
        return conn;
    }
}
