package com.altbank.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Player {

    private final List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }
}
