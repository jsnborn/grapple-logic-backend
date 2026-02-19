package com.grapplelogic.engine.controller;

import com.grapplelogic.engine.dto.MatchRequest;
import com.grapplelogic.engine.dto.TurnResult;
import com.grapplelogic.engine.service.CombatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/match")

public class MatchController {

    private final CombatService combatService;

    // Use Constructor Injection
    public MatchController(CombatService combatService) {
        this.combatService = combatService;
    }

    /**
     * Resolves a single exchange in a grappling match.
     * Accessible via POST http://localhost:8080/api/v1/match/resolve
     */
    @PostMapping("/resolve")
    public ResponseEntity<TurnResult> resolveExchange(@RequestBody MatchRequest request) {

        TurnResult result = combatService.resolveMove(
                request.attacker(),
                request.defender(),
                request.move(),
                request.currentPosition()
        );

        return ResponseEntity.ok(result);
    }
}
