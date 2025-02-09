package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.*;

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
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12); // Smaller font for the back button


    public RegisterPage() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 10, 15));
        mainPanel.setBackground(backgroundColor);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Register Label (centered at the top)
        JLabel registerLabel = new JLabel("Create an Account");
        registerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        registerLabel.setForeground(primaryColor);
        registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(registerLabel, BorderLayout.NORTH);

        // Labels
        JLabel nameLabel = createLabel("Full Name:");
        JLabel emailLabel = createLabel("Email:");
        JLabel passwordLabel = createLabel("Password:");
        JLabel ageLabel = createLabel("Age:");
        JLabel levelLabel = createLabel("Level:");

        // Text Fields
        nameTextField = createTextField();
        emailTextField = createTextField();
        passwordTextField = createPasswordField();
        ageTextField = createTextField();
        levelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setFont(inputFont);
        levelComboBox.setBackground(textFieldBackground);  // Light grey
        levelComboBox.setForeground(labelColor);


        // Add components to mainPanel
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

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); // Align to the Right
        buttonPanel.setBackground(backgroundColor);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Buttons
        JButton loginButton = createBackButton("Go to Login");
        JButton registerButton = createButton("Register"); // Register button on the right

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Action Listeners
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    // Helper methods for creating components with consistent styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(labelColor);  // Grey Label Color
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(inputFont);
        textField.setBackground(textFieldBackground);  // Light grey
        textField.setForeground(labelColor);
        textField.setCaretColor(primaryColor); // I added this for the cursor color
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // added some padding to make the text not so close to the border
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(inputFont);
        passwordField.setBackground(textFieldBackground);  // Light grey
        passwordField.setForeground(labelColor);
        passwordField.setCaretColor(primaryColor); // I added this for the cursor color
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // added some padding to make the text not so close to the border
        return passwordField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // added a hand cursor for better UX
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

    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(smallButtonFont);            
        button.setBackground(backgroundColor);      // Transparent background
        button.setForeground(primaryColor);        // Use primary color for text
        button.setFocusPainted(false);
        button.setBorderPainted(false);              // Remove border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect (optional)
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

    public boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z]+\\s[A-Za-z]+.*$"; // Ensures at least two words
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    public void registerUser(String name, String email, String password, String selectedLevel, int age) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM player_details WHERE Email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Email already exists. Redirecting to login page.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                new LoginPage().setVisible(true);
                dispose();
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO player_details (Name, Email, Password, Competition_Level, Age) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);
            insertStmt.setString(4, selectedLevel);
            insertStmt.setInt(5, age);
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            int competitorID = -1;
            if (generatedKeys.next()) {
                competitorID = generatedKeys.getInt(1);
            }

            Name playerName = new Name(name);
            int[] defaultScores = {0, 0, 0, 0, 0};

            Competitor competitor = new Competitor(competitorID, playerName, selectedLevel, age, defaultScores);
            new PlayerHomePage(competitor).setVisible(true);
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in registration: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RegisterPage frame = new RegisterPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
