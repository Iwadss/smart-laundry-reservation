/**
 * Document   : DBConnection.java
 * Author     : muhammadifwad
 * Description: Provides a method to establish a database connection to the laundry system.
 */
package ejb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:derby://localhost:1527/laundrydb";
    private static final String USER = "ifwad";
    private static final String PASSWORD = "ifwad123";

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}