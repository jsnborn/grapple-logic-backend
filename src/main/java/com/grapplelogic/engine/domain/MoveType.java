package com.grapplelogic.engine.domain;

public enum MoveType {
    DOUBLE_LEG("Takedown", "Takedown Defense"),
    PULL_GUARD("Ground Bottom", "Takedown Defense"),
    ARMBAR("Submission", "Ground Bottom"),
    TRIANGLE_CHOKE("Submission", "Ground Top");

    private final String attackerStat;
    private final String defenderStat;

    MoveType(String attackerStat, String defenderStat) {
        this.attackerStat = attackerStat;
        this.defenderStat = defenderStat;
    }

    public String getAttackerStat() { return attackerStat; }
    public String getDefenderStat() { return defenderStat; }
}
