package com.neoeteric.patient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlJavaConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/appointment"; // Change DB name if needed
        private static final String USER = "root"; // Replace with your MySQL username
        private static final String PASSWORD = "Count02"; // Replace with your MySQL password

        public static Connection getConnection() {
            Connection conn = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connected to MySQL Database!");
            } catch (ClassNotFoundException e) {
                System.out.println("❌ MySQL JDBC Driver not found!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("❌ Connection Failed!");
                e.printStackTrace();
            }
            return conn;
        }

        public static void main(String[] args) {
            getConnection(); // Test the connection
        }

}
