package ru.willBeEdited.scrabble.game;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.willBeEdited.scrabble.game.bag.Bag;
import ru.willBeEdited.scrabble.game.tile.Tile;
import ru.willBeEdited.scrabble.game.board.Board;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Hand;
import ru.willBeEdited.scrabble.game.player.Player;

import java.util.*;

import static ru.willBeEdited.scrabble.game.util.RandomInt.getNewId;

@Primary
@Controller
@SessionAttributes("game")
@Scope("prototype")
public class Game extends AbstractGame{
    private final List<Player> players = new ArrayList<>();

    private final Bag bag;

    public Game(Board board, Bag bag) {
        status = Status.PLAYER_TURN;
        id = getNewId();
        this.board = board;
        this.bag = bag;
        turn = 1;
    }

    public void addPlayer(Player player) {
        player.addAllToHand(bag.draw(7));
        if (players.isEmpty()) {
            setCurrentTurnPlayerId(player.getId());
        }
        players.add(player);
    }

    public String checkMove(Move move) {
        Player player = getCurrentPlayer();

        if (move.getTileId().isEmpty()) {
            return null; // move: pass
        } else if (move.getCoordinates().isEmpty()) {
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

            List<Integer> coordinates = move.getCoordinates();
            if (coordinates.size() != tiles.size()) {
                return "tiles and their coordinates for placement do not match";
            }

            int row = -1;
            int column = -1;
            int blankNumber = move.getBlank().size();
            int boardSize = board.getBoard().length;
            boolean[][] placed = new boolean[boardSize][boardSize];
            for (int i = 0; i < coordinates.size(); i += 2) {
                int x = coordinates.get(i);
                int y = coordinates.get(i + 1);
                if (x < 0 || boardSize <= x || y < 0 || boardSize <= y) {
                    return "incorrect coordinates";
                }

                if (placed[x][y]) {
                    return "cannot place tiles on the same square on the board";
                }
                placed[x][y] = true;

                if (i == 0) {
                    row = x;
                    column = y;
                } else {
                    if (row == x) {
                        column = -1;
                    } else if (column == y) {
                        row = -1;
                    } else {
                        return "all tiles must be placed in one row or column";
                    }
                }

                if (board.getBoard()[x][y].hasTile()) {
                    return "tried to place a tile on an occupied square";
                }

                Tile tile = tiles.get(i / 2);
                if (tile.isBlank()) {
                    if (blankNumber-- <= 0) {
                        return "not all blanks were given a character";
                    }
                }
            }

            List<Integer> coordinatesForWords = move.getCoordinatesForWords();
            for (int i = 0; i < tiles.size(); i += 2) {
                int x = coordinates.get(i);
                int y = coordinates.get(i + 1);
                if (x < 0 || boardSize <= x || y < 0 || boardSize <= y) {
                    return "incorrect coordinates for formed words";
                }

                // TODO: check if coordinatesForWords contains coordinates

                // TODO: check if words are adjacent to the placed tiles
            }

            return null;
        }
    }

    public List<Tile> makeMove(Move move) {
        List<Tile> drawnTiles = bag.draw(move.getTileId().size());
        super.makeMove(move, drawnTiles);
        return drawnTiles;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Bag getBag() {
        return bag;
    }

    @Override
    public Player getCurrentPlayer() {
        for (Player player : players) {
            if (player.getId() == currentTurnPlayerId) {
                return player;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

    @Override
    protected void nextPlayer() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == currentTurnPlayerId) {
                currentTurnPlayerId = players.get((i + 1) % players.size()).getId();
                return;
            }
        }

        throw new IllegalStateException("Current player not found with id " + currentTurnPlayerId);
    }

    @Override
    protected void checkForEnd() {
        checkForEnd(players);
    }
}
