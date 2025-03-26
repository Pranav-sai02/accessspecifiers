package com.neoeteric.appointment;

import java.sql.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // Mapping symptoms to specialist categories
    private static final Map<String, String> symptomToSpecialist = new HashMap<>();

    static {
        symptomToSpecialist.put("cold", "General");
        symptomToSpecialist.put("fever", "General");
        symptomToSpecialist.put("dizziness", "General");
        symptomToSpecialist.put("body pain", "General");
        symptomToSpecialist.put("weakness", "General");
        symptomToSpecialist.put("loss of appetite", "General");
        symptomToSpecialist.put("heart attack", "Cardio");
        symptomToSpecialist.put("heartbeat feels irregular", "Cardio");
        symptomToSpecialist.put("short of breath", "Cardio");
        symptomToSpecialist.put("numbness", "Neuro");
        symptomToSpecialist.put("loss of memory", "Neuro");
        symptomToSpecialist.put("seizures", "Neuro");
        symptomToSpecialist.put("blurry vision", "Ophthalmologist");
        symptomToSpecialist.put("eye pain", "Ophthalmologist");
        symptomToSpecialist.put("dry eyes", "Ophthalmologist");
        symptomToSpecialist.put("red eyes", "Ophthalmologist");
        symptomToSpecialist.put("depression", "Psychologist");
        symptomToSpecialist.put("unmotivated", "Psychologist");
        symptomToSpecialist.put("sad", "Psychologist");
        symptomToSpecialist.put("mood swings", "Psychologist");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Get Patient Details
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter Symptoms: ");
        String symptoms = scanner.nextLine().toLowerCase();

        scanner.close(); // Close scanner

        try {
            // Step 2: Establish Database Connection
            Connection conn = HospitalDbConnection.getConnection();
            System.out.println("Connected to Database: " + conn.getCatalog());

            // Step 3: Find the Doctor Specialization based on Symptoms
            String specialization = symptomToSpecialist.get(symptoms);

            if (specialization == null) {
                System.out.println("No specialist found for the given symptoms.");
                return;
            }

            // Step 4: Find a Doctor from `doctors` table with the required specialization
            String doctorQuery = "SELECT doctor_id FROM doctors WHERE specialization LIKE ?";
            PreparedStatement doctorStmt = conn.prepareStatement(doctorQuery);
            doctorStmt.setString(1, "%" + specialization + "%"); // Find doctor based on specialization
            ResultSet doctorResult = doctorStmt.executeQuery();

            int doctorId = -1;
            if (doctorResult.next()) {
                doctorId = doctorResult.getInt("doctor_id");
            } else {
                System.out.println("No available doctor for specialization: " + specialization);
                return;
            }

            // Step 5: Insert Patient Details into `patients` Table
            String insertPatientSQL = "INSERT INTO patients (name, age, phone_number, symptoms) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertPatientSQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, phoneNumber);
            pstmt.setString(4, symptoms);
            pstmt.executeUpdate();

            // Get Generated Patient ID
            ResultSet rs = pstmt.getGeneratedKeys();
            int patientId = -1;
            if (rs.next()) {
                patientId = rs.getInt(1);
            }

            // Step 6: Find Next Available Appointment Slot
            LocalTime startTime = LocalTime.of(9, 0); // First appointment at 9 AM
            LocalTime lunchBreakStart = LocalTime.of(13, 0); // Lunch Break 1 PM - 2 PM
            LocalTime endTime = LocalTime.of(18, 0); // Last appointment at 6 PM
            int appointmentDuration = 30; // Appointment duration in minutes

            String lastAppointmentQuery = "SELECT appointed_time FROM appointment_details WHERE doctor_id = ? ORDER BY appointed_time DESC LIMIT 1";
            PreparedStatement lastAppointmentStmt = conn.prepareStatement(lastAppointmentQuery);
            lastAppointmentStmt.setInt(1, doctorId);
            ResultSet lastAppointmentResult = lastAppointmentStmt.executeQuery();

            LocalTime nextAvailableSlot = startTime;
            if (lastAppointmentResult.next()) {
                nextAvailableSlot = lastAppointmentResult.getTime("appointed_time").toLocalTime().plusMinutes(appointmentDuration);

                // Handle lunch break
                if (nextAvailableSlot.equals(lunchBreakStart) || (nextAvailableSlot.isAfter(lunchBreakStart) && nextAvailableSlot.isBefore(lunchBreakStart.plusHours(1)))) {
                    nextAvailableSlot = lunchBreakStart.plusHours(1);
                }

                // Check if appointment time is beyond working hours
                if (nextAvailableSlot.isAfter(endTime)) {
                    System.out.println("No available slots for today. Please try another day.");
                    return;
                }
            }

            // Step 7: Insert Appointment into `appointment_details`
            String insertAppointmentSQL = "INSERT INTO appointment.appointment_details (patient_name, patient_id, doctor_id, symptoms, appointed_time) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement appointmentStmt = conn.prepareStatement(insertAppointmentSQL);
            appointmentStmt.setString(1, name);
            appointmentStmt.setInt(2, patientId);
            appointmentStmt.setInt(3, doctorId);
            appointmentStmt.setString(4, symptoms);
            appointmentStmt.setTime(5, Time.valueOf(nextAvailableSlot));

            appointmentStmt.executeUpdate();

            System.out.println(" Appointment booked successfully!");
            System.out.println(" Patient ID: " + patientId);
            System.out.println(" Doctor ID: " + doctorId);
            System.out.println(" Assigned Specialist: " + specialization);
            System.out.println(" Appointed Time: " + nextAvailableSlot);

            // Close connections
            doctorStmt.close();
            pstmt.close();
            appointmentStmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(" Error: Could not book appointment.");
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }
    }
}
