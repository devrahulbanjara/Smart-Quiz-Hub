package com.main_system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewReportsPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;

    public ViewReportsPage() {
        setTitle("View Full Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Name", "Age", "Level", "Overall Score"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JButton previousPageButton = new JButton("Previous Page");
        previousPageButton.addActionListener(e -> {
            new AdminHomePage().setVisible(true);
            dispose();
        });
        contentPane.add(previousPageButton, BorderLayout.SOUTH);

        displayFullReport();
    }

    private void displayFullReport() {
        try (Connection connection = establishConnection()) {
            String query = "SELECT p.ID, p.Name, p.Age, p.competition_level, s.overall_score FROM player_details p " +
                           "INNER JOIN competitor_scores s ON p.ID = s.competitor_id";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String level = rs.getString("competition_level");
                int overallScore = rs.getInt("overall_score");

                tableModel.addRow(new Object[]{id, name, age, level, overallScore});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching report data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection establishConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/SmartQuizHub";
            String username = "root";
            String password = "3241";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
