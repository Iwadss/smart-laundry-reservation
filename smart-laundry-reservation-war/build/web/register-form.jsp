<%-- 
    Document   : register
    Author     : muhammadifwad
    Description: Registration page for UniWash - allows new users to create an account
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - UniWash</title>

    <!-- Page styling -->
    <style>
        html, body {
            height: 100%;
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #ff6a00, #ee0979);
            color: #fff;
            display: flex;
            flex-direction: column;
        }

        .main-content {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .register-container {
            background: white;
            color: #333;
            padding: 40px;
            border-radius: 12px;
            width: 350px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        button {
            padding: 10px;
            background-color: #ee0979;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #ff6a00;
        }

        .link {
            text-align: center;
            margin-top: 15px;
        }

        .link a {
            color: #ee0979;
            text-decoration: none;
        }

        .link a:hover {
            text-decoration: underline;
        }

        .error {
            color: red;
            font-size: 0.9em;
            text-align: center;
            margin-bottom: 10px;
        }

        .success {
            color: green;
            font-size: 0.9em;
            text-align: center;
            margin-bottom: 10px;
        }

        footer {
            margin-top: auto;
        }
    </style>
</head>
<body>

    <%-- Main content wrapper --%>
    <div class="main-content">
        <div class="register-container">
            <h2>Create Account</h2>

            <%-- Error message from servlet --%>
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <div class="error"><%= error %></div>
            <% } %>

            <%-- Success message from servlet --%>
            <% String success = (String) request.getAttribute("success"); %>
            <% if (success != null) { %>
                <div class="success"><%= success %></div>
            <% } %>

            <%-- Registration form --%>
            <form method="post" action="RegisterServlet">
                <input type="text" name="name" placeholder="Full Name" required />
                <input type="email" name="email" placeholder="Email" required />
                <input type="password" name="password" placeholder="Password" required />
                <button type="submit">Register</button>
            </form>

            <%-- Link to login page --%>
            <div class="link">
                Already have an account? <a href="login-form.jsp">Login here</a>
            </div>
        </div>
    </div>

    <%-- Include global footer --%>
    <jsp:include page="footer.jsp" />

</body>
</html>