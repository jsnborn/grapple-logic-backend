package com.grapplelogic.engine.dto;

import com.grapplelogic.engine.domain.MatchPosition;

/**
 * A Record is a clean way to handle immutable data in Java 16+.
 * It automatically generates getters, toString, and equals methods.
 */
public record TurnResult(
        boolean success,
        String actionLog,
        int pointsGained,
        MatchPosition newPosition,
        boolean isGameOver
) {}
