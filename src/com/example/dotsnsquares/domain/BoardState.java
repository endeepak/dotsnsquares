package com.example.dotsnsquares.domain;

import java.util.ArrayList;

public class BoardState {
    private final int boardSize;
    private final boolean[] completedLines;
    //TODO: Remove the below fields from here
    private SquareMatrix squareMatrix;
    private Square[][] squares;

    public BoardState(int boardSize, boolean[] completedLines, SquareMatrix squareMatrix, Square[][] squares) {
        this.boardSize = boardSize;
        this.completedLines = completedLines;
        this.squareMatrix = squareMatrix;
        this.squares = squares;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean[] getCompletedLines() {
        return completedLines;
    }

    public SquareMatrix getSquareMatrix() {
        return squareMatrix;
    }

    public Square getFirstCompletableSquare() {
        for (Square[] squareList : squares) {
            for (Square square : squareList) {
                if(square.isCompletable())
                    return square;
            }
        }
        return null;
    }

    public ArrayList<Square> getInCompletableSquares() {
        ArrayList<Square> inCompletableSquares = new ArrayList<Square>();
        for (Square[] squareList : squares) {
            for (Square square : squareList) {
                if(square.isInCompletable())
                    inCompletableSquares.add(square);
            }
        }
        return inCompletableSquares;
    }

    public ArrayList<Square> getOpponentCompletableSquares() {
        ArrayList<Square> opponentCompletableSquares = new ArrayList<Square>();
        for (Square[] squareList : squares) {
            for (Square square : squareList) {
                if(square.isOpponentCompletable())
                    opponentCompletableSquares.add(square);
            }
        }
        return opponentCompletableSquares;
    }
}
