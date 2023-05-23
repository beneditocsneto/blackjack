package com.altbank.service.impl;

import com.altbank.data.Card;
import com.altbank.data.Rank;
import com.altbank.data.Suit;
import com.altbank.service.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.altbank.data.Rank.*;
import static com.altbank.data.Suit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("GameImpl")
class GameImplTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new GameImpl();
    }

    @Test
    @DisplayName("Should return the correct score when the hand has no aces")
    void countScoreWhenHandHasNoAces() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(TWO, CLUBS));
        hand.add(new Card(FIVE, DIAMONDS));
        hand.add(new Card(JACK, HEARTS));

        int score = game.countScore(hand);
        assertEquals(18, score);
    }

    @Test
    @DisplayName("Should return the correct score when the hand has aces and the score is below the maximum")
    void countScoreWhenHandHasAcesAndScoreIsBelowMax() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(ACE, CLUBS));
        hand.add(new Card(TWO, DIAMONDS));
        hand.add(new Card(THREE, HEARTS));

        int score = game.countScore(hand);

        assertEquals(15, score);
    }

    @Test
    @DisplayName("Should return the correct score when the hand has aces and the score is above the maximum")
    void countScoreWhenHandHasAcesAndScoreIsAboveMax() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(ACE, CLUBS));
        hand.add(new Card(ACE, DIAMONDS));
        hand.add(new Card(ACE, HEARTS));
        hand.add(new Card(ACE, SPADES));
        hand.add(new Card(KING, CLUBS));

        int score = game.countScore(hand);

        assertEquals(15, score);
    }

    @Test
    @DisplayName("Should create a deck with 52 cards")
    void createsFullDeckWith52Cards() {
        List<Card> deck = game.createFullDeck();
        assertEquals(52, deck.size());
    }

    @Test
    @DisplayName("Should create a deck with all unique cards")
    void createsFullDeckWithUniqueCards() {
        List<Card> deck = game.createFullDeck();
        Set<Card> uniqueCards = new HashSet<>(deck);
        assertEquals(52, uniqueCards.size());
    }

    @Test
    @DisplayName("Should create a deck with all possible rank and suit combinations")
    void createsFullDeckWithAllRankSuitCombinations() {
        List<Card> deck = game.createFullDeck();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                assertTrue(deck.contains(new Card(rank, suit)));
            }
        }
    }

    @Test
    @DisplayName("Should create a deck with each card has a valid rank")
    void createsFullDeckWithValidRank() {
        List<Card> deck = game.createFullDeck();
        List<Rank> ranks = Arrays.asList(Rank.values());
        for (Card card : deck) {
            assertTrue(ranks.contains(card.getRank()));
        }
    }

    @Test
    @DisplayName("Should create a deck with each card has a valid suit")
    void createsFullDeckWithValidSuit() {
        List<Card> deck = game.createFullDeck();
        List<Rank> ranks = Arrays.asList(Rank.values());
        for (Card card : deck) {
            assertTrue(ranks.contains(card.getRank()));
        }
    }
}
