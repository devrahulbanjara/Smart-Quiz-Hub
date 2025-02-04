package com.main_system;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class HomePage extends JFrame {
    private JPanel contentPane;
    
    public HomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel homeLabel = new JLabel("Welcome to Home Page");
        homeLabel.setBounds(150, 100, 200, 20);
        contentPane.add(homeLabel);
    }
}