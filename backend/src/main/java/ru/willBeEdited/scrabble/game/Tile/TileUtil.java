package ru.willBeEdited.scrabble.game.Tile;

public final class TileUtil {
    static int id = 0;

    static int getNewId() {
        return id++;
    }
}
