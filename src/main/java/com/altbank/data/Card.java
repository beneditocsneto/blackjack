package com.altbank.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Card {

    private final Rank rank;
    private final Suit suit;

    @Override
    public String toString() {
        return String.format("%s de %s", rank.getDesc(), suit.getDesc());
    }
}
