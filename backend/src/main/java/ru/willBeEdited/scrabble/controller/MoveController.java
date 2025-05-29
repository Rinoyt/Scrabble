package ru.willBeEdited.scrabble.controller;

import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Exchange;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.move.Pass;
import ru.willBeEdited.scrabble.game.move.Play;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

import static ru.willBeEdited.scrabble.domain.Games.*;

@RestController
@RequestMapping("/api/1/game")
public class MoveController {
    private final AbstractMessageSendingTemplate<String> messageSendingTemplate;

    public MoveController(AbstractMessageSendingTemplate<String> messageSendingTemplate) {
        this.messageSendingTemplate = messageSendingTemplate;
    }

    @PutMapping("move/pass")
    public void pass(@RequestBody Pass pass, @SessionAttribute("gameId") int gameId) {
        makeMove(gameId, pass);
        messageSendingTemplate.convertAndSend("/game/move/pass", pass);
    }

    @PutMapping("move/exchange")
    public List<Tile> exchange(@RequestBody Exchange exchange, @SessionAttribute("gameId") int gameId) {
        List<Tile> drawnTiles = makeMove(gameId, exchange);
        messageSendingTemplate.convertAndSend("/game/move/exchange", exchange);
        return drawnTiles;
    }

    @PutMapping("move/play")
    public List<Tile> play(@RequestBody Play play, @SessionAttribute("gameId") int gameId) {
        List<Tile> drawnTiles = makeMove(gameId, play);
        messageSendingTemplate.convertAndSend("/game/move/play", play);
        return drawnTiles;
    }

    private List<Tile> makeMove(int gameId, Move move) throws IllegalMoveException {
        Game game = getGame(gameId);
        move.check(game);
        return move.apply(game);
    }
}
