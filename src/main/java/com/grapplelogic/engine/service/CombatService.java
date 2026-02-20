package com.grapplelogic.engine.service;

import com.grapplelogic.engine.domain.GrapplingClass;
import com.grapplelogic.engine.domain.MatchPosition;
import com.grapplelogic.engine.domain.MoveType;
import com.grapplelogic.engine.domain.Player;
import com.grapplelogic.engine.dto.MatchRequest;
import com.grapplelogic.engine.dto.TurnResult;
import com.grapplelogic.engine.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CombatService {

    private final PlayerRepository playerRepository;

    public CombatService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public TurnResult resolveTurn(Long playerId, MatchRequest request) {
        MoveType move = request.move();
        GrapplingClass attacker = request.attacker();
        GrapplingClass defender = request.defender();
        MatchPosition currentPos = request.currentPosition();

        boolean success = false;
        String actionLog = "Invalid move for this position.";
        MatchPosition nextPos = currentPos;
        boolean isGameOver = false;
        int pointsGained = 0;

        // Standing Phase
        if (move == MoveType.DOUBLE_LEG || move == MoveType.PULL_GUARD) {
            if (currentPos == MatchPosition.STANDING) {
                success = (attacker.getTakedown() + (int)(Math.random() * 10)) >
                        (defender.getTakedownDefense() + (int)(Math.random() * 10));

                if (success) {
                    nextPos = (move == MoveType.DOUBLE_LEG) ? MatchPosition.SIDE_CONTROL : MatchPosition.GUARD;
                    actionLog = attacker + " successfully executed " + move;
                } else {
                    actionLog = defender + " stuffed the attempt!";
                }
            }
        }
        // Transition Phase
        else if (move == MoveType.BACK_TAKE) {
            if (currentPos == MatchPosition.SIDE_CONTROL || currentPos == MatchPosition.GUARD) {
                success = true;
                nextPos = MatchPosition.BACK_CONTROL;
                actionLog = attacker + " took the back!";
            }
        }
        // Finishing Phase
        else if (move == MoveType.REAR_NAKED_CHOKE) {
            if (currentPos == MatchPosition.BACK_CONTROL) {
                success = (attacker.getSubmission() + (int)(Math.random() * 10)) > 5;
                if (success) {
                    actionLog = attacker + " forced the tap with an RNC!";
                    isGameOver = true;
                    pointsGained = 10; // Assigning points for the UI
                } else {
                    actionLog = defender + " fought off the hands!";
                }
            }
        }

        // Database Update
        if (isGameOver && playerId != null) {
            applyRewards(playerId);
        }

        // Return with all 5 required parameters for the TurnResult record
        return new TurnResult(
                success,
                actionLog,
                nextPos.name(),
                isGameOver,
                pointsGained
        );
    }

    private void applyRewards(Long playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setXp(player.getXp() + 10);
            player.setWins(player.getWins() + 1);
            playerRepository.save(player); // Persist to PostgreSQL
        }
    }
}