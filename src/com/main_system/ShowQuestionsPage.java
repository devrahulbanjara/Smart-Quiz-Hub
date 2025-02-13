package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.Border;

/**
 * Represents the page where quiz questions are displayed based on the selected level.
 */
public class ShowQuestionsPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> levelDropdown;
    private JTextArea questionArea;
    private JButton btnGoBack;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);
    private Font textAreaFont = new Font("SansSerif", Font.PLAIN, 16);

    /**
     * Constructs the ShowQuestionsPage frame.
     */
    public ShowQuestionsPage() {
        setTitle("Quiz Questions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setMinimumSize(new Dimension(800, 600));

        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel levelLabel = createLabel("Select Level:");
        topPanel.add(levelLabel);

        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        levelDropdown = new JComboBox<>(levels);
        levelDropdown.setFont(inputFont);
        levelDropdown.setBackground(textFieldBackground);
        levelDropdown.setForeground(labelColor);
        levelDropdown.addActionListener(e -> fetchQuestions());
        topPanel.add(levelDropdown);

        questionArea = new JTextArea();
        questionArea.setFont(textAreaFont);
        questionArea.setEditable(false);
        questionArea.setBackground(textFieldBackground);
        questionArea.setForeground(labelColor);

        Border border = BorderFactory.createLineBorder(primaryColor, 1);
        questionArea.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5)));

        JScrollPane scrollPane = new JScrollPane(questionArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        btnGoBack = createBackButton("Go Back");
        bottomPanel.add(btnGoBack);

        btnGoBack.addActionListener(e -> dispose());

        fetchQuestions();
    }

    /**
     * Creates a JLabel with predefined styling.
     *
     * @param text The text for the label.
     * @return A configured JLabel.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        return label;
    }

    /**
     * Creates a JButton with predefined styling.
     *
     * @param text The text for the button.
     * @return A configured JButton.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFont(inputFont);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(primaryColor.brighter());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(primaryColor);
            }
        });
        return button;
    }

    /**
     * Creates a back button with predefined styling.
     *
     * @param text The text for the back button.
     * @return A configured JButton for going back.
     */
    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(smallButtonFont);
        button.setBackground(backgroundColor);
        button.setForeground(primaryColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setForeground(primaryColor.brighter());
            }

            public void mouseExited(MouseEvent evt) {
                button.setForeground(primaryColor);
            }
        });
        return button;
    }

    /**
     * Fetches and displays the questions from the database based on the selected level.
     */
    private void fetchQuestions() {
        String selectedLevel = (String) levelDropdown.getSelectedItem();
        questionArea.setText("");

        String query = "SELECT question_id, question_text FROM quiz_questions WHERE level = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241");
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, selectedLevel);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                questionArea.append("ID: " + rs.getInt("question_id") + " - " + rs.getString("question_text") + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
