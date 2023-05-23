package com.altbank.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Player {

    private final List<Card> hand;
}
