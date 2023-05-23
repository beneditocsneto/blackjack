package com.altbank.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static com.altbank.util.Utils.createFullDeck;

@Getter
@AllArgsConstructor
public class Deck {

    private final List<Card> cards;

    public Deck() {
        this.cards = createFullDeck();
    }
}
