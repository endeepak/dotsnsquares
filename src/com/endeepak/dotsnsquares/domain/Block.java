package com.endeepak.dotsnsquares.domain;

import com.endeepak.dotsnsquares.domain.Line;

import java.util.ArrayList;

public class Block {
    private ArrayList<Line> lines = new ArrayList<Line>();
    Integer numberOfSquares = 0;

    public void addLine(Line line) {
        addLine(line, 0);
    }

    public void addLine(Line line, int completedSquaresCount) {
        lines.add(line);
        numberOfSquares += completedSquaresCount;
    }

    public boolean hasLine(Line line) {
        return lines.contains(line);
    }

    public Integer getNumberOfSquares() {
        return numberOfSquares;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}
