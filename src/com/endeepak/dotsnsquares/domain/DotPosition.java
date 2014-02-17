package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;

public class DotPosition implements Serializable{
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
