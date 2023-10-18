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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author evansleong
 */
public abstract class Membership extends Person {

    protected final List<Membership> memberList = new ArrayList<>(); // Store members in a list

    private String memberHp, memberType;
    private double discountRate = 0;
    private static int noOfMember = 0;

    public Membership() {
    }

    public Membership(String name, String ic, int id, String memberHp, String memberType, double discountRate) {
        super(name, ic, id);
        this.memberHp = memberHp;
        this.memberType = memberType;
        this.discountRate = discountRate;
        noOfMember++;
    }

    public String getMemberHp() {
        return memberHp;
    }

    public String getMemberType() {
        return memberType;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static int getNoOfMember() {
        return noOfMember;
    }

    public void setMemberHp(String memberHp) {
        this.memberHp = memberHp;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double calculateDiscountRate() {
        return discountRate;
    }

    public abstract double calDiscount();

    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);
        String memberName, memberHP, memberIC;

        Random random = new Random();
        setId((random.nextInt(898) + 100));

        System.out.println("MEMBER ID >> " + "M-" + getId());
        System.out.println("[THIS IS YOUR MEMBER ID]");

        do {
            System.out.println("Press enter key to continue...");
            scanner.nextLine(); // Consume the newline character
            do {
                System.out.print("ENTER MEMBER IC: ");
                memberIC = scanner.nextLine();
                if (memberIC.matches("\\d{12}")) {
                    // Check if the IC already exists in the file
                    if (icExists("members.txt", memberIC)) {
                        System.out.println("<<<IC already exists in the file. Please reenter!>>>");
                    } else {
                        break; // IC is valid and does not exist in the file
                    }
                } else {
                    System.out.println("<<<Invalid. Please enter 12 digits!>>>");
                }
            } while (true);

            do {
                System.out.print("ENTER MEMBER NAME: ");
                memberName = scanner.nextLine();
                if (memberName.matches("[a-zA-Z ]+")) {
                    break;
                } else {
                    System.out.println("<<<Invalid. Please enter a valid name!>>>");
                }
            } while (true);

            do {
                System.out.print("ENTER MEMBER HP: ");
                memberHP = scanner.nextLine();

                // Check if memberHp contains 10 or 11 digits
                if (memberHP.matches("\\d{10,11}")) {
                    break;
                } else {
                    System.out.println("<<<Invalid input. Please enter a 10 or 11-digit number!>>>");
                }
            } while (true);

            setIc(memberIC);
            setName(memberName);
            setMemberHp(memberHP);

            System.out.println("---------------------------------------------------");
            System.out.println("ARE YOU CONFIRM THE MEMBER DETAILS ABOVE ARE CORRECT ?");
            System.out.print("ENTER YOUR OPTION (Y = YES, ANY KEY TO REPEAT): ");
            char yesNo = scanner.next().charAt(0);

            if (yesNo == 'Y' || yesNo == 'y') {
                try {
                    FileWriter writer = new FileWriter("members.txt", true);
                    writer.write(getName() + "\t");
                    writer.write(getIc() + "\t");
                    writer.write(getMemberHp() + "\t");
                    writer.write(getId() + "\t");
                    writer.write(getMemberType() + "\t");
                    writer.write(calDiscount() + "\t");
                    writer.write("\n");
                    writer.close();
                } catch (IOException e) {
                    System.err.println("Error writing to the file: " + e.getMessage());
                }

                System.out.println("NEW MEMBER ADDED TO THE SYSTEM...");
                System.out.println("---------------------------------------------------");
                System.out.println("MEMBER ID >> " + getId());
                System.out.println("MEMBER IC >> " + getIc());
                System.out.println("MEMBER NAME >> " + getName());
                System.out.println("MEMBER HP >> " + getMemberHp());
                System.out.println("MEMBER TYPE >> " + getMemberType());
                System.out.println("---------------------------------------------------");
                System.out.println("\n");

                break; // Exit the input loop and continue to the next member
            }
        } while (true);
        // You can remove the fileExists check here since it's done before writing.
        // loadMembersFromFile("members.txt"); // Load members if needed.
    }

    @Override
    public void delete() {
        Scanner scanner = new Scanner(System.in);

        logo();
        System.out.println("[ DELETE MEMBER ]");
        System.out.println("-------------------------------------------------------");

        System.out.print("ENTER MEMBER ID TO DELETE (ENTER 'E' TO CANCEL): M-");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("E")) {
            // User wants to cancel and return to the main menu
            clearScreen();
            return;
        }

        try {
            int memberIdToDelete = Integer.parseInt(input);
            File inputFile = new File("members.txt");
            File tempFile = new File("dltTemp.txt");

            boolean found = false;
            try (
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");

                    int dltMemberId = Integer.parseInt(data[3]);

                    if (dltMemberId == memberIdToDelete) {
                        // Display member details to delete
                        System.out.println("Member Details to Delete:");
                        System.out.println("MEMBER ID >> " + data[3]);
                        System.out.println("MEMBER IC >> " + data[1]);
                        System.out.println("MEMBER NAME >> " + data[0]);
                        System.out.println("MEMBER HP >> " + data[2]);
                        System.out.println("MEMBER TYPE >> " + data[4]);
                        System.out.println("-------------------------------------------------------");
                        System.out.print("CONFIRM DELETION? (Y = YES, ANY KEY TO CANCEL): ");
                        char confirm = scanner.next().trim().toUpperCase().charAt(0);

                        if (confirm == 'Y') {
                            found = true;
                            continue; // Skip this line in the output file
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
                    System.out.println("MEMBER WITH ID M-" + memberIdToDelete + " HAS BEEN DELETED.");
                }
            } else {
                System.out.println("MEMBER WITH ID M-" + memberIdToDelete + " NOT FOUND OR DELETION CANCELLED.");
            }

        } catch (NumberFormatException e) {
            System.out.println("<<<Invalid input. Please enter a valid member ID or 'E' to cancel.>>>");
        } catch (IOException e) {
            System.out.println("Error deleting member: " + e.getMessage());
        }
        systemPause();
        clearScreen();
    }

    @Override
    public void view() {
        loadMembersFromFile("members.txt");
        logo();
        System.out.println("[ VIEW ALL MEMBERS ]");
        System.out.println("-------------------------------------------------------");

        if (memberList.isEmpty()) {
            System.out.println("THERE IS NO MEMBER TO DISPLAY...");
        } else {
            boolean validChoice = false;
            while (!validChoice) {
                System.out.println("FILTER MEMBER BY MEMBERSHIP TYPE:");
                System.out.println("1. NORMAL");
                System.out.println("2. GOLD");
                System.out.println("3. PREMIUM");
                System.out.println("E. RETURN TO MENU");
                System.out.print("ENTER YOUR CHOICE (1, 2, 3, E) > ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.next().trim();

                switch (input.toUpperCase()) {
                    case "1":
                        displayMembersByType("Normal");
                        validChoice = true;
                        break;
                    case "2":
                        displayMembersByType("Gold");
                        validChoice = true;
                        break;
                    case "3":
                        displayMembersByType("Premium");
                        validChoice = true;
                        break;
                    case "E":
                        clearScreen();
                        return; // Return to the menu
                    default:
                        System.out.println("<<<Invalid choice. Please try again.>>>");
                }
            }
        }

        systemPause();
        clearScreen();
    }

    @Override
    public void search() {
        loadMembersFromFile("members.txt");
        Scanner scanner = new Scanner(System.in);

        logo();
        System.out.println("[ SEARCH MEMBER BY ID ]");
        System.out.println("-------------------------------------------------------");

        System.out.print("ENTER MEMBER ID TO SEARCH (3 DIGIT ONLY) OR 'E' TO CANCEL: M-");
        String input = scanner.nextLine().trim();

        if (input.equalsIgnoreCase("E")) {
            // User wants to cancel and return to the main menu
            clearScreen();
            return;
        }

        try {
            int memberIdToSearch = Integer.parseInt(input);

            boolean found = false;

            for (Membership member : memberList) {
                if (member.getId() == memberIdToSearch) {
                    found = true;
                    System.out.println("\tMEMBER FOUND !");
                    System.out.println(member.toString());
                    break; // Exit the loop once a member is found
                }
            }

            if (!found) {
                System.out.println("\tMEMBER NOT FOUND...");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid member ID or 'E' to cancel.");
        }

        systemPause();
        System.out.println("\n");
        clearScreen();
    }

    private void displayMembersByType(String membershipType) {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("MEMBER ID |MEMBER IC     |MEMBER HP   |MEMBER TYPE |MEMBER NAME");
        System.out.println("---------------------------------------------------------------------------------------");

        boolean foundMembers = false;

        for (Membership member : memberList) {
            if (membershipType.equals("Normal") && member instanceof NormalMember) {
                printMemberDetails(member);
                foundMembers = true;
            } else if (membershipType.equals("Gold") && member instanceof GoldMember) {
                printMemberDetails(member);
                foundMembers = true;
            } else if (membershipType.equals("Premium") && member instanceof PremiumMember) {
                printMemberDetails(member);
                foundMembers = true;
            }
        }

        if (!foundMembers) {
            System.out.println("NO " + membershipType + " MEMBERS FOUND.");
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private void printMemberDetails(Membership member) {
        System.out.println("M-" + member.getId()
                + "     |" + member.getName()
                + "  |" + member.getMemberHp()
                + "  |" + member.getMemberType()
                + "      |" + member.getIc());
    }

    @Override
    public String toString() {
        return "\nMEMBER INFO\n-------------\nMEMBER ID >> M-" + getId()
                + "\nMEMBER NAME: " + getName()
                + "\nMEMBER IC: " + getIc()
                + "\nMEMBER HP: " + getMemberHp()
                + "\nTYPE OF MEMBERSHIP: " + getMemberType();
    }

    public void loadMembersFromFile(String filePath) {
        memberList.clear(); // Clear the existing data in the ArrayList before loading

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming the data is tab-separated

                if (parts.length >= 6) {
                    String memberIC = parts[0];
                    String memberName = parts[1];
                    String memberHP = parts[2];
                    int memberId = Integer.parseInt(parts[3]);
                    String membershipType = parts[4];
                    double discountRate = Double.parseDouble(parts[5]);

                    Membership member = null;

                    switch (membershipType) {
                        case "Normal" ->
                            member = new NormalMember(memberName, memberIC, memberId, memberHP, membershipType, discountRate);
                        case "Gold" ->
                            member = new GoldMember(memberName, memberIC, memberId, memberHP, membershipType, discountRate);
                        case "Premium" ->
                            member = new PremiumMember(memberName, memberIC, memberId, memberHP, membershipType, discountRate);
                        default -> {
                            continue; // Skip invalid data
                        }
                    }

                    memberList.add(member); // Add the loaded member to the ArrayList
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from the file: " + e.getMessage());
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
}
