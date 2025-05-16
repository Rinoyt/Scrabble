package ru.willBeEdited.scrabble.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Bot;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/1")
public class GameController {
    private final ApplicationContext context;

    private final Bot bot;

    private final Map<Integer, Game> games = new HashMap<>();

    public GameController(ApplicationContext context, Bot bot) {
        this.context = context;
        this.bot = bot;
    }

    @GetMapping("game")
    public GameView getGame(Model model) {
        if (model.containsAttribute("gameView")) {
            return (GameView) model.getAttribute("gameView");
        }

        Game game = context.getBean(Game.class);

        Player player = context.getBean(Player.class);
        game.addPlayer(player);
        game.addPlayer(context.getBean(Player.class));

        games.put(game.getId(), game);
        return new GameView(game, player.getId());
    }

    @PutMapping("game")
    public List<Tile> makeMove(@RequestBody Move move, @ModelAttribute("gameView") GameView gameView) {
        Game game = games.get(gameView.getId());
        String error = game.checkMove(move);
        if (error != null) {
            throw new IllegalMoveException(error);
        }

        List<Tile> drawnTiles = game.makeMove(move);
//        return bot.chooseMove(game);
        return drawnTiles;
    }

    @GetMapping("game/reset")
    public RedirectView resetGame(@ModelAttribute("gameView") GameView gameView, ModelMap model) {
        games.remove(gameView.getId());
        model.remove("gameView");
        return new RedirectView("/api/1/game");
    }
}
