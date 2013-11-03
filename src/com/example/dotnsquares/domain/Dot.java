package com.example.dotnsquares.domain;

import android.graphics.Point;

import java.io.Serializable;

public class Dot implements Serializable {
    public final int row;
    public final int column;
    public final int x;
    public final int y;

    public Dot(int row, int column, int x, int y) {
        this.row = row;
        this.column = column;
        this.x = x;
        this.y = y;
    }

    public Point getPoint() {
        return new Point(x,y);
    }

    @Override
    public String toString() {
        return "Dot{" +
                "row=" + row +
                ", column=" + column +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
