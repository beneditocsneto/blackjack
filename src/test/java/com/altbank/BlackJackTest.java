package com.altbank;

import com.altbank.data.Card;
import com.altbank.data.Deck;
import com.altbank.data.Player;
import com.altbank.service.Dealer;
import com.altbank.service.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.altbank.data.Rank.*;
import static com.altbank.data.Suit.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BlackJackAppTest {

    private final Dealer dealerService = mock(Dealer.class);
    private final Game gameService = mock(Game.class);

    @Test
    @DisplayName("Should pick a card from the deck and remove it from the deck")
    void pickACardFromDeckAndRemoveFromDeck() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(ACE, HEARTS));
        cards.add(new Card(KING, SPADES));

        Deck deck = new Deck(cards);
        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);
        blackJackApp.deck = deck;

        when(dealerService.getTopCard(deck)).thenReturn(new Card(ACE, HEARTS));
        when(dealerService.removeTopCard(deck)).thenReturn(new Deck(cards.subList(1, cards.size())));

        Card pickedCard = blackJackApp.pickACardFromDeck();

        assertEquals(new Card(ACE, HEARTS), pickedCard);
        assertEquals(cards.subList(1, cards.size()), blackJackApp.deck.getCards());
        verify(dealerService, times(1)).getTopCard(deck);
        verify(dealerService, times(1)).removeTopCard(deck);
    }

    @Test
    @DisplayName("Should add a card to the player's hand")
    void addCardToPlayerHand() {
        Card card = new Card(ACE, HEARTS);
        Player player = new Player(new ArrayList<>());
        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);

        Player updatedPlayer = blackJackApp.addCardToPlayerHand(player, card);

        assertEquals(1, updatedPlayer.getHand().size());
        assertTrue(updatedPlayer.getHand().contains(card));
    }

    @Test
    @DisplayName("Should return a new player object with updated hand")
    void addCardToPlayerHandReturnsNewPlayerWithUpdatedHand() {
        Card card1 = new Card(ACE, HEARTS);
        Card card2 = new Card(KING, SPADES);
        List<Card> hand = new ArrayList<>();
        hand.add(card1);

        Player player = new Player(hand);
        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);

        when(dealerService.getTopCard(any(Deck.class))).thenReturn(card2);
        when(dealerService.removeTopCard(any(Deck.class))).thenReturn(new Deck(new ArrayList<>()));
        Player updatedPlayer = blackJackApp.addCardToPlayerHand(player, card2);

        assertEquals(2, updatedPlayer.getHand().size());
        assertTrue(updatedPlayer.getHand().contains(card1));
        assertTrue(updatedPlayer.getHand().contains(card2));
    }

    @Test
    @DisplayName("Should return a string representation of the player's hand with the last card separated by 'e'")
    void playerHandToStringWithLastCardSeparatedByE() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(TWO, CLUBS));
        hand.add(new Card(THREE, DIAMONDS));
        hand.add(new Card(ACE, HEARTS));

        Player player = new Player(hand);
        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);

        String expected = "Dois de Paus, Três de Ouros e As de Copas.";
        String actual = blackJackApp.playerHandToString(player.getHand());

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should end player turn when option 4 is selected")
    void playerTurnEndsWhenOption4IsSelected() {
        List<Card> hand = new ArrayList<>();
        hand.add(new Card(ACE, CLUBS));
        hand.add(new Card(KING, DIAMONDS));

        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);
        blackJackApp.player = new Player(hand);
        blackJackApp.deck = new Deck(List.of(new Card(TWO, HEARTS)));

        Scanner scanner = new Scanner("4\n");
        System.setIn(new ByteArrayInputStream(scanner.nextLine().getBytes()));

        blackJackApp.playerTurn();

        verify(dealerService, never()).getTopCard(any());
        verify(dealerService, never()).removeTopCard(any());
        verify(gameService, never()).countScore(any());
        verify(gameService, never()).checkLose(any());
    }

    @Test
    @DisplayName("Should execute player actions based on input")
    void playerTurnExecutesPlayerActionsBasedOnInput() {
        Deck deck = new Deck(List.of(new Card(TWO, HEARTS)));

        List<Card> hand = new ArrayList<>();
        hand.add(new Card(TWO, CLUBS));
        hand.add(new Card(KING, DIAMONDS));
        Player player = new Player(hand);

        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);
        blackJackApp.player = player;
        blackJackApp.deck = deck;

        when(dealerService.getTopCard(blackJackApp.deck)).thenReturn(new Card(TWO, HEARTS));
        when(dealerService.removeTopCard(blackJackApp.deck)).thenReturn(new Deck(List.of()));
        when(gameService.countScore(player.getHand())).thenReturn(13);

        String userInput = "1\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        blackJackApp.playerTurn();

        assertEquals(3, blackJackApp.player.getHand().size());
        assertTrue(blackJackApp.deck.getCards().isEmpty());
    }

    @Test
    @DisplayName("Should stop drawing cards when the deck is empty")
    void dealerTurnStopsWhenDeckIsEmpty() {
        List<Card> emptyCards = new ArrayList<>();
        Deck emptyDeck = new Deck(emptyCards);

        List<Card> hand = new ArrayList<>();
        hand.add(new Card(ACE, CLUBS));
        hand.add(new Card(KING, HEARTS));
        Player player = new Player(hand);

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(TWO, DIAMONDS));
        dealerHand.add(new Card(THREE, SPADES));
        Player dealer = new Player(dealerHand);

        when(dealerService.getTopCard(emptyDeck)).thenReturn(new Card(FOUR, CLUBS));
        when(dealerService.removeTopCard(emptyDeck)).thenReturn(emptyDeck);

        BlackJack.BlackJackApp blackJackApp = new BlackJack.BlackJackApp(dealerService, gameService);
        blackJackApp.deck = emptyDeck;
        blackJackApp.dealer = dealer;
        blackJackApp.player = player;

        blackJackApp.dealerTurn();

        verify(dealerService, times(0)).getTopCard(emptyDeck);
        verify(dealerService, times(0)).removeTopCard(emptyDeck);
    }

    @Test
    @DisplayName("Should continue drawing cards until dealer's score is greater than player's score")
    void dealerTurnContinuesDrawingCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(TWO, CLUBS));
        cards.add(new Card(ACE, DIAMONDS));
        Deck deck = new Deck(cards);

        List<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card(FIVE, HEARTS));
        playerHand.add(new Card(FOUR, SPADES));
        Player player = new Player(playerHand);

        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(new Card(TWO, CLUBS));
        dealerHand.add(new Card(THREE, DIAMONDS));
        Player dealer = new Player(dealerHand);

        when(dealerService.getTopCard(deck)).thenReturn(new Card(ACE, DIAMONDS));
        when(dealerService.removeTopCard(deck)).thenReturn(new Deck(new ArrayList<>()));
        when(gameService.countScore(dealerHand)).thenReturn(5, 15);
        when(gameService.countScore(playerHand)).thenReturn(9);

        BlackJack.BlackJackApp app = new BlackJack.BlackJackApp(dealerService, gameService);
        app.deck = deck;
        app.player = player;
        app.dealer = dealer;
        app.dealerTurn();

        assertTrue(gameService.countScore(dealer.getHand()) > gameService.countScore(player.getHand()));
    }
}
