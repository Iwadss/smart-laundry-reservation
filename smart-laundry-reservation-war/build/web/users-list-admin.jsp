<%-- 
    Document   : view-all-users.jsp
    Author     : muhammadifwad
    Description: Displays all registered users with the option to delete non-admin accounts (admin-only access).
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    if (session == null || session.getAttribute("user") == null || !"admin".equals(session.getAttribute("user"))) {
        response.sendRedirect("login-form.jsp");
        return;
    }

    List<Map<String, String>> users = (List<Map<String, String>>) request.getAttribute("users");
%>
<!DOCTYPE html>
<html>
<head>
    <title>View All Users</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #a8e063, #fcd34d); /* green + yellow */
            padding: 60px;
            margin: 0;
            color: #333;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            max-width: 900px;
            margin: auto;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        th, td {
            padding: 12px 16px;
            border: 1px solid #ccc;
            text-align: center;
        }

        th {
            background-color: #34d399; /* emerald green header */
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

        .error {
            color: red;
            margin-bottom: 20px;
            text-align: center;
        }

        .back-link {
            text-align: center;
            margin-top: 20px;
        }

        .back-link a {
            color: #065f46;
            text-decoration: none;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>All Registered Users</h2>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } else if (users == null || users.isEmpty()) { %>
            <p style="text-align:center;">No users found.</p>
        <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Map<String, String> user : users) { %>
                        <tr>
                            <td><%= user.get("name") %></td>
                            <td><%= user.get("email") %></td>
                            <td><%= user.get("role") %></td>
                            <td>
                                <form action="DeleteUserServlet" method="post" onsubmit="return confirm('Are you sure you want to delete this user?');">
                                    <input type="hidden" name="email" value="<%= user.get("email") %>">
                                    <button type="submit" class="delete-btn">Delete</button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>

        <div class="back-link">
            <a href="dashboard-admin.jsp">← Back to Dashboard</a>
        </div>
    </div>

</body>
</html>