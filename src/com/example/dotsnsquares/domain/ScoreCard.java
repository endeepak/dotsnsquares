package com.example.dotsnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreCard implements Serializable {
    private final ArrayList<ScoreEntry> scoreEntries = new ArrayList<ScoreEntry>();
    private int totalScore = 0;

    public ScoreCard(List<Player> players) {
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

    public int getScore(int playerIndex) {
        return scoreEntries.get(playerIndex).getScore();
    }

    public int getTotalScore() {
        return totalScore;
    }
}
