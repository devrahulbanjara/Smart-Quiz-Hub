package com.main_system_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.main_system.AddQuestionPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AddQuestionPageTest {

    private AddQuestionPage addQuestionPage;

    @BeforeEach
    void setUp() {
        addQuestionPage = new AddQuestionPage();
    }

    @Test
    void testAddQuestionToDatabase() throws SQLException {
        addQuestionPage.questionTextField.setText("What is 2 + 2?");
        addQuestionPage.option1TextField.setText("3");
        addQuestionPage.option2TextField.setText("4");
        addQuestionPage.option3TextField.setText("5");
        addQuestionPage.option4TextField.setText("6");
        addQuestionPage.correctOptionComboBox.setSelectedItem("2");
        addQuestionPage.levelComboBox.setSelectedItem("Beginner");

        System.out.println("Starting to add question to the database...");
        addQuestionPage.addQuestionToDatabase();
        System.out.println("Question added to database, now checking the database...");

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartQuizHub", "root", "3241")) {
            String query = "SELECT * FROM quiz_questions WHERE question_text = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, "What is 2 + 2?");
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Question found in the database.");
                    assertEquals("What is 2 + 2?", rs.getString("question_text"));
                    assertEquals("3", rs.getString("option_1"));
                    assertEquals("4", rs.getString("option_2"));
                    assertEquals("5", rs.getString("option_3"));
                    assertEquals("6", rs.getString("option_4"));
                    assertEquals(2, rs.getInt("correct_option"));
                    assertEquals("Beginner", rs.getString("level"));
                } else {
                    fail("Question not found in database.");
                }
            }
        } catch (SQLException e) {
            fail("Error during database operation: " + e.getMessage());
        }
    }

    @Test
    void testAddQuestionToDatabaseWithSQLException() {
        addQuestionPage.questionTextField.setText("What is the capital of France?");
        addQuestionPage.option1TextField.setText("Berlin");
        addQuestionPage.option2TextField.setText("Madrid");
        addQuestionPage.option3TextField.setText("Paris");
        addQuestionPage.option4TextField.setText("Rome");
        addQuestionPage.correctOptionComboBox.setSelectedItem("3");
        addQuestionPage.levelComboBox.setSelectedItem("Intermediate");

        System.out.println("Starting to test invalid database connection...");
        try {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/invalid_db_should_throw_error", "root", "3241")) {
                addQuestionPage.addQuestionToDatabase();
                fail("SQLException should have been thrown");
            }
        } catch (SQLException e) {
            System.out.println("Caught SQLException: " + e.getMessage());
            assertTrue(e.getMessage().contains("unknown database"));
        }
    }

}
