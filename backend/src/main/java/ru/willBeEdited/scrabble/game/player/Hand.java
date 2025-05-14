package ru.willBeEdited.scrabble.game.player;

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

    public boolean contains(int id) {
        for (Tile tile : hand) {
            if (tile.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public Tile get(int id) {
        for (Tile tile : hand) {
            if (tile.getId() == id) {
                return tile;
            }
        }

        return null;
    }

    public void remove(Tile tile) {
        if (!hand.contains(tile)) {
            throw new IllegalArgumentException("Hand doesn't contain tile");
        }
        hand.remove(tile);
    }

    public Tile remove(int id) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getId() == id) {
                return hand.remove(i);
            }
        }

        throw new IllegalArgumentException("Hand doesn't contain tile with id " + id);
    }
}
