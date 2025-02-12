package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * JFrame for deleting a question from the database.
 */
public class DeleteQuestionPage extends JFrame {
    private JPanel contentPane;
    private JButton btnShowQuestions;
    private JTextField questionIdTextField;
    private JButton btnGoToAdminHome;

    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Constructor for the DeleteQuestionPage.
     */
    public DeleteQuestionPage() {
        setTitle("Delete Question");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        contentPane = new JPanel();
        contentPane.setBackground(backgroundColor);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        contentPane.add(topPanel, BorderLayout.NORTH);

        JLabel deleteQuestionLabel = new JLabel("Delete Question");
        deleteQuestionLabel.setFont(titleFont);
        deleteQuestionLabel.setForeground(primaryColor);
        topPanel.add(deleteQuestionLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(backgroundColor);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JLabel lblEnterQuestionId = createLabel("Enter Question ID to delete:");
        centerPanel.add(lblEnterQuestionId);

        questionIdTextField = createTextField();
        questionIdTextField.setColumns(10);
        centerPanel.add(questionIdTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        btnShowQuestions = createButton("Show Questions");
        JButton deleteButton = createButton("Delete Question");
        btnGoToAdminHome = createBackButton("Go to Admin Home");

        buttonPanel.add(btnGoToAdminHome);
        buttonPanel.add(deleteButton);
        buttonPanel.add(btnShowQuestions);

        btnShowQuestions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowQuestionsPage().setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String questionId = questionIdTextField.getText().trim();

                if (questionId.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a valid Question ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(questionId);
                    if (id <= 0) {
                        JOptionPane.showMessageDialog(contentPane, "Please enter a positive Question ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    deleteQuestion(String.valueOf(id));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Invalid Question ID format. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnGoToAdminHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
            }
        });
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);
    }

    /**
     * Creates a JLabel with the specified text.
     *
     * @param text the text for the label
     * @return a JLabel with the specified text
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(labelColor);
        return label;
    }

    /**
     * Creates a JTextField with consistent styling.
     *
     * @return a JTextField with consistent styling
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(inputFont);
        textField.setBackground(textFieldBackground);
        textField.setForeground(labelColor);
        textField.setCaretColor(primaryColor);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    /**
     * Creates a JButton with consistent styling.
     *
     * @param text the text on the button
     * @return a JButton with consistent styling
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
     * Creates a back button with consistent styling.
     *
     * @param text the text on the back button
     * @return a JButton styled as a back button
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

    /**
     * Deletes the question with the specified question ID from the database.
     *
     * @param questionId the ID of the question to delete
     */
    private void deleteQuestion(String questionId) {
        String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
        String username = "root";
        String password = "3241";

        String sql = "DELETE FROM quiz_questions WHERE question_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, Integer.parseInt(questionId));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(contentPane, "Question deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(contentPane, "No question found with that ID.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error occurred while deleting the question: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(contentPane, "Invalid Question ID format. Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
