package com.robin.challenge.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MatchResult implements Serializable {
    private String homeTeam;
    private String awayTeam;
    private String fullTimeResult;
    private int fullTimeAwayGoals;
    private int fullTimeHomeGoals;
    private int awayShotsTarget;
    private int homeShotsTarget;

}
