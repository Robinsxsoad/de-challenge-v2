package com.robin.challenge.stage.simplefn;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FormatGoalsOutputSimpleFnTest {

    SeasonTeamStats seasonTeamStats = new SeasonTeamStats();

    KV<String, SeasonTeamStats> inputKv;

    @Before
    public void mockMatchResult() {
        seasonTeamStats.setTeamName("Man City");
        seasonTeamStats.setTotalPoints(4);
        seasonTeamStats.setTotalGoalsFor(5);
        seasonTeamStats.setTotalGoalsAgainst(3);
        seasonTeamStats.setTotalShotsOnTarget(12);

        inputKv = KV.of("Man City", seasonTeamStats);

    }

    @Test
    public void formatGoalsOutputSimpleFnApplyTest() {
        String expectedResult = "Man City;4;5;3;0,42";
        FormatGoalsOutputSimpleFn formatGoalsOutputSimpleFn = new FormatGoalsOutputSimpleFn();
        String outputFormat = formatGoalsOutputSimpleFn.apply(inputKv);
        Assert.assertEquals(expectedResult, outputFormat);
    }

}
