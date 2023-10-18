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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author evansleong
 */
public class Staff extends Person {

    private final List<Staff> staffList = new ArrayList<>(); // Store staff in a list

    private int stfAge;
    private String stfPassword;
    private double stfSalary;
    private LocalDateTime clockIn, clockOut;

    public Staff() {
    }

    public Staff(String name, String ic, int stfAge, double stfSalary, String stfPassword) {
        super(name, ic, Integer.parseInt(ic.substring(6)));
        this.stfAge = stfAge;
        this.stfSalary = stfSalary;
        this.stfPassword = stfPassword;
    }

    public String getStfIC() {
        return getIc();
    }

    public int getStfAge() {
        return stfAge;
    }

    public double getStfSalary() {
        return stfSalary;
    }

    public String getStfPassword() {
        return stfPassword;
    }

    public LocalDateTime getClockIn() {
        return clockIn;
    }

    public LocalDateTime getClockOut() {
        return clockOut;
    }

    public void setStfName(String stfName) {
        setName(stfName.toUpperCase());
    }

    public void setStfIC(String stfIC) {
        setIc(stfIC);
    }

    public void setStfId(int stfId) {
        setId(stfId);
    }

    public void setStfAge(int stfAge) {
        this.stfAge = stfAge;
    }

    public void setStfSalary(double stfSalary) {
        this.stfSalary = stfSalary;
    }

    public void setStfPassword(String stfPassword) {
        this.stfPassword = stfPassword;
    }

    public void setClockIn(LocalDateTime clockIn) {
        this.clockIn = clockIn;
    }

    public void setClockOut(LocalDateTime clockOut) {
        this.clockOut = clockOut;
    }

    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);
        String stfName, stfIc, stfPW, confirmPassword;
        int stfAGE;
        double stfSlr;

        logo();
        System.out.println("[ REGISTER STAFF ]");
        System.out.println("-------------------------------------------------------");

        do {
            System.out.print("ENTER NEW STAFF IC: ");
            stfIc = scanner.nextLine();
            if (stfIc.matches("\\d{12}")) {
                if (icExists("staff.txt", stfIc)) {
                    System.out.println("<<<IC already exists in the file. Please reenter!>>>");
                } else {
                    break; // IC is valid and does not exist in the file
                }
            } else {
                System.out.println("<<<Invalid input. Please enter a 12-digit IC number!>>>");
            }
        } while (true);

        do {
            System.out.print("ENTER NEW STAFF NAME: ");
            stfName = scanner.nextLine();
            if (stfName.matches("^[a-zA-Z ]+$")) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("Invalid input. Please enter a name with alphabet characters only.");
            }
        } while (true);

        do {
            System.out.print("ENTER NEW PASSWORD (8-16 alphanumeric characters): ");
            stfPW = scanner.nextLine();
            if (stfPW.matches("^[a-zA-Z0-9]{8,16}$")) {
                System.out.print("CONFIRM PASSWORD: ");
                confirmPassword = scanner.nextLine();
                if (stfPW.equals(confirmPassword)) {
                    break; // Valid input and confirmed, exit the loop
                } else {
                    System.out.println("<<<Password does not match the confirmation. Please re-enter!>>>");
                }
            } else {
                System.out.println("<<<Invalid input. Please enter a password with 8-16 alphanumeric characters!>>>");
            }
        } while (true);

        do {
            System.out.print("ENTER NEW STAFF AGE (between 18 and 54): ");
            stfAGE = scanner.nextInt();
            if (stfAGE >= 18 && stfAGE <= 54) {
                break; // Valid input, exit the loop
            } else {
                System.out.println("<<<Invalid input. Please enter an age between 18 and 54!>>>");
            }
        } while (true);

        do {
            System.out.print("ENTER NEW STAFF SALARY (digits only): ");
            if (scanner.hasNextDouble()) {
                stfSlr = scanner.nextDouble();
                if(stfSlr>=0){
                    break;
                }
                else{
                    System.out.println("<<Invalid input. Please enter a salary that is more than 0>>");
                }
            } else {
                System.out.println("<<<Invalid input. Please enter a salary with digits only!>>>");
                scanner.next(); // Consume the invalid input
            }
        } while (true);

        // Read the current staff ID from the file
        int currentStaffId = Integer.parseInt(stfIc.substring(6));

        // Update the staff ID for the new staff member
        setId(currentStaffId);
        setIc(stfIc);
        setName(stfName);
        setStfPassword(stfPW);
        setStfAge(stfAGE);
        setStfSalary(stfSlr);

        if (!fileExists("staff.txt")) {
            try {
                FileWriter writer = new FileWriter("staff.txt");
                writer.close();
            } catch (IOException ex) {
                System.err.println("Error creating the file: " + ex.getMessage());
            }
        }

        try (FileWriter writer = new FileWriter("staff.txt", true)) {
            writer.write(getId() + "\t");
            writer.write(getIc() + "\t");
            writer.write(getName() + "\t");
            writer.write(getStfPassword() + "\t");
            writer.write(getStfAge() + "\t");
            writer.write(getStfSalary() + "\t");
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        loadStaffFromFile("staff.txt");
        systemPause();
        clearScreen();
    }

    @Override
    public void delete() {
        Scanner scanner = new Scanner(System.in);

        logo();
        System.out.println("[ DELETE STAFF ]");
        System.out.println("-------------------------------------------------------");

        System.out.print("ENTER STAFF IC ID TO DELETE (ENTER 'E' TO CANCEL): S-");
        String staffICToDelete = scanner.nextLine().trim();

        if (staffICToDelete.equalsIgnoreCase("E")) {
            // User wants to cancel and return to the main menu
            clearScreen();
            return;
        }

        try {
            File inputFile = new File("staff.txt");
            File tempFile = new File("dltStaffTemp.txt");

            boolean found = false;

            try (
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] staffDetails = line.split("\t");

                    if (staffDetails.length >= 5) {
                        String staffIC = staffDetails[1];

                        if (staffIC.equals(staffICToDelete)) {
                            found = true;
                            continue; // Skip writing the line for the deleted staff
                        }
                    }

                    writer.write(line + System.getProperty("line.separator"));
                }
            }

            if (found) {
                inputFile.delete(); // Delete the original file
                boolean successful = tempFile.renameTo(inputFile);

                if (!successful) {
                    System.out.println("Error renaming the temp file.");
                } else {
                    System.out.println("STAFF WITH IC " + staffICToDelete + " HAS BEEN DELETED.");
                }
            } else {
                System.out.println("STAFF WITH IC " + staffICToDelete + " NOT FOUND.");
            }
        } catch (IOException e) {
            System.err.println("Error deleting staff: " + e.getMessage());
        }
        systemPause();
        clearScreen();
    }

    @Override
    public void view() {
        loadStaffFromFile("staff.txt");
        logo();
        System.out.println("[ VIEW ALL STAFF ]");
        System.out.println("-------------------------------------------------------");

        if (staffList.isEmpty()) {
            System.out.println("THERE IS NO STAFF TO DISPLAY...");
        } else {
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("STAFF ID  |STAFF NAME     |STAFF IC      |STAFF AGE |STAFF SALARY |");
            System.out.println("---------------------------------------------------------------------------------------");

            for (Staff staff : staffList) {
                System.out.println("M-" + staff.getId()
                        + "|" + staff.getName()
                        + "     |" + staff.getIc()
                        + "  |" + staff.getStfAge()
                        + "      |" + "RM " + staff.getStfSalary()
                        + "        |");
            }
            System.out.println("---------------------------------------------------------------------------------------");
        }

        systemPause();
        clearScreen();
    }

    @Override
    public void search() {
        loadStaffFromFile("staff.txt");
        Scanner scanner = new Scanner(System.in);

        logo();
        System.out.println("[ SEARCH STAFF BY ID ]");
        System.out.println("-------------------------------------------------------");

        System.out.print("ENTER STAFF ID TO SEARCH (5/6 DIGIT ONLY) OR '0' TO CANCEL: S-");
        int staffIdToSearch = scanner.nextInt();

        if (staffIdToSearch == 0) {
            // User wants to cancel and return to the main menu
            clearScreen();
            return;
        }

        boolean found = false;

        for (Staff staff : staffList) {
            if (staff.getId() == staffIdToSearch) {
                found = true;
                System.out.println("\tSTAFF FOUND !");
                System.out.println(staff.toString());
                break; // Exit the loop once a staff is found
            }
        }

        if (!found) {
            System.out.println("\tSTAFF NOT FOUND...");
        }
        systemPause();
        System.out.println("\n");
        clearScreen();
    }

    public boolean login(String stfIc, String stfPassword) {
        try {
            File file = new File("staff.txt");
            if (!file.exists()) {
                System.out.println("<<<No staff records found>>>");
                return false; // No records, so login fails
            }

            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    String stfLine = sc.nextLine();
                    String[] stfDtls = stfLine.split("\t"); // Assuming the data is tab-separated

                    if (stfDtls.length >= 5) {
                        String staffIC = stfDtls[1];
                        String staffPassword = stfDtls[3];

                        if (stfIc.equals(staffIC) && stfPassword.equals(staffPassword)) {
                            sc.close(); // Close the scanner before returning

                            // Get the current time
                            LocalDateTime loginTime = LocalDateTime.now();
                            setClockIn(loginTime); // Set the login time

                            // Retrieve and set the staff name
                            String staffName = stfDtls[2];
                            setStfName(staffName);

                            System.out.println("LOGIN SUCCESSFUL FOR " + getName() + " AT " + loginTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                            return true;
                        }
                    }
                }
                // Close the scanner
            }
            return false;
        } catch (FileNotFoundException ex) {
            System.err.println("Error reading the file: " + ex.getMessage());
            return false;
        }
    }

    private boolean icExists(String filePath, String targetIC) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming the data is tab-separated
                if (parts.length >= 2 && parts[1].equals(targetIC)) {
                    return true; // IC already exists in the file
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        }
        return false; // IC does not exist in the file
    }

    @Override
    public String toString() {
        return "\nSTAFF INFO\n-------------\nSTAFF ID >> S-" + getId()
                + "\nSTAFF NAME: " + getName()
                + "\nSTAFF IC: " + getIc()
                + "\nSATFF AGE: " + getStfAge()
                + "\nSTAFF SALARY: " + getStfSalary();
    }

    public void loadStaffFromFile(String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            staffList.clear(); // Clear the existing staff list

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");

                if (parts.length >= 6) {
                    int id = Integer.parseInt(parts[0]); // Parse the staff ID
                    String ic = parts[1];
                    String name = parts[2];
                    String password = parts[3];
                    int age = Integer.parseInt(parts[4]); // Parse the staff age
                    double salary = Double.parseDouble(parts[5]); // Parse the staff salary

                    // Create a new Staff object and add it to the staffList
                    Staff staff = new Staff(name, ic, age, salary, password);
                    staff.setId(id);
                    staffList.add(staff);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing data from the file: " + e.getMessage());
        }
    }

}
