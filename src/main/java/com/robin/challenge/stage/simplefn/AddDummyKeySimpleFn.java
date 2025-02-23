package com.robin.challenge.stage.simplefn;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import static com.robin.challenge.constant.PipelineConstants.DUMMY_KEY;

/**
 * Function to add a dummy key to the original PCollection of String and season stats. Adding this dummy key will allow the next step in the pipeline to iterate the PCollection elements.
 */
public class AddDummyKeySimpleFn extends SimpleFunction<KV<String, SeasonTeamStats>, KV<String, KV<String, SeasonTeamStats>>> {

    @Override
    public KV<String, KV<String, SeasonTeamStats>> apply(KV<String, SeasonTeamStats> input) {
        return KV.of(DUMMY_KEY, input);
    }
}
