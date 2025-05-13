package ru.willBeEdited.scrabble.game.player;

import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.Hand;
import ru.willBeEdited.scrabble.game.Tile.Tile;

@Component
public class Player {
    private final String name;
    private int score;
    private final Hand hand = new Hand();

    public Player() {
        this.name = "Guest";
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Hand getHand() {
        return hand;
    }

    public void addToHand(Tile tile) {
        hand.add(tile);
    }

    public void removeFromHand(Tile tile) {
        hand.remove(tile);
    }
}
