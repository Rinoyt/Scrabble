package ru.willBeEdited.scrabble.game.player;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.Game;
import ru.willBeEdited.scrabble.game.move.Move;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.List;

import static ru.willBeEdited.scrabble.game.player.PlayerUtil.getRandomInt;

@Component
@Scope("prototype")
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

    @Override
    public void updateHand(List<Tile> tiles) {
        // pass
    }
}
