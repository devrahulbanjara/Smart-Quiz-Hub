package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

class HighScores extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;  // Store competitor object
    private JButton btnGoBackHome;

    // Minimalist Color Scheme (Consistent with other classes)
    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);
    private Font tableFont = new Font("SansSerif", Font.PLAIN, 14);

    public HighScores(Competitor competitor) {
        this.competitor = competitor;  // Pass and store the competitor object

        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Top Panel for Title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Top 5 High Scores");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryColor);
        topPanel.add(titleLabel);

        // Center Panel for High Scores Table
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(backgroundColor);

        // Table
        String[] columns = {"Name", "Level", "Overall Percentage"};
        Object[][] data = getTopScoresFromDatabase();
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable highScoresTable = new JTable(tableModel);
        highScoresTable.setFont(tableFont);
        JTableHeader tableHeader = highScoresTable.getTableHeader();
        tableHeader.setFont(tableFont);
        highScoresTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(highScoresTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel for Back to Home Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        btnGoBackHome = createBackButton("Back to Home");
        bottomPanel.add(btnGoBackHome);


        // ActionListener for the "Back to Home" button
        btnGoBackHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayerHomePage(competitor).setVisible(true);  // Return to PlayerHomePage
                dispose();  // Close current HighScores page
            }
        });
    }


    // Helper Methods
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(titleFont);
        label.setForeground(primaryColor);
        return label;
    }

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


    private Object[][] getTopScoresFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
        String username = "root";
        String password = "3241";
        List<Object[]> scoresList = new ArrayList<>();

        String query = "SELECT name, level, overall_score FROM competitor_scores ORDER BY overall_score DESC LIMIT 5";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String level = resultSet.getString("level");
                int overallScore = resultSet.getInt("overall_score");
                scoresList.add(new Object[]{name, level, overallScore});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoresList.toArray(new Object[0][0]);
    }
}
