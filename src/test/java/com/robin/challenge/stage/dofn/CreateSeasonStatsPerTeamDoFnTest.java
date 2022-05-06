package com.robin.challenge.stage.dofn;

import com.robin.challenge.domain.MatchResult;
import com.robin.challenge.domain.SeasonTeamStats;
import com.robin.challenge.domain.SeasonTeamStatsTest;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CreateSeasonStatsPerTeamDoFnTest {
    @Rule
    public final transient TestPipeline testPipeline = TestPipeline.create();

    MatchResult matchResultA = new MatchResult();
    MatchResult matchResultB = new MatchResult();

    KV<String, Iterable<MatchResult>> inputKv;

    SeasonTeamStats seasonTeamStats = new SeasonTeamStats();

    @Before
    public void mockMatchResult() {
        matchResultA.setAwayTeam("Man City");
        matchResultA.setHomeTeam("Aston Villa");
        matchResultA.setFullTimeResult("D");
        matchResultA.setFullTimeHomeGoals(3);
        matchResultA.setFullTimeAwayGoals(3);
        matchResultA.setAwayShotsTarget(7);
        matchResultA.setHomeShotsTarget(5);

        matchResultB.setAwayTeam("Man City");
        matchResultB.setHomeTeam("Blackburn");
        matchResultB.setFullTimeResult("A");
        matchResultB.setFullTimeHomeGoals(0);
        matchResultB.setFullTimeAwayGoals(2);
        matchResultB.setAwayShotsTarget(5);
        matchResultB.setHomeShotsTarget(9);

        seasonTeamStats.setTeamName("Man City");
        seasonTeamStats.setTotalPoints(4);
        seasonTeamStats.setTotalGoalsFor(5);
        seasonTeamStats.setTotalGoalsAgainst(3);
        seasonTeamStats.setTotalShotsOnTarget(12);

        inputKv = KV.of("Man City", Arrays.asList(matchResultA, matchResultB));
    }

    @Test
    public void testProcessElement() throws IOException {
        PCollection<KV<String, SeasonTeamStats>> teamSeasonStats = testPipeline.apply(Create.of(inputKv)).apply(ParDo.of(new CreateSeasonStatsPerTeamDoFn()));
        PAssert.that(teamSeasonStats).containsInAnyOrder(KV.of("Man City", seasonTeamStats));
        testPipeline.run().waitUntilFinish();
    }


}
