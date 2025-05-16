package ru.willBeEdited.scrabble.game.player;

import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;

import static ru.willBeEdited.scrabble.game.player.PlayerUtil.getRandomInt;

@Component
public class MissingBot extends StandardPlayer implements Bot {
    public MissingBot() {
        super("Bot" + getRandomInt());
    }

    public MissingBot(String name) {
        super(name);
    }

    public MissingBot(Player player) {
        super(player);
    }

    // Always pass
    public Move chooseMove(Game game) {
        return new Move(super.getId());
    }
}
