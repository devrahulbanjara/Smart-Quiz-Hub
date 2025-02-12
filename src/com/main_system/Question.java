package com.main_system;

/**
 * Represents a question in the quiz.
 */
public class Question {
    private int questionId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;
    private String level;

    /**
     * Constructs a Question object with the provided details.
     *
     * @param questionId    The ID of the question.
     * @param questionText  The text of the question.
     * @param option1       The first option.
     * @param option2       The second option.
     * @param option3       The third option.
     * @param option4       The fourth option.
     * @param correctOption The correct option (1-4).
     * @param level         The difficulty level of the question.
     */
    public Question(int questionId, String questionText, String option1, String option2, String option3, String option4, int correctOption, String level) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
        this.level = level;
    }

    /**
     * Returns the ID of the question.
     *
     * @return The question ID.
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * Returns the text of the question.
     *
     * @return The question text.
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Returns the first option of the question.
     *
     * @return The first option.
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Returns the second option of the question.
     *
     * @return The second option.
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Returns the third option of the question.
     *
     * @return The third option.
     */
    public String getOption3() {
        return option3;
    }

    /**
     * Returns the fourth option of the question.
     *
     * @return The fourth option.
     */
    public String getOption4() {
        return option4;
    }

    /**
     * Returns the correct option for the question.
     *
     * @return The correct option (1-4).
     */
    public int getCorrectOption() {
        return correctOption;
    }

    /**
     * Returns the difficulty level of the question.
     *
     * @return The difficulty level.
     */
    public String getLevel() {
        return level;
    }
}
