package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;

public class AvoidOpponentSquareCompletion implements LineSelectionStrategy {

    @Override
    public Line getLine(BoardState boardState) {
        ArrayList<Integer> lineIndicesFromInCompletableSquares = getIncompleteLineIndices(boardState.getInCompletableSquares());
        ArrayList<Integer> lineIndicesOpponentCompletableSquare = getIncompleteLineIndices(boardState.getOpponentCompletableSquares());
        ArrayList<Integer> safeLineIndices = new ArrayList<Integer>();
        safeLineIndices.addAll(lineIndicesFromInCompletableSquares);
        safeLineIndices.removeAll(lineIndicesOpponentCompletableSquare);
        return safeLineIndices.size() > 0 ? boardState.getLine(new RandomArrayElementSelector().getNext(safeLineIndices)) : null;
    }

    private ArrayList<Integer> getIncompleteLineIndices(ArrayList<Square> squares) {
        ArrayList<Integer> incompleteLineIndices = new ArrayList<Integer>();
        for (Square square : squares) {
            incompleteLineIndices.addAll(square.getInCompleteLineIndices());
        }
        return incompleteLineIndices;
    }
}
