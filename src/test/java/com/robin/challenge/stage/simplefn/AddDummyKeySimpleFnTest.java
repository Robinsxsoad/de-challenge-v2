package com.robin.challenge.stage.simplefn;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class AddDummyKeySimpleFnTest {

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
    public void addDummyKeySimpleFnApplyTest(){
        AddDummyKeySimpleFn addDummyKeySimpleFn = new AddDummyKeySimpleFn();
        KV<String, KV<String, SeasonTeamStats>> result = addDummyKeySimpleFn.apply(inputKv);
        KV<String, KV<String, SeasonTeamStats>> expectedResult = KV.of("dummyKey", inputKv);

        Assert.assertEquals(expectedResult.getKey(), result.getKey());
        Assert.assertEquals(expectedResult.getValue(), inputKv);
        Assert.assertEquals(Objects.requireNonNull(expectedResult.getValue()).getValue(), seasonTeamStats);

    }

}
