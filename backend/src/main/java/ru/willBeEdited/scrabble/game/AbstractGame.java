package ru.willBeEdited.scrabble.game;

import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.board.square.SquareType;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;
import ru.willBeEdited.scrabble.game.util.Word;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static ru.willBeEdited.scrabble.game.util.GameUtil.getWords;

public abstract class AbstractGame {
    protected int id;

    protected Status status;
    // can be multiple in case of a draw
    protected List<Integer> winnerId;

    protected Board board;

    protected int turn;
    protected int currentTurnPlayerId;

    protected int scorelessTurns;

    public void makeMove(Move move, List<Tile> drawnTiles) {
        turn++;

        if (move.getTileId().isEmpty()) {
            scorelessTurns++; // move: pass
        } else {
            Player player = getCurrentPlayer();

            // move: draw or place tiles
            for (int id : move.getTileId()) {
                player.removeFromHand(id);
            }
            player.addAllToHand(drawnTiles);

            // move: place tiles
            List<Integer> coordinates = move.getCoordinates();

            List<Character> blanks = move.getBlank();
            Deque<Character> blanksQueue = new ArrayDeque<>();
            if (!blanks.isEmpty()) {
                blanksQueue.addAll(blanks);
            }

            // calculating score
            int score = 0;
            List<Integer> coordinatesForWords = move.getCoordinatesForWords();
            List<Word> words = getWords(getBoardSize(), coordinatesForWords);
            if (!words.isEmpty()) {
                for (Word word : words) {
                    int x = word.getX();
                    int y = word.getY();
                    int length = word.getLength();

                    int wordScore = 0;
                    int wordMultiplier = 1;

                    for (int l = 0; l < length; l++) {
                        int x_ = x;
                        int y_ = y;
                        Square square;

                        if (word.isHorizontal()) {
                            x_ += l;
                        } else {
                            y_ += l;
                        }
                        square = board.getSquare(x_, y_);

                        Tile tile = square.getTile();
                        if (tile == null) {
                            for (int i = 0; i < coordinates.size(); i += 2) {
                                Tile placedTile = move.getTiles().get(i / 2);

                                if (placedTile.isBlank() && placedTile.getCharacter() == ' ') {
                                    placedTile.setCharacter(blanksQueue.removeFirst());
                                }

                                if (coordinates.get(i) == x_ && coordinates.get(i + 1) == y_) {
                                    tile = placedTile;
                                    board.placeTile(x_, y_, tile);
                                    break;
                                }
                            }

                            int letterScore = tile.getScore();
                            switch (square.getType()) {
                                case WORD -> wordMultiplier *= square.getMultiplier();
                                case LETTER -> letterScore *= square.getMultiplier();
                                case BLANK -> {
                                }
                            }

                            wordScore += letterScore;
                        }
                    }

                    score = wordScore * wordMultiplier;
                }
            }

            if (score == 0) {
                scorelessTurns++;
            }
            player.addScore(score);
        }

        checkForEnd();
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

    public int getBoardSize() {
        return getBoard().getBoard().length;
    }

    public int getCurrentTurnPlayerId() {
        return currentTurnPlayerId;
    }

    public void setCurrentTurnPlayerId(int currentTurnPlayerId) {
        this.currentTurnPlayerId = currentTurnPlayerId;
    }

    public abstract Player getCurrentPlayer();

    protected abstract void nextPlayer();

    protected abstract void checkForEnd();

    protected boolean checkForEnd(List<Player> players) {
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
}
