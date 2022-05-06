package com.robin.challenge.stage;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.robin.challenge.constant.PipelineConstants.OUTPUT_FILE_FORMAT;

/**
 * Function to compare all the teams based on points.
 * In this function the teams are sorted using the collection utils class based on the comparison method implemented in the class SeasonTeamStats.
 */
public class PositionsTableSimpleFn extends SimpleFunction<KV<String, Iterable<KV<String, SeasonTeamStats>>>, String> {

    @Override
    public String apply(KV<String, Iterable<KV<String, SeasonTeamStats>>> input) {
        List<SeasonTeamStats> seasonTeamStatsList = new ArrayList<>();
        Objects.requireNonNull(input.getValue()).forEach(kv -> {
            seasonTeamStatsList.add(kv.getValue());
        });
        Collections.sort(seasonTeamStatsList);
        StringBuilder stringBuilder = new StringBuilder();
        seasonTeamStatsList.forEach(sortedSeasonTeamStats -> {
            stringBuilder.append(String.format(OUTPUT_FILE_FORMAT, sortedSeasonTeamStats.getTeamName(), sortedSeasonTeamStats.getTotalPoints(), sortedSeasonTeamStats.getTotalGoalsFor(), sortedSeasonTeamStats.getTotalGoalsAgainst(), sortedSeasonTeamStats.getGoalsShotsOnTargetRatio()));
        });
        return stringBuilder.toString().trim();
    }
}
