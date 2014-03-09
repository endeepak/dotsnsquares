package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.Square;

public class TrySquareCompletion implements LineSelectionStrategy {
    @Override
    public Line getLine(BoardState boardState) {
        Square firstCompletableSquare = boardState.getFirstCompletableSquare();
        return firstCompletableSquare != null ? boardState.getLine(firstCompletableSquare.getInCompleteLineIndices().get(0)) : null;
    }
}
