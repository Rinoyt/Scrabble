package ru.willBeEdited.scrabble.game;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.bag.BagView;
import ru.willBeEdited.scrabble.game.player.Opponent;
import ru.willBeEdited.scrabble.game.player.Player;

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
        super(game.getId(), game.getStatus(), game.getWinnerId(), game.getBoard(),
                game.getTurn(), game.getScorelessTurns(), game.getCurrentTurnPlayerId());

        bag = new BagView(game.getBag());

        opponents = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (player.getId() == playerId) {
                this.player = player;
                addPlayer(player);
            } else {
                Opponent opponent = new Opponent(player);
                opponents.add(opponent);
                addPlayer(opponent);
            }
        }
    }

    @Override
    protected void nextPlayer() {
        super.nextPlayer();

        if (player.getId() == getCurrentTurnPlayerId()) {
            setStatus(Status.PLAYER_TURN);
        } else {
            setStatus(Status.OPPONENT_TURN);
        }
    }

    @Override
    protected boolean checkForEnd() {
        boolean ended = super.checkForEnd();
        if (ended && !getWinnerId().contains(player.getId())) {
            setStatus(Status.LOST);
        }

        return ended;
    }

    // getters and setters for json

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

    @Override
    public BagView getBag() {
        return bag;
    }

    public void setBag(BagView bag) {
        this.bag = bag;
    }
}
