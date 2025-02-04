package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerHomePage extends JFrame {
    private JPanel contentPane;
    
    public PlayerHomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel homeLabel = new JLabel("Welcome to Player Home Page");
        homeLabel.setBounds(150, 30, 200, 20);
        contentPane.add(homeLabel);
        
        JButton viewHighScoresButton = new JButton("View High Scores");
        viewHighScoresButton.setBounds(150, 80, 150, 30);
        contentPane.add(viewHighScoresButton);
        
        JButton playQuizButton = new JButton("Play Quiz");
        playQuizButton.setBounds(150, 120, 150, 30);
        contentPane.add(playQuizButton);
        
        JButton viewPlayerDetailsButton = new JButton("View Player Details");
        viewPlayerDetailsButton.setBounds(150, 160, 150, 30);
        contentPane.add(viewPlayerDetailsButton);
        
        viewHighScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "View High Scores functionality not implemented yet.");
            }
        });
        
        playQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Play Quiz functionality not implemented yet.");
            }
        });
        
        viewPlayerDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "View Player Details functionality not implemented yet.");
            }
        });
    }
}
