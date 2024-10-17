package com.pluralsight;

import java.io.*;
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
        // This method should load transactions from a file with the given file name.
        // If the file does not exist, it should be created.
        // The transactions should be stored in the `transactions` ArrayList.
        // Each line of the file represents a single transaction in the following format:
        // <date>|<time>|<description>|<vendor>|<amount>
        // For example: 2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        // After reading all the transactions, the file should be closed.
        // If any errors occur, an appropriate error message should be displayed.
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created" + file.getName());
                }

            } catch (Exception e) {
                System.out.println("You need to add a file");
            }
            return;
        }
        String line;
        try {


            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");

                double amount = Double.parseDouble(parts[4]);


                String vendor = parts[3];
                String description = parts[2];
                LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                transactions.add(transaction);


            }
        } catch (Exception e) {
            System.err.println("Please be careful there is a mashed potato");
        }
    }

    private static void addDeposit(Scanner scanner) {
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a deposit.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount should be a positive number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new deposit should be added to the `transactions` ArrayList.

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
                System.out.println("Invalid time format. Please try again");

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
                System.out.println("Invalid input. Please enter a value bigger than zero.");
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            String line = String.format("%s|%s|%s|%s|%s|\n", dateOfDeposit, timeOfDeposit, description, vendor, amount);
            bw.write(line);
            System.out.println("Don`t worry, all potatoes are in the safe place.");
        } catch (Exception e) {
            System.out.println("Please be careful! Brandon will eat your potatoes.");
        }

    }

    private static void addPayment(Scanner scanner) {
        // This method should prompt the user to enter the date, time, description, vendor, and amount of a payment.
        // The user should enter the date and time in the following format: yyyy-MM-dd HH:mm:ss
        // The amount received should be a positive number than transformed to a negative number.
        // After validating the input, a new `Transaction` object should be created with the entered values.
        // The new payment should be added to the `transactions` ArrayList.
        System.out.println("Please enter following details:");

        String dateOfPayment;
        while (true) {
            System.out.println("Please enter the date as yyyy-MM-dd.");
            dateOfPayment = scanner.nextLine();
            try {
                LocalDate.parse(dateOfPayment, DATE_FORMATTER);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }

            String timeOfPayment;
            while (true) {
                System.out.print("Enter the time as HH:mm:ss: ");
                timeOfPayment = scanner.nextLine();
                try {
                    LocalTime.parse(timeOfPayment, TIME_FORMATTER);
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid time format. Please try again");

                }
            }


            System.out.print("Enter the time as HH:mm:ss: ");
            dateOfPayment = scanner.nextLine();


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
                    System.out.println("Invalid input. Please enter a value bigger than zero.");
                }
            }
            try (BufferedWriter bwr = new BufferedWriter(new FileWriter("transactions.csv", true))) {
                String line = String.format("%s|%s|%s|%s|%s|\n", timeOfPayment, timeOfPayment, description, vendor, amount);
                bwr.write(line);
                System.out.println("Don`t worry, all potatoes are in the safe place.");
            } catch (Exception e) {
                System.out.println("Please be careful! The transactions not added.");
            }
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
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    private static void displayLedger() {
        // This method should display a table of all transactions in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
        System.out.println("Date, Time, Description, Vendor, Amount");

        for (Transaction transaction : transactions) {
            //String date = transaction.getDate().format(DATE_FORMATTER).trim();
            //String time = transaction.getTime().format(TIME_FORMATTER).trim();
            //String description = transaction.getDescription().trim();
            //String vendor = transaction.getVendor().trim();
            //String amount = String.format("%.2f", transaction.getAmount());

            //System.out.printf("%s|%s|%s|%s|%s \n", date, time, description, vendor, amount);
            System.out.println(transaction.toString());

        }
    }

    private static void displayDeposits() {
        // This method should display a table of all deposits in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
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

            System.out.printf("%s | %s | %s | %s | %s |\n", date, time, description, vendor, amount);
        }
    }

    private static void displayPayments() {
        // This method should display a table of all payments in the `transactions` ArrayList.
        // The table should have columns for date, time, description, vendor, and amount.
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

            System.out.printf("%s|%s|%s|%s|%s|\n", date, time, description, vendor, amount);

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
                    // Generate a report for all transactions within the current month,
                    // including the date, time, description, vendor, and amount for each transaction.
                    monthToDateReport();
                    break;
                case "2":
                    // Generate a report for all transactions within the previous month,
                    // including the date, time, description, vendor, and amount for each transaction.
                    previousMonthReport();
                    break;
                case "3":
                    // Generate a report for all transactions within the current year,
                    // including the date, time, description, vendor, and amount for each transaction.
                    yearToDateReport();
                    break;

                case "4":
                    // Generate a report for all transactions within the previous year,
                    // including the date, time, description, vendor, and amount for each transaction.
                    previousYearReport();
                    break;
                case "5":
                    // Prompt the user to enter a vendor name, then generate a report for all transactions
                    // with that vendor, including the date, time, description, vendor, and amount for each transaction.
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
        // This method filters the transactions by date and prints a report to the console.
        // It takes two parameters: startDate and endDate, which represent the range of dates to filter by.
        // The method loops through the transactions list and checks each transaction's date against the date range.
        // Transactions that fall within the date range are printed to the console.
        // If no transactions fall within the date range, the method prints a message indicating that there are no results.


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


                System.out.printf("%s|%s|%s|%s|%s|\n", date, time, description, vendor, amount);

            }
        }
        if(!found){
            System.out.println("No transactions found!");
        }

    }


        private static void filterTransactionsByVendor (String vendor) {
            // This method filters the transactions by vendor and prints a report to the console.
            // It takes one parameter: vendor, which represents the name of the vendor to filter by.
            // The method loops through the transactions list and checks each transaction's vendor name against the specified vendor name.
            // Transactions with a matching vendor name are printed to the console.
            // If no transactions match the specified vendor name, the method prints a message indicating that there are no results.

            if (transactions.isEmpty()) {
                System.out.println("Unfortunately, there is no transaction for this account.");
                return;
            }
            System.out.println("Transaction filtered by vendor: " + vendor);

            boolean fixed = false;

            for (Transaction transaction : transactions) {

                if (transaction.getVendor().equalsIgnoreCase(vendor)) {

                    String date = transaction.getDate().format(DATE_FORMATTER);
                    String time = transaction.getTime().format(TIME_FORMATTER);
                    String description = transaction.getDescription();
                    String amount = String.format("%.2f", transaction.getAmount());


                    System.out.printf("%s | %s | %s | %s | %s\n", date, time, description, vendor, amount);
                    fixed = true;

                    if (!fixed){
                        System.err.println("No transactions found for vendor: " +vendor);
                    }
                }
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
        LocalDate startingDayOfTheCureentYear = today.withDayOfYear(1);
        LocalDate theLastDayOfThePreviousYear = startingDayOfTheCureentYear.minusDays(1);
        LocalDate theFirstDayOfThePreviousYear = theLastDayOfThePreviousYear.withDayOfYear(1);

        filterTransactionsByDate(theFirstDayOfThePreviousYear, theLastDayOfThePreviousYear);
    }

}

