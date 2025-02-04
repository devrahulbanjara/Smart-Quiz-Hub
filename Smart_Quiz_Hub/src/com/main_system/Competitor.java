package com.main_system;

public class Competitor {
    private int competitorID;
    private Name name;
    private String competitionLevel;
    private int age;

    public Competitor(int competitorID, Name name, String competitionLevel, int age) {
        this.competitorID = competitorID;
        this.name = name;
        this.competitionLevel = competitionLevel;
        this.age = age;
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

    public double getOverallScore() {
        return 5;
    }

    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + name.getFullName() + ", age " + age + ". " +
                name.getFullName() + " is a " + competitionLevel + " aged " + getAge() +" and has an overall score of " + getOverallScore() + ".";
    }

    public String getShortDetails() {
        return "CN " + competitorID + " (" + name.getInitials() + ") has overall score " + getOverallScore() + ".";
    }
}
