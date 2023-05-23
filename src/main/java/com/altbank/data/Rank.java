package com.altbank.data;

import lombok.Getter;

import java.util.Set;

import static java.util.Set.of;

public enum Rank {
    TWO(of(2)),
    THREE(of(3)),
    FOUR(of(4)),
    FIVE(of(5)),
    SIX(of(6)),
    SEVEN(of(7)),
    EIGHT(of(8)),
    NINE(of(9)),
    TEN(of(10)),
    JACK(of(11)),
    QUEEN(of(11)),
    KING(of(11)),
    ACE(of(1, 10));

    @Getter
    private final Set<Integer> points;

    Rank(Set<Integer> points) {
        this.points = points;
    }
}
