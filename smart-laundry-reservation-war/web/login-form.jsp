<%-- 
    Document   : login
    Author     : muhammadifwad
    Description: User login page for UniWash - Smart Laundry Slot Reservation System
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>UniWash Login</title>

    <!-- Responsive layout and page styling -->
    <style>
        /* General page setup */
        html, body {
            height: 100%;
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #8e2de2, #4a00e0);
            color: #fff;
            display: flex;
            flex-direction: column;
        }

        /* Ensures the main content fills the space and centers login box */
        .main-content {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        /* Login form container */
        .login-container {
            background: white;
            color: #333;
            padding: 40px;
            border-radius: 12px;
            width: 300px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        /* Form title */
        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        /* Form layout */
        form {
            display: flex;
            flex-direction: column;
        }

        /* Input field styling */
        input[type="email"],
        input[type="password"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        /* Submit button */
        button {
            padding: 10px;
            background-color: #6a11cb;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #2575fc;
        }

        /* Error message styling */
        .error {
            color: red;
            font-size: 0.9em;
            text-align: center;
            margin-bottom: 10px;
        }

        /* Register link styling */
        .link {
            text-align: center;
            margin-top: 15px;
        }

        .link a {
            color: #6a11cb;
            text-decoration: none;
        }

        .link a:hover {
            text-decoration: underline;
        }

        /* Fallback footer style (if not using footer.jsp) */
        footer {
            background-color: #f0f0f0;
            text-align: center;
            padding: 20px;
            font-size: 0.9em;
            color: #555;
        }
    </style>
</head>
<body>

    <%-- Main login form content --%>
    <div class="main-content">
        <div class="login-container">
            <h2>UniWash Login</h2>

            <%-- Display error message from server if login fails --%>
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <div class="error"><%= error %></div>
            <% } %>

            <%-- Login form submits to LoginServlet --%>
            <form method="post" action="LoginServlet">
                <input type="email" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Password" required />
                <button type="submit">Login</button>
            </form>

            <%-- Link to registration page if user has no account --%>
            <div class="link">
                Don't have an account? <a href="register-form.jsp">Register here</a>
            </div>
        </div>
    </div>

    <%-- Include common footer section --%>
    <jsp:include page="footer.jsp" />

</body>
</html>