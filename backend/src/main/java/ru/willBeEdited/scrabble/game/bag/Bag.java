package ru.willBeEdited.scrabble.game.bag;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.*;

@Component
@Scope("prototype")
public class Bag {
    private final Deque<Tile> bag = new ArrayDeque<>();

    public Bag() {
        int id = 0;

        List<Tile> tiles = new ArrayList<>();

        // K, J, X, Q, Z
        for (int i = 0; i < 1; i++) {
            tiles.add(new Tile('K', 5));
            tiles.add(new Tile('J', 8));
            tiles.add(new Tile('X', 8));
            tiles.add(new Tile('Q', 10));
            tiles.add(new Tile('Z', 10));
        }

        // [blank], B, C, M, P, F, H, V, W, Y
        for (int i = 0; i < 2; i++) {
            tiles.add(new Tile(' ', true, 0));
            tiles.add(new Tile('B', 3));
            tiles.add(new Tile('C', 3));
            tiles.add(new Tile('M', 3));
            tiles.add(new Tile('P', 3));
            tiles.add(new Tile('F', 4));
            tiles.add(new Tile('H', 4));
            tiles.add(new Tile('V', 4));
            tiles.add(new Tile('W', 4));
            tiles.add(new Tile('Y', 4));
        }

        // G
        for (int i = 0; i < 3; i++) {
            tiles.add(new Tile('G', 2));
        }

        // L, S, U, D
        for (int i = 0; i < 4; i++) {
            tiles.add(new Tile('L', 1));
            tiles.add(new Tile('S', 1));
            tiles.add(new Tile('U', 1));
            tiles.add(new Tile('D', 2));
        }

        // N, R, T
        for (int i = 0; i < 6; i++) {
            tiles.add(new Tile('N', 1));
            tiles.add(new Tile('R', 1));
            tiles.add(new Tile('T', 1));
        }

        // O
        for (int i = 0; i < 8; i++) {
            tiles.add(new Tile('O', 1));
        }

        // A, I
        for (int i = 0; i < 9; i++) {
            tiles.add(new Tile('A', 1));
            tiles.add(new Tile('I', 1));
        }

        // E
        for (int i = 0; i < 12; i++) {
            tiles.add(new Tile('E', 1));
        }

        Collections.shuffle(tiles);
        bag.addAll(tiles);
    }

    public Bag(Bag bag) {
        this.bag.addAll(bag.bag);
    }

    public int size() {
        return bag.size();
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

    public Tile draw() {
        if (!isEmpty()) {
            throw new IllegalStateException("Bag is empty");
        }
        return bag.removeFirst();
    }

    public List<Tile> draw(int amount) {
        List<Tile> drawnTiles = new ArrayList<>();
        while (!bag.isEmpty()) {
            drawnTiles.add(bag.removeFirst());
        }
        return drawnTiles;
    }
}
