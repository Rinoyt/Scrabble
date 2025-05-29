package ru.willBeEdited.scrabble.game.move;

import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.ScrabbleGame;
import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.tile.Tile;
import ru.willBeEdited.scrabble.game.util.Word;

import java.util.*;

import static ru.willBeEdited.scrabble.game.util.GameUtil.getWords;

public class Play extends Exchange {
    private Map<Integer, Character> blank;

    private List<Integer> coordinates;
    private List<Integer> coordinatesForWords;

    public Play() {
    }

    public Play(int playerId, List<Integer> tileId, Map<Integer, Character> blank,
                List<Integer> coordinates, List<Integer> coordinatesForWords) {
        super(playerId, tileId);
        this.blank = blank;
        this.coordinates = coordinates;
        this.coordinatesForWords = coordinatesForWords;
    }

    @Override
    public void check(Game game) throws IllegalMoveException {
        List<Tile> tiles = game.getAllFromHand(getTileId());
        if (tiles.size() != getTileId().size()) {
            throw new IllegalMoveException("tried to place tiles that were not in the hand");
        }

        if (coordinates.size() != tiles.size()) {
            throw new IllegalMoveException("tiles and their coordinates for placement do not match");
        }

        int row = -1;
        int column = -1;
        int boardSize = game.getBoardSize();
        boolean[][] placed = new boolean[boardSize][boardSize];
        for (int i = 0; i < coordinates.size(); i += 2) {
            int x = coordinates.get(i);
            int y = coordinates.get(i + 1);
            if (x < 0 || boardSize <= x || y < 0 || boardSize <= y) {
                throw new IllegalMoveException("incorrect coordinates");
            }

            if (placed[x][y]) {
                throw new IllegalMoveException("cannot place tiles on the same square on the board");
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
                    throw new IllegalMoveException("all tiles must be placed in one row or column");
                }
            }

            if (game.getSquareFromBoard(x, y).hasTile()) {
                throw new IllegalMoveException("tried to place a tile on an occupied square");
            }

            Tile tile = tiles.get(i / 2);
            if (tile.isBlank()) {
                if (!blank.containsKey(tile.getId())) {
                    throw new IllegalMoveException("not all blanks were given a character: the tile with id = " + tile.getId());
                }
            }
        }

        for (int i = 0; i < tiles.size(); i += 2) {
            int x = coordinates.get(i);
            int y = coordinates.get(i + 1);
            if (x < 0 || boardSize <= x || y < 0 || boardSize <= y) {
                throw new IllegalMoveException("incorrect coordinates for formed words");
            }

            // TODO: check if coordinatesForWords contains coordinates

            // TODO: check if words are adjacent to the placed tiles
        }
    }

    @Override
    protected void applyImpl(ScrabbleGame game, List<Tile> drawnTiles) {
        List<Integer> tileId = getTileId();
        List<Tile> tiles = game.getAllFromHand(tileId);

        // calculating score
        int score = 0;

        List<Word> words = getWords(game.getBoardSize(), coordinatesForWords);
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
                square = game.getSquareFromBoard(x_, y_);

                Tile tile = square.getTile();
                if (tile == null) {
                    for (int i = 0; i < coordinates.size(); i += 2) {
                        Tile placedTile = tiles.get(i / 2);

                        if (placedTile.isBlank() && placedTile.getCharacter() == ' ') {
                            placedTile.setCharacter(blank.get(tileId.get(i / 2)));
                        }

                        if (coordinates.get(i) == x_ && coordinates.get(i + 1) == y_) {
                            tile = placedTile;
                            game.placeTile(x_, y_, tile);
                            break;
                        }
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

            score = wordScore * wordMultiplier;
        }

        game.addScore(score);

        super.applyImpl(game, drawnTiles);
    }
}
