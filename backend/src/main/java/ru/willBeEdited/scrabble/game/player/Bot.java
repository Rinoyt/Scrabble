package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;

public interface Bot {
    Move chooseMove(Game game);
}
