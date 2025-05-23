package ru.willBeEdited.scrabble.game.util;

import java.util.ArrayList;
import java.util.List;

public final class GameUtil {
    public static List<Word> getWords(int boardSize, List<Integer> coordinates) {
        List<Word> words = new ArrayList<>();

        boolean[][] board = new boolean[boardSize][boardSize];
        for (int x = 0; x < coordinates.size(); x += 2) {
            board[coordinates.get(x)][coordinates.get(x+1)] = true;
        }

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                // check if the current letter could be the start of a word
                if (board[x][y]) {
                    // the word is horizontal and has more than 1 letter
                    if ((x == 0 || !board[x - 1][y]) && (x + 1 < boardSize || board[x + 1][y])) {
                        int length = 1;
                        while (x + length < boardSize && board[x + length][y]) {
                            length++;
                        }
                        
                        words.add(new Word(x, y, length, true, false));
                    }

                    // the word is vertical and has more than 1 letter
                    if ((y == 0 || !board[x][y - 1]) && (y + 1 < boardSize || board[x][y + 1])) {
                        int length = 1;
                        while (y + length < boardSize && board[x][y + length]) {
                            length++;
                        }

                        words.add(new Word(x, y, length, false, true));
                    }
                }
            }
        }

        return words;
    }
}
