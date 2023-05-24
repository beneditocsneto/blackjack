package com.altbank.service;

import com.altbank.data.Card;

import java.util.List;

public interface Game {

    List<Card> createFullDeck();

    int countScore(List<Card> hand);

    boolean checkWin(List<Card> hand);

    boolean checkLose(List<Card> hand);
}
