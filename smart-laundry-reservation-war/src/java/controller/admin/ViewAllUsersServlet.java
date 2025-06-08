/**
 * Document   : ViewAllUsersServlet.java
 * Author     : muhammadifwad
 * Description: Retrieves and displays all registered users (admin only).
 */
package controller.admin;

import ejb.bean.AdminBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ViewAllUsersServlet", urlPatterns = {"/ViewAllUsersServlet"})
public class ViewAllUsersServlet extends HttpServlet {

    // Inject EJB for admin-related operations
    @EJB
    private AdminBean adminBean;

    // Handles GET request to load all user accounts for admin viewing
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validate session and ensure user is admin
        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("user"))) {
            response.sendRedirect("login-form.jsp");
            return;
        }

        try {
            // Fetch list of all registered users from the database
            List<Map<String, String>> users = adminBean.getAllUsers();
            request.setAttribute("users", users);
        } catch (Exception e) {
            // Handle and log database access error
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
        }

        // Forward to JSP for displaying user list
        request.getRequestDispatcher("users-list-admin.jsp").forward(request, response);
    }
}