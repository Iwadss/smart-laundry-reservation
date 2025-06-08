/**
 * Document   : DeleteUserServlet.java
 * Author     : muhammadifwad
 * Description: Handles the deletion of a user account by admin via POST request.
 */
package controller.admin;

import ejb.bean.AdminBean;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", urlPatterns = {"/DeleteUserServlet"})
public class DeleteUserServlet extends HttpServlet {

    // Inject AdminBean to perform admin-related operations
    @EJB
    private AdminBean adminBean;

    // Handle POST request to delete a user based on email
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve email parameter from the request
        String email = request.getParameter("email");

        try {
            // Proceed to delete user if email is provided
            if (email != null && !email.isEmpty()) {
                adminBean.deleteUser(email);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log exception
        }

        // Redirect back to the user listing page
        response.sendRedirect("ViewAllUsersServlet");
    }
}