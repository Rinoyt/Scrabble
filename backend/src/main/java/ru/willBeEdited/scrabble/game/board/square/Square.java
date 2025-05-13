package ru.willBeEdited.scrabble.game.board.square;

import ru.willBeEdited.scrabble.game.Tile.Tile;

public class Square {
    private final SquareType type;
    private final int multiplier;
    private Tile tile;

    public Square() {
        type = SquareType.BLANK;
        multiplier = 1;
    }

    public Square(SquareType type, int multiplier) {
        this.type = type;
        this.multiplier = multiplier;
    }

    public SquareType getSquareType() {
        return type;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public boolean hasTile() {
        return tile != null;
    }

    public void placeTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
