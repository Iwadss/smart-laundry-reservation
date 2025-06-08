/**
 * Document   : ViewAllBookingsServlet.java
 * Author     : muhammadifwad
 * Description: Handles admin access to view all laundry bookings.
 */
package controller.admin;

import ejb.bean.AdminBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ViewAllBookingsServlet", urlPatterns = {"/ViewAllBookingsServlet"})
public class ViewAllBookingsServlet extends HttpServlet {

    // Inject AdminBean to handle admin-related operations
    @EJB
    private AdminBean adminBean;

    // Handles GET request to display all bookings to admin
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if session is valid and user is admin
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("user"))) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        try {
            // Retrieve all bookings from the database
            List<Map<String, String>> bookings = adminBean.getAllBookings();
            request.setAttribute("bookings", bookings);
        } catch (Exception e) {
            // Handle exception and display error message
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        // Forward to admin bookings list page
        request.getRequestDispatcher("bookings-list-admin.jsp").forward(request, response);
    }
}