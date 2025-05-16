package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public class Move {
    private int playerId;

    private int[] tileId;
    private List<Tile> tiles;
    private int[] coordinates;

    private List<Character> blank;

    public Move() {
    }

    public Move(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int[] getTileId() {
        return tileId;
    }

    public void setTileId(int[] tileId) {
        this.tileId = tileId;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
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
