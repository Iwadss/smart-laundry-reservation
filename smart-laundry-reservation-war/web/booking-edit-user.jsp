<%-- 
    Document   : booking-edit-user.jsp
    Author     : muhammadifwad
    Description: Allows user to edit their booking details (date, time, washer, dryer)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%-- Session check: ensure only authenticated users with role 'user' can access this page --%>
<%
    if (session == null || session.getAttribute("user") == null || !"user".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    // Retrieve booking and time slot data passed from servlet
    Map<String, String> booking = (Map<String, String>) request.getAttribute("booking");
    List<String> timeSlots = (List<String>) request.getAttribute("timeSlots");

    // If booking data is not available, redirect back to the bookings list
    if (booking == null) {
        response.sendRedirect("ViewBookingServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Booking</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #6a11cb, #2575fc);
            padding: 60px;
            margin: 0;
            color: #333;
        }

        .container {
            background: #fff;
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
            max-width: 520px;
            margin: auto;
        }

        h2 {
            text-align: center;
            color: #222;
            margin-bottom: 30px;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 24px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        label {
            font-weight: 600;
            margin-bottom: 8px;
        }

        input[type="date"],
        input[type="number"],
        select {
            padding: 10px 14px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
            background-color: #fff;
            color: #333;
        }

        input:focus,
        select:focus {
            border-color: #2575fc;
            outline: none;
        }

        button {
            background-color: #2575fc;
            color: white;
            font-weight: bold;
            border: none;
            padding: 14px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #1a5adf;
        }

        .back-link {
            text-align: center;
            margin-top: 20px;
        }

        .back-link a {
            color: #2575fc;
            text-decoration: none;
            font-weight: bold;
            font-size: 15px;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
    <body>

    <div class="container">
        <h2>Edit Booking</h2>

        <%-- Booking edit form: pre-filled with selected booking data --%>
        <form method="post" action="ViewBookingServlet">
            <input type="hidden" name="action" value="save" />
            <input type="hidden" name="id" value="<%= booking.get("id") %>" />

            <div class="form-group">
                <label for="date">Booking Date</label>
                <input type="date" name="date" id="date" value="<%= booking.get("date") %>" required />
            </div>

            <div class="form-group">
                <label for="time">Time Slot</label>
                <select name="time" id="time" required>
                    <option value="">-- Select Time --</option>
                    <% if (timeSlots != null) {
                        for (String slot : timeSlots) {
                            String selected = slot.equals(booking.get("time")) ? "selected" : "";
                    %>
                    <option value="<%= slot %>" <%= selected %>><%= slot %></option>
                    <% }} %>
                </select>
            </div>

            <div class="form-group">
                <label for="washer">Washer Loads</label>
                <input type="number" name="washer" id="washer" value="<%= booking.get("washer") %>" min="0" required />
            </div>

            <div class="form-group">
                <label for="dryer">Dryer Loads</label>
                <input type="number" name="dryer" id="dryer" value="<%= booking.get("dryer") %>" min="0" required />
            </div>

            <button type="submit">Save Changes</button>
        </form>

        <%-- Back navigation link --%>
        <div class="back-link">
            <a href="ViewBookingServlet">← Back to My Bookings</a>
        </div>
    </div>

</body>
</html>