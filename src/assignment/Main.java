/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evansleong
 */
public class Main {

    public static int totalPayment = 0;

    public static void main(String[] args) {
        Main main = new Main();
        main.entry();
    }

    public void entry() {
        Main main = new Main();
        Staff staff = new Staff();
        Scanner scanner = new Scanner(System.in);

        String stfIc, stfPassword, stfTempName, stfTempId;
        int registrationCode = 1234;
        boolean loggedIn = false;
        boolean exitProgram = false; // Added to control program exit

        while (!exitProgram) { // Loop for the entire program
            boolean inMainMenu = true; // Flag to control whether the user is in the main menu

            while (true) { // Loop for the menu
                clearScreen();
                logo();
                main.logMenu();
                System.out.print("ENTER YOUR SELECTION: ");

                while (!scanner.hasNextInt()) {
                    System.out.println("<<<INVALID OPTION - PLEASE ENTER A VALID NUMBER>>>");
                    System.out.print("ENTER YOUR SELECTION: ");
                    scanner.next(); // Consume non-integer input
                }

                int logMenuOpt = scanner.nextInt();

                scanner.nextLine();

                if (!inMainMenu) {
                    break; // Exit the menu loop if not in the main menu
                }

                switch (logMenuOpt) {
                    case 1:
                        // Check if the user enters the specific code to register
                        clearScreen();
                        logo();
                        System.out.print("ENTER REGISTRATION CODE OR PRESS 'E' TO RETURN TO THE MENU: ");
                        String inputCodeOrReturn = scanner.nextLine();

                        if (inputCodeOrReturn.equalsIgnoreCase("E")) {
                            clearScreen();
                            break; // Return to the main menu
                        }

                        int inputCode = Integer.parseInt(inputCodeOrReturn);

                        if (inputCode == registrationCode) {
                            // Allow registration
                            System.out.println("PROCEEDING TO STAFF REGISTRATION...");
                            systemPause();
                            clearScreen();
                            staff.add();
                            systemPause();
                        } else {
                            System.out.println("<<<INCORRECT ! REGISTRATION DENIED !>>>");
                            systemPause();
                        }
                        clearScreen();
                        break;
                    case 2:
                        while (!loggedIn) {
                            clearScreen();
                            logo();
                            System.out.println("[ LOG IN ]");
                            System.out.println("-------------------------------------------------------");
                            System.out.println("WELCOME TO OUR SYSTEM...");
                            System.out.println("Press enter key to continue...");
                            scanner.nextLine(); // Consume the newline character
                            System.out.print("ENTER IC: ");
                            stfIc = scanner.nextLine();

                            System.out.print("ENTER PASSWORD: ");
                            stfPassword = scanner.nextLine();

                            if (login(stfIc, stfPassword)) {
                                // Once the user successfully logs in, you can display the menu here
                                // Example: displayMenu();
                                loggedIn = true;
                                inMainMenu = false; // Set the flag to false to exit the main menu loop
                                systemPause();
                                clearScreen();
                                main.run();
                            } else {
                                System.out.println("<<<LOGIN FAILED ! PLEASE TRY AGAIN!\n");

                                // Check if the user wants to return to the menu
                                System.out.print("PRESS 'E' TO RETURN TO THE LOGMENU OR ANY OTHER KEY TO RETRY: ");
                                String returnToMenu = scanner.next();
                                if (returnToMenu.equalsIgnoreCase("E")) {
                                    clearScreen();
                                    break; // Return to the main menu
                                }
                                systemPause();
                            }
                        }
                        loggedIn = false; // Reset loggedIn flag
                        systemPause();
                        clearScreen();
                        break;
                    case 3:
                        // Exit the program
                        System.out.println("EXITING THE PROGRAM...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("<<<INVALID OPTION!>>>");
                        systemPause();
                }

                if (loggedIn) {
                    break; // Exit the inner loop and program if exitProgram is true
                }
            }
        }
    }

    public void run() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            logo();
            menu();
            System.out.print("ENTER YOUR SELECTION: ");

            while (!scanner.hasNextInt()) {
                System.out.println("<<<INVALID OPTION - PLEASE ENTER A VALID NUMBER>>>");
                System.out.print("ENTER YOUR SELECTION: ");
                scanner.next(); // Consume non-integer input
            }

            int opt = scanner.nextInt();

            switch (opt) {
                case 1 -> {
                    clearScreen();
                    runMember();
                }
                case 2 -> {
                    clearScreen();
                    try {
                        runSales();
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                case 3 -> {
                    clearScreen();
                    runStaff();
                }
                case 4 -> {
                    clearScreen();
                    runStock();
                }
                case 0 -> {
                    System.out.println("LOGGING OUT...");
                    entry();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION!>>>");
                    systemPause();
                    clearScreen();
                }
            }
        }
    }

    public static void logo() {
        System.out.println("  _____  _    ____     ____    _    _____ _____ ");
        System.out.println(" |_   _|/ \\  |  _ \\   / ___|  / \\  |  ___| ____|");
        System.out.println("   | | / _ \\ | |_) | | |     / _ \\ | |_  |  _|  ");
        System.out.println("   | |/ ___ \\|  _ <  | |___ / ___ \\|  _| | |___ ");
        System.out.println("   |_/_/   \\_\\_| \\_\\  \\____/_/   \\_\\_|   |_____|");
        System.out.println("                   .-=========-.");
        System.out.println("                   |  Welcome  |");
        System.out.println("                   |    to     |");
        System.out.println("                   |  TAR CAFE |");
        System.out.println("                   .-=========-.");
        System.out.println("-------------------------------------------------------");

    }

    public void logMenu() {
        System.out.println("[ LOGMENU ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. SIGN UP");
        System.out.println("2. LOG IN");
        System.out.println("3. EXIT");
    }

    public void menu() {
        System.out.println("[ MAIN MENU ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. MEMBERSHIP MANAGEMENT");
        System.out.println("2. SALES MANAGEMENT");
        System.out.println("3. STAFF MANAGEMENT");
        System.out.println("4. FOOD AND BEVERAGES");
        System.out.println("0. LOGOUT");
    }

    public void runMember() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        Membership member = new NormalMember();

        while (!exit) {
            logo();
            memberMenu();
            System.out.print("ENTER YOUR SELECTION: ");

            while (!scanner.hasNextInt()) {
                System.out.println("<<<INVALID OPTION - PLEASE ENTER A VALID NUMBER>>>");
                System.out.print("ENTER YOUR SELECTION: ");
                scanner.next(); // Consume non-integer input
            }

            int memberOpt = scanner.nextInt();

            switch (memberOpt) {
                case 1 -> {
                    clearScreen();
                    addToWhichMember();
                }
                case 2 -> {
                    clearScreen();
                    member.delete();
                }
                case 3 -> {
                    clearScreen();
                    member.search();
                }
                case 4 -> {
                    clearScreen();
                    member.view();
                }
                case 0 -> {
                    System.out.println("BACK TO MAIN MENU...");
                    systemPause();
                    clearScreen();
                    run();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION>>>");
                    systemPause();
                    clearScreen();
                }
            }
        }
    }

    public void runStaff() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        Staff staff = new Staff();

        while (!exit) {
            logo();
            staffMenu();
            System.out.print("ENTER YOUR SELECTION: ");

            while (!scanner.hasNextInt()) {
                System.out.println("<<<INVALID OPTION - PLEASE ENTER A VALID NUMBER>>>");
                System.out.print("ENTER YOUR SELECTION: ");
                scanner.next(); // Consume non-integer input
            }

            int staffOpt = scanner.nextInt();

            switch (staffOpt) {
                case 1 -> {
                    clearScreen();
                    staff.add();
                }
                case 2 -> {
                    clearScreen();
                    staff.delete();
                }
                case 3 -> {
                    clearScreen();
                    staff.search();
                }
                case 4 -> {
                    clearScreen();
                    staff.view();
                }
                case 0 -> {
                    System.out.println("BACK TO MAIN MENU...");
                    systemPause();
                    clearScreen();
                    run();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION>>>");
                    systemPause();
                    clearScreen();
                }
            }
        }
    }

    public void runStock() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        Stock stock = new Stock();

        while (!exit) {
            logo();
            stockMenu();
            System.out.print("ENTER YOUR SELECTION: ");

            while (!scanner.hasNextInt()) {
                System.out.println("<<<INVALID OPTION - PLEASE ENTER A VALID NUMBER>>>");
                System.out.print("ENTER YOUR SELECTION: ");
                scanner.next(); // Consume non-integer input
            }

            int stockOpt = scanner.nextInt();

            switch (stockOpt) {
                case 1 -> {
                    clearScreen();
                    stock.add();
                }
                case 2 -> {
                    clearScreen();
                    stock.delete();
                }
                case 3 -> {
                    clearScreen();
                    stock.view();
                }
                case 0 -> {
                    System.out.println("BACK TO MAIN MENU...");
                    systemPause();
                    clearScreen();
                    run();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION>>>");
                    systemPause();
                    clearScreen();
                }
            }
        }
    }

    public void runSales() throws IOException {
        boolean exit = false;
        Sales sales = new Sales();

        while (!exit) {
            logo();
            salesMenu();
            System.out.print("ENTER YOUR SELECTION: ");

            int salesOpt = sales.intValidation(0, 2);

            switch (salesOpt) {
                case 1 -> {
                    clearScreen();
                    totalPayment += runOrder();
                }
                case 2 -> {
                    clearScreen();
                    transactionRecord();
                }
                case 0 -> {
                    System.out.println("BACK TO MAIN MENU...");
                    systemPause();
                    clearScreen();
                    run();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION>>>");
                    systemPause();
                    clearScreen();
                }
            }
        }
    }

    public int runOrder() throws IOException {
        boolean exit = false;
        int successPayment = 0;

        Sales sales = new Sales();
        Payment payment = new Payment();

        while (!exit) {
            logo();
            createOrderMenu();
            System.out.print("ENTER YOUR SELECTION: ");
            int orderOpt = sales.intValidation(0, 5);

            switch (orderOpt) {
                case 1 -> {
                    clearScreen();
                    sales.addOrder();
                }
                case 2 -> {
                    clearScreen();
                    sales.editOrder();
                }
                case 3 -> {
                    clearScreen();
                    sales.searchOrder();
                }
                case 4 -> {
                    clearScreen();
                    sales.removeOrder();
                }
                case 5 -> {
                    clearScreen();
                    successPayment += payment.makePayment();
                }
                case 0 -> {
                    clearScreen();
                    runSales();
                }
                default -> {
                    System.out.println("<<<INVALID OPTION>>>");

                    systemPause();
                    clearScreen();
                }
            }
        }
        return successPayment;
    }

    public void memberMenu() {
        System.out.println("[ MEMBER MANAGEMENT SYSTEM ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. ADD NEW MEMBER");
        System.out.println("2. DELETE MEMBER");
        System.out.println("3. SEARCH MEMBER");
        System.out.println("4. VIEW REGISTERED MEMBER LIST");
        System.out.println("0. BACK TO MAIN MENU");
        System.out.println("-------------------------------------------------------");
    }

    public void staffMenu() {
        System.out.println("[ STAFF MANAGEMENT SYSTEM ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. ADD NEW STAFF");
        System.out.println("2. DELETE STAFF ");
        System.out.println("3. SEARCH STAFF ");
        System.out.println("4. VIEW REGISTERED STAFF LIST");
        System.out.println("0. BACK TO MAIN MENU");
        System.out.println("-------------------------------------------------------");
    }

    public void stockMenu() {
        System.out.println("[ FOOD AND BEVERAGE MANAGEMENT SYSTEM ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. ADD NEW PRODUCT");
        System.out.println("2. DELETE PRODUCT");
        System.out.println("3. VIEW PRODUCT LIST");
        System.out.println("0. BACK TO MAIN MENU");
        System.out.println("-------------------------------------------------------");
    }

    public void salesMenu() {
        System.out.println("[ SALES MANAGEMENT SYSTEM ]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. MAKE ORDER");
        System.out.println("2. TRANSACTION REPORT");
        System.out.println("0. BACK TO MAIN MENU");
        System.out.println("-------------------------------------------------------");
    }

    public void createOrderMenu() {
        System.out.println("[ ORDERING MANAGEMENT]");
        System.out.println("-------------------------------------------------------");
        System.out.println("1. ADD NEW ORDER");
        System.out.println("2. EDIT AN ORDER");
        System.out.println("3. SEARCH AN ORDER");
        System.out.println("4. REMOVE AN ORDER");
        System.out.println("5. MAKE PAYMENT");
        System.out.println("0. BACK TO PREVIOUS PAGE");
        System.out.println("-------------------------------------------------------");
    }

    public static void addToWhichMember() {
        Scanner scanner = new Scanner(System.in);
        char userChoice;

        do {
            logo();
            System.out.println("[ REGISTER MEMBER ]");
            System.out.println("-------------------------------------------------------");
            System.out.println("CHOOSE MEMBER TYPE:");
            System.out.println("1 = NORMAL");
            System.out.println("2 = GOLD");
            System.out.println("3 = PREMIUM");
            System.out.println("ENTER 'E' TO RETURN MIAN MENU");
            System.out.print("YOUR CHOICE: ");
            userChoice = scanner.next().charAt(0);
            System.out.println("-------------------------------------------------------");

            switch (userChoice) {
                case '1':
                    NormalMember normalMember = new NormalMember();
                    normalMember.setMemberType("Normal");
                    normalMember.add();
                    break;
                case '2':
                    GoldMember goldMember = new GoldMember();
                    goldMember.setMemberType("Gold");
                    goldMember.add();
                    break;
                case '3':
                    PremiumMember premiumMember = new PremiumMember();
                    premiumMember.setMemberType("Premium");
                    premiumMember.add();
                    break;
                case 'E':
                case 'e':
                    // User wants to cancel and return to the main menu
                    clearScreen();
                    return;
                default:
                    System.out.println("<<<INVALID OPTION>>>");
                    break;
            }

            // Ask if the user wants to add another member
            System.out.print("ADD MORE MEMBER ? (Y = YES , N = NO): ");
            char addAnother = scanner.next().charAt(0);
            if (addAnother == 'N' || addAnother == 'n') {
                clearScreen();
                return; // Return to the main menu
            }
            clearScreen();
        } while (true);
    }

    public static void clearScreen() {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(1);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_L);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_L);
        } catch (AWTException ex) {
        }
    }

    public static void systemPause() {
        System.out.println("\n\t\tPress Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Wait for the user to press Enter
    }

    public static boolean login(String ic, String password) {
        Staff staff = new Staff();
        boolean loggedIn;

        // Create a Staff object and invoke the login method
        loggedIn = staff.login(ic, password);

        return loggedIn;
    }

    public static void transactionRecord() {
        logo();
        System.out.println("[ TRANSACTION REPORT ]");
        System.out.println("-------------------------------------------------------");

        Payment.displayItemDetailsFromFile("PaidItem.txt");
        Payment.displayTransactionDetailsAndSum("Transaction.txt");

        systemPause();
        clearScreen();
    }

}
