package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;
import java.util.Random;

public class RandomLineSelection implements LineSelectionStrategy {
    private final RandomArrayElementSelector randomArrayElementSelector = new RandomArrayElementSelector();

    @Override
    public Line getLine(BoardState boardState) {
        return randomArrayElementSelector.getNext(boardState.getInCompleteLines());
    }
}