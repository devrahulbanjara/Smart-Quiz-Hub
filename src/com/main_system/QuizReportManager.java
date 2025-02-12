package com.main_system;

import java.sql.*;

/**
 * Manages the generation of quiz reports and statistics.
 */
public class QuizReportManager {
    private Connection connection;

    /**
     * Constructs a QuizReportManager object and establishes a database connection.
     */
    public QuizReportManager() {
        this.connection = establishConnection();
    }

    /**
     * Establishes a connection to the database.
     *
     * @return A connection object to the database.
     */
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

    /**
     * Executes a query on the database.
     *
     * @param query The SQL query to execute.
     * @return The result set from the query execution.
     * @throws SQLException If a database access error occurs.
     */
    private ResultSet executeQuery(String query) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        return stmt.executeQuery();
    }

    /**
     * Processes the result set and formats it into a string.
     *
     * @param rs     The result set to process.
     * @param header The header to prepend to the result.
     * @return A formatted string with the result set data.
     * @throws SQLException If a database access error occurs.
     */
    private StringBuilder handleResultSet(ResultSet rs, String header) throws SQLException {
        StringBuilder result = new StringBuilder();
        result.append(header).append("\n");
        while (rs.next()) {
            result.append(formatResultRow(rs)).append("\n");
        }
        return result;
    }

    /**
     * Formats a single row of the result set.
     *
     * @param rs The result set to format.
     * @return A formatted string representing the row.
     * @throws SQLException If a database access error occurs.
     */
    private String formatResultRow(ResultSet rs) throws SQLException {
        StringBuilder row = new StringBuilder();
        row.append(rs.getInt("ID")).append(" | ")
           .append(rs.getString("Name")).append(" | ")
           .append(rs.getInt("Age")).append(" | ")
           .append(rs.getString("competition_level")).append(" | ")
           .append(rs.getInt("overall_score"));
        return row.toString();
    }

    /**
     * Generates a full report of all competitors and their scores.
     *
     * @return A string containing the full report.
     */
    public String generateFullReport() {
        StringBuilder result = new StringBuilder();
        try {
            result.append("Full Report:\n\n");
            result.append("-------------------------------------------\n");
            result.append("| ID | Name | Age | Level | Overall Score |\n");
            result.append("-------------------------------------------");
            String query = "SELECT p.ID, p.Name, p.Age, p.competition_level, " +
                           "COALESCE(s.overall_score, 0) AS overall_score " +
                           "FROM player_details p " +
                           "LEFT JOIN competitor_scores s ON p.ID = s.competitor_id";
            ResultSet rs = executeQuery(query);

            result.append(handleResultSet(rs, ""));
        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error generating report.\n");
        }
        return result.toString();
    }

    /**
     * Displays the top performer for each competition level.
     *
     * @return A string containing the top performers by level.
     */
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

    /**
     * Generates various statistics for the competition.
     *
     * @return A string containing various statistics about the competition.
     */
    public String generateStatistics() {
        StringBuilder result = new StringBuilder();
        try {
            // Query to get total number of players
            String totalPlayersQuery = "SELECT COUNT(*) FROM player_details";
            ResultSet countRs = executeQuery(totalPlayersQuery);
            countRs.next();
            int totalPlayers = countRs.getInt(1);
            result.append("\nTotal number of players: ").append(totalPlayers).append("\n");

            // Query to get highest scores by level
            String highestScoreQuery = "SELECT p.competition_level, MAX(s.overall_score) AS highest_score " +
                    "FROM competitor_scores s " +
                    "JOIN player_details p ON s.competitor_id = p.ID " +
                    "GROUP BY p.competition_level";

            ResultSet highestScoreRs = executeQuery(highestScoreQuery);
            result.append("\nHighest Scores by Level:\n");
            while (highestScoreRs.next()) {
                String level = highestScoreRs.getString("competition_level");
                int highestScore = highestScoreRs.getInt("highest_score");
                result.append("Level: ").append(level).append(" | Highest Score: ").append(highestScore).append("\n");
            }

            // Query to get average score by level
            String averageScoreQuery = "SELECT p.competition_level, AVG(s.overall_score) AS average_score " +
                                        "FROM competitor_scores s " +
                                        "JOIN player_details p ON s.competitor_id = p.ID " +
                                        "GROUP BY p.competition_level";
            ResultSet averageScoreRs = executeQuery(averageScoreQuery);
            result.append("\nAverage Scores by Level:\n");
            while (averageScoreRs.next()) {
                String level = averageScoreRs.getString("competition_level");
                double avgScore = averageScoreRs.getDouble("average_score");
                result.append("Level: ").append(level).append(" | Average Score: ").append(String.format("%.2f", avgScore)).append("\n");
            }

            // Query to get total number of competitors by level
            String totalByLevelQuery = "SELECT p.competition_level, COUNT(*) AS total_competitors " +
                                       "FROM player_details p " +
                                       "GROUP BY p.competition_level";
            ResultSet totalByLevelRs = executeQuery(totalByLevelQuery);
            result.append("\nTotal Competitors by Level:\n");
            while (totalByLevelRs.next()) {
                String level = totalByLevelRs.getString("competition_level");
                int totalCompetitors = totalByLevelRs.getInt("total_competitors");
                result.append("Level: ").append(level).append(" | Total Competitors: ").append(totalCompetitors).append("\n");
            }

            // Query to get the overall highest score
            String overallHighestScoreQuery = "SELECT MAX(s.overall_score) AS overall_highest_score FROM competitor_scores s";
            ResultSet overallHighestScoreRs = executeQuery(overallHighestScoreQuery);
            overallHighestScoreRs.next();
            int overallHighestScore = overallHighestScoreRs.getInt("overall_highest_score");
            result.append("\nOverall Highest Score: ").append(overallHighestScore).append("\n");

        } catch (SQLException e) {
            e.printStackTrace();
            result.append("Error generating statistics.\n");
        }
        return result.toString();
    }

    /**
     * Searches for a competitor by their ID.
     *
     * @param competitorId The ID of the competitor to search for.
     * @return A string containing the details of the competitor, or an error message if not found.
     */
    public String searchCompetitorById(int competitorId) {
        StringBuilder result = new StringBuilder();
        try {
            String query = "SELECT p.ID, p.Name, p.age, p.competition_level, s.overall_score " +
                           "FROM player_details p " +
                           "LEFT JOIN competitor_scores s ON p.ID = s.competitor_id " +
                           "WHERE p.ID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, competitorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.append("Competitor Found:\n");
                result.append(formatResultRow(rs));
                Integer overallScore = rs.getObject("overall_score") == null ? null : rs.getInt("overall_score");
                if (overallScore == null) {
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
