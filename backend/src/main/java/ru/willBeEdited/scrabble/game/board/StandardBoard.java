package ru.willBeEdited.scrabble.game.board;

import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.board.square.Square;
import ru.willBeEdited.scrabble.game.board.square.SquareType;

@Component
public class StandardBoard extends AbstractBoard {
    private final Square[][] board;

    public StandardBoard() {
        board = super.board;

        // triple word
        assign4(0, 0, new Square(SquareType.WORD, 3));
        assign8(7, 0, new Square(SquareType.WORD, 3));

        // double word
        for (int i = 1; i < 5; i++) {
            assign4(i, i, new Square(SquareType.WORD, 2));
        }
        assign1(7, 7, new Square(SquareType.WORD, 2)); // center

        // triple letter
        assign8(5, 1, new Square(SquareType.LETTER, 3));
        assign4(5, 5, new Square(SquareType.LETTER, 3));

        // double letter
        assign4(7, 7, new Square(SquareType.LETTER, 2));
        assign8(3, 0, new Square(SquareType.LETTER, 2));
        assign8(6, 2, new Square(SquareType.LETTER, 2));
        assign8(7, 3, new Square(SquareType.LETTER, 2));
    }

    private void assign1(int x, int y, Square square) {
        board[x][y] = square;
    }

    private void assign4(int x, int y, Square square) {
        int max = board.length - 1;

        board[x][y] = square;
        board[max - x][y] = square;

        board[x][max - y] = square;
        board[max - x][max - y] = square;
    }

    private void assign8(int x, int y, Square square) {
        int max = board.length - 1;

        board[x][y] = square;
        board[max - x][y] = square;

        board[x][max - y] = square;
        board[max - x][max - y] = square;

        board[y][x] = square;
        board[y][max - x] = square;

        board[max - y][x] = square;
        board[max - y][max - x] = square;
    }
}
