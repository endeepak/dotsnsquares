package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.bot.StepWiseLineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.Block;
import com.endeepak.dotsnsquares.domain.Blocks;
import com.endeepak.dotsnsquares.domain.BlocksFinder;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.Square;

import java.util.ArrayList;

public class ThinkTwoStepsAheadToGiveMinimumBlock implements LineSelectionStrategy {
    private final AvoidOpponentSquareCompletion avoidOpponentSquareCompletion = new AvoidOpponentSquareCompletion();
    private final ProvideLineFromMinimumSquaresBlock provideLineFromMinimumSquaresBlock = new ProvideLineFromMinimumSquaresBlock();
    private final StepWiseLineSelectionStrategy stepWiseLineSelectionStrategy = new StepWiseLineSelectionStrategy(avoidOpponentSquareCompletion, provideLineFromMinimumSquaresBlock);

    @Override
    public Line getLine(BoardState boardState) {
        boolean hasCompletableSquare = boardState.getFirstCompletableSquare() != null;
        if(hasCompletableSquare) {
            if(hasSafeLineAfterCompletingSquares(boardState)) {
                return boardState.getFirstSquareCompletableLine();
            } else {
                int numberOfSquaresInCurrentBlock = getNumberOfSquaresInCurrentBlock(boardState);
                if(numberOfSquaresInCurrentBlock == 1) {
                    return boardState.getFirstSquareCompletableLine();
                } else if (numberOfSquaresInCurrentBlock == 2) {
                    BoardState boardStateAfterCompletingAvailableSquares = getBoardStateAfterCompletingAvailableSquares(boardState);
                    Blocks futureBlocksSorted = BlocksFinder.getBlocks(boardStateAfterCompletingAvailableSquares).sortedByNumberOfSquares();
                    if(futureBlocksSorted.size() == 0) {
                        return boardState.getFirstSquareCompletableLine();
                    } else {
                        Block minimumNumberOfSquaresBlock = futureBlocksSorted.get(0);
                        Integer minimumNumberOfSquares = minimumNumberOfSquaresBlock.getNumberOfSquares();
                        if(minimumNumberOfSquares <= 2) {
                            return boardState.getFirstSquareCompletableLine();
                        } else {
                            Line secondLineInTheCompletableSquareBlock = getSecondLineInTheCompletableSquareBlock(boardState);
                            return secondLineInTheCompletableSquareBlock != null ? secondLineInTheCompletableSquareBlock : boardState.getFirstSquareCompletableLine();
                        }
                    }
                } else {
                    //TODO(GeniusBot): avoid line which can fill more than one square
                    return boardState.getFirstSquareCompletableLine();
                }
            }
        } else {
            //TODO(GeniusBot): give common line in a block of 2
            return stepWiseLineSelectionStrategy.getLine(boardState);
        }
    }

    private Line getSecondLineInTheCompletableSquareBlock(BoardState boardState) {
        BoardState boardStateClone = boardState.getClone();
        Line firstSquareCompletableLine = boardStateClone.getFirstSquareCompletableLine();
        boardStateClone.markLineCompleted(firstSquareCompletableLine);
        return boardStateClone.getFirstSquareCompletableLine();
    }

    private int getNumberOfSquaresInCurrentBlock(BoardState boardState) {
        BoardState boardStateClone = boardState.getClone();
        int numberOfSquaresCurrentBlockSize = 0;
        while (boardStateClone.getFirstSquareCompletableLine() != null) {
            numberOfSquaresCurrentBlockSize++;
            boardStateClone.markLineCompleted(boardStateClone.getFirstSquareCompletableLine());
        }
        return numberOfSquaresCurrentBlockSize;
    }

    private boolean hasSafeLineAfterCompletingSquares(BoardState boardState) {
        BoardState boardStateAfterCompletingAvailableSquares = getBoardStateAfterCompletingAvailableSquares(boardState);
        Line safeLine = avoidOpponentSquareCompletion.getLine(boardStateAfterCompletingAvailableSquares);
        return safeLine != null;
    }

    private BoardState getBoardStateAfterCompletingAvailableSquares(BoardState boardState) {
        BoardState boardStateClone = boardState.getClone();
        while (boardStateClone.getFirstSquareCompletableLine() != null) {
            boardStateClone.markLineCompleted(boardStateClone.getFirstSquareCompletableLine());
        }
        return boardStateClone;
    }
}
