/**
 * Document   : DeleteBookingServlet.java
 * Author     : muhammadifwad
 * Description: Handles deletion of a booking record by admin.
 */
package controller.user;

import ejb.bean.BookingBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DeleteBookingServlet", urlPatterns = {"/DeleteBookingServlet"})
public class DeleteBookingServlet extends HttpServlet {

    // Inject the BookingBean EJB to perform booking operations
    @EJB
    private BookingBean bookingBean;

    // Handles POST request to delete a booking entry
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract booking details from request parameters
        String email = request.getParameter("email");
        String date = request.getParameter("date");
        String slot = request.getParameter("slot");
        int washer = Integer.parseInt(request.getParameter("washer"));
        int dryer = Integer.parseInt(request.getParameter("dryer"));

        try {
            // Attempt to delete the booking from database
            bookingBean.deleteBooking(email, date, slot, washer, dryer);
        } catch (Exception e) {
            e.printStackTrace(); // Log exception for debugging
        }

        // Redirect to view all bookings after deletion
        response.sendRedirect("ViewAllBookingsServlet");
    }
}