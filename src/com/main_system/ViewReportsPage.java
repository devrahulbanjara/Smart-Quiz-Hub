package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;

public class ViewReportsPage extends JFrame {
    private QuizReportManager quizReportManager;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton btnGoToAdminHome;

    // Minimalist Color Scheme (Consistent with other classes)
    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 16); // Increased button font size
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);
    private Font tableFont = new Font("SansSerif", Font.PLAIN, 14);

    public ViewReportsPage() {
        quizReportManager = new QuizReportManager();
        setTitle("View Reports");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        // Top Panel for Title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JLabel viewReportsLabel = new JLabel("View Quiz Reports");
        viewReportsLabel.setFont(titleFont);
        viewReportsLabel.setForeground(primaryColor);
        topPanel.add(viewReportsLabel);

        // Center Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(backgroundColor);

        JButton fullReportButton = createButton("Full Report");
        JButton topPerformerButton = createButton("Top Performer");
        JButton statisticsButton = createButton("Statistics");
        JButton searchCompetitorButton = createButton("Search Competitor");

        buttonPanel.add(fullReportButton);
        buttonPanel.add(topPerformerButton);
        buttonPanel.add(statisticsButton);
        buttonPanel.add(searchCompetitorButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Table Model
        String[] columnNames = {"ID", "Name", "Age", "Level", "Overall Score"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Result Table
        resultTable = new JTable(tableModel);
        resultTable.setFont(tableFont);
        resultTable.getTableHeader().setFont(tableFont);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Action Listeners for Buttons
        fullReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchFullReport();
            }
        });

        topPerformerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTopPerformer();
            }
        });

        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayStatistics();
            }
        });

        searchCompetitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCompetitor();
            }
        });

        // Bottom Panel for Back to Admin Home Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnGoToAdminHome = createBackButton("Back to Admin Home");
        bottomPanel.add(btnGoToAdminHome);

        // Action Listener for Back to Admin Home Button
        btnGoToAdminHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminHomePage().setVisible(true);
            }
        });

        // Add mainPanel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add bottomPanel to the frame
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper methods for creating components with consistent styling
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont); // Using the updated font here
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });
        return button;
    }

    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(smallButtonFont);
        button.setBackground(backgroundColor);
        button.setForeground(primaryColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect (optional)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(primaryColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(primaryColor);
            }
        });
        return button;
    }

    private void fetchFullReport() {
        String fullReport = quizReportManager.generateFullReport();
        tableModel.setRowCount(0);
        
        // Split the report by new lines
        String[] data = fullReport.split("\n");

        // Check if there are data lines after the title line
        if (data.length > 1) {
            // Start from index 1 to skip the "Full Report:" line
            for (int i = 1; i < data.length; i++) {
                // Trim extra spaces and split each line by " | "
                String[] rowData = data[i].trim().split(" \\| ");
                if (rowData.length == 5) { // Ensure we have all 5 columns
                    tableModel.addRow(rowData);
                }
            }
        } else {
            JOptionPane.showMessageDialog(ViewReportsPage.this, "No data available.", "Report Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    private void displayTopPerformer() {
        String topPerformers = quizReportManager.displayTopPerformer();
        tableModel.setRowCount(0);
        JOptionPane.showMessageDialog(ViewReportsPage.this, topPerformers, "Top Performer", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to display statistics
    private void displayStatistics() {
        String statistics = quizReportManager.generateStatistics();
        tableModel.setRowCount(0); // Clear previous results
        JOptionPane.showMessageDialog(ViewReportsPage.this, statistics, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to search competitor
    private void searchCompetitor() {
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
                    JOptionPane.showMessageDialog(ViewReportsPage.this, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ViewReportsPage.this, "Invalid ID entered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
