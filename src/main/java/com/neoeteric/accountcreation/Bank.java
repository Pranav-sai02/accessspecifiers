package com.neoeteric.accountcreation;
import  java.util.Scanner;

public class Bank {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.println("enter your name: ");
        String customer_name = scanner.nextLine();

        System.out.println("enter your address: ");
        String customer_address = scanner.nextLine();

        System.out.println("enter proof of identity type: ");
        String proof_of_identity = scanner.nextLine();

        System.out.println("enter your identity number: ");
        int identity_number = scanner.nextInt();
        scanner.nextLine();



        scanner.close();
    }



}
