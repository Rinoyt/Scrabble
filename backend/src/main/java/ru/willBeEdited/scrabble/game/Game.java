package ru.willBeEdited.scrabble.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Player;

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
        // pass
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
