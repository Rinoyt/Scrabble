package ru.willBeEdited.scrabble.game.Tile;

import static ru.willBeEdited.scrabble.game.Tile.TileUtil.getNewId;

public class Tile {
    private final int id;
    private char character;
    private final boolean isBlank;
    private final int score;

    public Tile(char character, boolean isBlank, int score) {
        this.id = getNewId();
        this.character = character;
        this.isBlank = isBlank;
        this.score = score;
    }

    public Tile(char character, int score) {
        this(character, false, score);
    }

    public int getId() {
        return id;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public boolean isBlank() {
        return isBlank;
    }

    public int getScore() {
        return score;
    }
}
