# 🧺 Smart Laundry Reservation System – Database Setup

This guide provides step-by-step instructions for installing JavaDB (Apache Derby), creating the `laundrydb` database, and configuring your Java EE application for seamless database connectivity.

---

## 📦 Prerequisites

- **Java SE Development Kit (JDK)** – Version 8 or above  
- **Apache NetBeans** (Recommended for Java EE development)  
- **Apache Derby (JavaDB)** – Usually included with NetBeans  
- JDBC Driver: `org.apache.derby.jdbc.ClientDriver`

---

## ⚙️ Step 1: Install and Start JavaDB (Derby)

### If using NetBeans:

1. Go to **Services > Databases**.
2. Right-click on **JavaDB** → **Start Server**.
3. Right-click on **JavaDB** → **Create Database**:
   - **Database Name:** `laundrydb`
   - **User Name:** `ifwad`
   - **Password:** `ifwad123`

---

## 🛠️ Step 2: Create Tables in `laundrydb`

Open the SQL command window on the `laundrydb` connection and run the following SQL commands:

```sql
-- USERS TABLE
CREATE TABLE USERS (
    ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    NAME VARCHAR(100) NOT NULL,
    EMAIL VARCHAR(100) UNIQUE NOT NULL,
    PASSWORD VARCHAR(100) NOT NULL,
    ROLE VARCHAR(20) NOT NULL
);

-- TIMESLOTS TABLE
CREATE TABLE TIMESLOTS (
    ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    SLOT VARCHAR(50) NOT NULL UNIQUE
);

-- BOOKINGS TABLE
CREATE TABLE BOOKINGS (
    ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    EMAIL VARCHAR(100) NOT NULL,
    BOOKING_DATE DATE NOT NULL,
    BOOKING_TIME VARCHAR(50) NOT NULL,
    WASHER_LOADS INT DEFAULT 0,
    DRYER_LOADS INT DEFAULT 0,
    TOTAL_COST DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (EMAIL) REFERENCES USERS(EMAIL)
);
```

---

## 🧩 Step 3: Configure Java Connection

Ensure your Java EE project includes the following class to manage database connectivity:

```java
package ejb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:derby://localhost:1527/laundrydb";
    private static final String USER = "ifwad";
    private static final String PASSWORD = "ifwad123";

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```

---

## ✅ Verification

You can test the DB connection like this:

```java
Connection conn = DBConnection.getConnection();
if (conn != null) {
    System.out.println("Connected to laundrydb successfully.");
}
```

---

## 📂 Project Structure Sample

```
smart_laundry_reservation/
│
├── smart_laundry_reservation-ejb/
│   └── Source Packages/
│       ├── ejb.bean/
│       │   ├── AdminBean.java
│       │   ├── AuthBean.java
│       │   └── BookingBean.java
│       └── ejb.db/
│           └── DBConnection.java
│
├── smart_laundry_reservation-war/
│   ├── Web Pages/
│   │   ├── booking-create-user.jsp
│   │   ├── booking-edit-user.jsp
│   │   ├── bookings-list-admin.jsp
│   │   ├── bookings-list-user.jsp
│   │   ├── dashboard-admin.jsp
│   │   ├── dashboard-user.jsp
│   │   ├── footer.jsp
│   │   ├── header.jsp
│   │   ├── landing.jsp
│   │   ├── login-form.jsp
│   │   ├── register-form.jsp
│   │   ├── time-slots-admin.jsp
│   │   └── users-list-admin.jsp
│   └── Source Packages/
│       ├── controller.admin/
│       │   ├── AdminServlet.java
│       │   ├── DeleteBookingServlet.java
│       │   ├── DeleteUserServlet.java
│       │   ├── ManageTimeSlotsServlet.java
│       │   ├── ViewAllBookingsServlet.java
│       │   └── ViewAllUsersServlet.java
│       ├── controller.auth/
│       │   ├── LoginServlet.java
│       │   ├── LogoutServlet.java
│       │   └── RegisterServlet.java
│       └── controller.user/
│           ├── BookingServlet.java
│           ├── DeleteBookingServlet.java
│           ├── EditBookingServlet.java
│           └── ViewBookingServlet.java
```

---

## 👤 Admin Credentials (for testing)

| Email            | Password   | Role   |
|------------------|------------|--------|
| admin@example.com | admin123   | admin  |

---

## 📞 Support

This project is intended for academic purposes only.  
For questions or feedback, please contact the project author directly via [GitHub](https://github.com/Iwadss).

---

## © License

2025 Muhammad Ifwad.  
This project is intended for academic use only and is not licensed for public distribution or commercial use.
