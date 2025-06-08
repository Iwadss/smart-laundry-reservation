<%-- 
    Document   : bookings-list-admin.jsp
    Author     : muhammadifwad
    Description: Admin page to view and manage all user laundry bookings, with delete option
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>

<%-- Access control: restrict access to logged-in admins only --%>
<%
    if (session == null || session.getAttribute("user") == null || !"admin".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    List<Map<String, String>> bookings = (List<Map<String, String>>) request.getAttribute("bookings");
%>

<!DOCTYPE html>
<html>
<head>
    <title>All Bookings - Admin</title>
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
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
            max-width: 1100px;
            margin: auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 25px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background-color: #4f46e5;
            color: white;
        }

        .delete-btn {
            background-color: red;
            color: white;
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-btn:hover {
            background-color: darkred;
        }

        .back-link {
            text-align: center;
            margin-top: 25px;
        }

        .back-link a {
            color: #4f46e5;
            text-decoration: none;
            font-weight: 500;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>All Laundry Bookings</h2>

        <%-- Display message if no bookings found --%>
        <% if (bookings == null || bookings.isEmpty()) { %>
            <p style="text-align:center;">No bookings found.</p>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>User Email</th>
                        <th>Date</th>
                        <th>Time Slot</th>
                        <th>Washer Loads</th>
                        <th>Dryer Loads</th>
                        <th>Total (RM)</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        int i = 1;
                        for (Map<String, String> booking : bookings) {
                    %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><%= booking.get("email") %></td>
                        <td><%= booking.get("date") %></td>
                        <td><%= booking.get("slot") %></td>
                        <td><%= booking.getOrDefault("washer", "0") %></td>
                        <td><%= booking.getOrDefault("dryer", "0") %></td>
                        <td>RM <%= booking.getOrDefault("cost", "0.00") %></td>
                        <td>
                            <%-- Delete booking form, confirmed before submission --%>
                            <form method="post" action="DeleteBookingServlet" onsubmit="return confirm('Delete this booking?');">
                                <input type="hidden" name="email" value="<%= booking.get("email") %>">
                                <input type="hidden" name="date" value="<%= booking.get("date") %>">
                                <input type="hidden" name="slot" value="<%= booking.get("slot") %>">
                                <input type="hidden" name="washer" value="<%= booking.getOrDefault("washer", "0") %>">
                                <input type="hidden" name="dryer" value="<%= booking.getOrDefault("dryer", "0") %>">
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>

        <%-- Back navigation link --%>
        <div class="back-link">
            <a href="dashboard-admin.jsp">← Back to Dashboard</a>
        </div>
    </div>
        
</body>
</html>