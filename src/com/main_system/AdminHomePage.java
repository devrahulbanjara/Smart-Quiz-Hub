package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AdminHomePage class that represents the home page of the admin interface.
 */
public class AdminHomePage extends JFrame {
    private JPanel contentPane;
    private JButton btnAddQuestion;
    private JButton btnUpdateQuestion;
    private JButton btnDeleteQuestion;
    private JButton btnViewReports;
    private JButton btnGoToLogin; 

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Constructs the AdminHomePage window.
     */
    public AdminHomePage() {
        setTitle("Admin Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel lblAdminHome = new JLabel("Admin Home Page");
        lblAdminHome.setFont(titleFont);
        lblAdminHome.setForeground(primaryColor);
        topPanel.add(lblAdminHome);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 20));
        centerPanel.setBackground(backgroundColor);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        btnAddQuestion = createButton("Add Question");
        btnUpdateQuestion = createButton("Update Question");
        btnDeleteQuestion = createButton("Delete Question");
        btnViewReports = createButton("View Reports");

        centerPanel.add(btnAddQuestion);
        centerPanel.add(btnUpdateQuestion);
        centerPanel.add(btnDeleteQuestion);
        centerPanel.add(btnViewReports);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        btnGoToLogin = createBackButton("Go to Login");
        bottomPanel.add(btnGoToLogin);

        btnAddQuestion.addActionListener(e -> {
            new AddQuestionPage().setVisible(true);
            dispose();
        });

        btnUpdateQuestion.addActionListener(e -> {
            new UpdateQuestionPage().setVisible(true);
            dispose();
        });

        btnDeleteQuestion.addActionListener(e -> {
            new DeleteQuestionPage().setVisible(true);
            dispose();
        });

        btnViewReports.addActionListener(e -> {
            new ViewReportsPage().setVisible(true);
            dispose();
        });

        btnGoToLogin.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    /**
     * Creates a button with a specified text and styling.
     *
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
     * Creates a back button with a specified text and styling.
     *
     * @param text The text to display on the button.
     * @return The created JButton.
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
