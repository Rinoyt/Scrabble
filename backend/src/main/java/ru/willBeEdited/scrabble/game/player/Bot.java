package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public interface Bot extends Player {
    Move chooseMove(Game game);

    void updateHand(List<Tile> tiles);
}
