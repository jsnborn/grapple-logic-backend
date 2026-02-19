package com.grapplelogic.engine.domain;

public class CombatService {

    // Core logic to resolve a move between two grapplers using RNG (D20 roll)
    public boolean calculateSuccess(GrapplingClass attacker, GrapplingClass defender, MoveType move) {
        int attackStat = getStatValue(attacker, move.getAttackerStat());
        int defenseStat = getStatValue(defender, move.getDefenderStat());

        // Roll a 20-sided die
        int roll = (int) (Math.random() * 20) + 1;

        // Formula: Roll + Attacker Stat > Defender Stat + 10 (Base Difficulty)
        return (roll + attackStat) > (defenseStat + 10);
    }

    private int getStatValue(GrapplingClass gClass, String statName) {
        return switch (statName) {
            case "Takedown" -> gClass.getTakedown();
            case "Takedown Defense" -> gClass.getTakedownDefense();
            case "Ground Top" -> gClass.getGroundTop();
            case "Ground Bottom" -> gClass.getGroundBottom();
            case "Submission" -> gClass.getSubmission();
            default -> 5;
        };
    }
}
