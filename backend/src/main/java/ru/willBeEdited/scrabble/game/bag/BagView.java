package ru.willBeEdited.scrabble.game.bag;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class BagView extends Bag {
    private int bagSize;

    public BagView() {
    }

    public BagView(Bag bag) {
        this.bagSize = bag.size();
    }

    @Override
    public int size() {
        return bagSize;
    }

    @Override
    public boolean isEmpty() {
        return bagSize == 0;
    }

    @Override
    public List<Tile> draw(int amount) {
        bagSize -= Math.min(amount, bagSize);
        return new ArrayList<>();
    }

    // getters and setters for json

    public int getBagSize() {
        return bagSize;
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }
}
