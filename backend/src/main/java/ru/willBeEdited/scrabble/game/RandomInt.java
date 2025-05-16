package ru.willBeEdited.scrabble.game;

public final class RandomInt {
    static int id = 0;

    public static int getNewId() {
        return id++;
    }
}
