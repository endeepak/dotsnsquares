package com.endeepak.dotsnsquares.domain;

import junit.framework.TestCase;

public class BlocksTest extends TestCase {

    public void testHasLine() {
        Block block = new Block();
        block.addLine(new Line(new DotPosition(0, 1), new DotPosition(0, 2)), 0);
        Blocks blocks = new Blocks();
        blocks.add(block);

        assertTrue(blocks.hasLine(new Line(new DotPosition(0, 1), new DotPosition(0, 2))));
    }
}
