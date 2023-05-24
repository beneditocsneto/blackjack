package com.altbank.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Card")
class CardTest {

    @Test
    @DisplayName("Should return the correct string representation of the card")
    void toStringReturnsCorrectStringRepresentation() {
        Card card = new Card(Rank.ACE, Suit.SPADES);
        String expectedString = "As de Espadas";
        String actualString = card.toString();
        assertEquals(expectedString, actualString);
    }
}
