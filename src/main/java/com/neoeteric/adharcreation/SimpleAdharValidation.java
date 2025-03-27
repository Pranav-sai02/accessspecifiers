package com.neoeteric.adharcreation;

import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;


    public class SimpleAdharValidation {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            // Step 1: Take Aadhaar and Mobile Number Input
            System.out.print("Enter Aadhaar Number: ");
            long adharnumber = scanner.nextLong();

            System.out.print("Enter Mobile Number: ");
            String mobileNumber = scanner.next();

            //  Step 2: Generate OTP
            int otp = generateOtp();
            System.out.println(otp);
            System.out.println("OTP has been sent to your mobile number.");

            //  Step 4: Ask User to Enter OTP
            System.out.print("Enter the OTP received: ");
            int enteredOtp = scanner.nextInt();

            //  Step 5: Validate OTP
            if (enteredOtp == otp) {
                System.out.println(" OTP Verified Successfully! Access Granted.");
            } else {
                System.out.println(" Invalid OTP. Please try again.");
            }

            scanner.close();
        }

        //  Generate a 5-digit OTP
        public static int generateOtp() {
            Random random = new Random();
            return 10000 + random.nextInt(90000); // Generates between 10000 and 99999
        }

}
