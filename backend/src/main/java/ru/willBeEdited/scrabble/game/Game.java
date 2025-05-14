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
}
