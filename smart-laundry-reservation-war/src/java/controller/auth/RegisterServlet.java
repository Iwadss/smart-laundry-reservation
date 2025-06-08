/**
 * Document   : RegisterServlet.java
 * Author     : muhammadifwad
 * Description: Handles user registration logic using EJB AuthBean.
 */
package controller.auth;

import ejb.bean.AuthBean;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    // Inject AuthBean EJB to handle authentication-related operations
    @EJB
    private AuthBean authBean;

    // Handle POST request for user registration
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters from the request
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input: all fields must be filled
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("register-form.jsp").forward(request, response);
            return;
        }

        try {
            // Check if the email is already registered
            if (authBean.emailExists(email)) {
                request.setAttribute("error", "Email already exists.");
                request.getRequestDispatcher("register-form.jsp").forward(request, response);
                return;
            }

            // Register the new user
            authBean.registerUser(name, email, password);

            // Show success message
            request.setAttribute("success", "Registration successful! You may now log in.");
            request.getRequestDispatcher("register-form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("register-form.jsp").forward(request, response);
        }
    }
}