package com.example.dotsnsquares.domain;

import java.io.Serializable;

public class SquareOwner implements Serializable {
    private int color;

    public SquareOwner(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
