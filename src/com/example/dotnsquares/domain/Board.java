package com.example.dotnsquares.domain;

import android.graphics.Path;
import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private final int boardSize;
    public final int numberOfDotRows;
    public final int numberOfDotColumns;
    public final int lineThickness;
    public final int dotRadius;
    final int dotDiameter;
    public final int lineSize;
    transient public final Path completedLinesPath = new Path();
    private final int dotMargin;
    transient public LineDrawing lineDrawing = new LineDrawing();
    public final Dot[][] dots;
    final boolean[] isLinesCompleted;
    public final Square[][] squares;
    private final ArrayList<LineDrawnEventListener> lineDrawingListeners = new ArrayList<LineDrawnEventListener>();
    private int numberOfSquaresCompleted = 0;
    private int currentSquareFillColor;
    private int numberOfSquares;

    public Board(int boardSize, int boardWidth) {
        this.boardSize = boardSize;
        dotRadius = boardWidth / (boardSize * 6);
        lineThickness = dotRadius;
        dotDiameter = 2 * dotRadius;
        dotMargin = dotDiameter;
        this.lineSize = (boardWidth - dotDiameter - 2 * dotMargin) / boardSize;
        numberOfDotRows = this.boardSize + 1;
        numberOfDotColumns = this.boardSize + 1;
        numberOfSquares = this.boardSize * this.boardSize;
        dots = new Dot[numberOfDotRows][numberOfDotColumns];
        isLinesCompleted = new boolean[2 * this.boardSize * (this.boardSize + 1)];
        squares = new Square[this.boardSize][this.boardSize];
        initialiseDots();
    }

    public void updateLineDrawingPosition(float x, float y) {
        LinePath linePath = LinePath.create(lineDrawing.getStartingDot().getPoint(), new Point((int) x, (int) y));
        Point currentPoint = linePath.getPointBasedOnDirection(x, y);
        lineDrawing.moveTo(currentPoint);
        lineDrawing.endAt(getEndDotFor(linePath));
        if (lineDrawing.isCompleted()) {
            Dot startingDot = lineDrawing.getStartingDot();
            Dot endDot = lineDrawing.getEndDot();
            Dot lowerDot = linePath.getDirectionType() == LinePath.DirectionType.Forward ? startingDot : endDot;
            int lineIndex = linePath.isHorizontal() ? lowerDot.row * (2 * boardSize + 1) + lowerDot.column : boardSize + (lowerDot.row * (2 * boardSize + 1)) + lowerDot.column;
            if(isLinesCompleted[lineIndex]) return;

            isLinesCompleted[lineIndex] = true;
            completedLinesPath.moveTo(startingDot.x, startingDot.y);
            completedLinesPath.lineTo(endDot.x, endDot.y);


            int numberOfSquaresCompletedPreviously = numberOfSquaresCompleted;
            if (linePath.isHorizontal()) {
                if (lowerDot.row < boardSize) checkAndMarkSquareCompletion(lowerDot.row, lowerDot.column);
                if (lowerDot.row > 0) checkAndMarkSquareCompletion(lowerDot.row - 1, lowerDot.column);
            } else if (linePath.isVertical()) {
                if (lowerDot.column < boardSize) checkAndMarkSquareCompletion(lowerDot.row, lowerDot.column);
                if (lowerDot.column > 0) checkAndMarkSquareCompletion(lowerDot.row, lowerDot.column - 1);
            }
            lineDrawing.reset();
            publishLineDrawnEvent(new LineDrawnEvent(numberOfSquaresCompleted - numberOfSquaresCompletedPreviously));
        }
    }

    private void publishLineDrawnEvent(LineDrawnEvent lineDrawnEvent) {
        for(LineDrawnEventListener lineDrawingListener: lineDrawingListeners){
            lineDrawingListener.onLineDrawn(lineDrawnEvent);
        }
    }

    private Dot getEndDotFor(LinePath linePath) {
        Dot startingDot = lineDrawing.getStartingDot();
        int distanceBetweenDots = lineSize - dotRadius;
        if(linePath.isHorizontal() && Math.abs(linePath.getDx()) >= distanceBetweenDots) {
            if (linePath.getDirection() == LinePath.Direction.LeftToRight)
                return startingDot.column < numberOfDotColumns - 1 ? dots[startingDot.row][startingDot.column + 1] : null;
            else
                return startingDot.column > 0 ? dots[startingDot.row][startingDot.column - 1] : null;
        }
        if(linePath.isVertical() && Math.abs(linePath.getDy()) >= distanceBetweenDots) {
            if (linePath.getDirection() == LinePath.Direction.TopToBottom)
                return startingDot.row < numberOfDotRows - 1 ? dots[startingDot.row + 1][startingDot.column] : null;
            else
                return startingDot.row > 0 ? dots[startingDot.row - 1][startingDot.column] : null;
        }
        return null;
    }

    void checkAndMarkSquareCompletion(int row, int column) {
        if(squares[row][column] != null) return;
        int firstLineIndex = row * (2 * boardSize + 1) + column;
        if (isLinesCompleted[firstLineIndex]
                && isLinesCompleted[firstLineIndex + boardSize]
                && isLinesCompleted[firstLineIndex + boardSize + 1]
                && isLinesCompleted[firstLineIndex + 2 * boardSize + 1]) {
            squares[row][column] = new Square(currentSquareFillColor);
            numberOfSquaresCompleted++;
        }
    }

    Dot getDotForCoordinates(float x, float y) {
        if(x < dotMargin || y < dotMargin) return null;

        int dotRowNumber = getRowOrColumnBasedOnPositionOnAxis(y - dotMargin);
        if (dotRowNumber == -1) return null;

        int dotColumnNumber = getRowOrColumnBasedOnPositionOnAxis(x - dotMargin);
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
        int boardTopLeftX = dotMargin + dotRadius;
        int boardTopLeftY = dotMargin + dotRadius;
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

    public void addLineDrawingListener(LineDrawnEventListener lineDrawingListener) {
        lineDrawingListeners.add(lineDrawingListener);
    }

    public void setCurrentSquareFillColor(int color) {
        this.currentSquareFillColor = color;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    public static class LineDrawnEvent {
        private int numberOfSquaresCompleted;

        public LineDrawnEvent(int numberOfSquaresCompleted) {
            this.numberOfSquaresCompleted = numberOfSquaresCompleted;
        }

        public int getNumberOfSquaresCompleted() {
            return numberOfSquaresCompleted;
        }

        public boolean areSquaresCompleted() {
            return numberOfSquaresCompleted > 0;
        }
    }

    public static interface LineDrawnEventListener {
        void onLineDrawn(LineDrawnEvent event);
    }
}