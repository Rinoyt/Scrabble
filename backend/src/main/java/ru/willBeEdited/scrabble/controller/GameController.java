package ru.willBeEdited.scrabble.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.context.ApplicationContext;
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
    public Move makeMove(@RequestBody Move move, @ModelAttribute("game") Game game, Bot bot) {
        game.makeMove(move);
        return bot.chooseMove(game);
    }

    @GetMapping("game/reset")
    public Game resetGame(ApplicationContext context, Model model) {
        Game newGame = context.getBean(Game.class);
        model.addAttribute("game", newGame);
        return newGame;
    }
}
