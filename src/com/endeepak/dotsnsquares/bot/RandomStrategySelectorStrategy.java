package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;

import java.util.Arrays;
import java.util.List;

public class RandomStrategySelectorStrategy implements LineSelectionStrategy {
    private final RandomArrayElementSelector randomArrayElementSelector = new RandomArrayElementSelector();
    private List<LineSelectionStrategy> lineSelectionStrategies;


    public RandomStrategySelectorStrategy(LineSelectionStrategy... lineSelectionStrategies) {
        this.lineSelectionStrategies = Arrays.asList(lineSelectionStrategies);
    }

    @Override
    public Line getLine(BoardState boardState) {
        return randomArrayElementSelector.getNext(lineSelectionStrategies).getLine(boardState);
    }
}
