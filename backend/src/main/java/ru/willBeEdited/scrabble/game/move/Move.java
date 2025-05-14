package ru.willBeEdited.scrabble.game.move;

import java.util.List;

public class Move {
    private int[] tileId;
    private int[] coordinates;
    private List<Character> blank;

    public int[] getTileId() {
        return tileId;
    }

    public void setTileId(int[] tileId) {
        this.tileId = tileId;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public List<Character> getBlank() {
        return blank;
    }

    public void setBlank(List<Character> blank) {
        this.blank = blank;
    }
}
