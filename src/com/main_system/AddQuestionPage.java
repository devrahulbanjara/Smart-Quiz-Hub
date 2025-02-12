package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddQuestionPage extends JFrame {
    private JPanel contentPane;
    public JTextField questionTextField;
	public JTextField option1TextField;
	public JTextField option2TextField;
	public JTextField option3TextField;
	public JTextField option4TextField;
    public JComboBox<String> correctOptionComboBox;
	public JComboBox<String> levelComboBox;
    private JButton btnGoToAdminHome;
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final Color PRIMARY_COLOR = new Color(66, 135, 245);
    private static final Color LABEL_COLOR = new Color(102, 102, 102);
    private static final Color TEXTFIELD_BG = new Color(245, 245, 245);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);
    private static final Font SMALL_BUTTON_FONT = new Font("SansSerif", Font.PLAIN, 12);

    public AddQuestionPage() {
        setTitle("Add Question");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(BACKGROUND_COLOR);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        topPanel.setBackground(BACKGROUND_COLOR);
        JLabel addQuestionLabel = new JLabel("Add Question");
        addQuestionLabel.setFont(TITLE_FONT);
        addQuestionLabel.setForeground(PRIMARY_COLOR);
        topPanel.add(addQuestionLabel);
        contentPane.add(topPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBackground(BACKGROUND_COLOR);
        contentPane.add(inputPanel, BorderLayout.CENTER);

        questionTextField = createTextField();
        option1TextField = createTextField();
        option2TextField = createTextField();
        option3TextField = createTextField();
        option4TextField = createTextField();
        correctOptionComboBox = createComboBox(new String[]{"1", "2", "3", "4"});
        levelComboBox = createComboBox(new String[]{"Beginner", "Intermediate", "Advanced"});

        String[] labels = {"Question Text:", "Option 1:", "Option 2:", "Option 3:", "Option 4:", "Correct Option:", "Level:"};
        Component[] fields = {questionTextField, option1TextField, option2TextField, option3TextField, option4TextField, correctOptionComboBox, levelComboBox};

        for (int i = 0; i < labels.length; i++) {
            inputPanel.add(createLabel(labels[i]));
            inputPanel.add(fields[i]);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton saveButton = createButton("Save Question");
        btnGoToAdminHome = createBackButton("Go to Admin Home");
        buttonPanel.add(btnGoToAdminHome);
        buttonPanel.add(saveButton);

        saveButton.addActionListener(e -> addQuestionToDatabase());
        btnGoToAdminHome.addActionListener(e -> {
            new AdminHomePage().setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(INPUT_FONT);
        textField.setBackground(TEXTFIELD_BG);
        textField.setForeground(LABEL_COLOR);
        textField.setCaretColor(PRIMARY_COLOR);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(INPUT_FONT);
        comboBox.setBackground(TEXTFIELD_BG);
        comboBox.setForeground(LABEL_COLOR);
        return comboBox;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(button.getBackground().brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        return button;
    }

    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setFont(SMALL_BUTTON_FONT);
        button.setBackground(BACKGROUND_COLOR);
        button.setForeground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(PRIMARY_COLOR.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(PRIMARY_COLOR);
            }
        });
        return button;
    }

    public void addQuestionToDatabase() {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddQuestionPage::new);
    }
}
