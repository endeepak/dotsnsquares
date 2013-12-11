package com.example.dotsnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardSize implements Serializable {
    private int size;

    public BoardSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("%s x %s", size, size);
    }

    public static ArrayList<BoardSize> fromSizes(int... sizes) {
        ArrayList<BoardSize> options = new ArrayList<BoardSize>();
        for (int size : sizes) {
            options.add(new BoardSize(size));
        }
        return options;
    }

    public int getSize() {
        return size;
    }
}
