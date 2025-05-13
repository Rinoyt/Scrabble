package ru.willBeEdited.scrabble.game.player;

import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;

@Component
public class MissingBot implements Bot {
    // Always pass
    public Move chooseMove(Game game) {
        return new Move();
    }
}
