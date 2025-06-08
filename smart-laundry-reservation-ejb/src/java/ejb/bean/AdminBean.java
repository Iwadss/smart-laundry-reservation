/**
 * Document   : AdminBean.java
 * Author     : muhammadifwad
 * Description: EJB Bean for admin operations like managing users, bookings, and time slots.
 */
package ejb.bean;

import ejb.db.DBConnection;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.*;

@Stateless
public class AdminBean {

    // Deletes a user account by email, excluding the main admin account
    public void deleteUser(String email) throws SQLException {
        if (!"admin@example.com".equalsIgnoreCase(email)) {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM USERS WHERE EMAIL = ?");
                stmt.setString(1, email);
                stmt.executeUpdate();
            }
        }
    }

    // Retrieves all registered users (name, email, role)
    public List<Map<String, String>> getAllUsers() throws SQLException {
        List<Map<String, String>> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT NAME, EMAIL, ROLE FROM USERS");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("name", rs.getString("NAME"));
                user.put("email", rs.getString("EMAIL"));
                user.put("role", rs.getString("ROLE"));
                users.add(user);
            }
        }
        return users;
    }

    // Retrieves all bookings in the system for admin view
    public List<Map<String, String>> getAllBookings() throws SQLException {
        List<Map<String, String>> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT EMAIL, BOOKING_DATE, BOOKING_TIME, WASHER_LOADS, DRYER_LOADS, TOTAL_COST FROM BOOKINGS ORDER BY BOOKING_DATE DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, String> booking = new HashMap<>();
                booking.put("email", rs.getString("EMAIL"));
                booking.put("date", rs.getString("BOOKING_DATE"));
                booking.put("slot", rs.getString("BOOKING_TIME"));
                booking.put("washer", rs.getString("WASHER_LOADS"));
                booking.put("dryer", rs.getString("DRYER_LOADS"));
                booking.put("cost", rs.getString("TOTAL_COST"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // Retrieves all available time slots
    public List<String> getTimeSlots() throws SQLException {
        List<String> timeSlots = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT SLOT FROM TIMESLOTS");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                timeSlots.add(rs.getString("SLOT"));
            }
        }
        return timeSlots;
    }

    // Adds a new time slot to the TIMESLOTS table
    public void addTimeSlot(String newSlot) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TIMESLOTS(SLOT) VALUES (?)");
            stmt.setString(1, newSlot);
            stmt.executeUpdate();
        }
    }

    // Deletes an existing time slot from the TIMESLOTS table
    public void deleteTimeSlot(String deleteSlot) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM TIMESLOTS WHERE SLOT = ?");
            stmt.setString(1, deleteSlot);
            stmt.executeUpdate();
        }
    }
}