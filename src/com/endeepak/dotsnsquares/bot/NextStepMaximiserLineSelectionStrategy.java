package com.endeepak.dotsnsquares.bot;

import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.domain.SquareMatrix;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;
import java.util.Random;

public class NextStepMaximiserLineSelectionStrategy implements LineSelectionStrategy {
    private Random random = new Random();

    @Override
    public Line getLine(BoardState boardState) {
        SquareMatrix squareMatrix = boardState.getSquareMatrix();
        Square firstCompletableSquare = boardState.getFirstCompletableSquare();
        if(firstCompletableSquare != null) {
            return squareMatrix.getLine(firstCompletableSquare.getInCompleteLineIndices().get(0));
        }

        ArrayList<Integer> lineIndicesFromInCompletableSquares = getIncompleteLineIndices(boardState.getInCompletableSquares());
        ArrayList<Integer> lineIndicesOpponentCompletableSquare = getIncompleteLineIndices(boardState.getOpponentCompletableSquares());
        ArrayList<Integer> safeLineIndices = new ArrayList<Integer>();
        safeLineIndices.addAll(lineIndicesFromInCompletableSquares);
        safeLineIndices.removeAll(lineIndicesOpponentCompletableSquare);

        if(safeLineIndices.size() > 0) {
            return squareMatrix.getLine(getRandomLineIndex(safeLineIndices));
        }

        if(lineIndicesOpponentCompletableSquare.size() > 0) {
            return squareMatrix.getLine(getRandomLineIndex(lineIndicesOpponentCompletableSquare));
        }
        throw new NoMoreLinesAvailableException();
    }

    private Integer getRandomLineIndex(ArrayList<Integer> lineIndices) {
        return lineIndices.get(getRandomIndex(lineIndices.size()));
    }

    private ArrayList<Integer> getIncompleteLineIndices(ArrayList<Square> squares) {
        ArrayList<Integer> incompleteLineIndices = new ArrayList<Integer>();
        for (Square square : squares) {
            incompleteLineIndices.addAll(square.getInCompleteLineIndices());
        }
        return incompleteLineIndices;
    }

    private int getRandomIndex(int size) {
        return size > 1 ? random.nextInt(size - 1) : 0;
    }
}
