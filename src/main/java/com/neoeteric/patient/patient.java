package com.neoeteric.patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class patient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Condition: ");
        String condition = scanner.nextLine();

        System.out.print("Enter Disease: ");
        String disease = scanner.nextLine();


        insertPatient(name, age, phone, condition, disease);

        scanner.close();
    }

    public static void insertPatient(String name, int age, String phone, String condition, String disease) {
        Connection conn = SqlJavaConnection.getConnection();
        if (conn == null) {
            System.out.println(" Failed to connect to database.");
            return;
        }

        // Adjusted SQL column names to match your `patients` table in `appointment` database
        String query = "INSERT INTO patients (name, age, phone_number, conditions, disease) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, phone);
            stmt.setString(4, condition);
            stmt.setString(5, disease);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Patient added successfully!");
            } else {
                System.out.println(" Failed to add patient.");
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

