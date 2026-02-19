package com.grapplelogic.engine.service;

import com.grapplelogic.engine.domain.GrapplingClass;
import com.grapplelogic.engine.domain.Player;
import com.grapplelogic.engine.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Creates a new fighter and saves them to PostgreSQL.
     */
    public Player createCharacter(String name, GrapplingClass grapplingClass) {
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setGrapplingClass(grapplingClass);
        newPlayer.setXp(0);
        newPlayer.setWins(0);

        // This line pushes the data to the database
        return playerRepository.save(newPlayer);
    }
}
