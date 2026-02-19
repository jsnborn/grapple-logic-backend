package com.grapplelogic.engine.dto;

import com.grapplelogic.engine.domain.GrapplingClass;
import com.grapplelogic.engine.domain.MatchPosition;
import com.grapplelogic.engine.domain.MoveType;

public record MatchRequest(
        GrapplingClass attacker,
        GrapplingClass defender,
        MoveType move,
        MatchPosition currentPosition
) {}
