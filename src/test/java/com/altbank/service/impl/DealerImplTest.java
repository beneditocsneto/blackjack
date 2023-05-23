package com.altbank.service.impl;

import com.altbank.data.Card;
import com.altbank.data.Deck;
import com.altbank.exception.DeckException;
import com.altbank.service.Dealer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.altbank.data.Rank.*;
import static com.altbank.data.Suit.CLUBS;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DealerImpl")
class DealerImplTest {

    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new DealerImpl();
    }

    @Test
    @DisplayName("Should throw a DeckException when trying to get the top card of an empty deck")
    void getTopCardOfEmptyDeckThrowsDeckException() {
        Deck emptyDeck = new Deck(List.of());
        assertThrows(
                DeckException.class,
                () -> dealer.getTopCard(emptyDeck),
                "Expected DeckException to be thrown when getting top card of empty deck");
    }

    @Test
    @DisplayName("Should return the top card of a non-empty deck")
    void getTopCardOfNonEmptyDeck() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(ACE, CLUBS));
        cards.add(new Card(KING, CLUBS));
        cards.add(new Card(QUEEN, CLUBS));
        cards.add(new Card(JACK, CLUBS));
        cards.add(new Card(TEN, CLUBS));
        Deck deck = new Deck(cards);

        Card topCard = dealer.getTopCard(deck);

        assertEquals(new Card(TEN, CLUBS), topCard);
    }

    @Test
    @DisplayName("Should throw a DeckException when trying to remove the top card from an empty deck")
    void removeTopCardWhenDeckIsEmptyThenThrowDeckException() {
        Deck emptyDeck = new Deck(List.of());

        assertThrows(
                DeckException.class,
                () -> dealer.removeTopCard(emptyDeck),
                "Expected DeckException to be thrown when trying to remove the top card from an empty deck");
    }

    @Test
    @DisplayName("Should remove the top card from the deck and return a new deck with the remaining cards")
    void removeTopCardWhenDeckHasCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(ACE, CLUBS));
        cards.add(new Card(KING, CLUBS));
        cards.add(new Card(QUEEN, CLUBS));
        Deck deck = new Deck(cards);

        Deck newDeck = dealer.removeTopCard(deck);

        assertEquals(2, newDeck.getCards().size());
        assertFalse(newDeck.getCards().contains(new Card(QUEEN, CLUBS)));
        assertTrue(newDeck.getCards().contains(new Card(ACE, CLUBS)));
        assertTrue(newDeck.getCards().contains(new Card(KING, CLUBS)));
    }

    @Test
    @DisplayName("Should return a shuffled deck with the same number of cards")
    void shuffleCardsReturnsShuffledDeckWithSameNumberOfCards() {
        Deck deck = new Deck();
        Deck shuffledDeck = dealer.shuffleCards(deck);

        assertEquals(deck.getCards().size(), shuffledDeck.getCards().size());
    }

    @Test
    @DisplayName("Should return a shuffled deck with the same cards but in different order")
    void shuffleCardsReturnsShuffledDeckWithSameCardsInDifferentOrder() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(ACE, CLUBS));
        cards.add(new Card(TWO, CLUBS));
        cards.add(new Card(THREE, CLUBS));
        cards.add(new Card(FOUR, CLUBS));
        cards.add(new Card(FIVE, CLUBS));
        cards.add(new Card(SIX, CLUBS));
        cards.add(new Card(SEVEN, CLUBS));
        cards.add(new Card(EIGHT, CLUBS));
        cards.add(new Card(NINE, CLUBS));
        cards.add(new Card(TEN, CLUBS));
        cards.add(new Card(JACK, CLUBS));
        cards.add(new Card(QUEEN, CLUBS));
        cards.add(new Card(KING, CLUBS));
        Deck deckWithKnownOrder = new Deck(cards);

        Deck shuffledDeck = dealer.shuffleCards(deckWithKnownOrder);

        assertEquals(deckWithKnownOrder.getCards().size(), shuffledDeck.getCards().size());
        assertTrue(shuffledDeck.getCards().containsAll(deckWithKnownOrder.getCards()));
        assertNotEquals(deckWithKnownOrder.getCards(), shuffledDeck.getCards());
    }
}
