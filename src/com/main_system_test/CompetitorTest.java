package com.main_system_test;

import org.junit.jupiter.api.Test;

import com.main_system.Competitor;
import com.main_system.Name;

import static org.junit.jupiter.api.Assertions.*;

class CompetitorTest {

    @Test
    void testCompetitorDefaultScores() {
        Name name = new Name("Rahul Dev Banjara");
        Competitor competitor = new Competitor(101, name, "Intermediate", 25);
        
        assertEquals(101, competitor.getCompetitorID());
        assertEquals("Rahul Dev Banjara", competitor.getName().getFullName());
        assertEquals("Intermediate", competitor.getCompetitionLevel());
        assertEquals(25, competitor.getAge());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, competitor.getScoreArray());
        assertEquals(0, competitor.getOverallScore());
    }

    @Test
    void testCompetitorWithScores() {
        Name name = new Name("Rahul Dev Banjara");
        int[] scores = {4, 3, 5, 2, 5};
        Competitor competitor = new Competitor(102, name, "Advanced", 30, scores);

        assertEquals(102, competitor.getCompetitorID());
        assertEquals("Rahul Dev Banjara", competitor.getName().getFullName());
        assertEquals("Advanced", competitor.getCompetitionLevel());
        assertEquals(30, competitor.getAge());
        assertArrayEquals(scores, competitor.getScoreArray());
        assertEquals(76, competitor.getOverallScore());
    }

    @Test
    void testUpdateScores() {
        Name name = new Name("Rahul Dev Banjara");
        Competitor competitor = new Competitor(103, name, "Beginner", 22);
        int[] newScores = {5, 5, 5, 5, 5};

        competitor.setScores(newScores);
        assertArrayEquals(newScores, competitor.getScoreArray());
        assertEquals(100, competitor.getOverallScore());
    }

    @Test
    void testUpdateAgeAndLevel() {
        Name name = new Name("Rahul Dev Banjara");
        Competitor competitor = new Competitor(104, name, "Beginner", 22);

        competitor.setAge(28);
        competitor.setCompetitionLevel("Advanced");

        assertEquals(28, competitor.getAge());
        assertEquals("Advanced", competitor.getCompetitionLevel());
    }

    @Test
    void testFullDetailsFormat() {
        Name name = new Name("Rahul Dev Banjara");
        Competitor competitor = new Competitor(105, name, "Intermediate", 25);

        String expected = "Competitor number 105, name Rahul Dev Banjara, age 25. Rahul Dev Banjara is a Intermediate aged 25 and has an overall score of 0.";
        assertEquals(expected, competitor.getFullDetails());
    }

    @Test
    void testShortDetailsFormat() {
        Name name = new Name("Rahul Dev Banjara");
        Competitor competitor = new Competitor(106, name, "Advanced", 30, new int[]{4, 3, 5, 2, 5});

        String expected = "CN 106 (RDB) has overall score 76.";
        assertEquals(expected, competitor.getShortDetails());
    }
}
