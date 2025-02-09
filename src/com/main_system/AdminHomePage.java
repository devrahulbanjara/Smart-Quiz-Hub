package com.main_system;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePage extends JFrame {
    private JPanel contentPane;
    private JButton btnAddQuestion;
    private JButton btnUpdateQuestion;
    private JButton btnDeleteQuestion;
    private JButton btnViewReports;
    private JButton btnPreviousPage;

    public AdminHomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblAdminHome = new JLabel("Admin Home Page");
        lblAdminHome.setBounds(230, 20, 150, 30);
        contentPane.add(lblAdminHome);

        btnAddQuestion = new JButton("Add Question");
        btnAddQuestion.setBounds(50, 80, 150, 30);
        contentPane.add(btnAddQuestion);

        btnUpdateQuestion = new JButton("Update Question");
        btnUpdateQuestion.setBounds(212, 80, 172, 30);
        contentPane.add(btnUpdateQuestion);

        btnDeleteQuestion = new JButton("Delete Question");
        btnDeleteQuestion.setBounds(396, 80, 150, 30);
        contentPane.add(btnDeleteQuestion);

        btnViewReports = new JButton("View Reports");
        btnViewReports.setBounds(220, 150, 150, 30);
        contentPane.add(btnViewReports);

        btnPreviousPage = new JButton("Previous Page");
        btnPreviousPage.setBounds(220, 220, 150, 30);
        contentPane.add(btnPreviousPage);

        // Action listeners
        btnAddQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddQuestionPage().setVisible(true);
                dispose();
            }
        });

        btnUpdateQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateQuestionPage().setVisible(true);
                dispose();
            }
        });

        btnDeleteQuestion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteQuestionPage().setVisible(true);
                dispose();
            }
        });

        btnViewReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewReportsPage().setVisible(true);
                dispose();
            }
        });

        btnPreviousPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }
}
