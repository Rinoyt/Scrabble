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

    public boolean draw() {
        if (isEmpty()) {
            return false;
        }

        bagSize--;
        return true;
    }

    public int draw(int amount) {
        if (bagSize < amount) {
            int tmp = bagSize;
            bagSize = 0;
            return tmp;
        }

        bagSize -= amount;
        return amount;
    }
}
