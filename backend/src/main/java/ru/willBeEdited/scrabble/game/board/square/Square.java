package ru.willBeEdited.scrabble.game.board.square;

import ru.willBeEdited.scrabble.game.tile.Tile;

public class Square {
    private SquareType type;
    private int multiplier;
    private Tile tile;

    public Square() {
        type = SquareType.BLANK;
        multiplier = 1;
    }

    public Square(SquareType type, int multiplier) {
        this.type = type;
        this.multiplier = multiplier;
    }

    public Square(Square square) {
        type = square.getType();
        multiplier = square.getMultiplier();
        if (square.getTile() != null) {
            tile = new Tile(square.getTile());
        }
    }

    public SquareType getType() {
        return type;
    }

    public void setType(SquareType type) {
        this.type = type;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public boolean hasTile() {
        return tile != null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
