package ru.willBeEdited.scrabble.game;

import org.junit.jupiter.api.Test;
import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.board.StandardBoard;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.player.StandardPlayer;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void getCurrentPlayer() {
        Game game = new Game(new StandardBoard(), new Bag());
        Player player = new StandardPlayer();
        game.addPlayer(player);
        assertEquals(player.getId(), game.getCurrentPlayer().getId());
    }

    @Test
    void makeMove() {
        Game game = new Game(new StandardBoard(), new Bag());
        Player player = new StandardPlayer();
        game.addPlayer(player);

        List<Tile> hand = player.getHand().getTiles();
        int[] tiles = new int[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            tiles[i] = hand.get(i).getId();
        }

        Move move = new Move();
        move.setPlayerId(player.getId());
        move.setTileId(tiles);
        assertEquals(7, game.makeMove(move).size());
    }
}