package com.example.dotnsquares.domain;

import java.io.Serializable;

public class Square implements Serializable {
    private int color;

    public Square(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
