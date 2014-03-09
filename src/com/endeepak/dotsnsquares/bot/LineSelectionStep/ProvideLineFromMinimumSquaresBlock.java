package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.Block;
import com.endeepak.dotsnsquares.domain.Blocks;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;

public class ProvideLineFromMinimumSquaresBlock implements LineSelectionStrategy {

    @Override
    public Line getLine(BoardState boardState) {
        Blocks blocks = getBlocks(boardState).sortedByNumberOfSquares();
        return blocks.size() > 0 ? blocks.get(0).getLines().get(0) : null;
    }

    private Blocks getBlocks(BoardState boardState) {
        ArrayList<Line> unSafeLines = boardState.getInCompleteLines();
        Blocks blocks = new Blocks();
        for (Line unSafeLine : unSafeLines) {
            if(blocks.hasLine(unSafeLine)) continue;
            BoardState boardStateClone = boardState.getClone();
            Block block = new Block();
            boardStateClone.markLineCompleted(unSafeLine);
            block.addLine(unSafeLine, 0);
            populateBlock(boardStateClone, block);
            blocks.add(block);
        }
        return blocks;
    }

    private void populateBlock(BoardState boardStateInThisPath, Block block) {
        Square firstCompletableSquare = boardStateInThisPath.getFirstCompletableSquare();
        if(firstCompletableSquare == null) return;
        Line firstCompletableLine = boardStateInThisPath.getLine(firstCompletableSquare.getInCompleteLineIndices().get(0));
        int previousCompletedSquaresCount = boardStateInThisPath.getCompletedSquaresCount();
        boardStateInThisPath.markLineCompleted(firstCompletableLine);
        block.addLine(firstCompletableLine, boardStateInThisPath.getCompletedSquaresCount() - previousCompletedSquaresCount);
        populateBlock(boardStateInThisPath, block);
    }
}
