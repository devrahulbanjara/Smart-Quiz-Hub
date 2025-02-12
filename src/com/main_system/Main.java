package com.main_system;

import javax.swing.*;
import java.util.Scanner;

/**
 * The Main class contains the entry point of the application and initiates the login page and console menu.
 */
public class Main {
    
    /**
     * The main method that runs the application. It creates and displays the LoginPage and launches the console menu.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginPage frame = new LoginPage();
                frame.setVisible(true);
            }
        });

        consoleMenu();
    }

    /**
     * Displays the console menu for managing competitor reports.
     * It handles user input and calls the appropriate methods from the QuizReportManager.
     */
    private static void consoleMenu() {
        Scanner scanner = new Scanner(System.in);
        QuizReportManager reportManager = new QuizReportManager();

        while (true) {
            System.out.println("---Competitor Management System---");
            System.out.println("\nEnter your choice:");
            System.out.println("1) Generate Full Report");
            System.out.println("2) Display Top Performer");
            System.out.println("3) Generate Statistics");
            System.out.println("4) Search Competitor by ID");
            System.out.println("5) Exit");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= 5) {
                    switch (choice) {
                        case 1:
                            System.out.println(reportManager.generateFullReport());
                            break;
                        case 2:
                            System.out.println(reportManager.displayTopPerformer());
                            break;
                        case 3:
                            System.out.println(reportManager.generateStatistics());
                            break;
                        case 4:
                            System.out.print("Enter competitor ID: ");
                            int id = scanner.nextInt();
                            System.out.println(reportManager.searchCompetitorById(id));
                            break;
                        case 5:
                            System.out.println("Exiting...");
                            System.exit(0);
                            break;
                    }
                } else {
                    System.out.println("Invalid input, please input between 1 to 5.");
                }
            } else {
                System.out.println("Invalid input, please input between 1 to 5.");
                scanner.next();
            }
        }
    }
}
