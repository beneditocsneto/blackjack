package com.altbank.service.impl;

import com.altbank.data.Card;
import com.altbank.data.Deck;
import com.altbank.exception.DeckException;
import com.altbank.service.Dealer;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class DealerImpl implements Dealer {

    private static final String NO_MORE_CARDS = "There are no more cards in the deck.";

    @Override
    public Deck shuffleCards(Deck deck) {
        List<Card> cards = new ArrayList<>(deck.getCards());
        Collections.shuffle(cards);
        return new Deck(cards);
    }

    @Override
    public Deck removeTopCard(Deck deck) {
        List<Card> cards = verifyDeckCards(deck);
        int lastCardIndex = cards.size() - 1;

        ArrayList<Card> modifiedCardList = new ArrayList<>(cards);
        modifiedCardList.remove(lastCardIndex);
        return new Deck(modifiedCardList);
    }

    @Override
    public Card getTopCard(Deck deck) {
        List<Card> cards = verifyDeckCards(deck);
        return cards.get(cards.size() - 1);
    }

    private List<Card> verifyDeckCards(Deck deck){
        List<Card> cards = deck.getCards();
        if (cards.isEmpty())
            throw new DeckException(NO_MORE_CARDS);

        return cards;
    }
}
