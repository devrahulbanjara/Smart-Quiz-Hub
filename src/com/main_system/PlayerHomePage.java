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

        // Back to Login Button
        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setBounds(137, 200, 176, 30);
        contentPane.add(backToLoginButton);

        // Action for View High Scores
        viewHighScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HighScores(competitor).setVisible(true);  // Pass the competitor to HighScores
                dispose();
            }
        });

        // Action for Play Quiz (You can modify this action accordingly)
        playQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayQuiz(competitor);  // Assuming PlayQuiz requires Competitor object
            }
        });

        // Action for View Player Details
        viewPlayerDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AllPlayers(competitor).setVisible(true);
                dispose();
            }
        });

        // Action for Back to Login
        backToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
}
