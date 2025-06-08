<%-- 
    Document   : dashboard-user
    Author     : muhammadifwad
    Description: User dashboard for booking and viewing laundry slots
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- Session validation to ensure only logged-in users can access this page --%>
<%
    if (session == null || session.getAttribute("user") == null || !"user".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    String name = (String) session.getAttribute("name");
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard - Laundry Slot Reservation</title>
    <style>
        /* Page styling for layout and buttons */
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(120deg, #89f7fe, #66a6ff);
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 60px;
            margin: 0;
            color: #333;
        }

        .container {
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        h1 {
            margin-bottom: 20px;
        }

        .btn-group {
            margin-top: 30px;
        }

        .btn-group a {
            display: inline-block;
            margin: 10px;
            padding: 12px 24px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
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

    <%-- Main content area displaying welcome message and navigation --%>
    <div class="container">
        <h1>Welcome, <%= name != null ? name : "User" %>!</h1>
        <p>Book your laundry slot or manage your bookings below.</p>

        <div class="btn-group">
            <a href="BookingServlet">Book a Slot</a>
            <a href="ViewBookingServlet">View My Bookings</a>
        </div>

        <div class="logout">
            <a href="LogoutServlet">Logout</a>
        </div>
    </div>

</body>
</html>