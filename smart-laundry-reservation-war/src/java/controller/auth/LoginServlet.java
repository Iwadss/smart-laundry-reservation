/**
 * Document   : LoginServlet.java
 * Author     : muhammadifwad
 * Description: Handles user authentication and redirects based on role.
 */
package controller.auth;

import ejb.bean.AuthBean;
import java.io.IOException;
import java.sql.ResultSet;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    // Inject EJB to handle authentication logic
    @EJB
    private AuthBean authBean;

    // Handles POST request for user login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Validate credentials using EJB
            ResultSet rs = authBean.loginUser(email, password);
            if (rs.next()) {
                // Extract user info
                String name = rs.getString("NAME");
                String role = rs.getString("ROLE");

                // Store user info in session
                HttpSession session = request.getSession();
                session.setAttribute("user", role);
                session.setAttribute("email", email);
                session.setAttribute("name", name);

                // Redirect based on role
                response.sendRedirect(role.equals("admin") ? "dashboard-admin.jsp" : "dashboard-user.jsp");

            } else {
                // Invalid login
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("login-form.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Handle unexpected error
            e.printStackTrace();
            request.setAttribute("error", "Login failed: " + e.getMessage());
            request.getRequestDispatcher("login-form.jsp").forward(request, response);
        }
    }
}