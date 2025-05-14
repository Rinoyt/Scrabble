package ru.willBeEdited.scrabble.exception;

public class IllegalMoveException extends RuntimeException {
    private final String message;

    public IllegalMoveException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
