package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Hand {
    private List<Tile> tiles = new ArrayList<>();

    public Hand() {
    }

    public Hand(Hand hand) {
        tiles.addAll(hand.tiles);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void add(Tile tile) {
        tiles.add(tile);
    }

    public void addAll(Collection<Tile> tiles) {
        for (Tile tile : tiles) {
            add(tile);
        }
    }

    public boolean contains(int id) {
        for (Tile tile : tiles) {
            if (tile.getId() == id) {
                return true;
            }
        }

        return false;
    }

    public Tile get(int id) {
        for (Tile tile : tiles) {
            if (tile.getId() == id) {
                return tile;
            }
        }

        return null;
    }

    public void remove(Tile tile) {
        if (!tiles.contains(tile)) {
            throw new IllegalArgumentException("hand doesn't contain tile");
        }
        tiles.remove(tile);
    }

    public Tile remove(int id) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getId() == id) {
                return tiles.remove(i);
            }
        }

        throw new IllegalArgumentException("Hand doesn't contain tile with id " + id);
    }

    public int size() {
        return tiles.size();
    }

    public int getSumScore() {
        int score = 0;
        for (Tile tile : tiles) {
            score += tile.getScore();
        }
        return score;
    }
}
