package com.neoeteric.adharcreation;

import java.util.*;

public class Adhar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your address: ");
        String address = scanner.nextLine();

        System.out.println("Fingerprint: ");
        String fingerprint = scanner.nextLine();

        System.out.println("Eyes: ");
        String eyes = scanner.nextLine();

        scanner.close();

        // ✅ Generate Aadhaar number after taking inputs
        long aadhaarId = generateAadhaarId();
        System.out.println("Generated Aadhaar ID: " + aadhaarId);
    }

    // ✅ Move the Aadhaar ID generator as a static method inside the same class
    public static long generateAadhaarId() {
        Random random = new Random();
        return 100000000000L + (long) (random.nextDouble() * 900000000000L);
    }
}
