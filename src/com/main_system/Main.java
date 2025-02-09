package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Thread guiThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        LoginPage frame = new LoginPage();
                        frame.setVisible(true);
                    }
                });
            }
        });

        Thread consoleThread = new Thread(new Runnable() {
            @Override
            public void run() {
                consoleMenu();
            }
        });

        guiThread.start();
        consoleThread.start();
    }

    private static void consoleMenu() {
        Scanner scanner = new Scanner(System.in);
        QuizReportManager reportManager = new QuizReportManager();

        while (true) {
            System.out.println("\nEnter your choice:");
            System.out.println("1) Generate Full Report");
            System.out.println("2) Display Top Performer");
            System.out.println("3) Generate Statistics");
            System.out.println("4) Search Competitor by ID");
            System.out.println("5) Exit");
            System.out.println();
            
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    reportManager.generateFullReport();
                    break;
                case 2:
                    reportManager.displayTopPerformer();
                    break;
                case 3:
                    reportManager.generateStatistics();
                    break;
                case 4:
                    System.out.print("Enter competitor ID: ");
                    int id = scanner.nextInt();
                    reportManager.searchCompetitorById(id);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
