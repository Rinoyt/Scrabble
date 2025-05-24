package ru.willBeEdited.scrabble.game.util;

public final class RandomInt {
    private static int id = 0;

    public static int getNewId() {
        return id++;
    }
}
