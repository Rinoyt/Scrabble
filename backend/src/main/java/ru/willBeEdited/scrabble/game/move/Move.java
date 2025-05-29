package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public interface Move {
    void check(Game game) throws IllegalMoveException;

    List<Tile> apply(Game game);

    void apply(GameView game, List<Tile> drawnTiles);

    int getPlayerId();

    void setPlayerId(int playerId);
}
