package ru.willBeEdited.scrabble.game.player;

import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.Collection;

public class Opponent extends StandardPlayer {
    private int handSize;

    public Opponent() {
    }

    public Opponent(Player player) {
        super(player);
        handSize = player.getHand().getTiles().size();
    }

    public int getHandSize() {
        return handSize;
    }

    public void setHandSize(int handSize) {
        this.handSize = handSize;
    }

    @Override
    public Hand getHand() {
        return null;
    }

    @Override
    public void setHand(Hand hand) {
        handSize = hand.getTiles().size();
    }

    @Override
    public void addToHand(Tile tile) {
        handSize++;
    }

    @Override
    public void addAllToHand(Collection<Tile> tiles) {
        handSize += tiles.size();
    }

    @Override
    public void removeFromHand(Tile tile) {
        handSize--;
    }

    @Override
    public void removeFromHand(int tileId) {
        handSize--;
    }
}
