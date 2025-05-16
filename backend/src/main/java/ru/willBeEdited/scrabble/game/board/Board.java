package ru.willBeEdited.scrabble.game.board;

import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.tile.Tile;

public interface Board {
    Square[][] getBoard();

    void setBoard(Square[][] board);

    void placeTile(int x, int y, Tile tile);

    Square getSquare(int x, int y);
}
