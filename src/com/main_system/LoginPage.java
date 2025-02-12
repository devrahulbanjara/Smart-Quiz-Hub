package com.main_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.*;

/**
 * The LoginPage class represents the login page where users can log in as either players or admins.
 */
class LoginPage extends JFrame {
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JRadioButton playerRadioButton;
    private JRadioButton adminRadioButton;

    /**
     * Initializes the LoginPage with necessary components.
     */
    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));
        setTitle("Login Page");
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setForeground(new Color(33, 150, 243));
        gbc.gridy = 1;
        mainPanel.add(loginLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(emailLabel, gbc);

        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        mainPanel.add(emailTextField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        passwordTextField = new JPasswordField(20);
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 189, 189)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        mainPanel.add(passwordTextField, gbc);

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

        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        gbc.gridy = 5;
        mainPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
        gbc.gridy = 6;
        mainPanel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String email = emailTextField.getText();
            String password = new String(passwordTextField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "None of the fields should be empty.");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format.");
                return;
            }

            if (password.length() < 8) {
                JOptionPane.showMessageDialog(null, "Password must be at least 8 characters.");
                return;
            }

            if (playerRadioButton.isSelected()) {
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
                if (authenticateUser(email, password, "admin_details")) {
                    new AdminHomePage().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid admin credentials.");
                }
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterPage().setVisible(true);
            dispose();
        });

        add(mainPanel);
    }

    /**
     * Styles the given button with a custom font, background, and border.
     * @param button the button to be styled
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Authenticates a user with the provided email and password by checking against the specified table.
     * @param email the email of the user
     * @param password the password of the user
     * @param tableName the table to check user credentials against
     * @return true if the credentials are valid, false otherwise
     */
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

    /**
     * Fetches a Competitor object based on the provided email and password.
     * @param email the email of the player
     * @param password the password of the player
     * @return the Competitor object if found, null otherwise
     */
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

    /**
     * Validates the provided email against a regular expression for proper email format.
     * @param email the email to be validated
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
