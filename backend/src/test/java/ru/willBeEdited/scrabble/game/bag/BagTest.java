package ru.willBeEdited.scrabble.game.bag;

import org.junit.jupiter.api.Test;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    @Test
    void size() {
        Bag bag = new Bag();
        assertEquals(100, bag.size());
    }

    @Test
    void isEmpty() {
        Bag bag = new Bag();
        assertFalse(bag.isEmpty());
    }

    @Test
    void draw() {
        Bag bag = new Bag();
        int size = bag.size();
        bag.draw();
        assertEquals(size - 1, bag.size());
    }

    @Test
    void testDraw() {
        Bag bag = new Bag();
        int size = bag.size();
        List<Tile> tiles = bag.draw(7);
        assertEquals(7, tiles.size());
        assertEquals(size - 7, bag.size());
    }
}