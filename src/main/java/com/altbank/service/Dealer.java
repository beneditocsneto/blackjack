package com.altbank.service;

import com.altbank.data.Card;
import com.altbank.data.Deck;

public interface Dealer {

    Deck shuffleCards(Deck deck);

    Deck removeTopCard(Deck deck);

    Card getTopCard(Deck deck);
}
