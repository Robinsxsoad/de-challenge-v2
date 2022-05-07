package com.robin.challenge;

import com.robin.challenge.domain.MatchResult;
import com.robin.challenge.domain.SeasonTeamStats;
import com.robin.challenge.option.EplPipelineOptions;
import com.robin.challenge.stage.comparator.GoalsAgainstComparator;
import com.robin.challenge.stage.comparator.GoalsForComparator;
import com.robin.challenge.stage.comparator.GoalsShotsOnTargetComparator;
import com.robin.challenge.stage.dofn.CreateMatchResultDoFn;
import com.robin.challenge.stage.dofn.CreateSeasonStatsPerTeamDoFn;
import com.robin.challenge.stage.simplefn.AddDummyKeySimpleFn;
import com.robin.challenge.stage.simplefn.FormatGoalsOutputSimpleFn;
import com.robin.challenge.stage.simplefn.PositionsTableSimpleFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.Max;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.commons.lang3.StringUtils;

import static com.robin.challenge.constant.PipelineConstants.MOST_GOALS_AGAINST_OUTPUT_PATH;
import static com.robin.challenge.constant.PipelineConstants.MOST_GOALS_FOR_OUTPUT_PATH;
import static com.robin.challenge.constant.PipelineConstants.MOST_GOALS_SHOTS_RATIO_OUTPUT_PATH;
import static com.robin.challenge.constant.PipelineConstants.OUTPUT_FILE_HEADER;
import static com.robin.challenge.constant.PipelineConstants.POSITIONS_TABLE_OUTPUT_PATH;


/**
 * Contains the Pipeline and the Options for it.
 */
public class EplPipeline {

    public static void main(String[] args) {
        EplPipelineOptions eplPipelineOptions = PipelineOptionsFactory.fromArgs(args).withValidation().as(EplPipelineOptions.class);
        Pipeline eplPipeline = Pipeline.create(eplPipelineOptions);
        runEplPipeline(eplPipeline, eplPipelineOptions);

    }

    /**
     * Function to run the pipeline. This function contains the pipeline logic (transformations).
     *
     * @param eplPipeline        Is the pipeline created in the main call of this class.
     * @param eplPipelineOptions Are the options passed to the pipeline. Contains the input dataset.
     */
    public static void runEplPipeline(Pipeline eplPipeline, EplPipelineOptions eplPipelineOptions) {
        // Common part of the pipeline. This applies to all the required reports in the challenge.
        // This reads the dataset specified as parameter and creates objects called MatchResult.
        // The main idea is to have all the match results for a team (being home or away).
        PCollection<KV<String, MatchResult>> seasonResultsPerTeam = eplPipeline
                .apply("Reading specified dataset", TextIO.read().from(eplPipelineOptions.getInputDataset()))
                .apply("Mapping input JSON string to MatchResult object", ParDo.of(new CreateMatchResultDoFn()));

        // Also part of the common pipeline stages. Right now it is separated from the first part for simplicity.
        // Group all season result per team and calculate the stats for each one of them.
        // All the stats are mapped using the team name as the key and a model as the value.
        PCollection<KV<String, SeasonTeamStats>> seasonTeamStats = seasonResultsPerTeam
                .apply("Grouping all the match results for a single team", GroupByKey.create())
                .apply("Calculating team stats for all the season", ParDo.of(new CreateSeasonStatsPerTeamDoFn()));


        // Output the team with most goals for
        // First, get the max goals for between all the season team stats created in the previous pipeline stages.
        // Then, format the output for the team found as the one with most goals for
        // Finally, the result is written to a file.
        seasonTeamStats.apply("Comparing and getting the global max goals for between all the teams", Max.globally(new GoalsForComparator()))
                .apply("Formatting the output of goals for comparison stage", MapElements.via(new FormatGoalsOutputSimpleFn()))
                .apply("Writing the goals for result to a file", TextIO.write().withHeader(OUTPUT_FILE_HEADER).withoutSharding().to(String.format(MOST_GOALS_FOR_OUTPUT_PATH, StringUtils.substringBetween(eplPipelineOptions.getInputDataset(), "/", "."))));

        // Output the team with most goals against
        // First, get the max goals against between all the season team stats created in the previous pipeline stages.
        // Then, format the output for the team found as the one with most goals against
        // Finally, the result is written to a file.
        seasonTeamStats.apply("Comparing and getting the global max goals against between all the teams", Max.globally(new GoalsAgainstComparator()))
                .apply("Formatting the output of goals against comparison stage", MapElements.via(new FormatGoalsOutputSimpleFn()))
                .apply("Writing the goals against result to a file", TextIO.write().withHeader(OUTPUT_FILE_HEADER).withoutSharding().to(String.format(MOST_GOALS_AGAINST_OUTPUT_PATH, StringUtils.substringBetween(eplPipelineOptions.getInputDataset(), "/", "."))));


        // Output the goals over shots on target ratio
        // First, get the max shots on target to goals ratio between all the season team stats created in the previous pipeline stages.
        // Then, format the output for the team found as the one with most shots on target to goals ratio
        // Finally, the result is written to a file.
        seasonTeamStats.apply("Comparing and getting the global max shots on target to goal ratio between all the teams", Max.globally(new GoalsShotsOnTargetComparator()))
                .apply("Formatting the output of shots on target to goal ratio comparison stage", MapElements.via(new FormatGoalsOutputSimpleFn()))
                .apply("Writing the shots on target to goals ratio result to a file", TextIO.write().withHeader(OUTPUT_FILE_HEADER).withoutSharding().to(String.format(MOST_GOALS_SHOTS_RATIO_OUTPUT_PATH, StringUtils.substringBetween(eplPipelineOptions.getInputDataset(), "/", "."))));

        // Output the position's table for the season
        // First, add a dummy key to convert the original PCollection into one with iterable values after grouping by key
        // Then create the position's table iterating through the values
        // Finally, the result is written to a file.
        seasonTeamStats.apply("Adding dummy key to the KV to make them iterable in the next stages", MapElements.via(new AddDummyKeySimpleFn())) // Add dummy key to make the KV iterable in the next step after grouping
                .apply("Grouping all the elements by dummy key to iterate the values in the next stage", GroupByKey.create())
                .apply("Creating the positions table output as string", MapElements.via(new PositionsTableSimpleFn()))
                .apply("Writing the result positions table to a file", TextIO.write().withHeader(OUTPUT_FILE_HEADER).withoutSharding().to(String.format(POSITIONS_TABLE_OUTPUT_PATH, StringUtils.substringBetween(eplPipelineOptions.getInputDataset(), "/", "."))));

        // Run the defined pipeline and wait until it finishes.
        eplPipeline.run().waitUntilFinish();
    }

}
