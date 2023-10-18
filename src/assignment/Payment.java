/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import static assignment.Main.clearScreen;
import static assignment.Main.logo;
import static assignment.Main.systemPause;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author LYL
 */
public class Payment {

    private final double TAX = 0.06;         //SST 6%
    private double taxAmount;
    private double overallTotal;
    private double discount = 0;
    Sales sales = new Sales();              //use the sales array
    ReadFile file = new ReadFile();
    private static int transactionID = 0;
    private static double amountPaid = 0;

    public double getTaxAmount() {
        return taxAmount;
    }

    //getter/accessor
    public double getOverallTotal() {
        return overallTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    //setter/mutator
    public void setOverallTotal(double overallTotal) {
        this.overallTotal = overallTotal;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int makePayment() {
        try {
            char proceedPayment;
            char yesNo = 'N';
            double totalAmount = 0;
            double total = 0;
            double memberDsct = 0;
            int itemNo = 0;
            char member;

            logo();
            System.out.println("[ CHECK OUT ]");
            System.out.println("-------------------------------------------------------");

            do {
                System.out.print("WOULD YOU LIKE TO PROCEED TO MAKE PAYMENT? (Y=YES, N=NO): ");
                proceedPayment = sales.charValidation();
            } while (proceedPayment != 'Y' && proceedPayment != 'N');

            while (Character.toUpperCase(proceedPayment) != 'N') {
                if (Character.toUpperCase(proceedPayment) == 'Y' && Sales.newOrder == 0) {
                    System.out.println("NO ORDER IS CREATED YET! PLEASE ATLEAST MAKE AN ORDER FIRST, THX!!");
                    break;
                } else {
                        System.out.print("IS HE/SHE A MEMBER? (Y=YES, PRESS ANY KEY IF NOT): ");
                        member = sales.charValidation();

                    if (Character.toUpperCase(member) == 'Y') {
                        System.out.print("ENTER MEMBER ID: ");
                        int compareMemberID = sales.intValidation(0, 0);

                        switch (file.verifyMember(compareMemberID)) {
                            case "Normal":
                                memberDsct = 0.05;
                                break;
                            case "Gold":
                                memberDsct = 0.10;
                                break;
                            case "Premium":
                                memberDsct = 0.15;
                                break;
                            default:
                                memberDsct = 0.0;
                                System.out.println("MEMBER ID IS NOT FOUND!\n");
                        }

                        System.out.printf("THE CUSTOMER IS A [%s] MEMBER, %.0f%% DISCOUNT WILL BE GIVEN. \n",
                                file.verifyMember(compareMemberID), memberDsct * 100);
                    }

                    System.out.println("-------------------------------------------------------");
                    System.out.println("-------------------------------------------------------");
                    System.out.println("CART CONTENTS:");
                    System.out.println("-------------------------------------------------------");
                    for (Stock item : Sales.getCart()) {
                        itemNo++;
                        System.out.printf("PRODUCT NUMBER %s\n", itemNo);
                        System.out.println("-------------------------------------------------------");
                        System.out.printf("PRODUCT NAME: %s\n", item.getStockName());
                        System.out.printf("QUANTITY: %d\n", item.getQty());
                        total = item.getQty() * item.getPrice();
                        System.out.printf("TOTAL COST: RM%.2f\n", total);
                        totalAmount += total; // Calculate the total amount
                        System.out.println("-------------------------------------------------------");
                    }
                    setDiscount(totalAmount * memberDsct);
                    setTaxAmount(totalAmount * TAX);
                    setOverallTotal(totalAmount - getDiscount() + getTaxAmount());
                    System.out.println("-------------------------------------------------------");
                    System.out.printf("TOTAL AMOUNT    : RM%.2f\n", totalAmount);
                    System.out.printf("DISCOUNT APPLIED: %.0f%%\n", memberDsct * 100);
                    System.out.printf("TOTAL DISCOUNT  : RM%.2f\n", getDiscount());
                    System.out.printf("TOTAL TAX       : RM%.2f\n", getTaxAmount());
                    System.out.printf("FINAL TOTAL     : RM%.2f\n", getOverallTotal());
                    System.out.println("-------------------------------------------------------");

                    // Proceed with payment
                    while (Character.toUpperCase(yesNo) == 'N' || Character.toUpperCase(proceedPayment) == 'Y') {
                        do {
                            System.out.print("ENTER THE AMOUND PAID:RM ");
                            amountPaid = sales.doubleValidation();
                        } while (amountPaid == -9999);

                        if (amountPaid < getOverallTotal()) {
                            System.out.println("<<<The amount is not sufficient. Please enter enough amount.>>>");

                            do {
                                    System.out.print("CANCEL PAYMENT? (Y=YES, N=NO): ");
                                yesNo = sales.charValidation();
                            } while (yesNo != 'Y' && yesNo != 'N');

                            if (Character.toUpperCase(yesNo) == 'Y') {
                                System.out.println("-------------------------------------------------------");
                                System.out.println("PAYMENT HAS BEEN CANCELLED");
                                clearScreen();
                                break; // Exit the payment process
                            }
                        } else {
                            System.out.printf("AMOUND PAID          :RM% .2f\n", amountPaid);
                            System.out.printf("CHANGE               :RM% .2f\n", amountPaid - getOverallTotal());

                            updateStockFile("stock.txt");
                            // Store the transaction in the transaction file
                            writeTransactionFiles("PaidItem.txt", "Transaction.txt", totalAmount);
                            // Clear the cart
                            sales.clearCart();

                            System.out.println("-------------------------------------------------------");
                            System.out.println("THE PAYMENT HAS COMPLETE\n");
                            printReceipt(totalAmount, memberDsct);
                            systemPause();
                            clearScreen();
                            return 1;
                        }
                    }
                    return 0;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        systemPause();
        clearScreen();
        return 0;
    }

    public void printReceipt(double totalAmount, double memberDsct) throws IOException {
        // Display the receipt
        System.out.println("\tINVOICE");
        System.out.println("===================================");
        System.out.printf("SUBTOTAL          :RM% .2f\n", totalAmount);
        System.out.printf("TAX(6%%)           :RM% .2f\n", getTaxAmount());
        System.out.printf("DISCOUNT(%.0f%%)      :RM% .2f\n", memberDsct, getDiscount());
        System.out.printf("TOTAL             :RM% .2f\n", getOverallTotal());
        systemPause();
        clearScreen();
    }

    public double change(double amountPaid, double totalAmount) {
        return amountPaid - totalAmount;
    }

    public void writeTransactionFiles(String itemFilename, String amountFilename, double totalAmount) throws IOException {
        transactionID++;

        try (FileWriter itemWriter = new FileWriter(itemFilename, true); FileWriter amountWriter = new FileWriter(amountFilename, true)) {

            // Write ordered items to the itemFilename
            for (Stock item : Sales.getCart()) {
                itemWriter.write(item.getStockID() + "\t");
                itemWriter.write(item.getStockName() + "\t");
                itemWriter.write(item.getQty() + "\t");
                itemWriter.write(item.getPrice() + "\t");
                itemWriter.write("\n");
            }

            // Write the total amount to the amountFilename
            amountWriter.write(totalAmount + "\t");
            amountWriter.write(getTaxAmount() + "\t");
            amountWriter.write(getDiscount() + "\t");
            amountWriter.write(getOverallTotal() + "\t");
            amountWriter.write("\n");

            // Close both writers
            itemWriter.close();
            amountWriter.close();
        } catch (IOException e) {
            System.err.println("Error writing to the files: " + e.getMessage());
        }
    }

    public static void displayItemDetailsFromFile(String filename) {
        System.out.printf("%-10s %-23s %-10s %-10s %-12s%n", "PRODUCT ID", "PRODUCT NAME", "QUANTITY", "PRICE(RM)", "SUBTOTAL");
        System.out.println("---------------------------------------------------------------");

        Map<Integer, Integer> productQuantities = new HashMap<>();

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String transactionLine = fileScanner.nextLine();
                String[] transactionDetails = transactionLine.split("\t");

                int productID = Integer.parseInt(transactionDetails[0]);
                String productName = transactionDetails[1];
                int quantity = Integer.parseInt(transactionDetails[2]);
                double price = Double.parseDouble(transactionDetails[3]);
                double subtotal = quantity * price;

                // Update the total quantity for the product ID
                productQuantities.put(productID, productQuantities.getOrDefault(productID, 0) + quantity);

                // Print the details for the current item
                System.out.printf("%-10d %-23s %-10d %-10.2f %-12.2f%n",
                        productID,
                        productName,
                        quantity,
                        price,
                        subtotal);
            }

            // Print the total quantities for each product ID
            System.out.println("-------------------------------------------------------");
            System.out.println("TOTAL QUANTITIES:");
            System.out.println("-------------------------------------------------------");
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productID = entry.getKey();
                int totalQuantity = entry.getValue();
                System.out.printf("PRODUCT ID %d : %d%n", productID, totalQuantity);
            }
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static void displayTransactionDetailsAndSum(String filename) {
        double subtotalSum = 0.0;
        double taxSum = 0.0;
        double totalSum = 0.0;
        double discountSum = 0.0;

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String transactionLine = fileScanner.nextLine();
                String[] transactionDetails = transactionLine.split("\t");

                double subtotal = Double.parseDouble(transactionDetails[0]);
                double tax = Double.parseDouble(transactionDetails[1]);
                double discount = Double.parseDouble(transactionDetails[2]);
                double total = Double.parseDouble(transactionDetails[3]);

                subtotalSum += subtotal;
                taxSum += tax;
                discountSum += discount;
                totalSum += total;
            }

            System.out.println("===================================================================");
            System.out.printf("SUBTOTAL SUM     : RM% .2f%n", subtotalSum);
            System.out.printf("TAX SUM (6%%)    : RM% .2f%n", taxSum);
            System.out.printf("TOTAL DISCOUNT   : RM% .2f%n", discountSum);
            System.out.printf("TOTAL SUM        : RM% .2f%n", totalSum);
            System.out.println("===================================================================");
        } catch (IOException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public void updateStockFile(String filename) throws IOException {
        // Create a data structure to store the updated stock data
        Map<String, String> updatedStockData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into components
                String[] parts = line.split("\t");

                if (parts.length >= 4) {
                    String stockID = parts[0];
                    String stockName = parts[1];
                    int currentQty = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);

                    // Update the quantity for items in the cart
                    for (Stock stock : Sales.getCart()) {
                        if (stockName.equals(stock.getStockName())) {
                            int purchasedQty = stock.getQty();
                            currentQty -= purchasedQty;
                            break; // No need to check further for this stock item
                        }
                    }

                    // Add the updated data to the data structure
                    updatedStockData.put(stockID, stockName + "\t" + currentQty + "\t" + price);
                }
            }
        }

        // Write the updated stock data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : updatedStockData.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

}
