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

    // Minimalist Color Scheme (Same as RegisterPage, PlayerHomePage, AdminHomePage)
    private Color backgroundColor = new Color(255, 255, 255);
    private Color primaryColor = new Color(66, 135, 245);
    private Color labelColor = new Color(102, 102, 102);
    private Color textFieldBackground = new Color(245, 245, 245);
    private Font titleFont = new Font("SansSerif", Font.BOLD, 24);
    private Font labelFont = new Font("SansSerif", Font.BOLD, 14);
    private Font inputFont = new Font("SansSerif", Font.PLAIN, 14);
    private Font buttonFont = new Font("SansSerif", Font.BOLD, 14);
    private Font smallButtonFont = new Font("SansSerif", Font.PLAIN, 12);

    public AddQuestionPage() {
        setTitle("Add Question");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);  // Adjusted size
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

        JLabel addQuestionLabel = new JLabel("Add Question");
        addQuestionLabel.setFont(titleFont);
        addQuestionLabel.setForeground(primaryColor);
        topPanel.add(addQuestionLabel);

        // Center Panel for Input Fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10)); // Adjusted rows for level
        inputPanel.setBackground(backgroundColor);
        contentPane.add(inputPanel, BorderLayout.CENTER);

        // Labels
        JLabel questionTextLabel = createLabel("Question Text:");
        JLabel option1Label = createLabel("Option 1:");
        JLabel option2Label = createLabel("Option 2:");
        JLabel option3Label = createLabel("Option 3:");
        JLabel option4Label = createLabel("Option 4:");
        JLabel correctOptionLabel = createLabel("Correct Option:");
        JLabel levelLabel = createLabel("Level:");

        // Text Fields
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

        // Add components to inputPanel
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
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));  // Aligned to the right
        buttonPanel.setBackground(backgroundColor);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Buttons
        JButton saveButton = createButton("Save Question");
        btnGoToAdminHome = createBackButton("Go to Admin Home");  // "Go to Admin Home" button

        buttonPanel.add(btnGoToAdminHome);
        buttonPanel.add(saveButton);


        // Action Listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addQuestionToDatabase();
            }
        });


        btnGoToAdminHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage().setVisible(true);
                dispose();
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
        button.setFont(smallButtonFont);            // Smaller font
        button.setBackground(backgroundColor);      // Transparent background
        button.setForeground(primaryColor);        // Use primary color for text
        button.setFocusPainted(false);
        button.setBorderPainted(false);              // Remove border
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
        SwingUtilities.invokeLater(() -> {
            try {
                AddQuestionPage frame = new AddQuestionPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
