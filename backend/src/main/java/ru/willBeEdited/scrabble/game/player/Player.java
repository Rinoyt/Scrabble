package ru.willBeEdited.scrabble.game.player;

import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.tile.Tile;

import static ru.willBeEdited.scrabble.game.player.PlayerUtil.getRandomInt;

@Component
public class Player {
    private int id;
    private String name;

    private int score;
    private Hand hand = new Hand();

    public Player() {
        id = getRandomInt();
        name = "Guest" + getRandomInt();
    }

    public Player(Player player) {
        id = player.getId();
        name = player.getName();
        score = player.getScore();
        hand = player.getHand();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addToHand(Tile tile) {
        hand.add(tile);
    }

    public void removeFromHand(Tile tile) {
        hand.remove(tile);
    }
}
