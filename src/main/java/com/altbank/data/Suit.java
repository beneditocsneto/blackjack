package com.altbank.data;

import lombok.Getter;

public enum Suit {
    CLUBS("Paus"),
    DIAMONDS("Ouros"),
    HEARTS("Copas"),
    SPADES("Espadas");

    @Getter
    private final String desc;

    Suit(String desc) {
        this.desc = desc;
    }
}
