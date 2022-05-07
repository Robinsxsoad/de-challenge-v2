package com.robin.challenge.stage.simplefn;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class PositionsTableSimpleFnTest {

    SeasonTeamStats seasonTeamStatsA = new SeasonTeamStats();
    SeasonTeamStats seasonTeamStatsB = new SeasonTeamStats();

    Iterable<KV<String, SeasonTeamStats>> valueKv;

    KV<String, Iterable<KV<String, SeasonTeamStats>>> inputKv;

    @Before
    public void mockMatchResult() {
        seasonTeamStatsA.setTeamName("Man City");
        seasonTeamStatsA.setTotalPoints(4);
        seasonTeamStatsA.setTotalGoalsFor(5);
        seasonTeamStatsA.setTotalGoalsAgainst(3);
        seasonTeamStatsA.setTotalShotsOnTarget(12);

        seasonTeamStatsB.setTeamName("Blackburn");
        seasonTeamStatsB.setTotalPoints(1);
        seasonTeamStatsB.setTotalGoalsFor(1);
        seasonTeamStatsB.setTotalGoalsAgainst(4);
        seasonTeamStatsB.setTotalShotsOnTarget(4);

        valueKv = Arrays.asList(KV.of("Man City", seasonTeamStatsA), KV.of("Blackburn", seasonTeamStatsB));

        inputKv = KV.of("dummyKey", valueKv);
    }

    @Test
    public void positionsTableSimpleFnApplyTest(){
        String expectedOutput = "Man City;4;5;3;0,42\nBlackburn;1;1;4;0,25";
        PositionsTableSimpleFn positionsTableSimpleFn = new PositionsTableSimpleFn();
        String positionsTableOutput = positionsTableSimpleFn.apply(inputKv);
        Assert.assertEquals(expectedOutput, positionsTableOutput);
    }

}
