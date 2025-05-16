package ru.willBeEdited.scrabble.game.player;

import java.util.Random;

public final class PlayerUtil {
    private static final Random random = new Random();

    public static int getRandomInt() {
        return random.nextInt(Integer.MAX_VALUE);
    }
}
