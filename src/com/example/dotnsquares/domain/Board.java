package com.example.dotnsquares.domain;

import android.graphics.Path;
import android.graphics.Point;

public class Board {
    public LineDrawing lineDrawing = new LineDrawing();
    private final int boardSize = 5;
    public final int numberOfDotRows = boardSize + 1;
    public final int numberOfDotColumns = boardSize + 1;
    public final int lineThickness = 10;
    public final int dotRadius = lineThickness;
    final int dotDiameter = 2 * dotRadius;
    public final int lineSize = 50;
    public final Path completedLinesPath = new Path();
    public final Dot[][] dots = new Dot[numberOfDotRows][numberOfDotColumns];
    final boolean[] isLinesCompleted = new boolean[2 * boardSize * (boardSize + 1)];
    public final String[][] squares = new String[boardSize][boardSize];

    public Board() {
        initialiseDots();
    }

    public void updateLineDrawingPosition(float x, float y) {
        Point currentPoint = getPointBasedOnDirection(x, y);
        lineDrawing.moveTo(currentPoint);
        lineDrawing.endAt(this.getDotForCoordinates(lineDrawing.getCurrentPoint().x, lineDrawing.getCurrentPoint().y));
        if (lineDrawing.isCompleted()) {
            Dot startingDot = lineDrawing.getStartingDot();
            Dot endDot = lineDrawing.getEndDot();
            completedLinesPath.moveTo(startingDot.x, startingDot.y);
            completedLinesPath.lineTo(endDot.x, endDot.y);

            LinearPath currentLinePath = LinearPath.create(startingDot.getPoint(), endDot.getPoint());
            Dot lowerDot = currentLinePath.getDirectionType() == LinearPath.DirectionType.Forward ? startingDot : endDot;
            int lineIndex = currentLinePath.isHorizontal() ? lowerDot.row * (2 * boardSize + 1) + lowerDot.column : boardSize + (lowerDot.row * (2 * boardSize + 1)) + lowerDot.column;
            isLinesCompleted[lineIndex] = true;

            if (currentLinePath.isHorizontal()) {
                if (lowerDot.row < boardSize) checkSquareCompletion(lowerDot.row, lowerDot.column);
                if (lowerDot.row > 0) checkSquareCompletion(lowerDot.row - 1, lowerDot.column);
            } else if (currentLinePath.isVertical()) {
                if (lowerDot.column < boardSize) checkSquareCompletion(lowerDot.row, lowerDot.column);
                if (lowerDot.column > 0) checkSquareCompletion(lowerDot.row, lowerDot.column - 1);
            }
            lineDrawing.reset();
        }

    }

    void checkSquareCompletion(int row, int column) {
        int firstLineIndex = row * (2 * boardSize + 1) + column;
        if (isLinesCompleted[firstLineIndex] && isLinesCompleted[firstLineIndex + boardSize]
                && isLinesCompleted[firstLineIndex + boardSize + 1] && isLinesCompleted[firstLineIndex + 2 * boardSize + 1])
            squares[row][column] = "C";
    }

    Point getPointBasedOnDirection(float x, float y) {
        LinearPath linearPath = LinearPath.create(lineDrawing.getStartingDot().getPoint(), new Point((int) x, (int) y));
        float xBasedOnDirection;
        float yBasedOnDirection;
        if (linearPath.isHorizontal()) {
            xBasedOnDirection = x;
            yBasedOnDirection = lineDrawing.getStartingDot().y;
        } else if (linearPath.isVertical()) {
            xBasedOnDirection = lineDrawing.getStartingDot().x;
            yBasedOnDirection = y;
        } else {
            xBasedOnDirection = x;
            yBasedOnDirection = y;
        }
        return new Point((int) xBasedOnDirection, (int) yBasedOnDirection);
    }

    Dot getDotForCoordinates(float x, float y) {
        int dotRowNumber = getRowOrColumnBasedOnPositionOnAxis(y);
        if (dotRowNumber == -1) return null;

        int dotColumnNumber = getRowOrColumnBasedOnPositionOnAxis(x);
        if (dotColumnNumber == -1) return null;

        return dotRowNumber < numberOfDotRows && dotColumnNumber < numberOfDotColumns ? dots[dotRowNumber][dotColumnNumber] : null;
    }

    int getRowOrColumnBasedOnPositionOnAxis(float position) {
        float modulus = position % lineSize;
        if (modulus <= dotDiameter) return (int) Math.floor(position / lineSize);
        else if (modulus >= lineSize - dotRadius) return (int) Math.ceil(position / lineSize);
        else return -1;
    }

    private void initialiseDots() {
        int boardTopLeftX = 0 + dotRadius;
        int boardTopLeftY = 0 + dotRadius;
        for (int row = 0; row < numberOfDotRows; row++) {
            for (int column = 0; column < numberOfDotColumns; column++) {
                dots[row][column] = new Dot(row, column, boardTopLeftX + column * lineSize, boardTopLeftY + row * lineSize);
            }
        }
    }

    public void startDrawingLineFrom(float x, float y) {
        lineDrawing.startFrom(getDotForCoordinates(x, y));
    }

    public boolean isLineDrawingStarted() {
        return lineDrawing.isStarted();
    }

    public void resetLineDrawing() {
        lineDrawing.reset();
    }
}