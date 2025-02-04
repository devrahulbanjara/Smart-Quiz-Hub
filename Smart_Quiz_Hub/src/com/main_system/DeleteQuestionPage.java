package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteQuestionPage extends JFrame {
    private JPanel contentPane;
    private JButton showQuestionsButton;
    private JTextField textField;

    public DeleteQuestionPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel deleteQuestionLabel = new JLabel("Delete Question");
        deleteQuestionLabel.setBounds(234, 29, 200, 30);
        contentPane.add(deleteQuestionLabel);

        showQuestionsButton = new JButton("Show Questions");
        showQuestionsButton.setBounds(219, 61, 150, 30);
        contentPane.add(showQuestionsButton);

        showQuestionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new ShowQuestionsPage().setVisible(true);
            }
        });

        JButton deleteButton = new JButton("Delete Question");
        deleteButton.setBounds(50, 330, 150, 30);
        contentPane.add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the Question ID from the text field
                String questionId = textField.getText().trim();

                if (questionId.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPane, "Please enter a valid Question ID.");
                    return;
                }

                // Connect to the database and delete the question
                deleteQuestion(questionId);
            }
        });

        // Previous Page Button
        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.setBounds(438, 360, 150, 30);
        contentPane.add(previousPageButton);
        
        JLabel lblEnterQuestionId = new JLabel("Enter Question ID to delete");
        lblEnterQuestionId.setBounds(90, 137, 213, 15);
        contentPane.add(lblEnterQuestionId);
        
        textField = new JTextField();
        textField.setBounds(303, 135, 114, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
            }
        });
    }

    private void deleteQuestion(String questionId) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/SmartQuizHub";  // Your database URL
        String username = "root";  // Your database username
        String password = "3241";  // Your database password

        // SQL delete statement
        String sql = "DELETE FROM quiz_questions WHERE question_id = ?";

        // Establish database connection
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the question_id parameter
            preparedStatement.setInt(1, Integer.parseInt(questionId));

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(contentPane, "Question deleted successfully!");
                // Optionally, you can refresh the table or any UI elements
            } else {
                JOptionPane.showMessageDialog(contentPane, "No question found with that ID.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(contentPane, "Error occurred while deleting the question.");
        }
    }
}
