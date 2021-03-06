package com.endeepak.dotsnsquares.domain;

import android.graphics.Path;
import android.graphics.Point;
import com.endeepak.dotsnsquares.exception.AlreadyDrawnLineException;

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
    public final SquareOwner[][] squareOwners;
    private final ArrayList<LineDrawnEventListener> lineDrawingListeners = new ArrayList<LineDrawnEventListener>();
    private int numberOfSquaresCompleted = 0;
    private int numberOfLinesCompleted = 0;
    private int currentSquareFillColor;
    private int numberOfSquares;
    private SquareMatrix squareMatrix;
    private BoardState boardState;

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
        squareMatrix = new SquareMatrix(boardSize);
        squareOwners = new SquareOwner[this.boardSize][this.boardSize];
        boardState = new BoardState(boardSize, squareMatrix);
        initialiseDots();
    }

    public void updateLineDrawingPosition(float x, float y, boolean aimForCenterOfDot) {
        if(!lineDrawing.isStarted()) {
            startDrawingLineFrom(x, y);
            return;
        }
        Dot startingDot = lineDrawing.getStartingDot();
        Point startingPoint = startingDot.getPoint();
        LinePath linePath = LinePath.create(startingPoint, new Point((int) x, (int) y));
        Point currentPoint = linePath.getPointBasedOnDirection(x, y);
        lineDrawing.moveTo(currentPoint);
        lineDrawing.endAt(getEndDotFor(linePath, aimForCenterOfDot));
        if (lineDrawing.isCompleted()) {
            Dot endDot = lineDrawing.getEndDot();
            Dot lowerDot = linePath.getDirectionType() == LinePath.DirectionType.Forward ? startingDot : endDot;
            int lineIndex = getLineIndex(linePath, lowerDot);
            if(boardState.isLineCompleted(lineIndex)) return;
            lineDrawing.reset();
            publishLineDrawnEvent(new LineDrawnEvent(new Line(startingDot.getPosition(), endDot.getPosition())));
        }
    }

    private int getLineIndex(LinePath linePath, Dot lowerDot) {
        return linePath.isHorizontal() ? squareMatrix.getHorizontalLineIndex(lowerDot.row, lowerDot.column) : squareMatrix.getVerticalLineIndex(lowerDot.row, lowerDot.column);
    }

    public BoardStateChange drawLine(Line line) {
        Dot startingDot = getDot(line.getStartingDotPosition());
        Dot endDot = getDot(line.getEndDotPosition());
        LinePath linePath = LinePath.create(startingDot.getPoint(), endDot.getPoint());
        Dot lowerDot = linePath.getDirectionType() == LinePath.DirectionType.Forward ? startingDot : endDot;
        int lineIndex = getLineIndex(linePath, lowerDot);
        if(boardState.isLineCompleted(lineIndex)) throw new AlreadyDrawnLineException();
        boardState.markLineCompleted(lineIndex);
        numberOfLinesCompleted++;
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
        int numberOfSquaresCompletedInThisMove = numberOfSquaresCompleted - numberOfSquaresCompletedPreviously;
        return new BoardStateChange(numberOfSquaresCompletedInThisMove);
    }

    private void publishLineDrawnEvent(LineDrawnEvent lineDrawnEvent) {
        for(LineDrawnEventListener lineDrawingListener: lineDrawingListeners){
            lineDrawingListener.onLineDrawn(lineDrawnEvent);
        }
    }

    private Dot getEndDotFor(LinePath linePath, boolean aimForCenterOfDot) {
        Dot nextDotInDirectionOfLinePath = getNextDotInDirectionOf(linePath);
        if(nextDotInDirectionOfLinePath == null) return null;
        Point point = linePath.isHorizontal() ? new Point(linePath.getEndPoint().x, nextDotInDirectionOfLinePath.y) : new Point(nextDotInDirectionOfLinePath.x, linePath.getEndPoint().y);
        int radius = aimForCenterOfDot ? 0 : dotRadius;
        return Circle.with(nextDotInDirectionOfLinePath.getPoint(), radius).contains(point) ? nextDotInDirectionOfLinePath : null;
    }

    private Dot getNextDotInDirectionOf(LinePath linePath) {
        Dot startingDot = lineDrawing.getStartingDot();
        if(linePath.isHorizontal()) {
            if (linePath.getDirection() == LinePath.Direction.LeftToRight)
                return startingDot.column < numberOfDotColumns - 1 ? dots[startingDot.row][startingDot.column + 1] : null;
            else
                return startingDot.column > 0 ? dots[startingDot.row][startingDot.column - 1] : null;
        }
        if(linePath.isVertical()) {
            if (linePath.getDirection() == LinePath.Direction.TopToBottom)
                return startingDot.row < numberOfDotRows - 1 ? dots[startingDot.row + 1][startingDot.column] : null;
            else
                return startingDot.row > 0 ? dots[startingDot.row - 1][startingDot.column] : null;
        }
        return null;
    }

    void checkAndMarkSquareCompletion(int row, int column) {
        if(squareOwners[row][column] != null) return;
        if (boardState.getSquare(row, column).isComplete()) {
            squareOwners[row][column] = new SquareOwner(currentSquareFillColor);
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
        Dot dot = getDotForCoordinates(x, y);
        lineDrawing.startFrom(dot);
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

    public int getNumberOfLinesCompleted() {
        return numberOfLinesCompleted;
    }

    public void removeLineDrawingListener(LineDrawnEventListener lineDrawnEventListener) {
        lineDrawingListeners.remove(lineDrawnEventListener);
    }

    public Dot getDot(DotPosition startingDotPosition) {
        return dots[startingDotPosition.getRow()][startingDotPosition.getColumn()];
    }

    public BoardState getState() {
        return boardState.getClone();
    }

    public static class LineDrawnEvent {
        private Line line;

        public LineDrawnEvent(Line line) {
            this.line = line;
        }

        public Line getLine() {
            return line;
        }
    }

    public static class BoardStateChange {
        private int numberOfSquaresCompleted;

        public BoardStateChange(int numberOfSquaresCompleted) {
            this.numberOfSquaresCompleted = numberOfSquaresCompleted;
        }

        public int getNumberOfSquaresCompleted() {
            return numberOfSquaresCompleted;
        }

        public boolean areSquaresCompleted() {
            return numberOfSquaresCompleted > 0;
        }
    }

    public static interface LineDrawnEventListener extends Serializable {
        void onLineDrawn(LineDrawnEvent event);
    }
}