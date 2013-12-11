package com.example.dotsnsquares.domain;

public class BruteForceLineSelectionStrategy implements LineSelectionStrategy {
    @Override
    public Line getLine(BoardState boardState) {
        boolean[] completedLines = boardState.getCompletedLines();
        for (int lineIndex = 0; lineIndex < completedLines.length; lineIndex++) {
            if (!completedLines[lineIndex])
                return boardState.getSquareMatrix().getLine(lineIndex);
        }
        throw new NoMoreLinesAvailableException();
    }
}