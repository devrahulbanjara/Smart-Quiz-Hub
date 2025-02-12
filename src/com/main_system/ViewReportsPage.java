package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;

/**
 * This class represents the View Reports page where administrators can view quiz results.
 */
public class ViewReportsPage extends JFrame {
    private QuizReportManager quizReportManager;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton btnGoToAdminHome;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 16);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);
    private Font tableFont = new Font("SansSerif", Font.PLAIN, 14);

    /**
     * Initializes the ViewReportsPage with necessary components.
     */
    public ViewReportsPage() {
        quizReportManager = new QuizReportManager();
        setTitle("View Reports");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);        
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(backgroundColor);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JLabel viewReportsLabel = new JLabel("View Quiz Reports");
        viewReportsLabel.setFont(titleFont);
        viewReportsLabel.setForeground(primaryColor);
        topPanel.add(viewReportsLabel);

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

        String[] columnNames = {"ID", "Name", "Age", "Level", "Overall Score"};
        tableModel = new DefaultTableModel(columnNames, 0);

        resultTable = new JTable(tableModel);
        resultTable.setFont(tableFont);
        resultTable.getTableHeader().setFont(tableFont);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        fullReportButton.addActionListener(e -> fetchFullReport());
        topPerformerButton.addActionListener(e -> displayTopPerformer());
        statisticsButton.addActionListener(e -> displayStatistics());
        searchCompetitorButton.addActionListener(e -> searchCompetitor());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        btnGoToAdminHome = createBackButton("Back to Admin Home");
        bottomPanel.add(btnGoToAdminHome);

        btnGoToAdminHome.addActionListener(e -> {
            dispose();
            new AdminHomePage().setVisible(true);
        });

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates a button with consistent styling.
     * 
     * @param text The text to be displayed on the button.
     * @return The created JButton.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont); 
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

    /**
     * Creates a "Back" button with consistent styling.
     * 
     * @param text The text to be displayed on the button.
     * @return The created JButton.
     */
    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(smallButtonFont);
        button.setBackground(backgroundColor);
        button.setForeground(primaryColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    /**
     * Fetches the full report and displays it in the table.
     */
    private void fetchFullReport() {
        String fullReport = quizReportManager.generateFullReport();
        tableModel.setRowCount(0);

        String[] data = fullReport.split("\n");

        if (data.length > 1) {
            for (int i = 1; i < data.length; i++) {
                String[] rowData = data[i].trim().split(" \\| ");
                if (rowData.length == 5) {
                    tableModel.addRow(rowData);
                }
            }
        } else {
            JOptionPane.showMessageDialog(ViewReportsPage.this, "No data available.", "Report Empty", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Displays the top performer in a message dialog.
     */
    private void displayTopPerformer() {
        String topPerformers = quizReportManager.displayTopPerformer();
        tableModel.setRowCount(0);
        JOptionPane.showMessageDialog(ViewReportsPage.this, topPerformers, "Top Performer", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays the statistics in a message dialog.
     */
    private void displayStatistics() {
        String statistics = quizReportManager.generateStatistics();
        tableModel.setRowCount(0);
        JOptionPane.showMessageDialog(ViewReportsPage.this, statistics, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Searches for a competitor by ID and displays their information.
     */
    private void searchCompetitor() {
        String input = JOptionPane.showInputDialog("Enter Competitor ID:");
        if (input != null && !input.isEmpty()) {
            try {
                int competitorId = Integer.parseInt(input);
                String result = quizReportManager.searchCompetitorById(competitorId);

                String[] data = result.split("\n");

                if (data.length > 0 && data[0].contains("Competitor Found")) {
                    String[] rowData = data[1].split(" \\| ");
                    tableModel.setRowCount(0);
                    tableModel.addRow(rowData);
                } else {
                    JOptionPane.showMessageDialog(ViewReportsPage.this, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ViewReportsPage.this, "Invalid ID entered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
