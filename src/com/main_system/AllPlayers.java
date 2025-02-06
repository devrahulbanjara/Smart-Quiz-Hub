package com.main_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;

public class AllPlayers extends JFrame {
    private JTable playersTable;
    private Competitor competitorFromHomePage;
    private JTextField idInputField;
    
    public AllPlayers(Competitor competitorFromHomePage) {
        this.competitorFromHomePage = competitorFromHomePage;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Create Top Panel for Title and "Show Full Details" button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("All Players");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel);

        idInputField = new JTextField(10);
        topPanel.add(idInputField);

        JButton showDetailsButton = new JButton("Show Full Details");
        topPanel.add(showDetailsButton);

        contentPane.add(topPanel, BorderLayout.NORTH);

        playersTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(playersTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        loadPlayersData();

        JButton previousPageButton = new JButton("Previous Page");
        contentPane.add(previousPageButton, BorderLayout.SOUTH);

        // Event Listener for the "Show Full Details" Button
        showDetailsButton.addActionListener(e -> {
            String idText = idInputField.getText().trim();
            if (!idText.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric ID.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int playerId = Integer.parseInt(idText);
            showFullDetails(playerId);
        });

        // Event Listener for the "Previous Page" Button
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof MouseEvent) {
                    MouseEvent me = (MouseEvent) event;
                    if (me.getSource() == previousPageButton && me.getID() == MouseEvent.MOUSE_CLICKED) {
                        goToPreviousPage();
                    }
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);

        // Double-click to show full details
        playersTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = playersTable.getSelectedRow();
                    if (selectedRow != -1) {
                        int playerId = (int) playersTable.getValueAt(selectedRow, 0);
                        showFullDetails(playerId);
                    }
                }
            }
        });
    }

    private void loadPlayersData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Short Details");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            String query = "SELECT p.ID, p.Name, p.Email, p.competition_level, p.age, " +
                           "c.score1, c.score2, c.score3, c.score4, c.score5 " +
                           "FROM player_details p LEFT JOIN competitor_scores c ON p.ID = c.competitor_id";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int playerId = rs.getInt("ID");
                String dbName = rs.getString("Name");

                dbName = (dbName != null && !dbName.trim().isEmpty()) ? dbName : "Unknown";
                Name playerName = new Name(dbName);

                String level = rs.getString("competition_level");
                int age = rs.getInt("age");

                int[] scores = new int[5];
                boolean scoresExist = false;
                for (int i = 1; i <= 5; i++) {
                    int score = rs.getInt("score" + i);
                    if (!rs.wasNull()) {
                        scores[i - 1] = score;
                        scoresExist = true;
                    } else {
                        scores[i - 1] = 0;
                    }
                }
                if (!scoresExist) {
                    Arrays.fill(scores, 0);
                }

                Competitor competitor = new Competitor(playerId, playerName, level, age, scores);
                model.addRow(new Object[]{playerId, competitor.getShortDetails()});
            }

            playersTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading players data: " + ex.getMessage());
        }
    }

    private void showFullDetails(int playerId) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            String query = "SELECT p.ID, p.Name, p.Email, p.competition_level, p.age, " +
                           "c.score1, c.score2, c.score3, c.score4, c.score5 " +
                           "FROM player_details p LEFT JOIN competitor_scores c ON p.ID = c.competitor_id " +
                           "WHERE p.ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbName = rs.getString("Name");
                dbName = (dbName != null && !dbName.trim().isEmpty()) ? dbName : "Unknown";
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

    private void goToPreviousPage() {
        new PlayerHomePage(competitorFromHomePage).setVisible(true);
        dispose();
    }
}
