package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;
import java.util.Random;

public class RandomLineSelectionStrategy implements LineSelectionStrategy {
    private final Random random = new Random();;

    @Override
    public Line getLine(BoardState boardState) {
        boolean[] completedLines = boardState.getCompletedLines();
        ArrayList<Integer> remainingLineIndexes = getRemainingLineIndexes(completedLines);
        int size = remainingLineIndexes.size();
        if (size == 0) throw new NoMoreLinesAvailableException();
        int randomIndex = size == 1 ? 0 : random.nextInt(size - 1);
        Integer lineIndex = remainingLineIndexes.get(randomIndex);
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