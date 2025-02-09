package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ViewReportsPage extends JFrame {
    private QuizReportManager quizReportManager;

    public ViewReportsPage() {
        quizReportManager = new QuizReportManager();
        setTitle("View Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        // Create buttons
        JButton fullReportButton = new JButton("Full Report");
        JButton topPerformerButton = new JButton("Top Performer");
        JButton statisticsButton = new JButton("Statistics");
        JButton searchCompetitorButton = new JButton("Search Competitor");

        // Add buttons to the panel
        buttonPanel.add(fullReportButton);
        buttonPanel.add(topPerformerButton);
        buttonPanel.add(statisticsButton);
        buttonPanel.add(searchCompetitorButton);

        // Create the table to display results
        String[] columnNames = {"ID", "Name", "Age", "Level", "Overall Score"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        // Add components to the main frame
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the "Previous Page" button and add it to the bottom
        JPanel bottomPanel = new JPanel();
        JButton previousPageButton = new JButton("Previous Page");
        bottomPanel.add(previousPageButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set the action listeners for the buttons
        fullReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch full report data
                String fullReport = quizReportManager.generateFullReport();
                tableModel.setRowCount(0); // Clear previous results
                String[] data = fullReport.split("\n");

                for (int i = 1; i < data.length; i++) {
                    String[] rowData = data[i].split(" \\| ");
                    tableModel.addRow(rowData); // Add each row to the table
                }
            }
        });

        topPerformerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String topPerformers = quizReportManager.displayTopPerformer();
                tableModel.setRowCount(0); // Clear previous results
                JOptionPane.showMessageDialog(ViewReportsPage.this, topPerformers);
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String statistics = quizReportManager.generateStatistics();
                tableModel.setRowCount(0); // Clear previous results
                JOptionPane.showMessageDialog(ViewReportsPage.this, statistics);
            }
        });

        searchCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter Competitor ID:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int competitorId = Integer.parseInt(input);
                        String result = quizReportManager.searchCompetitorById(competitorId);

                        // Parse the result and display it in the table
                        String[] data = result.split("\n");

                        if (data.length > 0 && data[0].contains("Competitor Found")) {
                            // Parse the result into table data
                            String[] rowData = data[1].split(" \\| ");
                            tableModel.setRowCount(0); // Clear previous results
                            tableModel.addRow(rowData); // Add the competitor details as a row in the table
                        } else {
                            JOptionPane.showMessageDialog(ViewReportsPage.this, result);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ViewReportsPage.this, "Invalid ID entered.");
                    }
                }
            }
        });

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new AdminHomePage().setVisible(true);
            }
        });


        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ViewReportsPage();
            }
        });
    }
}
