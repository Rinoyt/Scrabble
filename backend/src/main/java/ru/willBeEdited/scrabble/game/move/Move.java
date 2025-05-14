package ru.willBeEdited.scrabble.game.move;

public class Move {
    private int[] tileId;
    private int[] coordinates;

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
}
