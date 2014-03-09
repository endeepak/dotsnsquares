package com.endeepak.dotsnsquares.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class Blocks extends ArrayList<Block> {
    Blocks(Collection<? extends Block> collection) {
        super(collection);
    }

    public Blocks() {
    }

    public boolean hasLine(Line line) {
        for (Block block : this) {
            if(block.hasLine(line))
                return true;
        }
        return false;
    }

    public Blocks sortedByNumberOfSquares() {
        Block[] blocksArray = this.toArray(new Block[this.size()]);
        Arrays.sort(blocksArray, new Comparator<Block>() {
            @Override
            public int compare(Block lhs, Block rhs) {
                return lhs.getNumberOfSquares().compareTo(rhs.getNumberOfSquares());
            }
        });
        return new Blocks(Arrays.asList(blocksArray));
    }

}
