package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public class Move {
    private int playerId;

    private List<Integer> tileId;
    private List<Tile> tiles;
    private List<Integer> coordinates;

    // the player needs to pick which tiles form new words
    private List<Integer> coordinatesForWords;

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

    public List<Integer> getTileId() {
        return tileId;
    }

    public void setTileId(List<Integer> tileId) {
        this.tileId = tileId;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Integer> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Integer> getCoordinatesForWords() {
        return coordinatesForWords;
    }

    public void setCoordinatesForWords(List<Integer> coordinatesForWords) {
        this.coordinatesForWords = coordinatesForWords;
    }

    public List<Character> getBlank() {
        return blank;
    }

    public void setBlank(List<Character> blank) {
        this.blank = blank;
    }
}
