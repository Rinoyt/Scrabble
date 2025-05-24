package ru.willBeEdited.scrabble.controller;

import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.willBeEdited.scrabble.game.GameView;

@RestController
@RequestMapping("/api/1/game")
public class MoveController {
    private final AbstractMessageSendingTemplate<String> messageSendingTemplate;

    public MoveController(AbstractMessageSendingTemplate<String> messageSendingTemplate) {
        this.messageSendingTemplate = messageSendingTemplate;
    }

    @PutMapping("move/pass")
    public void pass(@SessionAttribute("gameView") GameView gameView) {
        messageSendingTemplate.convertAndSend("/game/move/pass", gameView.getPlayer().getId());
    }

//    @PutMapping("game/move/exchange")
//    @PutMapping("game/move/play")
}
