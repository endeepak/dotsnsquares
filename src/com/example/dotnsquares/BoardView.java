package com.example.dotnsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BoardView extends View implements View.OnTouchListener{
    private final Paint squareFillPaint;
    private LineDrawing lineDrawing = new LineDrawing();
    private final int boardSize = 5;
    private final int numberOfDotRows = boardSize + 1;
    private final int numberOfDotColumns = boardSize + 1;
    private final int lineThickness = 10;
    private final int dotRadius = lineThickness;
    private final int dotDiameter = 2 * dotRadius;
    private final int lineSize = 50;
    private final Path completedLinesPath = new Path();
    private final Dot[][] dots = new Dot[numberOfDotRows][numberOfDotColumns];
    private final boolean[] isLinesCompleted = new boolean[2 * boardSize * (boardSize + 1)];
    private final String[][] squares = new String[boardSize][boardSize];
    private final Paint linePaint;
    private final Paint dotsPaint;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiseDots();
        this.setOnTouchListener(this);

        dotsPaint = new Paint();
        dotsPaint.setColor(Color.BLUE);
        dotsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(lineThickness);

        squareFillPaint = new Paint();
        squareFillPaint.setColor(Color.GREEN);
        squareFillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurrentPath(canvas);
        if(lineDrawing.isCompleted()) {
            markCompletedLinesAndSquares();
            lineDrawing.reset();
        }
        drawCompletedSquares(canvas);
        drawDots(canvas);
        drawCompletedLines(canvas);
    }

    private void markCompletedLinesAndSquares() {
        Dot startingDot = lineDrawing.getStartingDot();
        Dot endDot = lineDrawing.getEndDot();
        completedLinesPath.moveTo(startingDot.x, startingDot.y);
        completedLinesPath.lineTo(endDot.x, endDot.y);

        LinearPath currentLinePath = LinearPath.create(startingDot.getPoint(), endDot.getPoint());
        Dot lowerDot = currentLinePath.getDirectionType() == LinearPath.DirectionType.Forward ? startingDot : endDot;
        int lineIndex = currentLinePath.isHorizontal() ? lowerDot.row * (2 * boardSize + 1) + lowerDot.column : boardSize + (lowerDot.row * (2 * boardSize + 1)) + lowerDot.column;
        isLinesCompleted[lineIndex] = true;

        if(currentLinePath.isHorizontal()){
            if(lowerDot.row < boardSize) checkSquareCompletion(lowerDot.row, lowerDot.column);
            if(lowerDot.row > 0) checkSquareCompletion(lowerDot.row - 1, lowerDot.column);
        } else if(currentLinePath.isVertical()) {
            if(lowerDot.column < boardSize) checkSquareCompletion(lowerDot.row, lowerDot.column);
            if(lowerDot.column > 0) checkSquareCompletion(lowerDot.row, lowerDot.column - 1);
        }
    }

    private void drawCompletedLines(Canvas canvas) {
        canvas.drawPath(completedLinesPath, linePaint);
    }

    private void drawCompletedSquares(Canvas canvas) {
        for(int row = 0; row < numberOfDotRows - 1; row++){
            for(int column = 0; column < numberOfDotColumns - 1; column++){
                String c = squares[row][column];
                Dot dot = dots[row][column];
                if(c != null)
                    canvas.drawRect(dot.x, dot.y, dot.x + lineSize, dot.y + lineSize, squareFillPaint);
            }
        }
    }

    private void checkSquareCompletion(int row, int column) {
        int firstLineIndex = row * (2 * boardSize + 1) + column;
        if(isLinesCompleted[firstLineIndex] && isLinesCompleted[firstLineIndex + boardSize]
                && isLinesCompleted[firstLineIndex + boardSize + 1] && isLinesCompleted[firstLineIndex + 2 * boardSize + 1])
            squares[row][column] = "C";
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lineDrawing.startFrom(getDotForCoordinates(x, y));
                break;
            case MotionEvent.ACTION_MOVE:
                if(lineDrawing.isStarted()) {
                    Point currentPoint = getPointBasedOnDirection(x, y);
                    lineDrawing.moveTo(currentPoint);
                    lineDrawing.endAt(getDotForCoordinates(lineDrawing.getCurrentPoint().x, lineDrawing.getCurrentPoint().y));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(lineDrawing.isStarted()) {
                    lineDrawing.reset();
                    invalidate();
                }
                break;
        }
        return true;
    }

    private Point getPointBasedOnDirection(float x, float y) {
        LinearPath linearPath = LinearPath.create(lineDrawing.getStartingDot().getPoint(), new Point((int) x, (int) y));
        float xBasedOnDirection;
        float yBasedOnDirection;
        if(linearPath.isHorizontal()){
            xBasedOnDirection = x;
            yBasedOnDirection = lineDrawing.getStartingDot().y;
        } else if(linearPath.isVertical()) {
            xBasedOnDirection = lineDrawing.getStartingDot().x;
            yBasedOnDirection = y;
        } else {
            xBasedOnDirection = x;
            yBasedOnDirection = y;
        }
        return new Point((int) xBasedOnDirection, (int) yBasedOnDirection);
    }

    private Dot getDotForCoordinates(float x, float y) {
        int dotRowNumber = getRowOrColumnBasedOnPositionOnAxis(y);
        if(dotRowNumber == -1) return null;

        int dotColumnNumber = getRowOrColumnBasedOnPositionOnAxis(x);
        if(dotColumnNumber == -1) return null;

        return dotRowNumber < numberOfDotRows && dotColumnNumber < numberOfDotColumns ? dots[dotRowNumber][dotColumnNumber] : null ;
    }

    private int getRowOrColumnBasedOnPositionOnAxis(float position) {
        float modulus = position % lineSize;
        if(modulus <= dotDiameter) return (int) Math.floor(position / lineSize);
        else if(modulus >= lineSize - dotRadius) return  (int) Math.ceil(position / lineSize);
        else return  -1;
    }

    private void drawCurrentPath(Canvas canvas) {
        if(!lineDrawing.isStarted()) return;
        Path currentPath = new Path();
        currentPath.moveTo(lineDrawing.getStartingDot().x, lineDrawing.getStartingDot().y);
        currentPath.lineTo(lineDrawing.getCurrentPoint().x, lineDrawing.getCurrentPoint().y);
        currentPath.setFillType(Path.FillType.WINDING);
        canvas.drawPath(currentPath, linePaint);
    }

    private void initialiseDots() {
        int boardTopLeftX = 0 +  dotRadius;
        int boardTopLeftY = 0 +  dotRadius;
        for(int row = 0; row < numberOfDotRows; row++){
            for(int column = 0; column < numberOfDotColumns; column++){
                dots[row][column] = new Dot(row, column, boardTopLeftX + column * lineSize, boardTopLeftY + row * lineSize);
            }
        }
    }

    private void drawDots(Canvas canvas) {
        for(int row = 0; row < numberOfDotRows; row++){
            for(int column = 0; column < numberOfDotColumns; column++){
                Dot dot = dots[row][column];
                canvas.drawCircle(dot.x, dot.y, dotRadius, dotsPaint);
            }
        }
    }
}
