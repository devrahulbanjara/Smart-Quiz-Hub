package com.main_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class RegisterPage extends JFrame {
    private JPanel contentPane;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField passwordTextField;
    
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
        
        passwordTextField = new JTextField();
        passwordTextField.setBounds(200, 130, 120, 20);
        contentPane.add(passwordTextField);
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 180, 120, 25);
        contentPane.add(registerButton);
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HomePage().setVisible(true);
                dispose();
            }
        });
    }
}