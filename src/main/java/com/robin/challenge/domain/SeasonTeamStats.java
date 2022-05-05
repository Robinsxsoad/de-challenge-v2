package com.robin.challenge.domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Builder
public class SeasonTeamStats implements Serializable {
    private int totalPoints;
    private int totalGoalsFor;
    private int totalGoalsAgainst;
    private int totalShotsOnTarget;

    public void addPoints(int points){
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

}
