package ru.willBeEdited.scrabble.game;

import org.junit.jupiter.api.Test;
import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.board.StandardBoard;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.player.StandardPlayer;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void getCurrentPlayer() {
        Game game = new Game(new StandardBoard(), new Bag());
        Player player = new StandardPlayer();
        game.addPlayer(player);
        assertEquals(player.getId(), game.getCurrentPlayer().getId());
    }
}