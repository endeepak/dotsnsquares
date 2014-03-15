package com.endeepak.dotsnsquares.bot.LineSelectionStep;

import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.domain.Block;
import com.endeepak.dotsnsquares.domain.Blocks;
import com.endeepak.dotsnsquares.domain.BlocksFinder;
import com.endeepak.dotsnsquares.domain.BoardState;
import com.endeepak.dotsnsquares.domain.Line;
import com.endeepak.dotsnsquares.domain.RandomArrayElementSelector;
import com.endeepak.dotsnsquares.domain.Square;
import com.endeepak.dotsnsquares.exception.NoMoreLinesAvailableException;

import java.util.ArrayList;

public class ProvideLineFromMinimumSquaresBlock implements LineSelectionStrategy {

    @Override
    public Line getLine(BoardState boardState) {
        Blocks blocks = BlocksFinder.getBlocks(boardState).sortedByNumberOfSquares();
        return blocks.size() > 0 ? blocks.get(0).getLines().get(0) : null;
    }
}
