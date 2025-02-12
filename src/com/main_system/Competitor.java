package com.main_system;

/**
 * Represents a competitor in the system.
 * Stores the details of a competitor including their ID, name, competition level, age, and scores.
 */
public class Competitor {
    private int competitorID;
    private Name name;
    private String competitionLevel;
    private int age;
    private int[] scores;

    /**
     * Constructs a Competitor with the specified competitor ID, name, competition level, and age.
     * The scores are initialized to an array of 5 elements, all set to 0.
     *
     * @param competitorID The ID of the competitor.
     * @param name The name of the competitor.
     * @param competitionLevel The competition level of the competitor.
     * @param age The age of the competitor.
     */
    public Competitor(int competitorID, Name name, String competitionLevel, int age) {
        this.competitorID = competitorID;
        this.name = name;
        this.competitionLevel = competitionLevel;
        this.age = age;
        this.scores = new int[5];
    }

    /**
     * Constructs a Competitor with the specified competitor ID, name, competition level, age, and scores.
     *
     * @param competitorID The ID of the competitor.
     * @param name The name of the competitor.
     * @param competitionLevel The competition level of the competitor.
     * @param age The age of the competitor.
     * @param scores An array of scores for the competitor.
     */
    public Competitor(int competitorID, Name name, String competitionLevel, int age, int[] scores) {
        this.competitorID = competitorID;
        this.name = name;
        this.competitionLevel = competitionLevel;
        this.age = age;
        this.scores = scores;
    }

    /**
     * Constructs a Competitor with the specified competitor ID, name, competition level, age, and overall score.
     * The overall score is placed in the first element of the scores array.
     *
     * @param competitorID The ID of the competitor.
     * @param name The name of the competitor.
     * @param competitionLevel The competition level of the competitor.
     * @param age The age of the competitor.
     * @param overallScore The overall score for the competitor.
     */
    public Competitor(int competitorID, Name name, String competitionLevel, int age, int overallScore) {
        this.competitorID = competitorID;
        this.name = name;
        this.competitionLevel = competitionLevel;
        this.age = age;
        this.scores = new int[5];  // assuming there are 5 scores (can be updated later)
        this.scores[0] = overallScore;  // Storing the overall score in the first element
    }

    /**
     * Gets the competitor's ID.
     *
     * @return The competitor ID.
     */
    public int getCompetitorID() {
        return competitorID;
    }

    /**
     * Sets the competitor's ID.
     *
     * @param competitorID The competitor ID.
     */
    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }

    /**
     * Gets the competitor's name.
     *
     * @return The name of the competitor.
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the competitor's name.
     *
     * @param name The name of the competitor.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Gets the competition level of the competitor.
     *
     * @return The competition level.
     */
    public String getCompetitionLevel() {
        return competitionLevel;
    }

    /**
     * Sets the competition level of the competitor.
     *
     * @param competitionLevel The competition level.
     */
    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    /**
     * Gets the age of the competitor.
     *
     * @return The age of the competitor.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the competitor.
     *
     * @param age The age of the competitor.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the scores array of the competitor.
     *
     * @return The array of scores.
     */
    public int[] getScoreArray() {
        return scores;
    }

    /**
     * Sets the scores for the competitor.
     *
     * @param scores An array of scores.
     */
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /**
     * Calculates and returns the overall score of the competitor as the sum of their individual scores.
     * This method simply sums the values in the scores array and returns the result.
     *
     * @return The sum of the individual scores (not percentage).
     */
    public int getOverallScore() {
        if (scores == null || scores.length == 0) {
            return 0;
        }

        int totalScore = 0;

        for (int score : scores) {
            totalScore += score;
        }

        return totalScore;
    }

    /**
     * Gets the full details of the competitor including ID, name, age, competition level, and overall score.
     *
     * @return A string containing the full details of the competitor.
     */
    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + name.getFullName() + ", age " + age + ". " +
                name.getFullName() + " is a " + competitionLevel + " aged " + getAge() +
                " and has an overall score of " + getOverallScore() + ".";
    }

    /**
     * Gets a short summary of the competitor's details including ID, initials, and overall score.
     *
     * @return A short details string.
     */
    public String getShortDetails() {
        return "CN " + competitorID + " (" + name.getInitials() + ") has overall score " + getOverallScore() + ".";
    }
}
