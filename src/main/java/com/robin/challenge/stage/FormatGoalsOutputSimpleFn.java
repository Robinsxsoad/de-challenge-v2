package com.robin.challenge.stage;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import static com.robin.challenge.constant.PipelineConstants.OUTPUT_FILE_FORMAT;

public class FormatGoalsOutputSimpleFn extends SimpleFunction<KV<String, SeasonTeamStats>, String> {
    @Override
    public String apply(KV<String, SeasonTeamStats> input) {
        SeasonTeamStats seasonTeamStats = input.getValue();
        return String.format(OUTPUT_FILE_FORMAT, seasonTeamStats.getTeamName(), seasonTeamStats.getTotalPoints(), seasonTeamStats.getTotalGoalsFor(), seasonTeamStats.getTotalGoalsAgainst(), seasonTeamStats.getGoalsShotsOnTargetRatio()).trim();
    }
}
