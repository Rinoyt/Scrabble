package ru.willBeEdited.scrabble.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.willBeEdited.scrabble.game.Tile.Tile;
import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Hand;
import ru.willBeEdited.scrabble.game.player.Player;

import java.util.*;

@Controller
@SessionAttributes("game")
public class Game {
    private Status status;

    private final Player player;
    private final Player opponent;

//    private Move lastMove;

    private final Board board;
    private final Bag bag;

//    private int turn;
//    private int currentPlayersTurn;

    public Game(Player player, Player opponent, Board board, Bag bag) {
        this.player = player;
        this.opponent = opponent;
        this.board = board;
        this.bag = bag;
    }

    public String checkMove(Move move) {
        if (move.getTileId() == null) {
            return null; // move: pass
        } else if (move.getCoordinates() == null) {
            if (bag.size() >= 7) {
                return null;
            }

            Hand hand = player.getHand();
            for (int id : move.getTileId()) {
                if (!hand.contains(id)) {
                    return "tried to exchange tiles that were not in the hand";
                }
            }

            return "can only exchange tiles when the bag has 7 or more tiles";
        } else {
            // move: place tiles
            Hand hand = player.getHand();
            List<Tile> tiles = new ArrayList<>();
            for (int id : move.getTileId()) {
                Tile tile = hand.get(id);
                if (tile == null) {
                    return "tried to place tiles that were not in the hand";
                }

                tiles.add(tile);
            }

            int[] coordinates = move.getCoordinates();
            if (coordinates.length != tiles.size()) {
                return "tiles and their coordinates for placement do not match";
            }

            int blankNumber = move.getBlank().size();
            int boardSize = board.getBoard().length;
            for (int i = 0; i < coordinates.length; i += 2) {
                int x = coordinates[i];
                int y = coordinates[i + 1];
                if (x < 0 || boardSize <= x || y < 0 || boardSize <= y) {
                    return "incorrect coordinates";
                }

                Tile tile = tiles.get(i / 2);
                if (tile.isBlank()) {
                    if (blankNumber-- <= 0) {
                        return "not all blanks were given a character";
                    }
                }
            }

            return null;
        }
    }

    public void makeMove(Move move) {
        if (move.getTileId() == null) {
            return; // move: pass
        }

        // move: draw or place tiles
        Hand hand = player.getHand();
        List<Tile> tiles = new ArrayList<>();
        for (int id : move.getTileId()) {
            tiles.add(hand.remove(id));
            if (!bag.isEmpty()) {
                hand.add(bag.draw());
            }
        }

        // move: place tiles
        int[] coordinates = move.getCoordinates();
        List<Character> blanks = move.getBlank();
        Deque<Character> blanksQueue = new ArrayDeque<>();
        if (blanks != null) {
            blanksQueue.addAll(blanks);
        }
        if (coordinates != null) {
            for (int i = 0; i < coordinates.length; i += 2) {
                int x = coordinates[i];
                int y = coordinates[i + 1];
                Tile tile = tiles.get(i / 2);
                if (tile.isBlank()) {
                    tile.setCharacter(blanksQueue.removeFirst());
                }
                board.placeTile(x, y, tile);
            }
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Board getBoard() {
        return board;
    }

    public Bag getBag() {
        return bag;
    }
}
