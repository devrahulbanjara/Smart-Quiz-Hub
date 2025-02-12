package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.*;

class LoginPage extends JFrame {
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JRadioButton playerRadioButton;
    private JRadioButton adminRadioButton;

    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login Page");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window

        // Main panel with a modern layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add a logo or image (optional)
        ImageIcon logoIcon = new ImageIcon("path/to/logo.png"); // Replace with your logo path
        JLabel logoLabel = new JLabel(logoIcon);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(logoLabel, gbc);

        // Login label
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(new Color(33, 150, 243)); // Blue color
        gbc.gridy = 1;
        mainPanel.add(loginLabel, gbc);

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(emailLabel, gbc);

        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        mainPanel.add(emailTextField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordTextField = new JPasswordField(20);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding
        ));
        gbc.gridx = 1;
        mainPanel.add(passwordTextField, gbc);

        // Role selection (Player/Admin)
        JPanel rolePanel = new JPanel();
        rolePanel.setBackground(new Color(245, 245, 245));
        playerRadioButton = new JRadioButton("Player", true);
        playerRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        adminRadioButton = new JRadioButton("Admin");
        adminRadioButton.setFont(new Font("Arial", Font.PLAIN, 14));

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(playerRadioButton);
        roleGroup.add(adminRadioButton);

        rolePanel.add(playerRadioButton);
        rolePanel.add(adminRadioButton);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(rolePanel, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        gbc.gridy = 5;
        mainPanel.add(loginButton, gbc);

        // Register button
        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
        gbc.gridy = 6;
        mainPanel.add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = new String(passwordTextField.getPassword());

                // Check if email or password is empty
                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "None of the fields should be empty.");
                    return;
                }

                // Validate email format
                if (!isValidEmail(email)) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.");
                    return;
                }

                // Validate password length
                if (password.length() < 8) {
                    JOptionPane.showMessageDialog(null, "Password must be at least 8 characters.");
                    return;
                }

                if (playerRadioButton.isSelected()) {
                    // Fetch the password from the database and compare
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
                         PreparedStatement stmt = conn.prepareStatement("SELECT Password, ID, Name, competition_level FROM player_details WHERE Email = ?")) {

                        stmt.setString(1, email);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            String dbPassword = rs.getString("Password");

                            if (password.equals(dbPassword)) {
                                int competitorID = rs.getInt("ID");
                                String fullName = rs.getString("Name");
                                String competitionLevel = rs.getString("competition_level");

                                Name name = new Name(fullName);
                                Competitor competitor = new Competitor(competitorID, name, competitionLevel, 0, new int[5]);

                                new PlayerHomePage(competitor).setVisible(true);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Incorrect password.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Player has not registered.");
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Authenticate admin credentials
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

        // Add main panel to the frame
        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(33, 150, 243)); // Blue color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

    private Competitor fetchCompetitor(String email, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player_details WHERE Email = ? AND Password = ?")) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int competitorID = rs.getInt("ID");
                String fullName = rs.getString("Name");
                String competitionLevel = rs.getString("competition_level");

                Name name = new Name(fullName);
                return new Competitor(competitorID, name, competitionLevel, 0, new int[5]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean isValidEmail(String email) {
        // Simple email regex pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
