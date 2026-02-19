package com.grapplelogic.engine.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "players")
@Data // This is a Lombok annotation that creates getters/setters automatically
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private GrapplingClass grapplingClass;

    private int xp = 0;
    private int wins = 0;

    // We can even store "Custom" stats here that grow as they level up!
    private int strengthBonus = 0;
    private int techniqueBonus = 0;
}