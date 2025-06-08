/**
 * Document   : ViewBookingServlet.java
 * Author     : muhammadifwad
 * Description: Handles user-side booking operations — view, add, update, and delete.
 */
package controller.user;

import ejb.bean.BookingBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@WebServlet(name = "ViewBookingServlet", urlPatterns = {"/ViewBookingServlet"})
public class ViewBookingServlet extends HttpServlet {

    // Inject BookingBean EJB to handle booking operations
    @EJB
    private BookingBean bookingBean;

    // Handles GET request to display all bookings and time slots for a user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        try {
            String email = (String) session.getAttribute("email");
            List<Map<String, String>> bookings = bookingBean.getBookingsByEmail(email);
            List<String> timeSlots = bookingBean.getAllTimeSlots();
            request.setAttribute("bookings", bookings);
            request.setAttribute("timeSlots", timeSlots);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load your bookings.");
        }

        request.getRequestDispatcher("bookings-list-user.jsp").forward(request, response);
    }

    // Handles POST actions: add, update, save, and delete bookings
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                // Add a new booking
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                int washer = Integer.parseInt(request.getParameter("washer"));
                int dryer = Integer.parseInt(request.getParameter("dryer"));

                bookingBean.createBooking(email, Date.valueOf(date), time, washer, dryer);
                response.sendRedirect("ViewBookingServlet");

            } else if ("update".equals(action)) {
                // Forward to booking edit page
                String id = request.getParameter("id");
                List<Map<String, String>> bookings = bookingBean.getBookingsByEmail(email);
                List<String> timeSlots = bookingBean.getAllTimeSlots();

                for (Map<String, String> booking : bookings) {
                    if (booking.get("id").equals(id)) {
                        request.setAttribute("booking", booking);
                        request.setAttribute("timeSlots", timeSlots);
                        request.getRequestDispatcher("booking-edit-user.jsp").forward(request, response);
                        return;
                    }
                }

                request.setAttribute("error", "Booking not found.");
                request.getRequestDispatcher("bookings-list-user.jsp").forward(request, response);

            } else if ("save".equals(action)) {
                // Save updates to a booking
                String id = request.getParameter("id");
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                int washer = Integer.parseInt(request.getParameter("washer"));
                int dryer = Integer.parseInt(request.getParameter("dryer"));

                bookingBean.updateBooking(id, email, date, time, washer, dryer);
                response.sendRedirect("ViewBookingServlet");

            } else if ("delete".equals(action)) {
                // Delete a booking by ID
                String id = request.getParameter("id");
                bookingBean.deleteBookingById(id, email);
                response.sendRedirect("ViewBookingServlet");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("bookings-list-user.jsp").forward(request, response);
        }
    }
}