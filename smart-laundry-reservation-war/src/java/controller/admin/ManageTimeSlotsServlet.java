/**
 * Document   : ManageTimeSlotsServlet.java
 * Author     : muhammadifwad
 * Description: Handles admin operations for adding and deleting laundry time slots.
 */
package controller.admin;

import ejb.bean.AdminBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageTimeSlotsServlet", urlPatterns = {"/ManageTimeSlotsServlet"})
public class ManageTimeSlotsServlet extends HttpServlet {

    // Inject AdminBean to perform business logic related to time slots
    @EJB
    private AdminBean adminBean;

    // Handle POST request to add or delete a time slot
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check for valid session and admin privileges
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("user"))) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        // Get parameters for adding or deleting a slot
        String newSlot = request.getParameter("newSlot");
        String deleteSlot = request.getParameter("deleteSlot");

        try {
            // Add new time slot if provided
            if (newSlot != null && !newSlot.trim().isEmpty()) {
                adminBean.addTimeSlot(newSlot.trim());
            }

            // Delete time slot if specified
            if (deleteSlot != null && !deleteSlot.trim().isEmpty()) {
                adminBean.deleteTimeSlot(deleteSlot.trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        // Reload the page to reflect updates
        doGet(request, response);
    }

    // Handle GET request to display current time slots
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validate admin session
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("user"))) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        try {
            // Retrieve all available time slots
            List<String> timeSlots = adminBean.getTimeSlots();
            request.setAttribute("timeSlots", timeSlots);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to load time slots: " + e.getMessage());
        }

        // Forward to JSP to display time slot management UI
        request.getRequestDispatcher("time-slots-admin.jsp").forward(request, response);
    }
}