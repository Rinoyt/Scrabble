package ru.willBeEdited.scrabble.game.Tile;

public class Tile {
    private char character;
    private final boolean isBlank;
    private final int score;

    public Tile(char character, boolean isBlank, int score) {
        this.character = character;
        this.isBlank = isBlank;
        this.score = score;
    }

    public Tile(char character, int score) {
        this.character = character;
        this.isBlank = false;
        this.score = score;
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
