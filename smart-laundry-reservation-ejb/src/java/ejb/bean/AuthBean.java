/**
 * Document   : AuthBean.java
 * Author     : muhammadifwad
 * Description: Handles user authentication and registration logic using database operations.
 */
package ejb.bean;

import ejb.db.DBConnection;
import java.sql.*;
import javax.ejb.Stateless;

@Stateless
public class AuthBean {

    // Verifies login credentials and returns user data if valid
    public ResultSet loginUser(String email, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT NAME, ROLE FROM USERS WHERE EMAIL = ? AND PASSWORD = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        return stmt.executeQuery();
    }

    // Checks if an email is already registered in the system
    public boolean emailExists(String email) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    // Registers a new user with default role as 'user'
    public void registerUser(String name, String email, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO USERS (NAME, EMAIL, PASSWORD, ROLE) VALUES (?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, "user"); // Default role
        stmt.executeUpdate();
    }
}