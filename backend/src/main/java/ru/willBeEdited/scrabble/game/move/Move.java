package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.game.Tile.Tile;

import java.util.Map;

public class Move {
    private final MoveType type;
    private Tile[] shipTiles;
    private Map<Tile, int[][]> placeTiles;

    public Move() {
        this.type = MoveType.PASS;
    }

    public Move(Tile[] tiles) {
        this.type = MoveType.DRAW;
        this.shipTiles = tiles;
    }

    public Move(Map<Tile, int[][]> move) {
        this.type = MoveType.PLACE_TILES;
        this.placeTiles = move;
    }

    public MoveType getType() {
        return type;
    }

    public Tile[] getShipTiles() {
        return shipTiles;
    }

    public Map<Tile, int[][]> getPlacement() {
        return placeTiles;
    }
}
