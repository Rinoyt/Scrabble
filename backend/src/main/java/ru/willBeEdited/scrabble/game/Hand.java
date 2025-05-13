package ru.willBeEdited.scrabble.game;

import ru.willBeEdited.scrabble.game.Tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Tile> hand = new ArrayList<>();

    public List<Tile> getHand() {
        return hand;
    }

    public void add(Tile tile) {
        hand.add(tile);
    }

    public void remove(Tile tile) {
        if (!hand.contains(tile)) {
            throw new IllegalArgumentException("Hand doesn't contain tile");
        }
        hand.remove(tile);
    }
}
