package com.example.dotnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreCard implements Serializable {
    private final ArrayList<ScoreEntry> scoreEntries = new ArrayList<ScoreEntry>();
    private int totalScore = 0;

    public ScoreCard(ArrayList<Player> players) {
        for (Player player : players) {
            scoreEntries.add(new ScoreEntry(player));
        }
    }

    public void updateScoreForPlayer(int playerIndex, int numberOfSquaresCompleted) {
        totalScore += numberOfSquaresCompleted;
        scoreEntries.get(playerIndex).updateScoreBy(numberOfSquaresCompleted);
    }

    public ArrayList<ScoreEntry> getScoreEntries() {
        return scoreEntries;
    }

    public int getTotalScore() {
        return totalScore;
    }
}