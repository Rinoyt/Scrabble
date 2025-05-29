package ru.willBeEdited.scrabble.game;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.board.Board;

import java.util.*;

import static ru.willBeEdited.scrabble.game.util.RandomInt.getNewId;

@Primary
@Controller
@SessionAttributes("game")
@Scope("prototype")
public class Game extends AbstractGame {
    private final Bag bag;

    public Game(Board board, Bag bag) {
        super(getNewId(), Status.PLAYER_TURN, new ArrayList<>(), board, 1, 0, -1);

        this.bag = bag;
    }

    @Override
    public Bag getBag() {
        return bag;
    }
}
