package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PlayerHomePage extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;

    // Minimalist Color Scheme (Same as RegisterPage)
    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 16);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12); // Smaller font for the back button


    public PlayerHomePage(Competitor competitor) {
        this.competitor = competitor;
        setTitle("Player Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400); // Increased size
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Top Panel for Welcome Message
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Vertical layout
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Padding below the panel
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel homeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        homeLabel.setFont(titleFont);
        homeLabel.setForeground(primaryColor);
        homeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        topPanel.add(homeLabel);

        JLabel lblPlayerName = new JLabel(competitor.getName().getFullName(), SwingConstants.CENTER);
        lblPlayerName.setFont(labelFont);
        lblPlayerName.setForeground(labelColor);
        lblPlayerName.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        topPanel.add(lblPlayerName);

        // Center Panel for Main Buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 20)); // 3 rows, 1 column, spacing
        centerPanel.setBackground(backgroundColor);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JButton viewHighScoresButton = createButton("View High Scores");
        JButton playQuizButton = createButton("Play Quiz");
        JButton viewPlayerDetailsButton = createButton("View Player Details");

        centerPanel.add(viewHighScoresButton);
        centerPanel.add(playQuizButton);
        centerPanel.add(viewPlayerDetailsButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); 
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));  
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        JButton backToLoginButton = createBackButton("Back to Login");
        bottomPanel.add(backToLoginButton);


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

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(buttonFont);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        button.setBackground(backgroundColor);      
        button.setForeground(primaryColor);        
        button.setFocusPainted(false);
        button.setBorderPainted(false);              
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
}
