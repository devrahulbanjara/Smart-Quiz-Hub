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

    public String generateFullReport() {
        StringBuilder result = new StringBuilder();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT p.ID, p.Name, p.Age, p.competition_level, s.overall_score " +
                "FROM player_details p " +
                "INNER JOIN competitor_scores s ON p.ID = s.competitor_id"
            );

            ResultSet rs = stmt.executeQuery();
            
            result.append("Full Report:\n");
            result.append("ID | Name | Age | Level | Overall Score\n");
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String level = rs.getString("competition_level");
                int overallScore = rs.getInt("overall_score");
                result.append(id).append(" | ").append(name).append(" | ").append(age)
                      .append(" | ").append(level).append(" | ").append(overallScore).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error generating report.\n");
        }
        return result.toString();
    }

    public String displayTopPerformer() {
        StringBuilder result = new StringBuilder();
        try {
            String query = "SELECT p.competition_level, p.Name, MAX(s.overall_score) AS highest_score " +
                           "FROM player_details p " +
                           "JOIN competitor_scores s ON p.ID = s.competitor_id " +
                           "GROUP BY p.competition_level ORDER BY highest_score DESC";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            result.append("\nTop Performers by Level:\n");
            while (rs.next()) {
                String level = rs.getString("competition_level");
                String name = rs.getString("Name");
                int highestScore = rs.getInt("highest_score");
                result.append("Level: ").append(level).append(" | Name: ").append(name)
                      .append(" | Highest Score: ").append(highestScore).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error fetching top performers.\n");
        }
        return result.toString();
    }

    public String generateStatistics() {
        StringBuilder result = new StringBuilder();
        try {
            // Total number of players
            PreparedStatement countStmt = connection.prepareStatement("SELECT COUNT(*) FROM player_details");
            ResultSet countRs = countStmt.executeQuery();
            countRs.next();
            int totalPlayers = countRs.getInt(1);
            result.append("\nTotal number of players: ").append(totalPlayers).append("\n");

            // Highest score per level
            String statsQuery = "SELECT p.competition_level, MAX(s.overall_score) AS highest_score " +
                                 "FROM competitor_scores s " +
                                 "JOIN player_details p ON s.competitor_id = p.ID " +
                                 "GROUP BY p.competition_level";
            PreparedStatement statsStmt = connection.prepareStatement(statsQuery);
            ResultSet statsRs = statsStmt.executeQuery();

            result.append("\nHighest Scores by Level:\n");
            while (statsRs.next()) {
                String level = statsRs.getString("competition_level");
                int highestScore = statsRs.getInt("highest_score");
                result.append("Level: ").append(level).append(" | Highest Score: ").append(highestScore).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error generating statistics.\n");
        }
        return result.toString();
    }

    public String searchCompetitorById(int competitorId) {
        StringBuilder result = new StringBuilder();
        try {
            System.out.println("Searching for Competitor with ID: " + competitorId);  // Log the competitorId

            // Use LEFT JOIN so that even if no entry exists in competitor_scores, we still get the player details
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT p.ID, p.Name, p.Age, p.competition_level, s.overall_score " +
                "FROM player_details p " +
                "LEFT JOIN competitor_scores s ON p.ID = s.competitor_id " +
                "WHERE p.ID = ?"
            );
            stmt.setInt(1, competitorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String level = rs.getString("competition_level");
                Integer overallScore = rs.getObject("overall_score") == null ? null : rs.getInt("overall_score"); // Handle null case

                result.append("Competitor Found:\n")
                      .append("ID: ").append(id).append(" | Name: ").append(name)
                      .append(" | Age: ").append(age).append(" | Level: ").append(level);

                if (overallScore != null) {
                    result.append(" | Overall Score: ").append(overallScore);
                } else {
                    result.append(" | Overall Score: N/A (No quiz taken)");
                }

                result.append("\n");
            } else {
                result.append("Competitor with ID ").append(competitorId).append(" not found.\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error fetching competitor data.\n");
        }
        return result.toString();
    }

}
