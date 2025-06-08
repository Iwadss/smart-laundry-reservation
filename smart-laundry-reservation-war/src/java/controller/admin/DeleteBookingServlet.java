/**
 * Document   : DeleteBookingServlet.java
 * Author     : muhammadifwad
 * Description: Handles deletion of a booking by an admin via POST request.
 */
package controller.admin;

import ejb.bean.BookingBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DeleteBookingServlet", urlPatterns = {"/DeleteBookingServlet"})
public class DeleteBookingServlet extends HttpServlet {

    // Inject BookingBean to perform booking operations
    @EJB
    private BookingBean bookingBean;

    // Handle POST request to delete a booking
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve booking parameters from request
        String email = request.getParameter("email");
        String date = request.getParameter("date");
        String slot = request.getParameter("slot");
        String washerStr = request.getParameter("washer");
        String dryerStr = request.getParameter("dryer");

        try {
            // Parse washer and dryer load counts
            int washer = Integer.parseInt(washerStr);
            int dryer = Integer.parseInt(dryerStr);

            // Delete the specified booking
            bookingBean.deleteBooking(email, date, slot, washer, dryer);

        } catch (Exception e) {
            e.printStackTrace(); // Log error if any
        }

        // Redirect back to booking list after deletion
        response.sendRedirect("ViewAllBookingsServlet");
    }
}