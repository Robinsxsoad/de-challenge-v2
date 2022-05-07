package com.robin.challenge.constant;

/**
 * Pipeline constant classes used in the code.
 */
public final class PipelineConstants {
    public static final String HOME_TEAM = "HomeTeam";
    public static final String AWAY_TEAM = "AwayTeam";
    public static final String FULL_TIME_RESULT = "FTR";
    public static final String FULL_TIME_HOME_GOALS = "FTHG";
    public static final String FULL_TIME_AWAY_GOALS = "FTAG";
    public static final String AWAY_SHOTS_ON_TARGET = "AST";
    public static final String HOME_SHOTS_ON_TARGET = "HST";

    public static final String HOME_WINS = "H";
    public static final String AWAY_WINS = "A";
    public static final String DRAW = "D";
    public static final int WIN_POINTS = 3;
    public static final int DRAW_POINTS = 1;

    public static final String DUMMY_KEY = "dummyKey";

    public static final String OUTPUT_FILE_HEADER = "team;total_points;goals_for;goals_against;goals_shots_on_target_ratio";
    public static final String OUTPUT_FILE_FORMAT = "%s;%d;%d;%d;%.2f\n";

    public static final String MOST_GOALS_FOR_OUTPUT_PATH = "output/%s/most-goals-for.txt";
    public static final String MOST_GOALS_AGAINST_OUTPUT_PATH = "output/%s/most-goals-against.txt";
    public static final String MOST_GOALS_SHOTS_RATIO_OUTPUT_PATH = "output/%s/most-goals-shots-target-ratio.txt";
    public static final String POSITIONS_TABLE_OUTPUT_PATH = "output/%s/positions-table.txt";
}
