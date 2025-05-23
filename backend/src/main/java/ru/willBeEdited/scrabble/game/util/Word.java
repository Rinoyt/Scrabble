package ru.willBeEdited.scrabble.game.util;

public final class Word {
    private final int x;
    private final int y;
    private final int length;
    private final boolean horizontal;
    private final boolean vertical;

    public Word(int x, int y, int length, boolean horizontal, boolean vertical) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLength() {
        return length;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return vertical;
    }
}
