package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.domain.SquareMatrix;

public class CompleteSquareAndDecideLineSelectionStrategy implements LineSelectionStrategy {
    private LineSelectionStrategy nextLineDeciderStrategy;

    public CompleteSquareAndDecideLineSelectionStrategy(LineSelectionStrategy nextLineDeciderStrategy) {
        this.nextLineDeciderStrategy = nextLineDeciderStrategy;
    }

    @Override
    public Line getLine(BoardState boardState) {
        SquareMatrix squareMatrix = boardState.getSquareMatrix();
        Square firstCompletableSquare = boardState.getFirstCompletableSquare();
        if(firstCompletableSquare != null) {
            return squareMatrix.getLine(firstCompletableSquare.getInCompleteLineIndices().get(0));
        }

        return nextLineDeciderStrategy.getLine(boardState);
    }
}
