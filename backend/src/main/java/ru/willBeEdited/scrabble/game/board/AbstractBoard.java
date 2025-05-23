package ru.willBeEdited.scrabble.game.board;

import ru.willBeEdited.scrabble.game.tile.Tile;
import ru.willBeEdited.scrabble.game.board.square.Square;

public abstract class AbstractBoard implements Board {
    protected Square[][] board;

    public AbstractBoard() {
        board = new Square[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = new Square();
            }
        }
    }

    @Override
    public Square[][] getBoard() {
        return board;
    }

    @Override
    public void setBoard(Square[][] board) {
        this.board = board;
    }

    @Override
    public void placeTile(int x, int y, Tile tile) {
        board[x][y].setTile(tile);
    }

    @Override
    public Square getSquare(int x, int y) {
        return board[x][y];
    }
}
