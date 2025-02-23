package com.robin.challenge.stage.dofn;

import com.robin.challenge.domain.MatchResult;
import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.util.Objects;

import static com.robin.challenge.constant.PipelineConstants.AWAY_WINS;
import static com.robin.challenge.constant.PipelineConstants.DRAW;
import static com.robin.challenge.constant.PipelineConstants.DRAW_POINTS;
import static com.robin.challenge.constant.PipelineConstants.HOME_WINS;
import static com.robin.challenge.constant.PipelineConstants.WIN_POINTS;


/**
 * This stage creates the season stats for a team. The input is the list of MatchResult associated to a key, which is the team name.
 */
public class CreateSeasonStatsPerTeamDoFn extends DoFn<KV<String, Iterable<MatchResult>>, KV<String, SeasonTeamStats>> {

    @ProcessElement
    public void processElement(@Element KV<String, Iterable<MatchResult>> teamSeasonResults, OutputReceiver<KV<String, SeasonTeamStats>> output) {
        String teamName = teamSeasonResults.getKey();
        Iterable<MatchResult> seasonMatchResultsPerTeam = teamSeasonResults.getValue();
        SeasonTeamStats seasonTeamStats = new SeasonTeamStats();
        Objects.requireNonNull(seasonMatchResultsPerTeam).forEach(matchResult -> {
            seasonTeamStats.setTeamName(teamName);
            seasonTeamStats.addPoints(calculatePointsToAdd(teamName, matchResult));
            seasonTeamStats.addTotalGoalsFor(calculateGoalsForToAdd(teamName, matchResult));
            seasonTeamStats.addTotalGoalsAgainst(calculateGoalsAgainstToAdd(teamName, matchResult));
            seasonTeamStats.addTotalShotsOnTarget(calculateTotalShotsOnTargetToAdd(teamName, matchResult));
        });

        output.output(KV.of(teamName, seasonTeamStats));

    }

    private static int calculatePointsToAdd(String teamName, MatchResult matchResult) {
        if (matchResult.getFullTimeResult().equals(DRAW)) {
            return DRAW_POINTS;
        } else {
            if (matchResult.getHomeTeam().equals(teamName) && matchResult.getFullTimeResult().equals(HOME_WINS)) {
                return WIN_POINTS;
            } else {
                if (matchResult.getAwayTeam().equals(teamName) && matchResult.getFullTimeResult().equals(AWAY_WINS)) {
                    return WIN_POINTS;
                }
            }
        }
        return 0;
    }

    private static int calculateGoalsForToAdd(String teamName, MatchResult matchResult) {
        if (matchResult.getHomeTeam().equals(teamName)) {
            return matchResult.getFullTimeHomeGoals();
        } else {
            if (matchResult.getAwayTeam().equals(teamName)) {
                return matchResult.getFullTimeAwayGoals();
            }
        }
        return 0;
    }

    private static int calculateGoalsAgainstToAdd(String teamName, MatchResult matchResult) {
        if (matchResult.getHomeTeam().equals(teamName)) {
            return matchResult.getFullTimeAwayGoals();
        } else {
            if (matchResult.getAwayTeam().equals(teamName)) {
                return matchResult.getFullTimeHomeGoals();
            }
        }
        return 0;
    }

    private static int calculateTotalShotsOnTargetToAdd(String teamName, MatchResult matchResult) {
        if (matchResult.getHomeTeam().equals(teamName)) {
            return matchResult.getHomeShotsTarget();
        } else {
            if (matchResult.getAwayTeam().equals(teamName)) {
                return matchResult.getAwayShotsTarget();
            }
        }
        return 0;
    }
}
