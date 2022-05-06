package com.robin.challenge.stage.comparator;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GoalsAgainstComparatorTest {

    SeasonTeamStats teamA;
    SeasonTeamStats teamB;
    KV<String, SeasonTeamStats> teamAStats;
    KV<String, SeasonTeamStats> teamBStats;

    @Before
    public void mockStats() {
        teamA = SeasonTeamStats.builder().teamName("Team A").totalGoalsAgainst(10).build();
        teamB = SeasonTeamStats.builder().teamName("Team B").totalGoalsAgainst(20).build();
        teamAStats = KV.of("Team A", teamA);
        teamBStats = KV.of("Team B", teamB);
    }

    @Test
    public void testGoalsAgainstCompare() {
        GoalsAgainstComparator goalsAgainstComparator = new GoalsAgainstComparator();
        int comparisonResult = goalsAgainstComparator.compare(teamAStats, teamBStats);
        Assert.assertEquals(-10, comparisonResult);
    }

}
