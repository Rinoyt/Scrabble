package ru.willBeEdited.scrabble.game;

import org.junit.jupiter.api.Test;
import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.board.StandardBoard;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Bot;
import ru.willBeEdited.scrabble.game.player.MissingBot;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.player.StandardPlayer;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
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
        game.addPlayer(new MissingBot());

        GameView gameView =  new GameView(game, player.getId());

        List<Tile> hand = player.getHand().getTiles();
        List<Integer> tiles = new ArrayList<>();
        for (int i = 0; i < hand.size(); i++) {
            tiles.add(hand.get(i).getId());
        }

        Move move = new Move();
        move.setPlayerId(player.getId());
        move.setTileId(tiles);

        List<Tile> drawnTiles = game.makeMove(move);
        gameView.makeMove(move, drawnTiles);
        assertEquals(7, drawnTiles.size());
    }
}