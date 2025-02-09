package com.main_system;

import java.sql.*;

public class QuizReportManager {
    private Connection connection;

    public QuizReportManager() {
        this.connection = establishConnection();
    }

    private Connection establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
            String username = "root";
            String password = "3241";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void generateFullReport() {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT p.ID, p.Name, p.Age, p.competition_level, s.overall_score " +
                "FROM player_details p " +
                "INNER JOIN competitor_scores s ON p.ID = s.competitor_id"
            );

            ResultSet rs = stmt.executeQuery();
            
            System.out.println("Full Report:");
            System.out.println("ID | Name | Age | Level | Overall Score");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String level = rs.getString("competition_level");
                int overallScore = rs.getInt("overall_score");
                System.out.println(id + " | " + name + " | " + age + " | " + level + " | " + overallScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayTopPerformer() {
        try {
            String query = "SELECT p.Name, p.competition_level, MAX(s.overall_score) AS highest_score FROM player_details p " +
                           "JOIN competitor_scores s ON p.ID = s.competitor_id GROUP BY p.competition_level ORDER BY highest_score DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nTop Performers by Level:");
            while (rs.next()) {
                String name = rs.getString("Name");
                String level = rs.getString("competition_level");
                int score = rs.getInt("highest_score");
                System.out.println("Level: " + level + " | Name: " + name + " | Highest Score: " + score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void generateStatistics() {
        try {
            // Total number of players
            PreparedStatement countStmt = connection.prepareStatement("SELECT COUNT(*) FROM player_details");
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int totalPlayers = countRs.getInt(1);
            System.out.println("\nTotal number of players: " + totalPlayers);

            // Highest score per level
            String statsQuery = "SELECT p.competition_level, MAX(s.overall_score) AS highest_score " +
                                 "FROM competitor_scores s " +
                                 "JOIN player_details p ON s.competitor_id = p.ID " +
                                 "GROUP BY p.competition_level";
            PreparedStatement statsStmt = connection.prepareStatement(statsQuery);
            ResultSet statsRs = statsStmt.executeQuery();

            System.out.println("\nStatistics:");
            while (statsRs.next()) {
                String level = statsRs.getString("competition_level");
                int highestScore = statsRs.getInt("highest_score");
                System.out.println("Level: " + level + " | Highest Score: " + highestScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchCompetitorById(int competitorId) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT p.ID, p.Name, p.Age, p.competition_level, s.overall_score FROM player_details p " +
                "INNER JOIN competitor_scores s ON p.ID = s.competitor_id WHERE p.ID = ?"
            );
            stmt.setInt(1, competitorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String level = rs.getString("competition_level");
                int overallScore = rs.getInt("overall_score");
                System.out.println("Competitor Found:");
                System.out.println("ID: " + id + " | Name: " + name + " | Age: " + age + " | Level: " + level + " | Overall Score: " + overallScore);
            } else {
                System.out.println("Competitor with ID " + competitorId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
