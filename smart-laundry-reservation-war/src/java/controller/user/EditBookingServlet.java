/**
 * Document   : EditBookingServlet.java
 * Author     : muhammadifwad
 * Description: Handles booking update and edit form display for authenticated users.
 */
package controller.user;

import ejb.bean.BookingBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "EditBookingServlet", urlPatterns = {"/EditBookingServlet"})
public class EditBookingServlet extends HttpServlet {

    // Inject the EJB responsible for booking operations
    @EJB
    private BookingBean bookingBean;

    // Handles POST requests for saving updates or displaying the edit form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String id = request.getParameter("id");
        String email = (session != null) ? (String) session.getAttribute("email") : null;

        // Redirect to login if user session is invalid or missing parameters
        if (id == null || email == null) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("save".equals(action)) {
                // Handle save action: update booking details
                String date = request.getParameter("date");
                String time = request.getParameter("time");
                int washer = Integer.parseInt(request.getParameter("washer"));
                int dryer = Integer.parseInt(request.getParameter("dryer"));

                bookingBean.updateBooking(id, email, date, time, washer, dryer);
                response.sendRedirect("ViewBookingServlet");
                return;
            }

            // Load bookings and time slots to populate the edit form
            List<Map<String, String>> bookings = bookingBean.getBookingsByEmail(email);
            List<String> timeSlots = bookingBean.getAllTimeSlots();

            // Find the matching booking by ID and forward to edit form
            for (Map<String, String> booking : bookings) {
                if (booking.get("id").equals(id)) {
                    request.setAttribute("booking", booking);
                    request.setAttribute("timeSlots", timeSlots);
                    request.getRequestDispatcher("booking-edit-user.jsp").forward(request, response);
                    return;
                }
            }

            // Show error if booking was not found
            request.setAttribute("error", "Booking not found.");
            request.getRequestDispatcher("ViewBookingServlet").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("ViewBookingServlet").forward(request, response);
        }
    }
}