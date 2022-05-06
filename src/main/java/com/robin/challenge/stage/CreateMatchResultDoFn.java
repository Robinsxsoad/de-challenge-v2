package com.robin.challenge.stage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.robin.challenge.domain.MatchResult;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import static com.robin.challenge.constant.PipelineConstants.AWAY_SHOTS_ON_TARGET;
import static com.robin.challenge.constant.PipelineConstants.AWAY_TEAM;
import static com.robin.challenge.constant.PipelineConstants.FULL_TIME_AWAY_GOALS;
import static com.robin.challenge.constant.PipelineConstants.FULL_TIME_HOME_GOALS;
import static com.robin.challenge.constant.PipelineConstants.FULL_TIME_RESULT;
import static com.robin.challenge.constant.PipelineConstants.HOME_SHOTS_ON_TARGET;
import static com.robin.challenge.constant.PipelineConstants.HOME_TEAM;


/**
 * This stage creates the MatchResult object for the home and the away team using the input String (the JSON array in the dataset).
 */
public class CreateMatchResultDoFn extends DoFn<String, KV<String, MatchResult>> {

    static Gson gson = new Gson();

    @ProcessElement
    public void processElement(@Element String inputText, OutputReceiver<KV<String, MatchResult>> outputReceiver) {
        JsonArray jsonArray = gson.fromJson(inputText, JsonArray.class);
        jsonArray.forEach(match -> {
            outputReceiver.output(KV.of(match.getAsJsonObject().get(HOME_TEAM).getAsString(), createMatchResult(match)));
            outputReceiver.output(KV.of(match.getAsJsonObject().get(AWAY_TEAM).getAsString(), createMatchResult(match)));
        });
    }

    private MatchResult createMatchResult(JsonElement matchString) {
        return MatchResult.builder()
                .homeTeam(matchString.getAsJsonObject().get(HOME_TEAM).getAsString())
                .awayTeam(matchString.getAsJsonObject().get(AWAY_TEAM).getAsString())
                .fullTimeResult(matchString.getAsJsonObject().get(FULL_TIME_RESULT).getAsString())
                .fullTimeHomeGoals(matchString.getAsJsonObject().get(FULL_TIME_HOME_GOALS).getAsInt())
                .fullTimeAwayGoals(matchString.getAsJsonObject().get(FULL_TIME_AWAY_GOALS).getAsInt())
                .awayShotsTarget(matchString.getAsJsonObject().get(AWAY_SHOTS_ON_TARGET).getAsInt())
                .homeShotsTarget(matchString.getAsJsonObject().get(HOME_SHOTS_ON_TARGET).getAsInt())
                .build();
    }

}
