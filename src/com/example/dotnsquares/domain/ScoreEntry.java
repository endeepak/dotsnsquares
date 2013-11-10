package com.example.dotnsquares.domain;

import java.io.Serializable;

public class ScoreEntry implements Serializable {
    private Player player;
    private int score;

    public ScoreEntry(Player player) {
        this.player = player;
        this.score = 0;
    }

    public void updateScoreBy(int numberOfSquaresCompleted) {
        score += numberOfSquaresCompleted;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }
}
