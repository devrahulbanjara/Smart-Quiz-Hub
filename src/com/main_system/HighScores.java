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
    private Competitor competitor;
    private JButton btnGoBackHome;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);
    private Font tableFont = new Font("SansSerif", Font.PLAIN, 14);

    public HighScores(Competitor competitor) {
        this.competitor = competitor;

        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Top 3 High Scores by Level");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(primaryColor);
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(backgroundColor);

        String[] columns = {"Name", "Level", "Overall Percentage"};

        // Create and add tables for each level
        JTable beginnerTable = createHighScoresTable(getTopScoresFromDatabase("Beginner", 3), columns);
        JTable intermediateTable = createHighScoresTable(getTopScoresFromDatabase("Intermediate", 3), columns);
        JTable advancedTable = createHighScoresTable(getTopScoresFromDatabase("Advanced", 3), columns);

        JScrollPane beginnerScrollPane = new JScrollPane(beginnerTable);
        JScrollPane intermediateScrollPane = new JScrollPane(intermediateTable);
        JScrollPane advancedScrollPane = new JScrollPane(advancedTable);

        centerPanel.add(createTablePanel("Beginner Level", beginnerScrollPane));
        centerPanel.add(createTablePanel("Intermediate Level", intermediateScrollPane));
        centerPanel.add(createTablePanel("Advanced Level", advancedScrollPane));

        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        btnGoBackHome = createBackButton("Back to Home");
        bottomPanel.add(btnGoBackHome);

        btnGoBackHome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayerHomePage(competitor).setVisible(true); 
                dispose();
            }
        });
    }

    private JPanel createTablePanel(String title, JScrollPane tableScrollPane) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(backgroundColor);
        
        JLabel titleLabel = createLabel(title);
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(titleFont);
        label.setForeground(primaryColor);
        return label;
    }

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

    private JTable createHighScoresTable(Object[][] data, String[] columns) {
        DefaultTableModel tableModel = new DefaultTableModel(data, columns);
        JTable highScoresTable = new JTable(tableModel);
        highScoresTable.setFont(tableFont);
        JTableHeader tableHeader = highScoresTable.getTableHeader();
        tableHeader.setFont(tableFont);
        highScoresTable.setRowHeight(30);
        return highScoresTable;
    }

    private Object[][] getTopScoresFromDatabase(String level, int limit) {
        String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
        String username = "root";
        String password = "3241";
        List<Object[]> scoresList = new ArrayList<>();

        String query = "SELECT name, level, overall_score FROM competitor_scores WHERE level = ? ORDER BY overall_score DESC LIMIT ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, level);
            statement.setInt(2, limit);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String scoreLevel = resultSet.getString("level");
                    int overallScore = resultSet.getInt("overall_score");
                    scoresList.add(new Object[]{name, scoreLevel, overallScore});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoresList.toArray(new Object[0][0]);
    }
}
