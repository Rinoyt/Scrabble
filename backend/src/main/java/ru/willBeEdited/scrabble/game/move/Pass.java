package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.ScrabbleGame;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public class Pass extends AbstractMove {
    public Pass() {}

    public Pass(int id) {
        super(id);
    }

    @Override
    public void check(Game game) throws IllegalMoveException {
        // no checks
    }

    @Override
    protected int drawAmount() {
        return 0;
    }

    @Override
    protected void applyImpl(ScrabbleGame game, List<Tile> drawnTiles) {
        // pass
    }
}
