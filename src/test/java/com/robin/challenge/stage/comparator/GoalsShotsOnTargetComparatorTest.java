package com.robin.challenge.stage.comparator;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class GoalsShotsOnTargetComparatorTest {

    SeasonTeamStats teamA;
    SeasonTeamStats teamB;
    KV<String, SeasonTeamStats> teamAStats;
    KV<String, SeasonTeamStats> teamBStats;

    @Before
    public void mockStats() {
        teamA = SeasonTeamStats.builder().teamName("Team A").totalGoalsFor(10).totalShotsOnTarget(20).build();
        teamB = SeasonTeamStats.builder().teamName("Team B").totalGoalsFor(20).totalShotsOnTarget(30).build();
        teamAStats = KV.of("Team A", teamA);
        teamBStats = KV.of("Team B", teamB);
    }

    @Test
    public void goalShotsOnTargetCompareTest(){
        GoalsShotsOnTargetComparator goalsShotsOnTargetComparator = new GoalsShotsOnTargetComparator();
        int comparisonResult = goalsShotsOnTargetComparator.compare(teamAStats, teamBStats);
        Assert.assertEquals(-1, comparisonResult);
    }


}
