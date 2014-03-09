package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;

public class AvoidGivingCompletableLineSelectionStrategy implements LineSelectionStrategy {

    @Override
    public Line getLine(BoardState boardState) {
        ArrayList<Integer> lineIndicesFromInCompletableSquares = getIncompleteLineIndices(boardState.getInCompletableSquares());
        ArrayList<Integer> lineIndicesOpponentCompletableSquare = getIncompleteLineIndices(boardState.getOpponentCompletableSquares());
        ArrayList<Integer> safeLineIndices = new ArrayList<Integer>();
        safeLineIndices.addAll(lineIndicesFromInCompletableSquares);
        safeLineIndices.removeAll(lineIndicesOpponentCompletableSquare);
        if(safeLineIndices.size() > 0) {
            return boardState.getLine(getRandomLineIndex(safeLineIndices));
        }

        if(lineIndicesOpponentCompletableSquare.size() > 0) {
            return boardState.getLine(getRandomLineIndex(lineIndicesOpponentCompletableSquare));
        }
        throw new NoMoreLinesAvailableException();
    }

    private Integer getRandomLineIndex(ArrayList<Integer> lineIndices) {
        return new RandomArrayElementSelector().getNext(lineIndices);
    }

    private ArrayList<Integer> getIncompleteLineIndices(ArrayList<Square> squares) {
        ArrayList<Integer> incompleteLineIndices = new ArrayList<Integer>();
        for (Square square : squares) {
            incompleteLineIndices.addAll(square.getInCompleteLineIndices());
        }
        return incompleteLineIndices;
    }
}
