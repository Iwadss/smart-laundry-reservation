<%-- 
    Document   : manage-times
    Author     : muhammadifwad
    Description: Admin interface to view, add, and delete available laundry booking time slots.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%
    if (session == null || session.getAttribute("user") == null || !"admin".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    // Sample time slots — replace this with data from database or servlet
    List<String> timeSlots = (List<String>) request.getAttribute("timeSlots");
    if (timeSlots == null) {
        timeSlots = Arrays.asList(
            "08:00 AM - 09:00 AM",
            "10:00 AM - 11:00 AM",
            "12:00 PM - 01:00 PM",
            "02:00 PM - 03:00 PM",
            "04:00 PM - 05:00 PM"
        );
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Time Slots - Admin</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #ff758c, #ff7eb3);
            padding: 60px;
            margin: 0;
            color: #333;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            max-width: 700px;
            margin: auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        form {
            display: flex;
            justify-content: center;
            margin-bottom: 30px;
        }

        input[type="text"] {
            padding: 10px;
            width: 60%;
            margin-right: 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            padding: 10px 20px;
            background-color: #ff4081;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        button:hover {
            background-color: #ff1744;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #ff7eb3;
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
            margin-top: 20px;
        }

        .back-link a {
            color: #ff4081;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Manage Booking Time Slots</h2>

        <form method="post" action="ManageTimeSlotsServlet">
            <input type="text" name="newSlot" placeholder="e.g. 06:00 PM - 07:00 PM" required />
            <button type="submit">Add Slot</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Time Slot</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int index = 1;
                    for (String slot : timeSlots) {
                %>
                    <tr>
                        <td><%= index++ %></td>
                        <td><%= slot %></td>
                        <td>
                            <form method="post" action="ManageTimeSlotsServlet" style="margin:0;">
                                <input type="hidden" name="deleteSlot" value="<%= slot %>"/>
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <div class="back-link">
            <a href="dashboard-admin.jsp">← Back to Dashboard</a>
        </div>
    </div>

</body>
</html>
