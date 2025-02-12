package com.main_system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.util.List;

public class PlayQuiz extends JFrame {
    private Competitor competitor;
    private Connection connection;
    private int currentRound;
    private final int totalRounds = 5;
    private final int questionsPerRound = 5;
    private int[] roundScores;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private List<Question> questions;
    private int questionIndex;
    private String selectedLevel;
    private int roundScore;
    private JButton submitButton;
    private int totalCorrectAnswers;

    // Theme
    private Color backgroundColor = new Color(255, 255, 255);  // White
    private Color primaryColor = new Color(66, 135, 245);      // Blue
    private Color labelColor = new Color(102, 102, 102);        // Grey
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);

    public PlayQuiz(Competitor competitor) {
        this.competitor = competitor;
        this.currentRound = 0;
        this.roundScores = new int[totalRounds];
        this.totalCorrectAnswers = 0;
        this.connection = establishConnection();

        setTitle("Play Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(backgroundColor); // Apply background color
        questionLabel = new JLabel("Question");
        questionLabel.setForeground(labelColor); // Apply label color
        questionLabel.setFont(titleFont);       // Apply title font
        topPanel.add(questionLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1));
        centerPanel.setBackground(backgroundColor); // Apply background color
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setBackground(backgroundColor);    // Apply background color
            optionButtons[i].setForeground(labelColor);      // Apply label color
            optionButtons[i].setFont(inputFont);               // Apply input font
            optionsGroup.add(optionButtons[i]);
            centerPanel.add(optionButtons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.setUI(new BasicButtonUI());
        submitButton.setBackground(primaryColor);  // Apply primary color
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(buttonFont); // Apply button font
        submitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        submitButton.addActionListener(new SubmitButtonListener());
        add(submitButton, BorderLayout.SOUTH);

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
            return rs.next();  // If a record is found, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void startQuiz() {
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
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM quiz_questions WHERE level = ?");
            stmt.setString(1, level);
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
            Collections.shuffle(questions);
            return questions.subList(0, Math.min(questionsPerRound, questions.size()));
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
            optionsGroup.clearSelection();
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
                saveScoresToDatabase(overallScore);
                double percentage = (double) totalCorrectAnswers / (totalRounds * questionsPerRound) * 100;
                JOptionPane.showMessageDialog(null,
                        "Quiz completed! Your overall score: " + overallScore + "%" + "\nTotal Correct Answers: " + totalCorrectAnswers +
                                "\nPercentage: " + String.format("%.2f", percentage) + "%",
                        "Quiz Results", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < optionButtons.length; i++) {
                if (optionButtons[i].isSelected()) {
                    Question currentQuestion = questions.get(questionIndex);
                    if (i + 1 == currentQuestion.getCorrectOption()) {
                        roundScore++;
                        totalCorrectAnswers++;
                    }
                    questionIndex++;
                    showQuestion();
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Please select an answer before submitting.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void saveScoresToDatabase(int overallScore) {
        try {
            // Calculate the percentage
            double percentage = (double) totalCorrectAnswers / (totalRounds * questionsPerRound) * 100;
            
            // Convert the percentage to an integer (rounded to the nearest whole number)
            int percentageScore = (int) Math.round(percentage);
            
            PreparedStatement insertStmt = connection.prepareStatement(
                    "INSERT INTO competitor_scores (competitor_id, name, level, score1, score2, score3, score4, score5, overall_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStmt.setInt(1, competitor.getCompetitorID());
            insertStmt.setString(2, competitor.getName().getFullName());
            insertStmt.setString(3, selectedLevel);
            insertStmt.setInt(4, roundScores[0]);
            insertStmt.setInt(5, roundScores[1]);
            insertStmt.setInt(6, roundScores[2]);
            insertStmt.setInt(7, roundScores[3]);
            insertStmt.setInt(8, roundScores[4]);
            insertStmt.setInt(9, percentageScore); // Save the percentage
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving scores to database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
