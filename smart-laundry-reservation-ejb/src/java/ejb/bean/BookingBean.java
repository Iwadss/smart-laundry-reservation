/**
 * Document   : BookingBean.java
 * Author     : muhammadifwad
 * Description: EJB bean for handling booking-related operations with the database.
 */
package ejb.bean;

import ejb.db.DBConnection;
import javax.ejb.Stateless;
import java.sql.*;
import java.util.*;
import java.sql.Date;

@Stateless
public class BookingBean {

    // Retrieve all available booking time slots from the database
    public List<String> getAllTimeSlots() throws SQLException {
        List<String> timeSlots = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT SLOT FROM TIMESLOTS";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                timeSlots.add(rs.getString("SLOT"));
            }
        }
        return timeSlots;
    }

    // Create a basic booking (no washer/dryer loads)
    public boolean createBooking(String email, Date date, String time) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO BOOKINGS (EMAIL, BOOKING_DATE, BOOKING_TIME) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setDate(2, date);
            stmt.setString(3, time);
            return stmt.executeUpdate() > 0;
        }
    }

    // Create a booking with washer/dryer loads and apply discount if applicable
    public boolean createBooking(String email, Date date, String time, int washerLoads, int dryerLoads) throws SQLException {
        double washerCost = washerLoads * 5.0;
        double dryerCost = dryerLoads * 5.0;
        int totalLoads = washerLoads + dryerLoads;
        double discount = (totalLoads >= 5) ? 0.10 : 0.0;
        double subtotal = washerCost + dryerCost;
        double finalCost = subtotal - (subtotal * discount);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO BOOKINGS (EMAIL, BOOKING_DATE, BOOKING_TIME, WASHER_LOADS, DRYER_LOADS, TOTAL_COST) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setDate(2, date);
            stmt.setString(3, time);
            stmt.setInt(4, washerLoads);
            stmt.setInt(5, dryerLoads);
            stmt.setDouble(6, finalCost);
            return stmt.executeUpdate() > 0;
        }
    }

    // Retrieve bookings for a specific user by email
    public List<Map<String, String>> getBookingsByEmail(String email) throws SQLException {
        List<Map<String, String>> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT ID, BOOKING_DATE, BOOKING_TIME, WASHER_LOADS, DRYER_LOADS, TOTAL_COST FROM BOOKINGS WHERE EMAIL = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, String> booking = new HashMap<>();
                booking.put("id", rs.getString("ID"));
                booking.put("date", rs.getDate("BOOKING_DATE").toString());
                booking.put("time", rs.getString("BOOKING_TIME"));
                booking.put("washer", rs.getString("WASHER_LOADS"));
                booking.put("dryer", rs.getString("DRYER_LOADS"));
                booking.put("cost", rs.getString("TOTAL_COST"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    // Delete a booking using all details (email, date, time, loads)
    public void deleteBooking(String email, String date, String slot, int washerLoads, int dryerLoads) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM BOOKINGS WHERE EMAIL = ? AND BOOKING_DATE = ? AND BOOKING_TIME = ? AND WASHER_LOADS = ? AND DRYER_LOADS = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, date);
            stmt.setString(3, slot);
            stmt.setInt(4, washerLoads);
            stmt.setInt(5, dryerLoads);
            stmt.executeUpdate();
        }
    }

    // Update booking details without washer/dryer loads
    public void updateBooking(String id, String email, String date, String time) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE BOOKINGS SET BOOKING_DATE = ?, BOOKING_TIME = ? WHERE ID = ? AND EMAIL = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setInt(3, Integer.parseInt(id));
            stmt.setString(4, email);
            stmt.executeUpdate();
        }
    }

    // Update booking details including washer/dryer loads and recalculate cost
    public void updateBooking(String id, String email, String date, String time, int washerLoads, int dryerLoads) throws SQLException {
        double washerCost = washerLoads * 5.0;
        double dryerCost = dryerLoads * 5.0;
        int totalLoads = washerLoads + dryerLoads;
        double discount = (totalLoads >= 5) ? 0.10 : 0.0;
        double subtotal = washerCost + dryerCost;
        double finalCost = subtotal - (subtotal * discount);

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE BOOKINGS SET BOOKING_DATE = ?, BOOKING_TIME = ?, WASHER_LOADS = ?, DRYER_LOADS = ?, TOTAL_COST = ? WHERE ID = ? AND EMAIL = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setInt(3, washerLoads);
            stmt.setInt(4, dryerLoads);
            stmt.setDouble(5, finalCost);
            stmt.setInt(6, Integer.parseInt(id));
            stmt.setString(7, email);
            stmt.executeUpdate();
        }
    }

    // Delete a booking using its ID and user's email
    public void deleteBookingById(String id, String email) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM BOOKINGS WHERE ID = ? AND EMAIL = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, email);
            stmt.executeUpdate();
        }
    }
}