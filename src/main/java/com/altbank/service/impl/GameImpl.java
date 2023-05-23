package com.altbank.service.impl;

import com.altbank.data.Card;
import com.altbank.data.Rank;
import com.altbank.data.Suit;
import com.altbank.service.Game;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

import static com.altbank.data.Rank.ACE;

@ApplicationScoped
public class GameImpl implements Game {

    private static final int MAX_SCORE = 21;
    private static final int ACE_DIFF_VALUE = 9;

    @Override
    public List<Card> createFullDeck() {
        List<Card> deck = new ArrayList<>();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }

    @Override
    public int countScore(List<Card> hand) {
        int score = handScore(hand);

        if (score > MAX_SCORE) {
            int numOfAces = countAces(hand);
            return reachMaxAceScore(score, numOfAces);
        }
        return score;
    }

    private int handScore(List<Card> hand) {
        return hand.stream()
                .map(card -> card.getRank().getPoints())
                .reduce(0, Integer::sum);
    }

    private int reachMaxAceScore(int score, int numOfAces) {
        int aceScore = score;

        for (int i = 0; i < numOfAces; i++) {
            aceScore -= ACE_DIFF_VALUE;
            if (score <= MAX_SCORE) return score;
        }

        return aceScore;
    }

    private int countAces(List<Card> hand) {
        return hand.stream()
                .filter(card -> card.getRank().equals(ACE)).map(e -> 1)
                .reduce(0, Integer::sum);
    }
}
