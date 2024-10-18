package com.pluralsight;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class FinancialTracker {

    private static final ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private static final String FILE_NAME = "transactions.csv";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D":
                    addDeposit(scanner);
                    break;
                case "P":
                    addPayment(scanner);
                    break;
                case "L":
                    ledgerMenu(scanner);
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

        scanner.close();
    }

    public static void loadTransactions(String fileName) {

        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created" + file.getName());
                }

            } catch (Exception e) {
                System.err.println("You need to add a file");
            }
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");


                LocalDate date = LocalDate.parse(String.format(parts[0].trim(), DATE_FORMATTER));
                LocalTime time = LocalTime.parse(String.format(parts[1].trim(),TIME_FORMATTER));
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);


                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);
            }
        } catch (Exception e) {
            System.err.println("Please be careful there is a mashed potato");
            e.printStackTrace();
        }
    }

    private static void addDeposit(Scanner scanner) {

        System.out.println("Please enter following details:");

        String dateOfDeposit;
        while (true) {
            System.out.print("Please enter the date as yyyy-MM-dd : ");
            dateOfDeposit = scanner.nextLine();
            try {
                LocalDate.parse(dateOfDeposit, DATE_FORMATTER);
                break; //exit loop if input is valid
            } catch (Exception e) {
                System.err.println("Invalid date format. Please try again.");
            }
        }


        String timeOfDeposit;
        while (true) {
            System.out.print("Enter the time as HH:mm:ss: ");
            timeOfDeposit = scanner.nextLine();
            try {
                LocalTime.parse(timeOfDeposit, TIME_FORMATTER);
                break;
            } catch (Exception e) {
                System.err.println("Invalid time format. Please try again");

            }
        }

        System.out.print("Enter a description: ");
        String description = scanner.nextLine();


        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();


        double amount = 0;
        while (true) {
            System.out.print("Enter the amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    break; // exit loop if valid
                } else {
                    System.out.println("Amount must be positive.");
                }
            } catch (Exception e) {
                System.err.println("Invalid input. Please enter a value bigger than zero.");
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            String line = String.format("%s|%s|%s|%s|%s\n", dateOfDeposit, timeOfDeposit, description, vendor, amount);
            bw.write(line);
            System.out.println("Don`t worry, all potatoes are in the safe place.");
        } catch (Exception e) {
            System.err.println("Please be careful! Brandon will eat your potatoes.");
        }

    }

    private static void addPayment(Scanner scanner) {

        System.out.println("Please enter following details:");

        String dateOfPayment;
        while (true) {
            System.out.println("Please enter the date as yyyy-MM-dd.");
            dateOfPayment = scanner.nextLine();
            try {
                LocalDate.parse(dateOfPayment, DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.err.println("Invalid date format. Please try again.");
            }
        }

            String timeOfPayment;
            while (true) {
                System.out.print("Enter the time as HH:mm:ss: ");
                timeOfPayment = scanner.nextLine();
                try {
                    LocalTime.parse(timeOfPayment, TIME_FORMATTER);
                    break;
                } catch (Exception e) {
                    System.err.println("Invalid time format. Please try again");

                }
            }
            System.out.println("Please enter a description: ");
            String description = scanner.nextLine().trim();

            System.out.println("Please enter a vendor");
            String vendor = scanner.nextLine().trim();

            double amount = 0;
            while (true) {
                System.out.print("Enter the amount: ");
                try {
                    amount = Double.parseDouble(scanner.nextLine());
                    if (amount > 0) {
                        break; // exit loop if valid
                    } else {
                        System.out.println("Amount must be positive.");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid input. Please enter a value bigger than zero.");
                }
            }
            try (BufferedWriter bwr = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                String line = String.format("%s|%s|%s|%s|-%s\n", timeOfPayment, timeOfPayment, description, vendor, amount);
                bwr.write(line);
                System.out.println("Don`t worry, all potatoes are in the safe place.");
            } catch (Exception e) {
                System.err.println("Please be careful! The transactions not added.");
            }
        }




    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) A`ll");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A":
                    displayLedger();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    reportsMenu(scanner);
                    break;
                case "H":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {

        System.out.println("Date, Time, Description, Vendor, Amount");

        for (Transaction transaction : transactions) {

            //System.out.printf("%s|%s|%s|%s|%s \n", date, time, description, vendor, amount);
            System.out.println(transaction.toString());

        }
    }

    private static void displayDeposits() {

        System.out.println("You can check for all deposits.");
        System.out.println("Date | Time | Description | Vendor | Amount");

        for (Transaction transaction : transactions) {
            double amount1 = Math.max(0, transaction.getAmount());

            // Skip the transaction if the amount is less than or equal to zero
            if (amount1 <= 0) {
                continue; // Skip this iteration
            }

            String date = transaction.getDate().format(DATE_FORMATTER).trim();
            String time = transaction.getTime().format(TIME_FORMATTER).trim();
            String description = transaction.getDescription().trim();
            String vendor = transaction.getVendor().trim();
            String amount = String.format("%.2f", amount1);

            System.out.printf("%s | %s | %s | %s | %s\n", date, time, description, vendor, amount);
        }
    }

    private static void displayPayments() {

        System.out.println("You can check for all payments");
        System.out.println("Date | Time | Description | Vendor | Amount");


        for (Transaction transaction : transactions) {
            double amount2 = Math.min(0, transaction.getAmount());

            if (amount2>= 0) {
                continue;
            }

            String date = transaction.getDate().format(DATE_FORMATTER);
            String time = transaction.getTime().format(TIME_FORMATTER);
            String description = transaction.getDescription();
            String vendor = transaction.getVendor();
            String amount = String.format("%.2f", transaction.getAmount());

            System.out.printf("%s|%s|%s|%s|%s\n", date, time, description, vendor, amount);

        }
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":

                    monthToDateReport();
                    break;
                case "2":

                    previousMonthReport();
                    break;
                case "3":

                    yearToDateReport();
                    break;

                case "4":

                    previousYearReport();
                    break;
                case "5":

                    System.out.println("Please enter a vendor name");
                    String vendorName = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendorName);
                    break;

                case "0":
                    running = false;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

    }


    private static void filterTransactionsByDate(LocalDate startDate, LocalDate endDate) {



        // Check if the transactions list is empty
        if (transactions.isEmpty()) {
            System.out.println("Unfortunately, there is no transaction for this account.");
            return;
        } else {
            System.out.println("This is the list of transactions from" + startDate + " to " + endDate);
        }

        boolean found = false;

        // Loop through transactions and check the date
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            // Check if the transaction date is within the specified range
            if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate)) && (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate))) {

                String date = transaction.getDate().format(DATE_FORMATTER);
                String time = transaction.getTime().format(TIME_FORMATTER);
                String description =transaction.getDescription();
                String vendor = transaction.getVendor();
                String amount = String.format("%.2f",transaction.getAmount());
                found = true;


                System.out.printf("%s|%s|%s|%s|%s\n", date, time, description, vendor, amount);

            }
        }
        if(!found){
            System.out.println("No transactions found!");
        }

    }
    private static void filterTransactionsByVendor(String vendor) {

        if (transactions.isEmpty()) {
            System.out.println("Unfortunately, there are no transactions for this account.");
            return;
        }

        System.out.println("Transactions filtered by vendor: " + vendor);

        boolean found = false;

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().trim().equalsIgnoreCase(vendor.trim())) {
                String date = transaction.getDate().format(DATE_FORMATTER);
                String time = transaction.getTime().format(TIME_FORMATTER);
                String description = transaction.getDescription();
                String amount = String.format("%.2f", transaction.getAmount());

                System.out.printf("%s | %s | %s | %s | %s\n", date, time, description, vendor, amount);
                found = true;
            }
        }

        // If no transactions were found, print a message
        if (!found) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }

    private static void monthToDateReport() {
        LocalDate today = LocalDate.now();
        LocalDate startingDayOfTheMonth = today.withDayOfMonth(1);

        filterTransactionsByDate(startingDayOfTheMonth, today);
    }

    private static void previousMonthReport() {
        LocalDate today = LocalDate.now();
        LocalDate startingDayOfTheCurrentMonth = today.withDayOfMonth(1);
        LocalDate theLastDayOfThePreviousMonth = startingDayOfTheCurrentMonth.minusDays(1);
        LocalDate theFirstDayOfPreviousMonth = theLastDayOfThePreviousMonth.withDayOfMonth(1);

        filterTransactionsByDate(startingDayOfTheCurrentMonth, theLastDayOfThePreviousMonth);
    }
    private static void yearToDateReport(){
        LocalDate today = LocalDate.now();
        LocalDate startingDateOfTheYear = today.withDayOfYear(1);

        filterTransactionsByDate(startingDateOfTheYear, today);
    }

    private static void previousYearReport() {
        LocalDate today = LocalDate.now();
        LocalDate startingDayOfTheCurrentYear = today.withDayOfYear(1);
        LocalDate theLastDayOfThePreviousYear = startingDayOfTheCurrentYear.minusDays(1);
        LocalDate theFirstDayOfThePreviousYear = theLastDayOfThePreviousYear.withDayOfYear(1);

        filterTransactionsByDate(theFirstDayOfThePreviousYear, theLastDayOfThePreviousYear);
    }

}
