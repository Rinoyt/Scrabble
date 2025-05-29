package ru.willBeEdited.scrabble.game;

import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame implements ScrabbleGame {
    private int id;

    private Status status;
    // can be multiple in case of a draw
    private List<Integer> winnerId;

    private List<Player> players;

    private Board board;

    private int turn;
    private int scorelessTurns;

    private int currentTurnPlayerId;

    public AbstractGame() {
    }

    public AbstractGame(int id, Status status, List<Integer> winnerId, Board board,
                        int turn, int scorelessTurns, int currentTurnPlayerId) {
        this.id = id;
        this.status = status;
        this.winnerId = winnerId;
        this.players = new ArrayList<>();
        this.board = board;
        this.turn = turn;
        this.scorelessTurns = scorelessTurns;
        this.currentTurnPlayerId = currentTurnPlayerId;
    }

    @Override
    public void startTurn() {
        turn++;
        scorelessTurns++;
    }

    @Override
    public void endTurn() {
        checkForEnd();
        nextPlayer();
    }

    @Override
    public void addScore(int score) {
        getCurrentPlayer().addScore(score);

        if (score != 0) {
            scorelessTurns = 0;
        }
    }

    @Override
    public List<Tile> draw(int amount) {
        return getBag().draw(amount);
    }

    public void addPlayer(Player player) {
        player.addAllToHand(draw(7));
        if (players.isEmpty()) {
            setCurrentTurnPlayerId(player.getId());
        }
        players.add(player);
    }

    @Override
    public Player getCurrentPlayer() {
        for (Player player : players) {
            if (player.getId() == currentTurnPlayerId) {
                return player;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

    @Override
    public void addAllToHand(List<Tile> tiles) {
        getCurrentPlayer().addAllToHand(tiles);
    }

    @Override
    public List<Tile> getAllFromHand(List<Integer> tileId) {
        return getCurrentPlayer().getAllFromHand(tileId);
    }

    @Override
    public void removeAllFromHand(List<Integer> tileId) {
        getCurrentPlayer().removeAllFromHand(tileId);
    }

    @Override
    public int getBagSize() {
        return getBag().size();
    }

    @Override
    public int getBoardSize() {
        return getBoard().getBoard().length;
    }

    @Override
    public Square getSquareFromBoard(int x, int y) {
        return board.getSquare(x, y);
    }

    @Override
    public void placeTile(int x, int y, Tile tile) {
        board.placeTile(x, y, tile);
    }

    protected void nextPlayer() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == currentTurnPlayerId) {
                currentTurnPlayerId = players.get((i + 1) % players.size()).getId();
                return;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

    protected boolean checkForEnd() {
        if (status != Status.PLAYER_TURN && status != Status.OPPONENT_TURN) {
            return false;
        }

        if (scorelessTurns >= 6 || getCurrentPlayer().getHandSize() == 0) {
//            for (Player player : players) {
//                player.addScore(-player.getHand().getSumScore());
//            }

            int bestScore = -1;
            List<Integer> winnerId = new ArrayList<>();
            for (Player player : players) {
                if (player.getScore() > bestScore) {
                    winnerId = new ArrayList<>();
                    winnerId.add(player.getId());
                    bestScore = player.getScore();
                    status = Status.WON;
                } else if (player.getScore() == bestScore) {
                    winnerId.add(player.getId());
                    status = Status.DRAW;
                }
            }

            this.winnerId = winnerId;
            return true;
        }

        return false;
    }

    // getters and setters for json

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Integer> getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(List<Integer> winnerId) {
        this.winnerId = winnerId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public abstract Bag getBag();

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getCurrentTurnPlayerId() {
        return currentTurnPlayerId;
    }

    public void setCurrentTurnPlayerId(int currentTurnPlayerId) {
        this.currentTurnPlayerId = currentTurnPlayerId;
    }

    public int getScorelessTurns() {
        return scorelessTurns;
    }

    public void setScorelessTurns(int scorelessTurns) {
        this.scorelessTurns = scorelessTurns;
    }
}
