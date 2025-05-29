package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.Collection;
import java.util.List;

public interface Player {
    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    int getScore();

    void setScore(int score);

    void addScore(int score);

    Hand getHand();

    void setHand(Hand hand);

    void addToHand(Tile tile);

    void addAllToHand(List<Tile> tiles);

    List<Tile> getAllFromHand(List<Integer> tileId);

    void removeAllFromHand(List<Integer> tileId);

    int getHandSize();

    void removeFromHand(Tile tile);

    void removeFromHand(int tileId);
}
