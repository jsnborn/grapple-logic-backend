package com.grapplelogic.engine.domain;

public enum MoveType {
    // These are the "Constants". They are like static instances of this class.
    // The numbers in the parentheses are passed to the constructor below
    DOUBLE_LEG("Takedown", "Takedown Defense", 2),
    PULL_GUARD("Ground Bottom", "Takedown Defense", 0),
    PASS_GUARD("Ground Top", "Ground Bottom", 3),
    BACK_TAKE("Ground Top", "Ground Bottom", 4), // Move to get to the Back
    REAR_NAKED_CHOKE("Submission", "Submission Defense", 0), // The Finisher
    ARMBAR("Submission", "Ground Bottom", 0);

    private final String attackerStat;
    private final String defenderStat;
    private final int points;

    MoveType(String attackerStat, String defenderStat, int points) {
        this.attackerStat = attackerStat;
        this.defenderStat = defenderStat;
        this.points = points;
    }

    public String getAttackerStat() { return attackerStat; }
    public String getDefenderStat() { return defenderStat; }
    public int getPoints() { return points; }
}
