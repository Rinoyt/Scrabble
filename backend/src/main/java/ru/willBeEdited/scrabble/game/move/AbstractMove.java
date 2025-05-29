package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.ScrabbleGame;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMove implements Move {
    protected int playerId;

    public AbstractMove() {
    }

    public AbstractMove(int playerId) {
        this.playerId = playerId;
    }

    @Override
    public List<Tile> apply(Game game) {
        List<Tile> drawnTiles = game.draw(drawAmount());
        apply(game, drawnTiles);
        return drawnTiles;
    }

    protected abstract int drawAmount();

    @Override
    public void apply(GameView game, List<Tile> drawnTiles) {
        game.draw(drawAmount());
        apply((ScrabbleGame) game, drawnTiles);
    }

    public void apply(ScrabbleGame game, List<Tile> drawnTiles) {
        game.startTurn();
        applyImpl(game, drawnTiles);
        game.endTurn();
    }

    protected abstract void applyImpl(ScrabbleGame game, List<Tile> drawnTiles);

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
