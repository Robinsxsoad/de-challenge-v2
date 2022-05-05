package com.robin.challenge.option;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

/**
 * Pipeline options. The only required parameter is the input dataset.
 */
public interface EplPipelineOptions extends PipelineOptions {

    @Description("Input dataset")
    String getInputDataset();

    void setInputDataset(String inputDataset);

}
