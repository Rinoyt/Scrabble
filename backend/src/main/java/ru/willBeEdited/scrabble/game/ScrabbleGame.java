package ru.willBeEdited.scrabble.game;

import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

public interface ScrabbleGame {
    void startTurn();

    void endTurn();

    void addScore(int score);

    List<Tile> draw(int amount);

    void addPlayer(Player player);

    Player getCurrentPlayer();

    void addAllToHand(List<Tile> tiles);

    List<Tile> getAllFromHand(List<Integer> tileId);

    void removeAllFromHand(List<Integer> tileId);

    int getBagSize();

    int getBoardSize();

    Square getSquareFromBoard(int x, int y);

    void placeTile(int x, int y, Tile tile);
}
