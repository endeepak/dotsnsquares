package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;

public class FirstAvailableLineSelection implements LineSelectionStrategy {
    @Override
    public Line getLine(BoardState boardState) {
        return boardState.getInCompleteLines().get(0);
    }
}