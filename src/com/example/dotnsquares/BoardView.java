package com.example.dotnsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class BoardView extends View implements View.OnTouchListener{
    private LineDrawing lineDrawing = new LineDrawing();
    private final int boardSize = 5;
    private final int numberOfDotRows = boardSize + 1;
    private final int numberOfDotColumns = boardSize + 1;
    private final int lineThickness = 10;
    private final int dotRadius = lineThickness;
    private final int dotDiameter = 2 * dotRadius;
    private final int lineSize = 50;
    private final Point[][] dots = new Point[numberOfDotRows][numberOfDotColumns];
    private final Path completedLinesPath = new Path();

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    private void initialise() {
        initialiseDots();
        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDots(canvas);
        drawCurrentPath(canvas);
        if(lineDrawing.isCompleted()) {
            completedLinesPath.moveTo(lineDrawing.getStartingDot().x, lineDrawing.getStartingDot().y);
            completedLinesPath.lineTo(lineDrawing.getEndDot().x, lineDrawing.getEndDot().y);
            Log.i("onDraw", "Resetting line Drawing" + "Completed " + lineDrawing.isCompleted() + " end point " + lineDrawing.getEndDot());
            lineDrawing.reset();
        }
        canvas.drawPath(completedLinesPath, getLinePaint());
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
        TouchSwipe touchSwipe = TouchSwipe.create(lineDrawing.getStartingDot(), new Point((int) x, (int) y));
        float xBasedOnDirection;
        float yBasedOnDirection;
        if(touchSwipe.isHorizontal()){
            xBasedOnDirection = x;
            yBasedOnDirection = lineDrawing.getStartingDot().y;
        } else if(touchSwipe.isVertical()) {
            xBasedOnDirection = lineDrawing.getStartingDot().x;
            yBasedOnDirection = y;
        } else {
            xBasedOnDirection = x;
            yBasedOnDirection = y;
        }
        return new Point((int) xBasedOnDirection, (int) yBasedOnDirection);
    }

    private Point getDotForCoordinates(float x, float y) {
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
        Log.i("drawCurrentPath", "Drawing " + lineDrawing.getStartingDot() + " to "+ lineDrawing.getCurrentPoint());
        Log.i("drawCurrentPath", "Completed " + lineDrawing.isCompleted() + " end point " + lineDrawing.getEndDot());
        Paint paint = getLinePaint();
        Path currentPath = new Path();
        currentPath.moveTo(lineDrawing.getStartingDot().x, lineDrawing.getStartingDot().y);
        currentPath.lineTo(lineDrawing.getCurrentPoint().x, lineDrawing.getCurrentPoint().y);
        currentPath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(currentPath, paint);
        lineDrawing.endAt(getDotForCoordinates(lineDrawing.getCurrentPoint().x, lineDrawing.getCurrentPoint().y));
    }

    private Paint getLinePaint() {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineThickness);
        return paint;
    }

    private void initialiseDots() {
        int boardTopLeftX = 0 +  dotRadius;
        int boardTopLeftY = 0 +  dotRadius;
        for(int row = 0; row < numberOfDotRows; row++){
            for(int column = 0; column < numberOfDotColumns; column++){
                Point point = new Point(boardTopLeftX + column * lineSize, boardTopLeftY + row * lineSize);
                dots[row][column] = point;
            }
        }
    }

    private void drawDots(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        for(int row = 0; row < numberOfDotRows; row++){
            for(int column = 0; column < numberOfDotColumns; column++){
                Point point = dots[row][column];
                canvas.drawCircle(point.x, point.y, dotRadius, paint);
            }
        }
    }
}
