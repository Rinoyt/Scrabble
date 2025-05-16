package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.tile.Tile;

public interface Player {
    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public int getScore();

    public void setScore(int score);

    public void addScore(int score);

    public Hand getHand();

    public void setHand(Hand hand);

    public void addToHand(Tile tile);

    public void removeFromHand(Tile tile);
}
