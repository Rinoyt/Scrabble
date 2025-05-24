package ru.willBeEdited.scrabble.controller;

import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.move.Move;

@RestController
@RequestMapping("/api/1/game")
public class MoveController {
    private final AbstractMessageSendingTemplate<String> messageSendingTemplate;

    public MoveController(AbstractMessageSendingTemplate<String> messageSendingTemplate) {
        this.messageSendingTemplate = messageSendingTemplate;
    }

    @PutMapping("move/pass")
    public void pass(@RequestBody Move move, @SessionAttribute("gameView") GameView gameView) {
        System.out.println("pass!");
    }

//    @PutMapping("game/move/exchange")
//    @PutMapping("game/move/play")
}
