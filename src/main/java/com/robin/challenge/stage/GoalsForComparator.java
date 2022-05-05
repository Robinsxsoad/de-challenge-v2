package com.robin.challenge.stage;

import com.robin.challenge.domain.SeasonTeamStats;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class GoalsForComparator implements Comparator<KV<String, SeasonTeamStats>>, Serializable {

    @Override
    public int compare(KV<String, SeasonTeamStats> o1, KV<String, SeasonTeamStats> o2) {
        return Objects.requireNonNull(o1.getValue()).getTotalGoalsFor() - Objects.requireNonNull(o2.getValue()).getTotalGoalsFor();
    }
}
