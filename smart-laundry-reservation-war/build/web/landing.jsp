<%-- 
    Document   : landing
    Author     : muhammadifwad
    Description: Landing page for UniWash - includes hero, login/register buttons, and about section.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>UniWash - Smart Laundry Slot Reservation</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <style>
        /* General Page Styling */
        html, body {
            height: 100%;
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #e0c3fc, #8ec5fc);
            color: #333;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        .main-content {
            flex: 1;
        }

        /* Hero Section Styling */
        .hero {
            text-align: center;
            padding: 80px 20px 40px;
        }

        .hero h2 {
            font-size: 2.2em;
            margin-bottom: 10px;
            color: #6a11cb;
        }

        .hero p {
            font-size: 1.2em;
        }

        .btn-group {
            margin-top: 30px;
        }

        .btn-group a {
            display: inline-block;
            margin: 10px;
            padding: 14px 28px;
            background-color: #6a11cb;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: background 0.3s, transform 0.2s;
        }

        .btn-group a:hover {
            background-color: #2575fc;
            transform: scale(1.05);
        }

        /* About Section Styling */
        .about {
            background-color: white;
            border-radius: 12px;
            margin: 40px auto;
            padding: 40px 20px;
            max-width: 900px;
            text-align: center;
            box-shadow: 0 10px 20px rgba(0,0,0,0.1);
        }

        .about h3 {
            color: #6a11cb;
            margin-bottom: 20px;
        }

        .about p {
            font-size: 1.1em;
            line-height: 1.6em;
        }
    </style>
</head>
<body>

    <%-- Include shared header with UniWash branding --%>
    <jsp:include page="header.jsp" />

    <%-- Main content section with hero and about details --%>
    <div class="main-content">

        <%-- Hero section introducing UniWash and login/register CTA --%>
        <section class="hero">
            <h2>Book Your Laundry Slot Hassle-Free</h2>
            <p>Fast, efficient, and convenient laundry time booking system.</p>
            <div class="btn-group">
                <a href="login-form.jsp">Login</a>
                <a href="register-form.jsp">Register</a>
            </div>
        </section>

        <%-- About UniWash: brief platform introduction for new users --%>
        <section class="about">
            <h3>About UniWash</h3>
            <p>
                UniWash is a streamlined laundry slot booking platform created with students in mind.
                Say goodbye to long queues and booking confusion. Simply register, pick your slot,
                and enjoy a clean and organized laundry experience.
            </p>
        </section>

    </div>

    <%-- Include shared footer with copyright --%>
    <jsp:include page="footer.jsp" />

</body>
</html>