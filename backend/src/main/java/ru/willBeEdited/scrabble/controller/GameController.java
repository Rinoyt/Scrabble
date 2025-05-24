package ru.willBeEdited.scrabble.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.player.Bot;
import ru.willBeEdited.scrabble.game.player.Player;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ru.willBeEdited.scrabble.domain.Games.*;

@RestController
@RequestMapping("/api/1")
public class GameController {
    private final ApplicationContext context;
    private final AbstractMessageSendingTemplate<String> messageSendingTemplate;
    private final ObjectMapper objectMapper;

    private final Bot bot;

    private final Map<Integer, List<GameView>> gameViews = new HashMap<>();

    public GameController(ApplicationContext context, AbstractMessageSendingTemplate<String> messageSendingTemplate, ObjectMapper objectMapper, Bot bot) {
        this.context = context;
        this.messageSendingTemplate = messageSendingTemplate;
        this.objectMapper = objectMapper;
        this.bot = bot;
    }

    @GetMapping("game")
    public GameView getGameView(HttpSession session) {
        if (session.getAttribute("gameView") != null) {
            return (GameView) session.getAttribute("gameView");
        }

        Game game = context.getBean(Game.class);

        Player player = context.getBean(Player.class);
        game.addPlayer(player);
        game.addPlayer(context.getBean(Bot.class));

        addGame(game);

        GameView gameView = context.getBean(GameView.class, game, player.getId());
        gameViews.put(game.getId(), new ArrayList<>());
        gameViews.get(game.getId()).add(gameView);
        session.setAttribute("gameView", gameView);
        return gameView;
    }

    // For some fk reason "@RequestBody Move move" doesn't work
    @PutMapping("game")
    public List<Tile> makeMove(HttpServletRequest request, @SessionAttribute("gameView") GameView gameView) {
        Scanner s;
        Move move;
        try {
            s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
            move = objectMapper.readValue(s.nextLine(), Move.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Game game = getGame(gameView.getId());
        if (gameView.getPlayer().getId() != game.getCurrentTurnPlayerId()) {
            throw new IllegalMoveException("Out of turn move");
        }
        String error = game.checkMove(move);
        if (error != null) {
            throw new IllegalMoveException(error);
        }
        
        List<Tile> drawnTiles = game.makeMove(move);
        for (GameView view : gameViews.get(game.getId())) {
            view.makeMove(move, drawnTiles);
        }
        messageSendingTemplate.convertAndSend("/game/move", move);

        // check if the current player is a bot
        if (game.getCurrentPlayer() instanceof Bot) {
            Move botMove = bot.chooseMove(game);
//            String error = game.checkMove(move);
            List<Tile> botDrawnTiles = game.makeMove(botMove);
            bot.updateHand(botDrawnTiles);
            for (GameView view : gameViews.get(game.getId())) {
                view.makeMove(botMove, botDrawnTiles);
            }
            messageSendingTemplate.convertAndSend("/game/move", botMove);
        }

        return drawnTiles;
    }

    @GetMapping("game/reset")
    public RedirectView resetGame(@SessionAttribute("gameView") GameView gameView, HttpSession session) {
        removeGame(gameView.getId());
        session.removeAttribute("gameView");
        return new RedirectView("/api/1/game");
    }
}
