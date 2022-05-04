package com.robin.challenge;

import com.robin.challenge.domain.MatchResult;
import com.robin.challenge.domain.SeasonTeamStats;
import com.robin.challenge.option.EplPipelineOptions;
import com.robin.challenge.stage.CreateMatchResultDoFn;
import com.robin.challenge.stage.CreateSeasonStatsPerTeam;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

public class EplPipeline {

    public static void main(String[] args) {
        EplPipelineOptions eplPipelineOptions = PipelineOptionsFactory.fromArgs(args).withValidation().as(EplPipelineOptions.class);

        Pipeline eplPipeline = Pipeline.create(eplPipelineOptions);

        // Common part of the pipeline. This applies to all the required reports in the challenge.
        // This reads the dataset specified as parameter and creates objects called MatchResult.
        // The main idea is to have all the match results for a team (being home or away).
        PCollection<KV<String, MatchResult>> seasonResultsPerTeam = eplPipeline
                .apply("Reading specified dataset", TextIO.read().from(eplPipelineOptions.getInputDataset()))
                .apply("Mapping input string to MatchResult object", ParDo.of(new CreateMatchResultDoFn()));
        //.apply(ToString.kvs()) // From here, this is only for debugging TODO: remove this and the next line!
        //.apply(TextIO.write().withoutSharding().to("output.txt"));


        // Also part of the common pipeline stages. Right now it is separated from the first part for simplicity.
        // Group all season result per team and calculate the stats for each one of them.
        // All the stats are mapped using the team name as the key and a model as the value.
        PCollection<KV<String, SeasonTeamStats>> seasonTeamStats = seasonResultsPerTeam
                .apply(GroupByKey.create())
                .apply("Calculating team stats for all the season", ParDo.of(new CreateSeasonStatsPerTeam()));



        eplPipeline.run().waitUntilFinish();

    }

}
