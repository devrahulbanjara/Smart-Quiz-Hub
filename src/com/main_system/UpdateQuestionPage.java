package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateQuestionPage extends JFrame {
    private JPanel contentPane;
    private JTextField questionIdTextField, questionTextField, option1TextField, option2TextField, option3TextField, option4TextField;
    private JComboBox<String> correctOptionComboBox, levelComboBox;
    private JButton btnGoToAdminHome;
    private JButton btnShowQuestions;

    // Minimalist Color Scheme (Same as other classes)
    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    public UpdateQuestionPage() {
        setTitle("Update Question");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 550); // Adjusted size
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Top Panel for Title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel updateQuestionLabel = new JLabel("Update Question");
        updateQuestionLabel.setFont(titleFont);
        updateQuestionLabel.setForeground(primaryColor);
        topPanel.add(updateQuestionLabel);

        // Center Panel for Input Fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(9, 2, 10, 10)); // Adjusted rows for Question ID
        inputPanel.setBackground(backgroundColor);
        contentPane.add(inputPanel, BorderLayout.CENTER);

        // Labels
        JLabel questionIdLabel = createLabel("Question ID:");
        JLabel questionTextLabel = createLabel("Question Text:");
        JLabel option1Label = createLabel("Option 1:");
        JLabel option2Label = createLabel("Option 2:");
        JLabel option3Label = createLabel("Option 3:");
        JLabel option4Label = createLabel("Option 4:");
        JLabel correctOptionLabel = createLabel("Correct Option:");
        JLabel levelLabel = createLabel("Level:");

        // Text Fields
        questionIdTextField = createTextField();
        questionTextField = createTextField();
        option1TextField = createTextField();
        option2TextField = createTextField();
        option3TextField = createTextField();
        option4TextField = createTextField();

        // Combo Boxes
        correctOptionComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        correctOptionComboBox.setFont(inputFont);
        correctOptionComboBox.setBackground(textFieldBackground);
        correctOptionComboBox.setForeground(labelColor);

        levelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setFont(inputFont);
        levelComboBox.setBackground(textFieldBackground);
        levelComboBox.setForeground(labelColor);
        
        btnShowQuestions = createButton("Show Questions");


        // Add components to inputPanel
        inputPanel.add(questionIdLabel);
        inputPanel.add(questionIdTextField);
        inputPanel.add(btnShowQuestions);
        inputPanel.add(new JLabel(""));

        inputPanel.add(questionTextLabel);
        inputPanel.add(questionTextField);

        inputPanel.add(option1Label);
        inputPanel.add(option1TextField);

        inputPanel.add(option2Label);
        inputPanel.add(option2TextField);

        inputPanel.add(option3Label);
        inputPanel.add(option3TextField);

        inputPanel.add(option4Label);
        inputPanel.add(option4TextField);

        inputPanel.add(correctOptionLabel);
        inputPanel.add(correctOptionComboBox);

        inputPanel.add(levelLabel);
        inputPanel.add(levelComboBox);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10)); // Aligned to the right
        buttonPanel.setBackground(backgroundColor);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Buttons
        JButton updateButton = createButton("Update Question");
        btnGoToAdminHome = createBackButton("Go to Admin Home"); // "Go to Admin Home" button

        buttonPanel.add(btnGoToAdminHome);
        buttonPanel.add(updateButton);


        // Action Listeners
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestionInDatabase();
            }
        });

        btnGoToAdminHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
            }
        });
        
        btnShowQuestions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new ShowQuestionsPage().setVisible(true);
        	}
        });

        setVisible(true);
    }

    // Helper methods for creating components with consistent styling
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(inputFont);
        textField.setBackground(textFieldBackground);
        textField.setForeground(labelColor);
        textField.setCaretColor(primaryColor);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
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

        // Add hover effect (optional)
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

    private void updateQuestionInDatabase() {
        String questionId = questionIdTextField.getText();
        String questionText = questionTextField.getText();
        String option1 = option1TextField.getText();
        String option2 = option2TextField.getText();
        String option3 = option3TextField.getText();
        String option4 = option4TextField.getText();
        String correctOption = (String) correctOptionComboBox.getSelectedItem();
        String level = (String) levelComboBox.getSelectedItem();

        if (questionId.isEmpty() || questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() ||
                option3.isEmpty() || option4.isEmpty() || correctOption.isEmpty() || level.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            String query = "UPDATE quiz_questions SET question_text = ?, option_1 = ?, option_2 = ?, option_3 = ?, option_4 = ?, correct_option = ?, level = ? WHERE question_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, questionText);
                stmt.setString(2, option1);
                stmt.setString(3, option2);
                stmt.setString(4, option3);
                stmt.setString(5, option4);
                stmt.setInt(6, Integer.parseInt(correctOption));
                stmt.setString(7, level);
                stmt.setInt(8, Integer.parseInt(questionId));

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Question updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "No question found with the given ID.");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating question: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UpdateQuestionPage frame = new UpdateQuestionPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
