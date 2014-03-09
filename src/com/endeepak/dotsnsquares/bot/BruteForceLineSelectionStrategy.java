package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;

public class BruteForceLineSelectionStrategy implements LineSelectionStrategy {
    @Override
    public Line getLine(BoardState boardState) {
        return boardState.getInCompleteLines().get(0);
    }
}