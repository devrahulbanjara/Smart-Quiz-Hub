package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllPlayers extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;  // Store competitor object

    public AllPlayers(Competitor competitor) {
        this.competitor = competitor;  // Pass and store the competitor object

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Get player details from database
        List<Competitor> competitors = getAllPlayersFromDatabase();
        
        // Prepare data to display in JTable
        String[] columns = {"Player ID", "Full Details", "Short Details"};
        Object[][] data = new Object[competitors.size()][3];

        // Populate data for JTable using Competitor's methods
        for (int i = 0; i < competitors.size(); i++) {
            Competitor c = competitors.get(i);
            data[i][0] = c.getCompetitorID();
            data[i][1] = c.getFullDetails();  // Full Details using getFullDetails() method
            data[i][2] = c.getShortDetails();  // Short Details using getShortDetails() method
        }

        // Create table with data
        JTable playersTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(playersTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("All Players", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Create a "Previous Page" button
        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.setBounds(220, 300, 120, 30);
        contentPane.add(previousPageButton, BorderLayout.SOUTH);

        // Action listener for the "Previous Page" button
        previousPageButton.addActionListener(e -> {
            new PlayerHomePage(competitor).setVisible(true);  // Return to PlayerHomePage
            dispose();  // Close current AllPlayers page
        });
    }

    private List<Competitor> getAllPlayersFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
        String username = "root";
        String password = "3241";
        List<Competitor> competitors = new ArrayList<>();
        
        String query = "SELECT p.ID, p.Name, p.Email, p.Password, c.competition_level, c.age, c.scores FROM player_details p JOIN competitor_scores c ON p.ID = c.competitor_id";
        
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                int competitorID = resultSet.getInt("ID");
                String nameStr = resultSet.getString("Name");
                String[] nameParts = nameStr.split(" ");
                Name name = new Name(nameParts[0], nameParts[1], nameParts[2]);  // Assuming name is stored as "first middle last"
                String competitionLevel = resultSet.getString("competitionLevel");
                int age = resultSet.getInt("age");
                String scoresStr = resultSet.getString("scores");
                int[] scores = parseScores(scoresStr);
                
                Competitor competitor = new Competitor(competitorID, name, competitionLevel, age, scores);
                competitors.add(competitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return competitors;
    }

    private int[] parseScores(String scoresStr) {
        String[] scoresArray = scoresStr.split(",");
        int[] scores = new int[scoresArray.length];
        for (int i = 0; i < scoresArray.length; i++) {
            scores[i] = Integer.parseInt(scoresArray[i].trim());
        }
        return scores;
    }
}
