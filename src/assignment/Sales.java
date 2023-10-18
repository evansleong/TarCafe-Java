/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import static assignment.Main.clearScreen;
import static assignment.Main.logo;
import static assignment.Main.systemPause;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author LYL
 */
public class Sales {

    protected static final List<Stock> stocklist = new ArrayList<>(); // Store stock in array list
    private static ArrayList<Stock> cart = new ArrayList<>(); //Store ordered stock in cart

    private static int count = 0;
    private int qty;
    private int itemID;
    private String itemName;
    private double price;
    private double totalCost;
    protected static int newOrder = 0;

    public Sales() {
    }

    //getter or accessor   
    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalCost() {
        return totalCost;
    }

    //setter or mutator
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int qty) {
        this.qty = qty;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    //other methods
    public double calTotal(int qty, double pricePerQty) {
        return qty * pricePerQty;
    }

    public static ArrayList<Stock> getCart() {
        return cart;
    }

    public void addOrder() throws IOException {
        if (count == 0) {
            loadStockFromFile("stock.txt");
            count++;
        }
        char nextOrder;

        // Create a new ArrayList to store items in the cart
        do {
            do {
                logo();
                System.out.println("[ ORDERING SYSTEM ]");
                System.out.println("-------------------------------------------------------");
                newOrder++;
                displayAvailableItems();
                System.out.printf("\tORDER NO.: %d\n\n", newOrder);
                do {
                    System.out.print("PRODUCT ID(0 TO STOP ORDER): ");
                    setItemID(intValidation(0, 0));
                } while (getItemID() == -9999);

                if (getItemID() == 0) {
                    newOrder--;
                    break; // Break out of the inner loop
                }

                // Find the stock item in the stocklist ArrayList by its item ID
                Stock foundStock = null;
                for (Stock stock : stocklist) {
                    if (stock.getStockID() == getItemID()) {
                        foundStock = stock;
                        break;
                    }
                }

                if (foundStock == null || foundStock.getQty() == 0) {
                    System.out.println("<<<The item ID is not matched or no quantity available, please enter the correct one!>>>");
                    newOrder--;
                } else {
                    System.out.printf("PRODUCT NAME: %s\n", foundStock.getStockName());
                    System.out.printf("PRODUCT PRICE: RM%.2f\n", foundStock.getPrice());
                    System.out.printf("AVAILABLE QUANTITY: %d\n", foundStock.getQty());
                    System.out.println("-------------------------------------------------------");

                    do {
                        System.out.print("ENTER DESIRED QUANTITY(Enter 999 to re-enter): ");
                        setQuantity(intValidation(0, 10000));

                        if (getQuantity() == 999) {
                            newOrder--;
                            break; // Break out of the innermost loop
                        }

                        if (getQuantity() <= 0 || getQuantity() > foundStock.getQty()) {
                            System.out.println("<<<Invalid quantity. Please try again!>>>");
                        } else {
                            // Calculate the total cost for this item
                            setTotalCost(foundStock.getPrice() * getQuantity());
                            System.out.println("-------------------------------------------------------");
                            System.out.printf("TOTAL COST: RM%.2f\n", getTotalCost());
                            System.out.println("-------------------------------------------------------");

                            // Update order details here if needed
                            System.out.println("[ORDER ADDED]");

                            // Update the stocklist ArrayList by deducting the ordered quantity
                            foundStock.setQty(foundStock.getQty() - getQuantity());
                            setItemID(foundStock.getStockID());
                            setItemName(foundStock.getStockName());
                            setQuantity(qty);
                            setPrice(foundStock.getPrice());

                            // Add the item to the cart ArrayList
                            cart.add(new Stock(newOrder, getItemID(), getItemName(), getQuantity(), getPrice()));

                            break; // Exit the quantity input loop
                        }
                    } while (true);
                }
                systemPause();
                clearScreen();
            } while (true);

            // ... Rest of your code ...
            do {
                System.out.print("FINISHED ORDERING? (Y=YES, N=NO): ");
                nextOrder = charValidation();
            } while (nextOrder != 'Y' && nextOrder != 'y' && nextOrder != 'N' && nextOrder != 'n');
        } while (Character.toUpperCase(nextOrder) != 'Y');

        // At this point, you have the cart ArrayList containing ordered items
        // You can use this ArrayList for further processing or display
        systemPause();
        clearScreen();
    }

    public void searchOrder() throws IOException {
        logo();
        System.out.println("[ SEARCH AN ORDER ]");
        System.out.println("-------------------------------------------------------");

        System.out.print("ENTER ORDER NO TO SEARCH: ");
        int orderNoSearch = intValidation(0, 10000);

        boolean found = false;

        for (Stock item : cart) {
            if (item.getOrderNo() == orderNoSearch) {
                found = true;
                System.out.println("ORDER NO " + item.getOrderNo());
                System.out.println("PRODUCT NAME: " + item.getStockName());
                System.out.println("QUANTITY: " + item.getQty());
                System.out.printf("TOTAL COST: RM%.2f\n", item.getPrice() * item.getQty());
                System.out.println("-----------------------");
            }
        }

        if (!found) {
            System.out.println("The order is not found in the cart. Please try again.");
        }

        systemPause();
        clearScreen();
    }

    public void removeOrder() {
        logo();
        System.out.println("[ REMOVE AN ORDER ]");
        System.out.println("-------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);

        System.out.print("ENTER ORDER NO TO REMOVE: ");
        int orderNoRemove =  intValidation(0,0);

        boolean found = false;
        int indexToRemove = -1;

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getOrderNo() == orderNoRemove) {
                found = true;
                indexToRemove = i;
                break;
            }
        }

        if (!found) {
            System.out.println("<<<The order is not found in the cart. Please try again.>>>");
        } else {
            System.out.println("-------------------------------------------------------");
            System.out.println("ORDER DETAILS:");
            Stock cartItem = cart.get(indexToRemove);
            System.out.println("-------------------------------------------------------");
            System.out.println("ORDER NO " + cartItem.getOrderNo());
            System.out.println("-------------------------------------------------------");
            System.out.println("PRODUCT NAME: " + cartItem.getStockName());
            System.out.println("CURRENT QUANTITY: " + cartItem.getQty());

            System.out.print("DO YOU WANT TO DELETE THIS ORDER (Y = YES, N = NO): "); // Change "yes/no" to "y/n"
            String confirm = scanner.nextLine().toLowerCase();

            if (confirm.equals("y")) { // Check for "y" instead of "yes"
                // Get the removed item from the cart
                Stock removedItem = cart.remove(indexToRemove);

                // Add the removed item back to the stocklist
                for (Stock stockItem : stocklist) {
                    if (stockItem.getStockID() == removedItem.getStockID()) {
                        stockItem.setQty(stockItem.getQty() + removedItem.getQty());
                        break;
                    }
                }

                System.out.println("ORDER REMOVED SUCCESSFULLY");
            } else {
                System.out.println("ORDER REMOVAL CANCELLED");
            }
        }

        systemPause();
        clearScreen();
    }

    public void editOrder() {
        logo();
        System.out.println("[ EDIT AN ORDER ]");
        System.out.println("-------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);

        System.out.print("ENTER ORDER NO TO EDIT: ");
        int orderNoEdit = intValidation(0,0);
        System.out.println("-------------------------------------------------------");

        boolean edited = false;

        OUTER:
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getOrderNo() == orderNoEdit) {
                Stock cartItem = cart.get(i);
                System.out.println("ORDER NO " + cartItem.getOrderNo());
                System.out.println("-------------------------------------------------------");
                System.out.println("PRODUCT NAME: " + cartItem.getStockName());
                System.out.println("CURRENT QUANTITY: " + cartItem.getQty());
                System.out.println("-------------------------------------------------------");
                Stock stockItem = null;
                for (Stock stock : stocklist) {
                    if (stock.getStockID() == cartItem.getStockID()) {
                        stockItem = stock;
                        break;
                    }
                }
                if (stockItem != null) {
                    System.out.println("AVAILABLE QUANTITY IN STOCK: " + stockItem.getQty());
                    System.out.println("-------------------------------------------------------");
                    int maxQuantityToAdd = stockItem.getQty();
                    System.out.println("1. REDUCE QUANTITY");
                    System.out.println("2. ADD QUANTITY");
                    System.out.println("0. CANCEL");
                    System.out.println("-------------------------------------------------------");
                    System.out.print("ENTER YOUR CHOICE: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice) {
                        case 0:
                            edited = true;
                            System.out.println("EDIT CANCELLED");
                            break OUTER;
                        case 1:
                            // Reduce Quantity
                            System.out.print("ENTER REDUCED QUANTITY: ");
                            int reducedQuantity = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            if (reducedQuantity < 0 || reducedQuantity > cartItem.getQty()) {
                                System.out.println("<<<Invalid quantity. Please enter a valid quantity>>>");
                            } else {
                                // Update the quantity of the item in the cart
                                cartItem.setQty(cartItem.getQty() - reducedQuantity);
                                edited = true;
                                System.out.println("QUANTITY REDUCED SUCCESSFULLY");
                                // Update the stocklist as well
                                stockItem.setQty(stockItem.getQty() + reducedQuantity);
                                break OUTER;
                            }
                            break;
                        case 2:
                            // Add Quantity
                            System.out.print("ENTER ADDED QUANTITY: ");
                            int addedQuantity = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            if (addedQuantity < 0 || addedQuantity > maxQuantityToAdd) {
                                System.out.println("<<<Invalid quantity. Please enter a valid quantity>>>");
                            } else {
                                // Update the quantity of the item in the cart
                                cartItem.setQty(cartItem.getQty() + addedQuantity);
                                edited = true;
                                System.out.println("QUANTITY ADDED SUCCESSFULLY");
                                // Update the stocklist as well
                                stockItem.setQty(stockItem.getQty() - addedQuantity);
                                break OUTER;
                            }
                            break;
                        default:
                            System.out.println("<<<Invalid choice. Please enter a valid option.>>>");
                            break;
                    }
                } else {
                    System.out.println("<<<UNABLE TO FIND THE STOCK IN STOCKLIST!>>>");
                }
                break;
            }
        }

        if (!edited) {
            System.out.println("<<<NO ORDER FOUND IN THE CART/INVALID QUANTITY!>>>");
        }

        systemPause();
        clearScreen();
    }

    public void loadStockFromFile(String filePath) {
        stocklist.clear(); // Clear the existing data in the ArrayList before loading

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming the data is tab-separated

                if (parts.length >= 4) {
                    int stockId = Integer.parseInt(parts[0]);
                    String stockName = parts[1];
                    int stockQty = Integer.parseInt(parts[2]);
                    double stockPrice = Double.parseDouble(parts[3]);

                    Stock stock = null;
                    stock = new Stock(stockId, stockName, stockQty, stockPrice);

                    stocklist.add(stock); // Add the loaded member to the ArrayList
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public void displayAvailableItems() {
        System.out.println("-------------------------------------------------------");
        System.out.println("       AVAILABLE ITEMS FOR PURCHASE");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-10s\n", "PRODUCT ID", "PRODUCT NAME", "PRICE (RM)", "QUANTITY");
        System.out.println("-------------------------------------------------------");

        for (Stock stockItem : stocklist) {
            if (stockItem.getQty() > 0) {
                System.out.printf("%-15d %-20s %-15.2f %-10d\n", stockItem.getStockID(), stockItem.getStockName(), stockItem.getPrice(), stockItem.getQty());
            }
        }

        System.out.println("-------------------------------------------------------");
    }

//VALIDATION
    public int intValidation(int startingNum, int endingNum) {
        Scanner scanner = new Scanner(System.in);
        int input;

        //Starting Num == 0 && Ending Num == 0 to get validate the input only(without range)
        try {
            input = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println("PLEASE ENTER AN INTEGER INPUT.\n");
            return -9999;
        }

        if (startingNum == 0 && endingNum != 0) {
            if (input < startingNum || input > endingNum) {
                System.out.println("THE INPUT IS OUT OF RANGE, PLEASE INPUT A CORRECT ONE!!\n");
                scanner.nextLine(); // Consume the newline character
                clearScreen();
                return -9999;
            }
        }

        return input;
    }

    public char charValidation() {
        Scanner scanner = new Scanner(System.in);
        char input;

        try {
            input = scanner.nextLine().charAt(0);
        } catch (Exception ex) {
            System.out.println("PLEASE ENTER A CHARACTER INPUT. \n");
            return 'O';
        }

        return Character.toUpperCase(input);
    }

    public double doubleValidation() {
        Scanner scanner = new Scanner(System.in);
        double input;

        try {
            input = scanner.nextDouble();
        } catch (Exception ex) {
            System.out.println("PLEASE ENTER A VALID DOUBLE INPUT. \n");
            return -9999;
        }

        return input;
    }

}
