package ru.willBeEdited.scrabble.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.bag.BagView;
import ru.willBeEdited.scrabble.game.board.StandardBoard;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Opponent;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.player.StandardPlayer;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class GameView extends AbstractGame {
    private Player player;
    private List<Opponent> opponents;

    private BagView bag;

    public GameView() {
    }

    public GameView(Game game, int playerId) {
        id = game.getId();
        status = game.getStatus();
        board = new StandardBoard(game.getBoard());
        turn = game.getTurn();
        currentTurnPlayerId = game.getCurrentTurnPlayerId();

        bag = new BagView(game.getBag());
        opponents = new ArrayList<>();
        for (Player play : game.getPlayers()) {
            if (play.getId() == playerId) {
                player = new StandardPlayer(play);
            } else {
                opponents.add(new Opponent(play));
            }
        }
    }

    public List<Tile> makeMove(Move move) {
        int drawnTilesSize = bag.draw(move.getTileId().size());
        List<Tile> drawnTiles = new ArrayList<>();
        for (int i = 0; i < drawnTilesSize; i++) {
            drawnTiles.add(new Tile());
        }
        super.makeMove(move, drawnTiles);
        return drawnTiles;
    }

    public void makeMove(Move move, List<Tile> drawnTiles) {
        bag.draw(drawnTiles.size());
        super.makeMove(move, drawnTiles);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Opponent> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<Opponent> opponents) {
        this.opponents = opponents;
    }

    public BagView getBag() {
        return bag;
    }

    public void setBag(BagView bag) {
        this.bag = bag;
    }

    @Override
    public Player getCurrentPlayer() {
        if (player.getId() == currentTurnPlayerId) {
            return player;
        }

        for (Opponent opponent : opponents) {
            if (opponent.getId() == currentTurnPlayerId) {
                return opponent;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

    @Override
    protected void nextPlayer() {
        if (player.getId() == currentTurnPlayerId) {
            currentTurnPlayerId = opponents.getFirst().getId();
            status = Status.PLAYER_TURN;
            return;
        }

        for (int i = 0; i < opponents.size(); i++) {
            if (opponents.get(i).getId() == currentTurnPlayerId) {
                currentTurnPlayerId = opponents.get((i + 1) % opponents.size()).getId();
                status = Status.OPPONENT_TURN;
                return;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

//    @Override
//    protected void checkForEnd() {
//        if (status != Status.PLAYER_TURN && status != Status.OPPONENT_TURN) {
//            return;
//        }
//
//        if (scorelessTurns >= 6 || getCurrentPlayer().getHand().size() == 0) {
//            int bestScore = player.getScore();
//            int winnerId = player.getId();
//            status = Status.WON;
//            for (Opponent opponent : opponents) {
//                if (opponent.getScore() > bestScore) {
//                    winnerId = opponent.getId();
//                    bestScore = opponent.getScore();
//                    status = Status.WON;
//                } else if (opponent.getScore() == bestScore) {
//                    status = Status.DRAW;
//                }
//            }
//        }
//    }
}
