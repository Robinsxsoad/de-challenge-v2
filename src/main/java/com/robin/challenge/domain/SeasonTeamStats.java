package com.robin.challenge.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * This class maps the stats for a specific team in the complete season.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SeasonTeamStats implements Comparable<SeasonTeamStats>, Serializable {
    private String teamName;
    private int totalPoints;
    private int totalGoalsFor;
    private int totalGoalsAgainst;
    private int totalShotsOnTarget;

    public void addPoints(int points) {
        this.totalPoints = this.totalPoints + points;
    }

    public void addTotalGoalsFor(int goals) {
        this.totalGoalsFor = this.totalGoalsFor + goals;
    }

    public void addTotalGoalsAgainst(int goals) {
        this.totalGoalsAgainst = this.totalGoalsAgainst + goals;
    }

    public void addTotalShotsOnTarget(int shotsOnTarget) {
        this.totalShotsOnTarget = this.totalShotsOnTarget + shotsOnTarget;
    }

    public double getGoalsShotsOnTargetRatio() {
        return ((double) this.totalGoalsFor) / this.totalShotsOnTarget;
    }

    /**
     * Implemented to sort list using Collection utils
     *
     * @param o the object to be compared.
     * @return the int value depending on the points of the team.
     */
    @Override
    public int compareTo(SeasonTeamStats o) {
        return o.getTotalPoints() - this.totalPoints;
    }

}
