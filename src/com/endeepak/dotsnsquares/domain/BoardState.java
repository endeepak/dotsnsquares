package com.endeepak.dotsnsquares.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardState implements Serializable, Cloneable {
    private final boolean[] completedLines;
    private final int boardSize;
    //TODO: Remove the below fields from here
    private SquareMatrix squareMatrix;

    public BoardState(int boardSize, SquareMatrix squareMatrix) {
        this.boardSize = boardSize;
        this.squareMatrix = squareMatrix;
        this.completedLines = new boolean[2 * this.boardSize * (this.boardSize + 1)];
    }

    public Square getFirstCompletableSquare() {
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Square square = getSquare(row, column);
                if(square.isCompletable())
                    return square;
            }
        }
        return null;
    }

    public ArrayList<Square> getInCompletableSquares() {
        ArrayList<Square> inCompletableSquares = new ArrayList<Square>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Square square = getSquare(row, column);
                if(square.isInCompletable())
                    inCompletableSquares.add(square);
            }
        }
        return inCompletableSquares;
    }

    public ArrayList<Square> getOpponentCompletableSquares() {
        ArrayList<Square> opponentCompletableSquares = new ArrayList<Square>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                Square square = getSquare(row, column);
                if(square.isOpponentCompletable())
                    opponentCompletableSquares.add(square);
            }
        }
        return opponentCompletableSquares;
    }


    public BoardState getClone() {
        try {
            return (BoardState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLineCompleted(int lineIndex) {
        return completedLines[lineIndex];
    }

    public void markLineCompleted(int lineIndex) {
        completedLines[lineIndex] = true;
    }

    public Square getSquare(int row, int column) {
        int firstLineIndex = row * (2 * boardSize + 1) + column;
        int[] allLineIndices = new int[] {firstLineIndex, firstLineIndex + boardSize, firstLineIndex + boardSize + 1, firstLineIndex + 2 * boardSize + 1};
        ArrayList<Integer> completedLineIndices = new ArrayList<Integer>();
        ArrayList<Integer> inCompleteLineIndices = new ArrayList<Integer>();
        for (int lineIndex : allLineIndices) {
            if(isLineCompleted(lineIndex))
                completedLineIndices.add(lineIndex);
            else
                inCompleteLineIndices.add(lineIndex);
        }
        return new Square(completedLineIndices, inCompleteLineIndices);
    }

    public Line getLine(Integer lineIndex) {
        return squareMatrix.getLine(lineIndex);
    }

    public ArrayList<Line> getInCompleteLines() {
        ArrayList<Line> remainingLineIndexes = new ArrayList<Line>();
        for (int lineIndex = 0; lineIndex < completedLines.length; lineIndex++) {
            if (!completedLines[lineIndex])
                remainingLineIndexes.add(getLine(lineIndex));
        }
        return remainingLineIndexes;
    }
}
