package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateQuestionPage extends JFrame {
    private JPanel contentPane;
    private JTextField questionIdTextField, questionTextField, option1TextField, option2TextField, option3TextField, option4TextField;
    private JComboBox<String> correctOptionComboBox, levelComboBox;

    public UpdateQuestionPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel updateQuestionLabel = new JLabel("Update Question");
        updateQuestionLabel.setBounds(242, 12, 200, 30);
        contentPane.add(updateQuestionLabel);

        JLabel questionIdLabel = new JLabel("Question ID");
        questionIdLabel.setBounds(50, 70, 100, 20);
        contentPane.add(questionIdLabel);
        questionIdTextField = new JTextField();
        questionIdTextField.setBounds(160, 70, 68, 30);
        contentPane.add(questionIdTextField);

        JLabel questionTextLabel = new JLabel("Question Text");
        questionTextLabel.setBounds(50, 110, 100, 20);
        contentPane.add(questionTextLabel);
        questionTextField = new JTextField();
        questionTextField.setBounds(160, 110, 300, 30);
        contentPane.add(questionTextField);

        JLabel option1Label = new JLabel("Option 1");
        option1Label.setBounds(50, 150, 100, 20);
        contentPane.add(option1Label);
        option1TextField = new JTextField();
        option1TextField.setBounds(160, 150, 300, 30);
        contentPane.add(option1TextField);

        JLabel option2Label = new JLabel("Option 2");
        option2Label.setBounds(50, 190, 100, 20);
        contentPane.add(option2Label);
        option2TextField = new JTextField();
        option2TextField.setBounds(160, 190, 300, 30);
        contentPane.add(option2TextField);

        JLabel option3Label = new JLabel("Option 3");
        option3Label.setBounds(50, 230, 100, 20);
        contentPane.add(option3Label);
        option3TextField = new JTextField();
        option3TextField.setBounds(160, 230, 300, 30);
        contentPane.add(option3TextField);

        JLabel option4Label = new JLabel("Option 4");
        option4Label.setBounds(50, 270, 100, 20);
        contentPane.add(option4Label);
        option4TextField = new JTextField();
        option4TextField.setBounds(160, 270, 300, 30);
        contentPane.add(option4TextField);

        JLabel correctOptionLabel = new JLabel("Correct Option");
        correctOptionLabel.setBounds(50, 310, 100, 20);
        contentPane.add(correctOptionLabel);
        correctOptionComboBox = new JComboBox<>(new String[] {"1", "2", "3", "4"});
        correctOptionComboBox.setBounds(160, 310, 60, 30);
        contentPane.add(correctOptionComboBox);

        JLabel levelLabel = new JLabel("Level");
        levelLabel.setBounds(50, 350, 100, 20);
        contentPane.add(levelLabel);
        levelComboBox = new JComboBox<>(new String[] {"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setBounds(160, 350, 150, 30);
        contentPane.add(levelComboBox);

        JButton updateButton = new JButton("Update Question");
        updateButton.setBounds(50, 400, 200, 30);
        contentPane.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateQuestionInDatabase();
            }
        });

        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.setBounds(388, 371, 200, 30);
        contentPane.add(previousPageButton);
        
        JButton btnNewButton = new JButton("Show Questions");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new ShowQuestionsPage().setVisible(true);
        	}
        });
        btnNewButton.setBounds(262, 64, 150, 33);
        contentPane.add(btnNewButton);

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
            }
        });
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
            String query = "UPDATE quiz_questions SET question_text = ?, option_1 = ?, option_2 = ?, option_3 = ?, option_4 = ?, correct_option = ?, level = ? WHERE id = ?";
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
}
