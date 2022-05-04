package com.robin.challenge.option;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface EplPipelineOptions extends PipelineOptions {

    @Description("Input dataset")
    String getInputDataset();
    void setInputDataset(String inputDataset);

}
