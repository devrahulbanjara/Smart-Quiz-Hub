package com.main_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

class RegisterPage extends JFrame {
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    
    public RegisterPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel registerLabel = new JLabel("Register Page");
        registerLabel.setBounds(180, 30, 100, 15);
        contentPane.add(registerLabel);
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(100, 70, 70, 15);
        contentPane.add(nameLabel);
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(100, 100, 70, 15);
        contentPane.add(emailLabel);
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 130, 90, 15);
        contentPane.add(passwordLabel);
        
        nameTextField = new JTextField();
        nameTextField.setBounds(200, 70, 120, 20);
        contentPane.add(nameTextField);
        
        emailTextField = new JTextField();
        emailTextField.setBounds(200, 100, 120, 20);
        contentPane.add(emailTextField);
        
        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(200, 130, 120, 20);
        contentPane.add(passwordTextField);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(100, 180, 120, 25);
        contentPane.add(registerButton);
        
        JButton loginButton = new JButton("Go to Login");
        loginButton.setBounds(230, 180, 120, 25);
        contentPane.add(loginButton);
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText().trim();
                String email = emailTextField.getText().trim();
                String password = new String(passwordTextField.getPassword()).trim();
                
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.");
                    return;
                }
                
                registerUser(name, email, password);
            }
        });
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }

    private void registerUser(String name, String email, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM player_details WHERE Email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Email already exists. Redirecting to login page.");
                new LoginPage().setVisible(true);
                dispose();
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement(
                "INSERT INTO player_details (Name, Email, Password) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, password);
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            int competitorID = -1;
            if (generatedKeys.next()) {
                competitorID = generatedKeys.getInt(1);
            }

            Name playerName = new Name(name, "", "");  // Adjust as necessary
            String competitionLevel = "Beginner";  // Default level
            int age = 18;  // Default age (adjust if needed)
            int[] defaultScores = {0, 0, 0, 0, 0};  // Default empty scores

            Competitor competitor = new Competitor(competitorID, playerName, competitionLevel, age, defaultScores);
            new PlayerHomePage(competitor).setVisible(true);
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in registration: " + ex.getMessage());
        }
    }


}