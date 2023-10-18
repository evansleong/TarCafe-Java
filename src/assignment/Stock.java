/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author Dephnie
 */
import static assignment.Main.clearScreen;
import static assignment.Main.logo;
import static assignment.Main.systemPause;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Stock {

    private int stockID = 0;
    private String stockName;
    private int qty;
    private double price;
    private int orderNo;

    Scanner scanner = new Scanner(System.in);

    public Stock() {
    }

    public Stock(int orderNo, int stockID, String stockName, int qty, double price) {
        this.orderNo = orderNo;
        this.stockID = stockID;
        this.stockName = stockName;
        this.qty = qty;
        this.price = price;
    }

    public Stock(int stockID, String stockName, int qty, double price) {
        this.stockID = stockID;
        this.stockName = stockName;
        this.qty = qty;
        this.price = price;
    }

    public int getOrderNo() {
        return orderNo;
    }

    //accessor/getter
    public int getStockID() {
        return stockID;
    }

    public String getStockName() {
        return stockName.toUpperCase();
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    //mutator/setter
    public void setStockID(int stockID) {
        this.stockID = stockID;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void add() {
        boolean continueAdding = true;
        String inputName;
        boolean nameExists;
        int productCount = 10000, lastStockID = 10000;

        File stockFile = new File("stock.txt");
        if (!stockFile.exists()) {
            try {
                if (stockFile.createNewFile()) {
                    System.out.println("File 'stock.txt' created successfully.");
                } else {
                    System.err.println("Error creating the 'stock.txt' file.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("Error creating the 'stock.txt' file: " + e.getMessage());
                return;
            }
        }

        // Read the last stockID from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("stock.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 0) {
                    stockID = Integer.parseInt(parts[0]);
                    if (stockID > lastStockID) {
                        lastStockID = stockID;
                    }
                }
                productCount++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the 'stock.txt' file: " + e.getMessage());
            return;
        }

        do {
            logo();
            System.out.println("[ ADD NEW PRODUCT ]");
            System.out.println("-------------------------------------------------------");
            stockID = lastStockID + 1;
            System.out.println("PRODUCT ID >> P-" + getStockID());
            systemPause();

            do {
                nameExists = false;
                System.out.print("ENTER PRODUCT NAME [OR 'E' TO Exit]: ");
                inputName = scanner.nextLine();

                if (inputName == null || inputName.trim().isEmpty()) {
                    System.out.println("<<<INVALID PRODUCT NAME! Please enter a valid name.>>>");
                    nameExists = true;
                    continue;
                }

                if (inputName.equalsIgnoreCase("E")) {
                    System.out.println("\nEXITING PRODUCT ADDITION");
                    continueAdding = false;
                    stockID = 0;
                    break;
                }

                setStockName(inputName);

                try (Scanner fileScanner = new Scanner(new File("stock.txt"))) {
                    while (fileScanner.hasNextLine()) {
                        String memberLine = fileScanner.nextLine();
                        String[] memberDetails = memberLine.split("\t");
                        String searchStockName = memberDetails[1];

                        if (searchStockName.equals(getStockName())) {
                            nameExists = true;
                            break;
                        }
                    }

                    if (nameExists) {
                        System.out.println("<<<The item name already exists, try another name!>>>");
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("The 'stock.txt' file does not exist. Skipping name check.");
                }
            } while (nameExists);

            if (!continueAdding) {
                break;
            }

            do {
                System.out.print("ENTER PRODUCT QUANTITY TO BE ADDED: ");
                if (scanner.hasNextInt()) {
                    qty = scanner.nextInt();
                    if (qty >= 1) {
                        setQty(qty);
                        break;
                    } else {
                        System.out.println("<<<QUANTITY CANNOT BE LESS THAN  ONE!!!>>>");
                    }
                } else {
                    System.out.println("<<<INVALID INPUT>>>");
                    scanner.nextLine();
                }
            } while (true);

            do {
                System.out.print("ENTER PRICE OF THE PRODUCT:  RM ");
                if (scanner.hasNextDouble()) {
                    price = scanner.nextDouble();
                    if (price >= 1) {
                        setPrice(price);
                        break; // Exit the loop when valid input is provided
                    } else {
                        System.out.println("<<<PRICE CANNOT BE LESS THAN  ONE!!!>>>");
                    }
                } else {
                    System.out.println("<<<INVALID INPUT>>>");
                    scanner.nextLine(); // Consume the entire invalid input line
                    scanner.nextLine(); // Consume the newline character
                }
            } while (true);

            System.out.println("\nPRODUCT INFORMATION:");
            System.out.println(toString());

            OUTER:
            while (true) {
                scanner.nextLine();
                System.out.print("\nDO YOU WANT TO ADD THIS PRODUCT? (Y = YES / N = NO): ");
                String confirmation = scanner.nextLine().toUpperCase();
                switch (confirmation) {
                    case "Y" -> {
                        try (FileWriter writer = new FileWriter("stock.txt", true)) {
                            writer.write(stockID + "\t");
                            writer.write(getStockName() + "\t");
                            writer.write(getQty() + "\t");
                            writer.write(getPrice() + "\t");
                            writer.write("\n");
                            System.out.println("\nNEW PRODUCT ADDED TO THE SYSTEM...");
                            System.out.println("---------------------------------------------------");
                            lastStockID++;
                            break OUTER;
                        } catch (IOException e) {
                            System.err.println("Error writing to the file: " + e.getMessage());
                        }
                    }
                    case "N" -> {
                        System.out.println("\nPRODUCT NOT ADDED. RETURNING TO THE MAIN MENU...");
                        break OUTER;
                    }
                    default ->
                        System.out.println("<<<Invalid input. Please enter 'Y' for yes or 'N' for no.>>>");
                }
            }

            System.out.print("\nDO YOU WANT TO ADD ANOTHER PRODUCT? (Y FOR YES, ANY KEY TO EXIT): ");
            String continueInput = scanner.nextLine().toUpperCase();
            clearScreen();

            if (!continueInput.equals("Y")) {
                System.out.println("\nEXISITING PRODUCT ADDITION...");
                continueAdding = false;
            }
        } while (continueAdding);

        System.out.println("\n");
        systemPause();
        clearScreen();
    }

    public void delete() {
        boolean found = false;
        String productToDelete = null;
        int inputID = 0;
        boolean continueDelete;
        String input;

        do {
            logo();
            System.out.println("[ DELETE A PRODUCT ]");
            System.out.println("-------------------------------------------------------");
            continueDelete = true;
            while (true) {
                System.out.print("ENTER PRODUCT ID TO BE DELETED [OR '0' TO EXIT]: ");
                if (scanner.hasNextInt()) {
                    inputID = scanner.nextInt();
                    if (inputID == 0) {
                        // Exit the method if input is 0
                        System.out.println("EXISITING DELETE OPERATION.");
                        systemPause();
                        clearScreen();
                        return;
                    } else {
                        break; // Valid input; exit the loop
                    }
                } else {
                    System.out.println("<<<Invalid input. Please enter a valid integer or '0' to exit.>>>");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            setStockID(inputID);

            try (Scanner fileScanner = new Scanner(new File("stock.txt"))) {
                while (fileScanner.hasNextLine()) {
                    String stockLine = fileScanner.nextLine();
                    String[] stockDetails = stockLine.split("\t");

                    int productID = Integer.parseInt(stockDetails[0]);

                    if (productID == getStockID()) {
                        found = true;
                        productToDelete = stockLine;
                        break; // Stop searching after finding the product
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
            }

            if (found) {
                System.out.println("-------------------------------------------------------");
                System.out.println("PRODUCT INFORMATION TO BE DELETED:");
                System.out.println(productToDelete);
                System.out.println("-------------------------------------------------------");

                System.out.print("ARE YOU SURE YOU WANT TO DELETE THIS PRODUCT? (Y = YES, any key to CANCEL): ");
                String confirm = scanner.next().toUpperCase();

                if (confirm.equals("Y")) {
                    deleteProductFromFile(getStockID());
                    System.out.println("PRODUCT WITH ID " + getStockID() + " HAS BEEN DELETED");
                } else {
                    System.out.println("DELETION CANCELLED.");
                }
            } else {
                System.out.println("PRODUCT WITH ID " + getStockID() + " NOT FOUND");
            }

            System.out.print("\nDO YOU WANT TO DELETE ANOTHER PRODUCT? (Y FOR YES, ANY KEY TO EXIT): ");
            input = scanner.next().toUpperCase();
            clearScreen();

            if (!input.equals("Y")) {
                System.out.println("EXITING PRODUCT DELETION");
                continueDelete = false;
            }
        } while (continueDelete);

    }

// Method to delete a product from the file
    private void deleteProductFromFile(int productIDToDelete) {
        File inputFile = new File("stock.txt");
        File tempFile = new File("dltStkTemp.txt");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile)); // read StockFile
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) { // write to tempFile
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");

                int productID = Integer.parseInt(data[0]);

                if (productID == productIDToDelete) {
                    continue; // Skip the product to be deleted
                }

                writer.write(line + System.getProperty("line.separator"));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("ERROR DELETING PRODUCT: " + e.getMessage());
        }

        // Delete the original file and rename the temp file
        if (inputFile.delete()) {
            boolean successful = tempFile.renameTo(inputFile);
            if (!successful) {
                System.out.println("Error renaming the temp file.");
            }
        }
    }

//    public void view() {
//        logo();
//        System.out.println("[ VIEW ALL PRODUCTS IN STOCK ]");
//        System.out.println("-------------------------------------------------------");
//
//        // Read and display all products from stock.txt
//        try (Scanner fileScanner = new Scanner(new File("stock.txt"))) {
//            while (fileScanner.hasNextLine()) {
//                String stockLine = fileScanner.nextLine();
//                String[] stockDetails = stockLine.split("\t");
//
//                int productID = Integer.parseInt(stockDetails[0]);
//                String productName = stockDetails[1];
//                int quantity = Integer.parseInt(stockDetails[2]);
//                double prc = Double.parseDouble(stockDetails[3]);
//
//                // Skip products with quantity 0
//                if (quantity > 0) {
//                    Stock product = new Stock(productID, productName, quantity, prc);
//                    System.out.println(product.toString());
//                    System.out.println("-------------------------------------------------------");
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + e.getMessage());
//        }
//
//        systemPause();
//        clearScreen();
//    }
    public void view() {
        logo();
        System.out.println("         [ VIEW ALL PRODUCTS IN STOCK ]");
        System.out.println("------------------------------------------------------------------");

        // Header
        System.out.printf("%-10s      %-25s%-10s  %-10s\n", "PRODUCT ID", "PRODUCT NAME", "QUANTITY", "PRICE");
        System.out.println("------------------------------------------------------------------");

        // Read and display all products from stock.txt
        try (Scanner fileScanner = new Scanner(new File("stock.txt"))) {
            while (fileScanner.hasNextLine()) {
                String stockLine = fileScanner.nextLine();
                String[] stockDetails = stockLine.split("\t");

                int productID = Integer.parseInt(stockDetails[0]);
                String productName = stockDetails[1];
                int quantity = Integer.parseInt(stockDetails[2]);
                double price = Double.parseDouble(stockDetails[3]);

                // Skip products with quantity 0
                if (quantity > 0) {
                    // Format and print each product
                    System.out.printf("%-10d      %-25s%-10d  RM%-10.2f\n", productID, productName, quantity, price);
                    System.out.println("------------------------------------------------------------------");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        systemPause();
        clearScreen();
    }

    @Override
    public String toString() {
        return "\nPRODUCT ID >> " + getStockID()
                + "\nPRODUCT NAME >> " + getStockName()
                + "\nADDED QUANTITY >> " + getQty()
                + "\nPRODUCT PRICE >> RM" + getPrice();
    }
}
