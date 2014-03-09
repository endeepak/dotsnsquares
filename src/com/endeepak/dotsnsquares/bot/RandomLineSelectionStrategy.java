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
        return randomArrayElementSelector.getNext(boardState.getInCompleteLines());
    }
}