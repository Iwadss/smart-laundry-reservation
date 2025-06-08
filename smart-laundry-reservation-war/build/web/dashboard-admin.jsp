<%-- 
    Document   : dashboard-admin
    Author     : muhammadifwad
    Description: Admin dashboard for managing users, bookings, and time slots
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- Ensure only admin users can access this dashboard --%>
<%
    if (session == null || session.getAttribute("user") == null || !"admin".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    String user = (String) session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Laundry Slot Reservation</title>
    <style>
        /* Basic layout and styling for the admin dashboard */
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #00c6ff, #0072ff);
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 60px;
            margin: 0;
            color: #fff;
        }

        .container {
            background: #fff;
            color: #333;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            max-width: 700px;
            width: 100%;
            text-align: center;
        }

        h1 {
            margin-bottom: 10px;
        }

        p {
            margin-bottom: 30px;
        }

        .btn-group a {
            display: inline-block;
            margin: 10px;
            padding: 14px 28px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: background 0.3s;
        }

        .btn-group a:hover {
            background-color: #0056b3;
        }

        .logout {
            margin-top: 40px;
        }

        .logout a {
            color: red;
            text-decoration: none;
        }

        .logout a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <%-- Welcome message and admin action buttons --%>
    <div class="container">
        <h1>Welcome, <%= user != null ? user : "Admin" %>!</h1>
        <p>Use the buttons below to manage the system:</p>

        <div class="btn-group">
            <a href="ViewAllUsersServlet">👥 View All Users</a>
            <a href="ViewAllBookingsServlet">📋 View All Bookings</a>
            <a href="ManageTimeSlotsServlet">⏰ Manage Time Slots</a>
        </div>

        <div class="logout">
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

</body>
</html>