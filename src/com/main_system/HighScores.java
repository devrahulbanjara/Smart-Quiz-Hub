package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

class HighScores extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;  // Store competitor object

    public HighScores(Competitor competitor) {
        this.competitor = competitor;  // Pass and store the competitor object

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Create a table to display the high scores
        String[] columns = {"Name", "Level", "Overall Score"};
        Object[][] data = getTopScoresFromDatabase();
        JTable highScoresTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(highScoresTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Top 5 High Scores", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Create a "Previous Page" button
        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.setBounds(180, 300, 120, 30);
        contentPane.add(previousPageButton, BorderLayout.SOUTH);

        // ActionListener for the "Previous Page" button
        previousPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayerHomePage(competitor).setVisible(true);  // Return to PlayerHomePage
                dispose();  // Close current HighScores page
            }
        });
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
