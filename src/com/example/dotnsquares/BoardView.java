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
    private Path currentPath;
    private Point currentStartingDot;
    private final int boardSize = 5;
    private final int numberOfDotRows = boardSize + 1;
    private final int numberOfDotColumns = boardSize + 1;
    private final int lineThickness = 10;
    private final int dotRadius = lineThickness;
    private final int dotDiameter = 2 * dotRadius;
    private final int lineSize = 70;
    private final Point[][] dots = new Point[numberOfDotRows][numberOfDotColumns];

    public BoardView(Context context) {
        super(context);
        initialiseDots();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialiseDots();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialiseDots();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setOnTouchListener(this);
        drawDots(canvas);
        drawCurrentPath(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(currentPath == null){
                    currentPath = new Path();
                }
                currentStartingDot = getStartingDot(x, y);
                if(currentStartingDot != null) {
                    currentPath.moveTo(currentStartingDot.x, currentStartingDot.y);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(currentStartingDot != null) {
                    currentPath.lineTo(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentStartingDot != null) {
                    currentPath.lineTo(x, y);
                    invalidate();
                    currentStartingDot = null;
                }
                break;
        }
        return true;
    }

    private Point getStartingDot(float x, float y) {
        int dotRowNumber;
        int dotColumnNumber;
        float yModulus = y % lineSize;
        float xModulus = x % lineSize;
        if(yModulus <= dotDiameter) dotRowNumber = (int) Math.floor(y / lineSize);
        else if(yModulus >= lineSize - dotRadius) dotRowNumber = (int) Math.ceil(y / lineSize);
        else return null;

        if(xModulus <= dotDiameter) dotColumnNumber = (int) Math.floor(x / lineSize);
        else if(xModulus >= lineSize - dotRadius) dotColumnNumber = (int) Math.ceil(x / lineSize);
        else return null;

        return dotRowNumber < numberOfDotRows && dotColumnNumber < numberOfDotColumns ? dots[dotRowNumber][dotColumnNumber] : null ;
    }

    private void drawCurrentPath(Canvas canvas) {
        if(currentPath == null) return;
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(lineThickness);
        currentPath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(currentPath, paint);
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
