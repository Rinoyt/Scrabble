package ru.willBeEdited.scrabble.game;

import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public abstract class AbstractGame {
    protected int id;

    protected Status status;
    // can be multiple in case of a draw
    protected final List<Integer> winnerId = new ArrayList<>();

    protected Board board;

    protected int turn;
    protected int currentTurnPlayerId;

    protected int scorelessTurns;

    public void makeMove(Move move, List<Tile> drawnTiles) {
        turn++;

        if (move.getTileId() == null) {
            return; // move: pass
        }

        Player player = getCurrentPlayer();

        // move: draw or place tiles
        for (int id : move.getTileId()) {
            player.removeFromHand(id);
        }
        player.addAllToHand(drawnTiles);

        // move: place tiles
        List<Integer> coordinates = move.getCoordinates();

        // calculating score
        int score = 0;
        List<Integer> coordinatesForWords = move.getCoordinatesForWords();
        // TODO: calculate the score

        if (score == 0) {
            scorelessTurns++;
        }
        player.addScore(score);

        // placing tiles on the board
        List<Character> blanks = move.getBlank();
        Deque<Character> blanksQueue = new ArrayDeque<>();
        if (blanks != null) {
            blanksQueue.addAll(blanks);
        }
        if (coordinates != null) {
            for (int i = 0; i < coordinates.size(); i += 2) {
                int x = coordinates.get(i);
                int y = coordinates.get(i + 1);
                Tile tile = move.getTiles().get(i / 2);
                if (tile.isBlank()) {
                    tile.setCharacter(blanksQueue.removeFirst());
                }
                board.placeTile(x, y, tile);
            }
        }

//        checkForEnd();
        nextPlayer();
    }

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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

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

    public abstract Player getCurrentPlayer();

    protected abstract void nextPlayer();

//    protected abstract void checkForEnd();
}
