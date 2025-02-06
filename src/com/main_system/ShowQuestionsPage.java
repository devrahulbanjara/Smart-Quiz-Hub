package com.main_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ShowQuestionsPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> levelDropdown;
    private JTextArea questionArea;
    
    public ShowQuestionsPage() {
        setTitle("Quiz Questions");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);
        
        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        levelDropdown = new JComboBox<>(levels);
        levelDropdown.addActionListener(e -> fetchQuestions());
        
        questionArea = new JTextArea();
        questionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(questionArea);
        
        contentPane.add(levelDropdown, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

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