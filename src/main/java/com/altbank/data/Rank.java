package com.altbank.data;

import lombok.Getter;

public enum Rank {

    TWO(2, "Dois"),
    THREE(3, "Três"),
    FOUR(4, "Quatro"),
    FIVE(5, "Cinco"),
    SIX(6, "Seis"),
    SEVEN(7, "Sete"),
    EIGHT(8, "Oito"),
    NINE(9, "Nove"),
    TEN(10, "Dez"),
    JACK(11, "Valete"),
    QUEEN(11, "Dama"),
    KING(11, "Rei"),
    ACE(10, "As");

    @Getter
    private final int points;
    @Getter
    private final String desc;

    Rank(int points, String desc) {
        this.points = points;
        this.desc = desc;
    }
}
