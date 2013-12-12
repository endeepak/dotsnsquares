package com.example.dotsnsquares.bot;

import com.example.dotsnsquares.domain.BoardState;
import com.example.dotsnsquares.domain.Line;
import com.example.dotsnsquares.exception.NoMoreLinesAvailableException;

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