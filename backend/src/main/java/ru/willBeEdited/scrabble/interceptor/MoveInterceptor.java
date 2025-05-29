package ru.willBeEdited.scrabble.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.willBeEdited.scrabble.domain.Games;
import ru.willBeEdited.scrabble.exception.GameStateException;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Exchange;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.move.Pass;
import ru.willBeEdited.scrabble.game.move.Play;
import ru.willBeEdited.scrabble.game.player.Bot;

@Component
public class MoveInterceptor implements HandlerInterceptor {
    private final HttpSession session;
    private final AbstractMessageSendingTemplate<String> messageSendingTemplate;

    public MoveInterceptor(HttpSession session, AbstractMessageSendingTemplate<String> messageSendingTemplate) {
        this.session = session;
        this.messageSendingTemplate = messageSendingTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int playerId = getPlayerId();
        Game game = getGame(getGameId());

        if (playerId != game.getCurrentTurnPlayerId()) {
            throw new IllegalMoveException("Out of turn move");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Game game = getGame(getGameId());

        // check if the current player is a bot
        if (game.getCurrentPlayer() instanceof Bot bot) {
            Move move = bot.chooseMove(game);
            move.apply(game);

            switch (move) {
                case Play play -> messageSendingTemplate.convertAndSend("/game/play", play);
                case Exchange exchange -> messageSendingTemplate.convertAndSend("/game/exchange", exchange);
                case Pass pass -> messageSendingTemplate.convertAndSend("/game/pass", pass);
                default -> throw new IllegalMoveException("Invalid move by bot");
            }
        }
    }

    private Game getGame(int id) {
        Game game = Games.getGame(id);
        if (game == null) {
            throw new GameStateException("No game found");
        }

        return game;
    }

    private int getPlayerId() {
        Integer playerId = (Integer) session.getAttribute("playerId");
        if (playerId == null) {
            throw new GameStateException("No playerId found in session");
        }

        return playerId;
    }

    private int getGameId() {
        Integer gameId = (Integer) session.getAttribute("gameId");
        if (gameId == null) {
            throw new GameStateException("No gameId found in session");
        }

        return gameId;
    }
}
