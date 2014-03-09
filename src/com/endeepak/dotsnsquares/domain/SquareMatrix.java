package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;

public class SquareMatrix implements Serializable {
    private int size;
    private int[][] horizontalLineIndexes;
    private int[][] verticalLineIndexes;
    private Line[] lines;

    public SquareMatrix(int size) {
        this.size = size;
        this.horizontalLineIndexes = new int[size + 1][size];
        this.verticalLineIndexes = new int[size][size + 1];
        this.lines =  new Line[2 * size * (size + 1)];
        for (int row = 0; row <= size ; row++) {
            for (int column = 0; column < size ; column ++) {
                int horizontalLineIndex = getHorizontalLineIndex(row, column);
                horizontalLineIndexes[row][column] = horizontalLineIndex;
                lines[horizontalLineIndex] = new Line(new DotPosition(row, column), new DotPosition(row, column +1));
            }
        }
        for (int column = 0; column <= size ; column++) {
            for (int row = 0; row < size ; row ++) {
                int verticalLineIndex = getVerticalLineIndex(row, column);
                verticalLineIndexes[row][column] = verticalLineIndex;
                lines[verticalLineIndex] = new Line(new DotPosition(row, column), new DotPosition(row +1, column));
            }
        }
    }

    public int getVerticalLineIndex(int row, int column) {
        return size + (row * (2 * size + 1)) + column;
    }

    public int getHorizontalLineIndex(int row, int column) {
        return row * (2 * size + 1) + column;
    }

    public Line getLine(int lineIndex) {
        return lines[lineIndex];
    }
}
