package com.main_system;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class LoginPage extends JFrame {
    private JPanel contentPane;
    private JTextField emailTextField;
    private JPasswordField passwordTextField;

    public LoginPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
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
        
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 170, 100, 25);
        contentPane.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(220, 170, 100, 25);
        contentPane.add(registerButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String email = emailTextField.getText();
            	String password = new String(passwordTextField.getPassword());
            	
            	if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.");
                    return;
                }
            	
                if (authenticateUser(email,password)) {
                    new HomePage().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password.");
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

    private boolean authenticateUser(String email, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM player_details WHERE Email = ? AND Password = ?")) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}


