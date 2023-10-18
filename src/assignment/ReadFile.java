/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author LYL
 */
public class ReadFile {

    public String verifyMember(int compareMemberID) {
        try (Scanner fileScanner = new Scanner(new File("members.txt"))) {
            while (fileScanner.hasNextLine()) {
                String memberLine = fileScanner.nextLine();
                String[] memberDetails = memberLine.split("\t");

                int memberID = Integer.parseInt(memberDetails[3]);

                if (memberID == compareMemberID) {
                    return memberDetails[4];
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return "INVALID";
    }

}
