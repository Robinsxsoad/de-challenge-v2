package com.robin.challenge.domain;


import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeasonTeamStatsTest {

    private final SeasonTeamStats seasonTeamStats = new SeasonTeamStats();

    @Before
    public void createMockSeasonTeamStats() {
        seasonTeamStats.setTeamName("Test Team A");
        seasonTeamStats.setTotalGoalsFor(100);
        seasonTeamStats.setTotalGoalsAgainst(80);
        seasonTeamStats.setTotalShotsOnTarget(200);
        seasonTeamStats.setTotalPoints(90);
    }


    @Test
    public void testAddPoints() {
        seasonTeamStats.addPoints(3);
        assertEquals(93, seasonTeamStats.getTotalPoints());
    }

    @Test
    public void testGoalsShotsOnTargetRatio() {
        Assert.assertEquals(0, Double.compare(0.5, seasonTeamStats.getGoalsShotsOnTargetRatio()));
    }

    @Test
    public void testCompareTo() {
        SeasonTeamStats seasonTeamStatsB = SeasonTeamStats.builder().totalPoints(91).build();
        int comparisonResult = seasonTeamStats.compareTo(seasonTeamStatsB);
        assertEquals(1, comparisonResult);
    }

}
