package ru.willBeEdited.scrabble.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;

@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<Object> onIllegalMoveException(IllegalMoveException e) {
        return new ResponseEntity<>(e, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
