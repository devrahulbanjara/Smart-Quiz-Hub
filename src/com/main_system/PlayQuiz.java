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
    private int roundScore;

    public PlayQuiz(Competitor competitor) {
        this.competitor = competitor;
        this.currentRound = 0;
        this.roundScores = new int[totalRounds];
        this.connection = establishConnection();

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

        if (isCompetitorAlreadyPlayed()) {
            JOptionPane.showMessageDialog(null, "You have already played this quiz.", "Info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }

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

    private boolean isCompetitorAlreadyPlayed() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM competitor_scores WHERE competitor_id = ?");
            stmt.setInt(1, competitor.getCompetitorID());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void startQuiz() {
        // Select the level from the database based on the competitor's record
        selectedLevel = competitor.getCompetitionLevel();
        questions = fetchQuestions(selectedLevel);
        questionIndex = 0;
        roundScore = 0;
        showQuestion();
    }

    private List<Question> fetchQuestions(String level) {
        List<Question> questions = new ArrayList<>();
        if (connection == null) return questions;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM quiz_questions WHERE level = ? ORDER BY RAND() LIMIT ?");
            stmt.setString(1, level);
            stmt.setInt(2, questionsPerRound);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new Question(
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
        return questions;
    }

    private void showQuestion() {
        if (questionIndex < questions.size()) {
            Question q = questions.get(questionIndex);
            questionLabel.setText(q.getQuestionText());
            optionButtons[0].setText(q.getOption1());
            optionButtons[1].setText(q.getOption2());
            optionButtons[2].setText(q.getOption3());
            optionButtons[3].setText(q.getOption4());
        } else {
            roundScores[currentRound] = roundScore;
            currentRound++;

            if (currentRound < totalRounds) {
                JOptionPane.showMessageDialog(null, "Round " + currentRound + " completed! Starting next round...");
                questionIndex = 0;
                roundScore = 0;
                questions = fetchQuestions(selectedLevel);
                showQuestion();
            } else {
                competitor.setScores(roundScores);
                int overallScore = competitor.getOverallScore();

                // Calculate the total number of correct answers and the total possible answers
                int totalCorrectAnswers = 0;
                for (int score : roundScores) {
                    totalCorrectAnswers += score;
                }

                // Total possible answers is totalRounds * questionsPerRound
                int totalPossibleAnswers = totalRounds * questionsPerRound;

                // Calculate percentage
                double overallPercentage = ((double) totalCorrectAnswers / totalPossibleAnswers) * 100;

                saveScoresToDatabase(overallScore);

                // Show scores for each round and overall percentage
                StringBuilder resultMessage = new StringBuilder("Quiz completed!\nYour scores per round:\n");
                for (int i = 0; i < totalRounds; i++) {
                    resultMessage.append("Round ").append(i + 1).append(": ").append(roundScores[i]).append("/").append(questionsPerRound).append("\n");
                }
                resultMessage.append("\nOverall score: ").append(totalCorrectAnswers).append("/")
                        .append(totalPossibleAnswers).append("\n")
                        .append("Overall percentage: ").append(String.format("%.2f", overallPercentage)).append("%\n")
                        .append("Difficulty level: ").append(selectedLevel);

                JOptionPane.showMessageDialog(null, resultMessage.toString(), "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
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
                roundScore++;
            }
            questionIndex++;
            showQuestion();
        }
    }

    private void saveScoresToDatabase(int overallScore) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO competitor_scores (competitor_id, name, level, score1, score2, score3, score4, score5, overall_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, competitor.getCompetitorID());
            stmt.setString(2, competitor.getName().getFullName());
            stmt.setString(3, selectedLevel);
            stmt.setInt(4, roundScores[0]);
            stmt.setInt(5, roundScores[1]);
            stmt.setInt(6, roundScores[2]);
            stmt.setInt(7, roundScores[3]);
            stmt.setInt(8, roundScores[4]);
            stmt.setInt(9, overallScore);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}