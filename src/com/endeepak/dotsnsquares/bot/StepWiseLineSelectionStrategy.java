package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;

import java.util.Arrays;
import java.util.List;

public class StepWiseLineSelectionStrategy implements LineSelectionStrategy {
    private List<LineSelectionStrategy> lineSelectionSteps;

    public StepWiseLineSelectionStrategy(LineSelectionStrategy... lineSelectionSteps) {
        this.lineSelectionSteps = Arrays.asList(lineSelectionSteps);
    }

    @Override
    public Line getLine(BoardState boardState) {
        for (LineSelectionStrategy lineSelectionStrategy : lineSelectionSteps) {
            Line line = lineSelectionStrategy.getLine(boardState);
            if(line != null) return line;
        }
        return boardState.getInCompleteLines().get(0);
    }
}
