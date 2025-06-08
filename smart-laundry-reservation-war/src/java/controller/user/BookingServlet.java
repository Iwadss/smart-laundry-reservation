/**
 * Document   : BookingServlet.java
 * Author     : muhammadifwad
 * Description: Handles the creation of new laundry bookings for authenticated users.
 */
package controller.user;

import ejb.bean.BookingBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "BookingServlet", urlPatterns = {"/BookingServlet"})
public class BookingServlet extends HttpServlet {

    // Inject the BookingBean EJB for business logic
    @EJB
    private BookingBean bookingBean;

    // Handle GET request to show booking form with available time slots
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        // Redirect to login if user is not authenticated
        if (session == null || !"user".equals(session.getAttribute("user"))) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        try {
            // Load available time slots and set them in request scope
            List<String> timeSlots = bookingBean.getAllTimeSlots();
            request.setAttribute("timeSlots", timeSlots);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load time slots.");
        }

        // Forward to booking form page
        request.getRequestDispatcher("booking-create-user.jsp").forward(request, response);
    }

    // Handle POST request to create a new booking
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        // Redirect to login if user is not authenticated
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        // Retrieve form inputs
        String dateStr = request.getParameter("date");
        String timeSlot = request.getParameter("time");
        String washerStr = request.getParameter("washer");
        String dryerStr = request.getParameter("dryer");
        String email = (String) session.getAttribute("email");

        // Validate required fields
        if (dateStr == null || timeSlot == null || dateStr.isEmpty() || timeSlot.isEmpty() || washerStr == null || dryerStr == null) {
            request.setAttribute("error", "All fields are required.");
            doGet(request, response);
            return;
        }

        try {
            // Parse form data
            int washer = Integer.parseInt(washerStr);
            int dryer = Integer.parseInt(dryerStr);
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            Date sqlDate = new Date(utilDate.getTime());

            // Attempt to create booking
            boolean success = bookingBean.createBooking(email, sqlDate, timeSlot, washer, dryer);
            if (success) {
                response.sendRedirect("ViewBookingServlet"); // Redirect on success
            } else {
                request.setAttribute("error", "Booking failed.");
                doGet(request, response); // Reload form on failure
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            doGet(request, response); // Show error and reload form
        }
    }
}