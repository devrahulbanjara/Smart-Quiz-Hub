package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class LoginPage extends JFrame {
    private JPanel contentPane;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JRadioButton playerRadioButton;
    private JRadioButton adminRadioButton;
    private JComboBox<String> levelComboBox;

    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel loginLabel = new JLabel("Login Page");
        loginLabel.setBounds(180, 30, 100, 15);
        contentPane.add(loginLabel);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 90, 70, 15);
        contentPane.add(emailLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 120, 90, 15);
        contentPane.add(passwordLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(200, 90, 120, 20);
        contentPane.add(emailTextField);

        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(200, 120, 120, 20);
        contentPane.add(passwordTextField);

        playerRadioButton = new JRadioButton("Player", true);
        playerRadioButton.setBounds(100, 150, 100, 20);
        contentPane.add(playerRadioButton);

        adminRadioButton = new JRadioButton("Admin");
        adminRadioButton.setBounds(220, 150, 100, 20);
        contentPane.add(adminRadioButton);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(playerRadioButton);
        roleGroup.add(adminRadioButton);

        JLabel levelLabel = new JLabel("Select Level");
        levelLabel.setBounds(100, 180, 100, 20);
        contentPane.add(levelLabel);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        levelComboBox = new JComboBox<>(levels);
        levelComboBox.setBounds(200, 180, 120, 20);
        contentPane.add(levelComboBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 220, 100, 25);
        contentPane.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(220, 220, 100, 25);
        contentPane.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = new String(passwordTextField.getPassword());
                String selectedLevel = (String) levelComboBox.getSelectedItem();

                if (playerRadioButton.isSelected()) {
                    Competitor competitor = fetchCompetitor(email, password, selectedLevel);
                    if (competitor != null) {
                        // Check if the level selected is different from the saved level in the database
                        if (!competitor.getCompetitionLevel().equals(selectedLevel)) {
                            // Update the level in the database
                            updateCompetitorLevel(competitor.getCompetitorID(), selectedLevel);
                            competitor.setCompetitionLevel(selectedLevel); // Update in the app
                        }
                        new PlayerHomePage(competitor).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid user email or password.");
                    }
                } else {
                    if (authenticateUser(email, password, "admin_details")) {
                        new AdminHomePage().setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid admin credentials.");
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterPage().setVisible(true);
                dispose();
            }
        });
    }

    private boolean authenticateUser(String email, String password, String tableName) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE Email = ? AND Password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Competitor fetchCompetitor(String email, String password, String selectedLevel) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player_details WHERE Email = ? AND Password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int competitorID = rs.getInt("ID");
                String fullName = rs.getString("Name");

                // Save the selected level to the database if it's not already saved
                saveCompetitorLevel(competitorID, selectedLevel);

                Name name = new Name(fullName);
                return new Competitor(competitorID, name, selectedLevel, 0, new int[5]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void saveCompetitorLevel(int competitorID, String selectedLevel) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement("UPDATE player_details SET competition_level = ? WHERE ID = ?")) {
            stmt.setString(1, selectedLevel);
            stmt.setInt(2, competitorID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCompetitorLevel(int competitorID, String selectedLevel) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement("UPDATE player_details SET competition_level = ? WHERE ID = ?")) {
            stmt.setString(1, selectedLevel);
            stmt.setInt(2, competitorID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
