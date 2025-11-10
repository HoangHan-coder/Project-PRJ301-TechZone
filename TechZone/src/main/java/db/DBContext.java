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
 * Provides methods for creating JDBC connections to the application database.
 * 
 * <p>No configuration values are modified here; only constants are read to
 * compose the JDBC connection URL and authenticate.</p>
 * 
 * @author NgKaitou
 */
public class DBContext {

    // Database name used in the JDBC URL
    private static final String DB_NAME = "TechZonev2"; 
    // Database username for authentication
    private static final String DB_USER_NAME = "sa"; 
    // Database password for authentication
    private static final String DB_PASSWORD = "123456"; 

    /**
     * Creates and returns a new SQL Server JDBC connection using the configured
     * credentials and connection properties.
     * 
     * @return an open {@link Connection} if successful, otherwise {@code null}
     */
    public Connection getConnection() {
        // Holds the JDBC connection instance to be returned
        Connection conn = null; 
        try {
            // Load SQL Server JDBC driver class into JVM
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 
            // Build JDBC URL with DB name and TLS settings
            String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true"; 
            // Open a connection using the URL and credentials
            conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD); 
        } catch (ClassNotFoundException | SQLException ex) {
            // Log any driver loading or SQL connection error at SEVERE level
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex); 
        }
        // Return the connection (may be null if opening failed)
        return conn; 
    }
}
