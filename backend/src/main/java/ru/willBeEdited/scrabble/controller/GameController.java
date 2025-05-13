package ru.willBeEdited.scrabble.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Bot;

@RestController
@RequestMapping("/api/1")
public class GameController {
    @GetMapping("game")
    public Game getGame(@ModelAttribute("game") Game game) {
        return game;
    }

    @PutMapping("game")
    public Move makeMove(@ModelAttribute("game") Game game, Move move, Bot bot) {
        game.makeMove(move);
        return bot.chooseMove(game);
    }

    @GetMapping("game/reset")
    public Game resetGame(Model model, Game game) {
        model.addAttribute("game", game);
        return game;
    }
}
