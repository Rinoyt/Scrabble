package ru.willBeEdited.scrabble.game.board;

import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.Tile.Tile;

public interface Board {
    Square[][] getBoard();

    void placeTile(int x, int y, Tile tile);

    Square getSquare(int x, int y);
}
