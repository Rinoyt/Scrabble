package ru.willBeEdited.scrabble.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.player.Bot;
import ru.willBeEdited.scrabble.game.player.Player;

import static ru.willBeEdited.scrabble.domain.Games.*;

@RestController
@RequestMapping("/api/1")
public class GameController {
    private final ApplicationContext context;
    private final HttpSession session;

    public GameController(ApplicationContext context, HttpSession session) {
        this.context = context;
        this.session = session;
    }

    @GetMapping("game")
    public GameView getGameView() {
        if (session.getAttribute("gameId") != null) {
            int gameId = Integer.parseInt(session.getAttribute("gameId").toString());
            if (getGame(gameId) == null) {
                session.removeAttribute("gameId");
            }
        }

        if (session.getAttribute("gameId") == null ||
                session.getAttribute("playerId") == null) {
            Game game = context.getBean(Game.class);

            Player player = context.getBean(Player.class);
            game.addPlayer(player);
            game.addPlayer(context.getBean(Bot.class));

            addGame(game);

            session.setAttribute("gameId", game.getId());
            session.setAttribute("playerId", player.getId());
        }

        int gameId = Integer.parseInt(session.getAttribute("gameId").toString());
        int playerId = Integer.parseInt(session.getAttribute("playerId").toString());

        return context.getBean(GameView.class, getGame(gameId), playerId);
    }

    @GetMapping("game/reset")
    public RedirectView resetGame(@SessionAttribute("gameView") GameView gameView) {
        removeGame(gameView.getId());
        session.removeAttribute("gameView");
        return new RedirectView("/api/1/game");
    }
}
