package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * PlayerHomePage is the main interface displayed for a player after logging in.
 * It shows the player's information and allows navigation to various sections like high scores, playing the quiz, and viewing player details.
 */
class PlayerHomePage extends JFrame {
    private JPanel contentPane;
    private Competitor competitor;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 16);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Constructs the PlayerHomePage with the given competitor's details.
     * Sets up the UI components and their behavior.
     * @param competitor The competitor whose information will be displayed.
     */
    public PlayerHomePage(Competitor competitor) {
        this.competitor = competitor;
        setTitle("Player Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel homeLabel = new JLabel("Welcome", SwingConstants.CENTER);
        homeLabel.setFont(titleFont);
        homeLabel.setForeground(primaryColor);
        homeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(homeLabel);

        JLabel lblPlayerName = new JLabel(competitor.getName().getFullName(), SwingConstants.CENTER);
        lblPlayerName.setFont(labelFont);
        lblPlayerName.setForeground(labelColor);
        lblPlayerName.setAlignmentX(Component.CENTER_ALIGNMENT); 
        topPanel.add(lblPlayerName);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 20));
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

        viewHighScoresButton.addActionListener(e -> {
            new HighScores(competitor).setVisible(true);
            dispose();
        });

        playQuizButton.addActionListener(e -> new PlayQuiz(competitor));

        viewPlayerDetailsButton.addActionListener(e -> {
            new AllPlayers(competitor).setVisible(true);
            dispose();
        });

        backToLoginButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    /**
     * Creates a JButton with a given text, styling, and hover effects.
     * @param text The text to display on the button.
     * @return The created JButton.
     */
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

    /**
     * Creates a back button with a given text, styling, and hover effects.
     * @param text The text to display on the back button.
     * @return The created JButton for the back action.
     */
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
