package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddQuestionPage extends JFrame {
    private JPanel contentPane;
    private JTextField questionTextField, option1TextField, option2TextField, option3TextField, option4TextField;
    private JComboBox<String> correctOptionComboBox, levelComboBox;

    public AddQuestionPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel addQuestionLabel = new JLabel("Add Question");
        addQuestionLabel.setBounds(250, 20, 200, 30);
        contentPane.add(addQuestionLabel);

        // Labels and text fields for entering question and options
        JLabel questionTextLabel = new JLabel("Question Text");
        questionTextLabel.setBounds(50, 70, 100, 20);
        contentPane.add(questionTextLabel);
        questionTextField = new JTextField();
        questionTextField.setBounds(160, 70, 300, 30);
        contentPane.add(questionTextField);

        JLabel option1Label = new JLabel("Option 1");
        option1Label.setBounds(50, 110, 100, 20);
        contentPane.add(option1Label);
        option1TextField = new JTextField();
        option1TextField.setBounds(160, 110, 300, 30);
        contentPane.add(option1TextField);

        JLabel option2Label = new JLabel("Option 2");
        option2Label.setBounds(50, 150, 100, 20);
        contentPane.add(option2Label);
        option2TextField = new JTextField();
        option2TextField.setBounds(160, 150, 300, 30);
        contentPane.add(option2TextField);

        JLabel option3Label = new JLabel("Option 3");
        option3Label.setBounds(50, 190, 100, 20);
        contentPane.add(option3Label);
        option3TextField = new JTextField();
        option3TextField.setBounds(160, 190, 300, 30);
        contentPane.add(option3TextField);

        JLabel option4Label = new JLabel("Option 4");
        option4Label.setBounds(50, 230, 100, 20);
        contentPane.add(option4Label);
        option4TextField = new JTextField();
        option4TextField.setBounds(160, 230, 300, 30);
        contentPane.add(option4TextField);

        JLabel correctOptionLabel = new JLabel("Correct Option");
        correctOptionLabel.setBounds(50, 270, 100, 20);
        contentPane.add(correctOptionLabel);
        correctOptionComboBox = new JComboBox<>(new String[] {"1", "2", "3", "4"});
        correctOptionComboBox.setBounds(160, 270, 60, 30);
        contentPane.add(correctOptionComboBox);

        JLabel levelLabel = new JLabel("Level");
        levelLabel.setBounds(50, 310, 100, 20);
        contentPane.add(levelLabel);
        levelComboBox = new JComboBox<>(new String[] {"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setBounds(160, 310, 150, 30);
        contentPane.add(levelComboBox);

        // Save Button
        JButton saveButton = new JButton("Save Question");
        saveButton.setBounds(50, 350, 200, 30);
        contentPane.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestionToDatabase();
            }
        });

        // Previous Page Button
        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.setBounds(270, 350, 200, 30);
        contentPane.add(previousPageButton);

        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
            }
        });
    }

    private void addQuestionToDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            String query = "INSERT INTO quiz_questions (question_text, option_1, option_2, option_3, option_4, correct_option, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, questionTextField.getText());
                stmt.setString(2, option1TextField.getText());
                stmt.setString(3, option2TextField.getText());
                stmt.setString(4, option3TextField.getText());
                stmt.setString(5, option4TextField.getText());
                stmt.setInt(6, Integer.parseInt((String) correctOptionComboBox.getSelectedItem()));
                stmt.setString(7, (String) levelComboBox.getSelectedItem());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Question added successfully!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding question: " + e.getMessage());
        }
    }
}
