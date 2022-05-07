package com.robin.challenge;

import com.robin.challenge.option.EplPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class EplPipelineTest {

    @Rule
    public final transient TestPipeline testPipeline = TestPipeline.create();
    String inputDataset = "./src/test/resources/data/dummy_season.json";

    String outputPath = "output/src/";
    String expectedMostGoalsForFilePath = "output/src/test/resources/data/dummy_season/most-goals-for.txt";
    String expectedMostGoalsAgainstFilePath = "output/src/test/resources/data/dummy_season/most-goals-against.txt";
    String expectedMostShotsGoalsRatioFilePath = "output/src/test/resources/data/dummy_season/most-goals-shots-target-ratio.txt";
    String expectedPositionsTableFilePath = "output/src/test/resources/data/dummy_season/positions-table.txt";

    @Before
    public void deleteFiles() throws IOException {
        FileUtils.deleteDirectory(new File(outputPath));
    }

    @After
    public void deleteCreatedFiles() throws IOException {
        FileUtils.deleteDirectory(new File(outputPath));
    }

    @Test
    public void eplPipelineTest() {

        EplPipelineOptions eplPipelineOptions = PipelineOptionsFactory.fromArgs("--inputDataset=" + inputDataset).withValidation().as(EplPipelineOptions.class);

        Pipeline p = Pipeline.create(eplPipelineOptions);

        EplPipeline.runEplPipeline(p, eplPipelineOptions);

        p.run().waitUntilFinish();

        Assert.assertTrue(fileExists(expectedMostGoalsForFilePath));
        Assert.assertTrue(fileExists(expectedMostGoalsAgainstFilePath));
        Assert.assertTrue(fileExists(expectedMostShotsGoalsRatioFilePath));
        Assert.assertTrue(fileExists(expectedPositionsTableFilePath));

    }

    private boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

}
