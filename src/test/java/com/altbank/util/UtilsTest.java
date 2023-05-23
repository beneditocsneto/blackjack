package com.altbank.util;

import com.altbank.data.Card;
import com.altbank.data.Rank;
import com.altbank.data.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilsTest {

    @Test
    @DisplayName("Should create a deck with 52 cards")
    void createsFullDeckWith52Cards() {
        List<Card> deck = Utils.createFullDeck();
        assertEquals(52, deck.size());
    }

    @Test
    @DisplayName("Should create a deck with all unique cards")
    void createsFullDeckWithUniqueCards() {
        List<Card> deck = Utils.createFullDeck();
        Set<Card> uniqueCards = new HashSet<>(deck);
        assertEquals(52, uniqueCards.size());
    }

    @Test
    @DisplayName("Should create a deck with all possible rank and suit combinations")
    void createsFullDeckWithAllRankSuitCombinations() {
        List<Card> deck = Utils.createFullDeck();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                assertTrue(deck.contains(new Card(rank, suit)));
            }
        }
    }

    @Test
    @DisplayName("Should create a deck with each card has a valid rank")
    void createsFullDeckWithValidRank(){
        List<Card> deck = Utils.createFullDeck();
        List<Rank> ranks = Arrays.asList(Rank.values());
        for (Card card : deck) {
            assertTrue(ranks.contains(card.getRank()));
        }
    }

    @Test
    @DisplayName("Should create a deck with each card has a valid suit")
    void createsFullDeckWithValidSuit(){
        List<Card> deck = Utils.createFullDeck();
        List<Rank> ranks = Arrays.asList(Rank.values());
        for (Card card : deck) {
            assertTrue(ranks.contains(card.getRank()));
        }
    }
}
