package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerHomePage extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;

    public PlayerHomePage(Competitor competitor) {
        this.competitor = competitor;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel homeLabel = new JLabel("Welcome to Player Home Page");
        homeLabel.setBounds(150, 30, 200, 20);
        contentPane.add(homeLabel);
        
        JButton viewHighScoresButton = new JButton("View High Scores");
        viewHighScoresButton.setBounds(137, 78, 176, 30);
        contentPane.add(viewHighScoresButton);
        
        JButton playQuizButton = new JButton("Play Quiz");
        playQuizButton.setBounds(137, 120, 176, 30);
        contentPane.add(playQuizButton);
        
        JButton viewPlayerDetailsButton = new JButton("View Player Details");
        viewPlayerDetailsButton.setBounds(137, 160, 176, 30);
        contentPane.add(viewPlayerDetailsButton);
        
        viewHighScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "View High Scores functionality not implemented yet.");
            }
        });
        
        playQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayQuiz(competitor);
            }
        });
        
        viewPlayerDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "View Player Details functionality not implemented yet.");
            }
        });
    }
}
