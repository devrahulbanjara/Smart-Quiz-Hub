package com.main_system;

import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayQuiz extends JFrame {
    private Competitor competitor;
    private Connection connection;
    private int currentRound;
    private final int totalRounds = 5;
    private final int questionsPerRound = 5;
    private int[] roundScores;
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private List<Question> questions;
    private int questionIndex;
    private String selectedLevel;

    public PlayQuiz(Competitor competitor) {
        this.competitor = competitor;
        this.currentRound = 0;
        this.roundScores = new int[totalRounds];
        this.connection = establishConnection();

        if (hasAlreadyPlayed()) {
            JOptionPane.showMessageDialog(null, "You have already played the quiz. You cannot play again.", "Quiz Locked", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }

        setTitle("Play Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        questionLabel = new JLabel("Question");
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new OptionButtonListener());
            centerPanel.add(optionButtons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        startQuiz();
        setVisible(true);
    }

    private Connection establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
            String username = "root";
            String password = "3241";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private boolean hasAlreadyPlayed() {
        if (connection == null) return false;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM competitor_scores WHERE competitor_id = ?");
            stmt.setInt(1, competitor.getCompetitorID());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void startQuiz() {
        if (selectedLevel == null) {
            selectedLevel = selectLevel();
        }
        questions = fetchQuestions(selectedLevel);
        questionIndex = 0;
        showQuestion();
    }

    private String selectLevel() {
        String[] levels = {"Beginner", "Intermediate", "Advanced"};
        return (String) JOptionPane.showInputDialog(null, "Select Level:", "Quiz Level",
                JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
    }

    private List<Question> fetchQuestions(String level) {
        List<Question> questionList = new ArrayList<>();
        if (connection == null) return questionList;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM quiz_questions WHERE level = ? ORDER BY RAND() LIMIT ?");
            stmt.setString(1, level);
            stmt.setInt(2, questionsPerRound);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(
                        rs.getInt("question_id"),
                        rs.getString("question_text"),
                        rs.getString("option_1"),
                        rs.getString("option_2"),
                        rs.getString("option_3"),
                        rs.getString("option_4"),
                        rs.getInt("correct_option"),
                        rs.getString("level")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    private void showQuestion() {
        if (questionIndex < questions.size()) {
            Question q = questions.get(questionIndex);
            questionLabel.setText("Round " + (currentRound + 1) + ": " + q.getQuestionText());
            optionButtons[0].setText(q.getOption1());
            optionButtons[1].setText(q.getOption2());
            optionButtons[2].setText(q.getOption3());
            optionButtons[3].setText(q.getOption4());
        } else {
            roundScores[currentRound] = questionIndex;
            currentRound++;

            if (currentRound < totalRounds) {
                JOptionPane.showMessageDialog(null, "Round " + currentRound + " is over! Now starting Round " + (currentRound + 1));
                questionIndex = 0;
                questions = fetchQuestions(selectedLevel);
                showQuestion();
            } else {
                saveScoresToDatabase();
                JOptionPane.showMessageDialog(null, "Quiz completed! Your final scores: " + Arrays.toString(roundScores));
                dispose();
            }
        }
    }

    private class OptionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            Question currentQuestion = questions.get(questionIndex);
            int selectedAnswer = Arrays.asList(optionButtons).indexOf(clickedButton) + 1;
            if (selectedAnswer == currentQuestion.getCorrectOption()) {
                roundScores[currentRound]++;
            }
            questionIndex++;
            showQuestion();
        }
    }

    private void saveScoresToDatabase() {
        if (connection == null) return;

        try {
            String query = "INSERT INTO competitor_scores (competitor_id, name, level, score1, score2, score3, score4, score5) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(query);
            insertStmt.setInt(1, competitor.getCompetitorID());
            insertStmt.setString(2, competitor.getName().getFullName());
            insertStmt.setString(3, selectedLevel);
            insertStmt.setInt(4, roundScores[0]);
            insertStmt.setInt(5, roundScores[1]);
            insertStmt.setInt(6, roundScores[2]);
            insertStmt.setInt(7, roundScores[3]);
            insertStmt.setInt(8, roundScores[4]);

            insertStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Scores saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
