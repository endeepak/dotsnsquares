package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;
import java.util.Random;

public class RandomLineSelectionStrategy implements LineSelectionStrategy {
    private final RandomArrayElementSelector randomArrayElementSelector = new RandomArrayElementSelector();

    @Override
    public Line getLine(BoardState boardState) {
        boolean[] completedLines = boardState.getCompletedLines();
        ArrayList<Integer> remainingLineIndexes = getRemainingLineIndexes(completedLines);
        if (remainingLineIndexes.isEmpty()) throw new NoMoreLinesAvailableException();
        Integer lineIndex = randomArrayElementSelector.getNext(remainingLineIndexes);
        return boardState.getSquareMatrix().getLine(lineIndex);
    }

    private ArrayList<Integer> getRemainingLineIndexes(boolean[] completedLines) {
        ArrayList<Integer> remainingLineIndexes = new ArrayList<Integer>();
        for (int lineIndex = 0; lineIndex < completedLines.length; lineIndex++) {
            if (!completedLines[lineIndex])
                remainingLineIndexes.add(lineIndex);
        }
        return remainingLineIndexes;
    }
}