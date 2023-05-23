package com.altbank.util;

import com.altbank.data.Card;
import com.altbank.data.Rank;
import com.altbank.data.Suit;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils() {
    }

    public static List<Card> createFullDeck() {
        List<Card> deck = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }
}
