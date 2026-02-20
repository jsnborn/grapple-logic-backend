package com.grapplelogic.engine.dto;

public record TurnResult(
        boolean success,
        String actionLog,
        String newPosition,
        boolean isGameOver,
        int pointsGained
) {}