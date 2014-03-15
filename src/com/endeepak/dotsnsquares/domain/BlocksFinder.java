package com.endeepak.dotsnsquares.domain;

import java.util.ArrayList;

public class BlocksFinder {
    public static Blocks getBlocks(BoardState boardState) {
        ArrayList<Line> inCompleteLines = boardState.getInCompleteLines();
        Blocks blocks = new Blocks();
        for (Line unSafeLine : inCompleteLines) {
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

    private static void populateBlock(BoardState boardStateInThisPath, Block block) {
        Square firstCompletableSquare = boardStateInThisPath.getFirstCompletableSquare();
        if(firstCompletableSquare == null) return;
        Line firstCompletableLine = boardStateInThisPath.getLine(firstCompletableSquare.getInCompleteLineIndices().get(0));
        int previousCompletedSquaresCount = boardStateInThisPath.getCompletedSquaresCount();
        boardStateInThisPath.markLineCompleted(firstCompletableLine);
        block.addLine(firstCompletableLine, boardStateInThisPath.getCompletedSquaresCount() - previousCompletedSquaresCount);
        populateBlock(boardStateInThisPath, block);
    }
}
