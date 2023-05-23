package com.altbank.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Card {

    private final Rank rank;
    private final Suit suit;
}
