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
        contentPane.setLayout(null);
        setContentPane(contentPane);
        
        JLabel homeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        homeLabel.setBounds(100, 12, 250, 20);
        contentPane.add(homeLabel);

        JLabel lblPlayerName = new JLabel(competitor.getName().getFullName(), SwingConstants.CENTER);
        lblPlayerName.setBounds(50, 33, 350, 20);
        contentPane.add(lblPlayerName);

        JButton viewHighScoresButton = new JButton("View High Scores");
        viewHighScoresButton.setBounds(137, 78, 176, 30);
        contentPane.add(viewHighScoresButton);

        JButton playQuizButton = new JButton("Play Quiz");
        playQuizButton.setBounds(137, 120, 176, 30);
        contentPane.add(playQuizButton);

        JButton viewPlayerDetailsButton = new JButton("View Player Details");
        viewPlayerDetailsButton.setBounds(137, 160, 176, 30);
        contentPane.add(viewPlayerDetailsButton);

        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setBounds(137, 200, 176, 30);
        contentPane.add(backToLoginButton);
        
        viewHighScoresButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HighScores(competitor).setVisible(true);
                dispose();
            }
        });

        playQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayQuiz(competitor);
            }
        });

        viewPlayerDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AllPlayers(competitor).setVisible(true);
                dispose();
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
}
