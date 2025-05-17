package ru.willBeEdited.scrabble.game.tile;

import static ru.willBeEdited.scrabble.game.RandomInt.getNewId;

public class Tile {
    private int id;
    private char character;
    private boolean isBlank;
    private int score;

    public Tile() {
    }

    public Tile(char character, boolean isBlank, int score) {
        this.id = getNewId();
        this.character = character;
        this.isBlank = isBlank;
        this.score = score;
    }

    public Tile(char character, int score) {
        this(character, false, score);
    }

    public Tile(Tile tile) {
        this.id = tile.id;
        this.character = tile.character;
        this.isBlank = tile.isBlank;
        this.score = tile.score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBlank(boolean blank) {
        isBlank = blank;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
