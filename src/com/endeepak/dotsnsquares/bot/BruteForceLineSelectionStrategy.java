package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

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