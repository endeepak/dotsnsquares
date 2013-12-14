package com.example.dotsnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Square implements Serializable {
    private final ArrayList<Integer> completedLineIndices;
    private final ArrayList<Integer> inCompleteLineIndices;

    public Square(ArrayList<Integer> completedLineIndices, ArrayList<Integer> inCompleteLineIndices) {
        this.completedLineIndices = completedLineIndices;
        this.inCompleteLineIndices = inCompleteLineIndices;
    }

    public boolean isComplete() {
        return completedLineIndices.size() == 4;
    }

    public boolean isCompletable() {
        return completedLineIndices.size() == 3;
    }

    public boolean isInCompletable() {
        return completedLineIndices.size() < 2;
    }

    public boolean isOpponentCompletable() {
        return completedLineIndices.size() == 2;
    }

    public ArrayList<Integer> getInCompleteLineIndices() {
        return inCompleteLineIndices;
    }
}
