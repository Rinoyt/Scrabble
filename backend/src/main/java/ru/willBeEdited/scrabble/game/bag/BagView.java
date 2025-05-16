package ru.willBeEdited.scrabble.game.bag;

public class BagView {
    private int bagSize;

    public BagView() {
    }

    public BagView(Bag bag) {
        this.bagSize = bag.size();
    }

    public int getBagSize() {
        return bagSize;
    }

    public void setBagSize(int bagSize) {
        this.bagSize = bagSize;
    }

    public boolean isEmpty() {
        return bagSize == 0;
    }

    public void draw() {
        bagSize--;
    }

    public void draw(int amount) {
        bagSize -= amount;
    }
}
