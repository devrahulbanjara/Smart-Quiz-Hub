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

    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
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
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 200, 100, 25);
        contentPane.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(220, 200, 100, 25);
        contentPane.add(registerButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailTextField.getText();
                String password = new String(passwordTextField.getPassword());

                if (playerRadioButton.isSelected()) {
                    Competitor competitor = fetchCompetitor(email, password);
                    if (competitor != null) {
                        new PlayerHomePage(competitor).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password.");
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
            
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("User authenticated successfully.");
                return true;
            } else {
                System.out.println("Invalid credentials.");
                return false;
            }
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
                String competitionLevel = "Beginner";
                int age = 18;
                int[] scores = {0, 0, 0, 0, 0};  

                Name name = new Name(fullName, "", "");  
                return new Competitor(competitorID, name, competitionLevel, age, scores);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
