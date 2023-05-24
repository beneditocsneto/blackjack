package com.altbank;

import com.altbank.data.Card;
import com.altbank.data.Deck;
import com.altbank.data.Player;
import com.altbank.service.Dealer;
import com.altbank.service.Game;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@QuarkusMain
public class BlackJack {

    public static void main(String[] args) {
        Quarkus.run(BlackJackApp.class, args);
    }

    public static class BlackJackApp implements QuarkusApplication {

        private final Dealer dealerService;
        private final Game gameService;
        Deck deck;
        Player player;
        Player dealer;

        public BlackJackApp(Dealer dealerService, Game gameService) {
            this.dealerService = dealerService;
            this.gameService = gameService;
            this.deck = new Deck(this.gameService.createFullDeck());
            this.player = new Player();
            this.dealer = new Player();
        }

        @Override
        public int run(String... args) throws Exception {
            System.out.println("Bem vindo. O dealer irá distribuir as cartas.");
            deck = dealerService.shuffleCards(deck);
            player = addCardToPlayerHand(player, pickACardFromDeck());
            dealer = addCardToPlayerHand(dealer, pickACardFromDeck());
            player = addCardToPlayerHand(player, pickACardFromDeck());
            dealer = addCardToPlayerHand(dealer, pickACardFromDeck());

            System.out.printf("Suas cartas são: %s%n", playerHandToString(player.getHand()));
            System.out.printf("As cartas do dealer são: %s e outra carta virada para baixo.%n", dealer.getHand().get(1).toString());

            playerTurn();
            if (!gameService.checkLose(player.getHand())) {
                dealerTurn();
                if (gameService.checkLose(dealer.getHand())) {
                    System.out.println("O dealer ultrapassou o limite de pontos, você venceu.");
                    return 0;
                }
            }
            System.out.printf("Você fez %s pontos.%n", gameService.countScore(player.getHand()));
            System.out.println("O dealer venceu.");
            return 0;
        }

        Card pickACardFromDeck() {
            Card topCard = dealerService.getTopCard(deck);
            deck = dealerService.removeTopCard(deck);
            return topCard;
        }

        Player addCardToPlayerHand(Player player, Card card) {
            ArrayList<Card> hand = new ArrayList<>(player.getHand());
            hand.add(card);
            return new Player(hand);
        }

        String playerHandToString(List<Card> hand) {
            Card lastCard = hand.get(hand.size() - 1);
            ArrayList<Card> cards = new ArrayList<>(hand);
            cards.remove(hand.size() - 1);
            return format("%s e %s.",
                    cards.stream().map(Card::toString).collect(joining(", ")),
                    lastCard.toString());
        }

        void playerTurn() {
            Scanner input = new Scanner(System.in);
            boolean endedTurn = false;
            while (!deck.getCards().isEmpty() && !endedTurn) {
                System.out.println("\nDigite o código da ação que deseja realizar:");
                System.out.println("1 para pedir mais cartas.");
                System.out.println("2 para consultar sua pontuação.");
                System.out.println("3 para ver suas cartas.");
                System.out.println("4 para finalizar jogada.");
                int playerChoose = input.nextInt();
                clearTerminal();
                switch (playerChoose) {
                    case 1:
                        Card card = pickACardFromDeck();
                        System.out.printf("Você pegou a carta: %s%n", card.toString());
                        player = addCardToPlayerHand(player, card);
                        break;
                    case 2:
                        System.out.printf("Você possui %s pontos.%n", gameService.countScore(player.getHand()));
                        break;
                    case 3:
                        System.out.printf("Suas cartas são: %s%n", playerHandToString(player.getHand()));
                        break;
                    case 4:
                        endedTurn = true;
                        break;
                    default:
                }
            }
        }

        void dealerTurn() {
            clearTerminal();
            System.out.println("Então é a vez do dealer.");
            System.out.printf("Suas cartas são: %s%n", playerHandToString(dealer.getHand()));

            while (!deck.getCards().isEmpty()
                    && gameService.countScore(dealer.getHand()) <= gameService.countScore(player.getHand())) {
                Card card = pickACardFromDeck();
                dealer = addCardToPlayerHand(dealer, card);
                System.out.printf("O dealer optou por comprar uma carta: %s%n", card.toString());
            }
            System.out.println("O dealer terminou sua jogada.");
            System.out.printf("Suas cartas são: %s.%n", playerHandToString(dealer.getHand()));
            System.out.println(String.format("\nO dealer fez %s pontos.", gameService.countScore(dealer.getHand())));
        }

        private void clearTerminal() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }
}
