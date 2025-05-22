package ru.willBeEdited.scrabble.game.player;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.willBeEdited.scrabble.game.tile.Tile;

import java.util.Collection;

import static ru.willBeEdited.scrabble.game.player.PlayerUtil.getRandomInt;

@Primary
@Component
@Scope("prototype")
public class StandardPlayer implements Player {
    private int id;
    private String name;

    private int score;
    private Hand hand = new Hand();

    public StandardPlayer() {
        this("Guest" + getRandomInt());
    }

    public StandardPlayer(String name) {
        id = getRandomInt();
        this.name = name;
    }

    public StandardPlayer(Player player) {
        id = player.getId();
        name = player.getName();
        score = player.getScore();
        hand = new Hand(player.getHand());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void addToHand(Tile tile) {
        hand.add(tile);
    }

    @Override
    public void addAllToHand(Collection<Tile> tiles) {
        hand.addAll(tiles);
    }

    @Override
    public int getHandSize() {
        return hand.size();
    }

    public void removeFromHand(Tile tile) {
        hand.remove(tile);
    }

    @Override
    public void removeFromHand(int tileId) {
        hand.remove(tileId);
    }
}
