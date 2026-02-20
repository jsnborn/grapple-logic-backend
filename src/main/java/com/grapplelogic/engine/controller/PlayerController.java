package com.grapplelogic.engine.controller;

import com.grapplelogic.engine.domain.GrapplingClass;
import com.grapplelogic.engine.domain.Player;
import com.grapplelogic.engine.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Endpoint to create a new fighter.
     * POST http://localhost:8080/api/v1/players/create?name=YourName&style=BJJ
     */
    @PostMapping("/create")
    public Player createPlayer(@RequestParam String name, @RequestParam GrapplingClass style) {
        // We call the service to handle the heavy lifting (saving to DB)
        return playerService.createCharacter(name, style);
    }
}
