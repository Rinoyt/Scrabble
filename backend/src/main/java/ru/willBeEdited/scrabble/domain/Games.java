package ru.willBeEdited.scrabble.domain;

import ru.willBeEdited.scrabble.game.Game;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Games {
    private static final Map<Integer, Game> games = new ConcurrentHashMap<>();

    public static Game getGame(int id) {
        return games.get(id);
    }

    public static void addGame(Game game) {
        games.put(game.getId(), game);
    }

    public static void removeGame(int id) {
        games.remove(id);
    }
}
