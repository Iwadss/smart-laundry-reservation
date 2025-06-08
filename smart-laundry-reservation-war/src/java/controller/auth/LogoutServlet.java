/**
 * Document   : LogoutServlet.java
 * Author     : muhammadifwad
 * Description: Handles user logout by ending the current session and redirecting to the landing page.
 */
package controller.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

    // Handles GET request to logout the user
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the existing session, if any
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate session to log out user
        }

        // Redirect user to landing page after logout
        response.sendRedirect("landing.jsp");
    }

    // Handles POST logout request (optional support)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Reuse doGet for logout via POST
    }

    @Override
    public String getServletInfo() {
        return "Handles user logout by invalidating session and redirecting.";
    }
}