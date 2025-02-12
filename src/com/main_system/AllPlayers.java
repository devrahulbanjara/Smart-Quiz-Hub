package com.main_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AllPlayers extends JFrame {
    private JTable playersTable;
    private Competitor competitorFromHomePage;
    private JTextField idInputField;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    public AllPlayers(Competitor competitorFromHomePage) {
        this.competitorFromHomePage = competitorFromHomePage;

        // Basic JFrame setup
        setupFrame();

        // Setup UI components
        setupUIComponents();

        // Load players data into the table
        loadPlayersData();
    }

    // Setup JFrame details
    private void setupFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
    }

    // Setup the UI components like buttons, labels, etc.
    private void setupUIComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(backgroundColor);

        JLabel titleLabel = createLabel("All Players");
        topPanel.add(titleLabel);

        idInputField = createTextField();
        topPanel.add(idInputField);

        JButton showDetailsButton = createButton("Show Full Details");
        topPanel.add(showDetailsButton);

        getContentPane().add(topPanel, BorderLayout.NORTH);

        playersTable = new JTable();
        playersTable.setFont(inputFont);
        playersTable.setForeground(labelColor);
        JScrollPane scrollPane = new JScrollPane(playersTable);
        scrollPane.getViewport().setBackground(backgroundColor);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton backToHomeButton = createBackButton("Back to Home");
        bottomPanel.add(backToHomeButton);

        // Event listeners
        showDetailsButton.addActionListener(e -> onShowDetailsButtonClick());
        backToHomeButton.addActionListener(e -> goToPreviousPage());
    }

    // Create a label with custom styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(titleFont);
        label.setForeground(primaryColor);
        return label;
    }

    // Create a text field for ID input
    private JTextField createTextField() {
        JTextField textField = new JTextField(10);
        textField.setFont(inputFont);
        textField.setForeground(labelColor);
        return textField;
    }

    // Create a button with custom styling
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

    // Create the back button with custom styling
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

    // Show full player details when button is clicked
    private void onShowDetailsButtonClick() {
        String idText = idInputField.getText().trim();
        if (!idText.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int playerId = Integer.parseInt(idText);
        showFullDetails(playerId);
    }

    // Establish database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
    }

    // Load all players data from the database
    private void loadPlayersData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Short Details");

        try (Connection conn = getConnection()) {
            String query = "SELECT p.ID, p.Name, p.competition_level, p.age, " +
                           "COALESCE(c.overall_score, 0) AS overall_score " +
                           "FROM player_details p " +
                           "LEFT JOIN competitor_scores c ON p.ID = c.competitor_id " +
                           "ORDER BY p.ID ASC";

            ResultSet rs = executeQuery(conn, query);

            while (rs.next()) {
                int playerId = rs.getInt("ID");
                String dbName = rs.getString("Name");
                if (dbName == null || dbName.trim().isEmpty()) {
                    dbName = "Unknown";
                }
                Name playerName = new Name(dbName);

                String level = rs.getString("competition_level");
                int overallScore = rs.getInt("overall_score");

                Competitor competitor = new Competitor(playerId, playerName, level, rs.getInt("age"), overallScore);

                model.addRow(new Object[]{playerId, competitor.getShortDetails()});
            }

            playersTable.setModel(model);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading players data: " + ex.getMessage());
        }
    }

    // Execute a database query and return the result set
    private ResultSet executeQuery(Connection conn, String query) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }

    private void showFullDetails(int playerId) {
        try (Connection conn = getConnection()) {
            String query = "SELECT p.ID, p.Name, p.Email, p.competition_level, p.age, " +
                    "c.score1, c.score2, c.score3, c.score4, c.score5 " +
                    "FROM player_details p LEFT JOIN competitor_scores c ON p.ID = c.competitor_id " +
                    "WHERE p.ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbName = rs.getString("Name");
                if (dbName == null || dbName.trim().isEmpty()) {
                    dbName = "Unknown";
                }
                Name playerName = new Name(dbName);

                String level = rs.getString("competition_level");
                int age = rs.getInt("age");

                int[] scores = new int[5];
                for (int i = 1; i <= 5; i++) {
                    scores[i - 1] = rs.getInt("score" + i);
                }

                Competitor competitor = new Competitor(playerId, playerName, level, age, scores);
                JOptionPane.showMessageDialog(null, competitor.getFullDetails(), "Player Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No player found with ID: " + playerId, "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching player details: " + ex.getMessage());
        }
    }

    // Navigate back to the home page
    private void goToPreviousPage() {
        new PlayerHomePage(competitorFromHomePage).setVisible(true);
        dispose();
    }
}
