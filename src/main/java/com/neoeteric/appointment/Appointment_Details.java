package com.neoeteric.appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment_Details {

    // Method to insert an appointment
    public static void insertAppointment(String patient_name, int patient_id, int doctor_id, String appointment_time, String details) {
        String insertAppointmentSQL = "INSERT INTO appointment_details (patient_id, doctor_id, appointment_time) VALUES (?, ?, ?, ?)";


        try (Connection conn = HospitalDbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertAppointmentSQL)){
            stmt.setInt(1, patient_id);
            stmt.setInt(2, doctor_id);
            stmt.setString(3, appointment_time);
            stmt.setString(4, details);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Appointment added successfully!");
            } else {
                System.out.println(" Failed to add appointment.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all appointments
    public static void getAllAppointments() {
        String query = "SELECT * FROM appointment_details";

        try (Connection conn = HospitalDbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n Appointment Details:");
            while (rs.next()) {
                System.out.println("Appointment name: " + rs.getString("appointment_name"));
                System.out.println("Appointment ID: " + rs.getInt("appointment_id"));
                System.out.println("Patient ID: " + rs.getInt("patient_id"));
                System.out.println("Doctor ID: " + rs.getInt("doctor_id"));
                System.out.println("Time: " + rs.getString("appointment_time"));
                System.out.println("Details: " + rs.getString("details"));
                System.out.println("----------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

