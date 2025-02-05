package com.main_system;

import java.util.Arrays;

public class Competitor {
    private int competitorID;
    private Name name;
    private String competitionLevel;
    private int age;
    private int[] scores;

    public Competitor(int competitorID, Name name, String competitionLevel, int age, int[] scores) {
        this.competitorID = competitorID;
        this.name = name;
        this.competitionLevel = competitionLevel;
        this.age = age;
        this.scores = scores;
    }

    public int getCompetitorID() {
        return competitorID;
    }

    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getCompetitionLevel() {
        return competitionLevel;
    }

    public void setCompetitionLevel(String competitionLevel) {
        this.competitionLevel = competitionLevel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int[] getScoreArray() {
        return scores;
    }

    public double getOverallScore() {
        if (scores == null || scores.length == 0) {
            return 0;
        }

        Arrays.sort(scores);
        
        if (scores.length > 2) {
            int sum = 0;
            for (int i = 1; i < scores.length - 1; i++) {
                sum += scores[i];
            }
            return (double) sum / (scores.length - 2);
        }
        
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return (double) sum / scores.length;
    }

    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + name.getFullName() + ", age " + age + ". " +
                name.getFullName() + " is a " + competitionLevel + " aged " + getAge() +
                " and has an overall score of " + getOverallScore() + ".";
    }

    public String getShortDetails() {
        return "CN " + competitorID + " (" + name.getInitials() + ") has overall score " + getOverallScore() + ".";
    }
}
