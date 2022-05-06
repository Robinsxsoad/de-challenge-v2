package com.robin.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Match result class. This class maps the input dataset, since each element in the file is the result of a match.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResult implements Serializable {
    private String homeTeam;
    private String awayTeam;
    private String fullTimeResult;
    private int fullTimeAwayGoals;
    private int fullTimeHomeGoals;
    private int awayShotsTarget;
    private int homeShotsTarget;

}
