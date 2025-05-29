package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.ScrabbleGame;
import ru.willBeEdited.scrabble.game.player.Hand;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public class Exchange extends AbstractMove {
    private List<Integer> tileId;

    public Exchange() {
    }

    public Exchange(int playerId, List<Integer> tileId) {
        super(playerId);
        this.tileId = tileId;
    }

    @Override
    public void check(Game game) throws IllegalMoveException {
        if (game.getBagSize() < 7) {
            throw new IllegalMoveException("can only exchange tiles when the bag has 7 or more tiles");
        }

        if (game.getAllFromHand(getTileId()).size() != getTileId().size()) {
            throw new IllegalMoveException("tried to exchange tiles that were not in the hand");
        }
    }

    @Override
    protected int drawAmount() {
        return tileId.size();
    }

    @Override
    protected void applyImpl(ScrabbleGame game, List<Tile> drawnTiles) {
        game.removeAllFromHand(getTileId());
        game.addAllToHand(drawnTiles);
    }

    public List<Integer> getTileId() {
        return tileId;
    }

    public void setTileId(List<Integer> tileId) {
        this.tileId = tileId;
    }
}
