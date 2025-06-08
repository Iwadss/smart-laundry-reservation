<%-- 
    Document   : bookings-list-user.jsp
    Author     : muhammadifwad
    Description: Displays a logged-in user's laundry bookings with options to update or delete each entry
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%-- Access control: only logged-in users with 'user' role can view this page --%>
<%
    if (session == null || session.getAttribute("user") == null || !"user".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    List<Map<String, String>> bookings = (List<Map<String, String>>) request.getAttribute("bookings");
    List<String> timeSlots = (List<String>) request.getAttribute("timeSlots");
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Bookings</title>
    <style>
        /* Styling for layout, table and buttons */
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #42e695, #3bb2b8);
            padding: 60px;
            margin: 0;
            color: #333;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            max-width: 1000px;
            margin: auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background-color: #009688;
            color: white;
        }

        button {
            border: none;
            padding: 10px 16px;
            border-radius: 6px;
            cursor: pointer;
            min-width: 80px;
        }

        .update-btn {
            background-color: #ffca28;
            color: #333;
        }

        .update-btn:hover {
            background-color: #f4b400;
        }

        .action-btn {
            background-color: #e53935;
            color: white;
        }

        .action-btn:hover {
            background-color: #b71c1c;
        }

        .back-link {
            text-align: center;
            margin-top: 20px;
        }

        .back-link a {
            color: #009688;
            text-decoration: none;
            font-size: 16px;
            font-weight: bold;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>My Laundry Bookings</h2>

        <%-- Check if user has bookings, otherwise show fallback text --%>
        <% if (bookings == null || bookings.isEmpty()) { %>
            <p style="text-align:center;">No bookings found.</p>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Date</th>
                        <th>Time Slot</th>
                        <th>Washer</th>
                        <th>Dryer</th>
                        <th>Total Price (RM)</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        int i = 1;
                        for (Map<String, String> booking : bookings) { 
                    %>
                    <tr>
                        <%-- Form to redirect to EditBookingServlet with selected booking ID --%>
                        <form method="post" action="EditBookingServlet">
                            <input type="hidden" name="id" value="<%= booking.get("id") %>" />
                            <td><%= i++ %></td>
                            <td><%= booking.get("date") %></td>
                            <td><%= booking.get("time") %></td>
                            <td><%= booking.get("washer") %></td>
                            <td><%= booking.get("dryer") %></td>
                            <td>RM <%= booking.get("cost") %></td>
                            <td><button type="submit" class="update-btn">Update</button></td>
                        </form>

                        <%-- Form to delete booking via ViewBookingServlet using hidden booking ID --%>
                        <td>
                            <form method="post" action="ViewBookingServlet" onsubmit="return confirm('Are you sure you want to delete this booking?');">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="id" value="<%= booking.get("id") %>" />
                                <button type="submit" class="action-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>

        <%-- Back to user dashboard link --%>
        <div class="back-link">
            <a href="dashboard-user.jsp">← Back to Dashboard</a>
        </div>
    </div>

</body>
</html>