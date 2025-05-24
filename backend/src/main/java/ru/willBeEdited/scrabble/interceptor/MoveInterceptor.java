package ru.willBeEdited.scrabble.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.willBeEdited.scrabble.exception.GameStateException;
import ru.willBeEdited.scrabble.exception.IllegalMoveException;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.GameView;

import static ru.willBeEdited.scrabble.domain.Games.getGame;

@Component
public class MoveInterceptor implements HandlerInterceptor {
    private final HttpSession session;

    public MoveInterceptor(HttpSession session) {
        this.session = session;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GameView gameView = (GameView) session.getAttribute("gameView");
        if (gameView == null) {
            throw new GameStateException("No gameView found");
        }

        Game game = getGame(gameView.getId());
        if (game == null) {
            throw new GameStateException("No game found");
        }

        if (gameView.getPlayer().getId() != game.getCurrentTurnPlayerId()) {
            throw new IllegalMoveException("Out of turn move");
        }
        return true;
    }
}
