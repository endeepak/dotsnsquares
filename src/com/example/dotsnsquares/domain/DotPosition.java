package com.example.dotsnsquares.domain;

public class DotPosition {
    private final int row;
    private final int column;

    public DotPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
