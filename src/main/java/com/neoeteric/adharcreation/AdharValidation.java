package com.neoeteric.adharcreation;

import java.net.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;


public class AdharValidation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Take Aadhaar and Mobile Number Input
        System.out.print("Enter Aadhaar Number: ");
        long adharnumber = scanner.nextLong();

        System.out.print("Enter Mobile Number: ");
        String mobileNumber = scanner.next();

        //  Step 2: Generate OTP
        int otp = generateOtp();
        System.out.println("OTP has been sent to your mobile number.");

        //  Step 3: Send OTP via Fast2SMS
        sendOtp(mobileNumber, otp);

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

    //  Send OTP via Fast2SMS API
    public static void sendOtp(String mobileNumber, int otp) {
        try {
            String apiKey = ""; // API key
            String message = "Your OTP is: " + otp;

            String urlString = "https://www.fast2sms.com/dev/bulkV2";
            String postData = "authorization=" + apiKey
                    + "&route=q"
                    + "&message=" + message
                    + "&language=english"
                    + "&flash=0"
                    + "&numbers=" + mobileNumber;

            //  Send HTTP Request
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setDoOutput(true);

            //  Write data to request body
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            //  Check Response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println(" OTP sent successfully to " + mobileNumber);
            } else {
                System.out.println(" OTP sending failed. Response Code: " + responseCode);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

