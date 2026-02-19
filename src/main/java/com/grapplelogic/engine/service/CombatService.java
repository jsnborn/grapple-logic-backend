package com.grapplelogic.engine.service;

import com.grapplelogic.engine.domain.GrapplingClass;
import com.grapplelogic.engine.domain.MatchPosition;
import com.grapplelogic.engine.domain.MoveType;
import com.grapplelogic.engine.dto.TurnResult;
import org.springframework.stereotype.Service;

@Service
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

    public TurnResult resolveMove(GrapplingClass attacker, GrapplingClass defender, MoveType move, MatchPosition currentPos) {

        // 1. Position Check: You can't RNC if you aren't on the back!
        if (move == MoveType.REAR_NAKED_CHOKE && currentPos != MatchPosition.BACK_CONTROL) {
            return new TurnResult(false, "You need to take the back before attempting an RNC!", 0, currentPos, false);
        }

        // 2. Standard Math Logic
        int attackStat = getStatValue(attacker, move.getAttackerStat());
        int defenseStat = getStatValue(defender, move.getDefenderStat());
        int roll = (int) (Math.random() * 20) + 1;

        boolean success = (roll + attackStat) > (defenseStat + 10);

        // 3. Determine New Position & Points
        MatchPosition nextPos = currentPos;
        int pointsEarned = 0;
        boolean isGameOver = false;

        if (success) {
            pointsEarned = move.getPoints();
            nextPos = determineNextPosition(move);
            if (move == MoveType.REAR_NAKED_CHOKE || move == MoveType.ARMBAR) {
                isGameOver = true;
                nextPos = MatchPosition.SUBMITTED;
            }
        }

        String log = success ? "Successful " + move : attacker + " failed the " + move;
        return new TurnResult(success, log, pointsEarned, nextPos, isGameOver);
    }

    private MatchPosition determineNextPosition(MoveType move) {
        return switch (move) {
            // Logic for how positions transition
            case DOUBLE_LEG -> MatchPosition.SIDE_CONTROL;
            case BACK_TAKE -> MatchPosition.BACK_CONTROL;
            case PULL_GUARD -> MatchPosition.GUARD;
            default -> MatchPosition.STANDING;
        };
    }
}
