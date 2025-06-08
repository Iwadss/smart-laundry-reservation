<%-- 
    Document   : booking-create-user.jsp
    Author     : muhammadifwad
    Description: Allows users to book a laundry slot by selecting date, time slot, and machine loads
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- Session validation: redirect if user not logged in or not a 'user' role --%>
<%
    if (session == null || session.getAttribute("user") == null || !"user".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    // Fetch available time slots from request attribute
    List timeSlots = (List) request.getAttribute("timeSlots");
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Book a Laundry Slot</title>
        <style>
            body {
                font-family: 'Segoe UI', sans-serif;
                background: linear-gradient(to right, #fc5c7d, #6a82fb);
                display: flex;
                align-items: center;
                justify-content: center;
                height: 100vh;
                margin: 0;
                color: #fff;
            }
            .booking-container {
                background: white;
                color: #333;
                padding: 40px;
                border-radius: 12px;
                width: 420px;
            }
            h2 {
                text-align: center;
                margin-bottom: 25px;
            }
            form {
                display: flex;
                flex-direction: column;
            }
            label {
                font-weight: bold;
                margin-bottom: 5px;
            }
            input[type="date"], select, input[type="number"] {
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }
            button {
                padding: 10px;
                background-color: #6a82fb;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-weight: bold;
            }
            button:hover {
                background-color: #fc5c7d;
            }
            .link {
                text-align: center;
                margin-top: 20px;
            }
            .link a {
                color: #6a82fb;
                text-decoration: none;
            }
        </style>
    </head>
    <body>

    <div class="booking-container">
        <h2>Book a Laundry Slot</h2>

        <%-- Booking form: user selects date, time, washer and dryer loads --%>
        <form method="post" action="BookingServlet">
            <label>Select Date:</label>
            <input type="date" name="date" required />

            <label>Select Time Slot:</label>
            <select name="time" required>
                <option value="">-- Select Time --</option>
                <% if (timeSlots != null) {
                    for (int i = 0; i < timeSlots.size(); i++) {
                        String slot = (String) timeSlots.get(i); %>
                    <option value="<%= slot %>"><%= slot %></option>
                <% }} %>
            </select>

            <label>Washer Loads:</label>
            <input type="number" name="washer" min="0" required />

            <label>Dryer Loads:</label>
            <input type="number" name="dryer" min="0" required />

            <button type="submit">Book Now</button>
        </form>

        <%-- Link to return to user dashboard --%>
        <div class="link">
            <a href="dashboard-user.jsp">← Back to Dashboard</a>
        </div>
    </div>
    </body>
</html>