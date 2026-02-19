package com.grapplelogic.engine.repository;

import com.grapplelogic.engine.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // This automatically handles Save, Find, and Delete
}