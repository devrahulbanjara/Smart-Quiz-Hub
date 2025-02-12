package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.*;

/**
 * Represents the registration page for the SmartQuizHub application.
 */
public class RegisterPage extends JFrame {

    private JPanel contentPane;
    public JTextField nameTextField;
    public JTextField emailTextField;
    public JPasswordField passwordTextField;
    public JTextField ageTextField;
    private JComboBox<String> levelComboBox;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Constructs the RegisterPage frame.
     */
    public RegisterPage() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 15));
        mainPanel.setBackground(backgroundColor);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JLabel registerLabel = new JLabel("Create an Account");
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        registerLabel.setForeground(primaryColor);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(registerLabel, BorderLayout.NORTH);

        JLabel nameLabel = createLabel("Full Name:");
        JLabel emailLabel = createLabel("Email:");
        JLabel passwordLabel = createLabel("Password:");
        JLabel ageLabel = createLabel("Age:");
        JLabel levelLabel = createLabel("Level:");

        nameTextField = createTextField();
        emailTextField = createTextField();
        passwordTextField = createPasswordField();
        ageTextField = createTextField();
        levelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setFont(inputFont);
        levelComboBox.setBackground(textFieldBackground);
        levelComboBox.setForeground(labelColor);

        mainPanel.add(nameLabel);
        mainPanel.add(nameTextField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailTextField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordTextField);
        mainPanel.add(ageLabel);
        mainPanel.add(ageTextField);
        mainPanel.add(levelLabel);
        mainPanel.add(levelComboBox);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.setBackground(backgroundColor);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton loginButton = createBackButton("Go to Login");
        JButton registerButton = createButton("Register");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        registerButton.addActionListener(e -> {
            String name = nameTextField.getText().trim();
            String email = emailTextField.getText().trim();
            String password = new String(passwordTextField.getPassword()).trim();
            String ageText = ageTextField.getText().trim();
            String selectedLevel = (String) levelComboBox.getSelectedItem();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidName(name)) {
                JOptionPane.showMessageDialog(null, "Invalid name. First name and last name both required (e.g., Sandeep Sharma).", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid email format. Please enter a valid email.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                if (age < 0) {
                    JOptionPane.showMessageDialog(null, "Age cannot be negative.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                registerUser(name, email, password, selectedLevel, age);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid age.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    /**
     * Creates a JLabel with predefined styling.
     *
     * @param text The label text.
     * @return A configured JLabel.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        return label;
    }

    /**
     * Creates a JTextField with predefined styling.
     *
     * @return A configured JTextField.
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(inputFont);
        textField.setBackground(textFieldBackground);
        textField.setForeground(labelColor);
        textField.setCaretColor(primaryColor);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    /**
     * Creates a JPasswordField with predefined styling.
     *
     * @return A configured JPasswordField.
     */
    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(inputFont);
        passwordField.setBackground(textFieldBackground);
        passwordField.setForeground(labelColor);
        passwordField.setCaretColor(primaryColor);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return passwordField;
    }

    /**
     * Creates a JButton with predefined styling.
     *
     * @param text The button text.
     * @return A configured JButton.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
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

    /**
     * Creates a back JButton with predefined styling.
     *
     * @param text The button text.
     * @return A configured JButton for going back.
     */
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

    /**
     * Validates the name to ensure it contains both first and last names.
     *
     * @param name The name to validate.
     * @return True if the name is valid, false otherwise.
     */
    public boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z]+\\s[A-Za-z]+.*$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Validates the email address format.
     *
     * @param email The email to validate.
     * @return True if the email is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates the password to ensure it is at least 8 characters long.
     *
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    public boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    /**
     * Registers a new user by inserting their details into the database.
     *
     * @param name        The user's full name.
     * @param email       The user's email address.
     * @param password    The user's password.
     * @param selectedLevel The competition level selected by the user.
     * @param age         The user's age.
     */
    public void registerUser(String name, String email, String password, String selectedLevel, int age) {
        String url = "jdbc:mysql://localhost:3306/smartquizhub";
        String username = "root";
        String dbPassword = "root";

        try (Connection connection = DriverManager.getConnection(url, username, dbPassword)) {
            String query = "INSERT INTO users (name, email, password, level, age) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, selectedLevel);
                statement.setInt(5, age);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new LoginPage().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Something went wrong. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while registering: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
