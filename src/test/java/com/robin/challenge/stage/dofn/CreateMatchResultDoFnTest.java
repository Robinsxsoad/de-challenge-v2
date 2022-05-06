package com.robin.challenge.stage.dofn;

import com.robin.challenge.domain.MatchResult;
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

public class CreateMatchResultDoFnTest {
    @Rule
    public final transient TestPipeline testPipeline = TestPipeline.create();
    String inputDataset = "./src/test/resources/data/dummy_season.json";

    MatchResult matchResultA = new MatchResult();

    @Before
    public void mockMatchResult() {
        matchResultA.setAwayTeam("Man City");
        matchResultA.setHomeTeam("Aston Villa");
        matchResultA.setFullTimeResult("D");
        matchResultA.setFullTimeHomeGoals(3);
        matchResultA.setFullTimeAwayGoals(3);
        matchResultA.setAwayShotsTarget(7);
        matchResultA.setHomeShotsTarget(5);
    }

    @Test
    public void testProcessElement() throws IOException {
        PCollection<KV<String, MatchResult>> matchResults = testPipeline.apply(Create.of(readMockSeason())).apply(ParDo.of(new CreateMatchResultDoFn()));
        PAssert.that(matchResults).containsInAnyOrder(KV.of("Aston Villa", matchResultA), KV.of("Man City", matchResultA));
        testPipeline.run().waitUntilFinish();
    }

    private String readMockSeason() throws IOException {
        return new String(Files.readAllBytes(Paths.get(inputDataset)));
    }

}
