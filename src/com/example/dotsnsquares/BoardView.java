package com.example.dotsnsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.dotsnsquares.domain.Board;
import com.example.dotsnsquares.domain.Dot;
import com.example.dotsnsquares.domain.Square;

public class BoardView extends View implements View.OnTouchListener{
    private final Paint squareFillPaint;
    private final int foregroundColor = Color.BLUE;
    private final Paint focusPaint;
    private Board board;
    private final Paint linePaint;
    private final Paint dotsPaint;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        dotsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotsPaint.setColor(foregroundColor);
        dotsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(foregroundColor);
        linePaint.setStyle(Paint.Style.STROKE);

        focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        focusPaint.setColor(foregroundColor);
        focusPaint.setAlpha(125);
        focusPaint.setStyle(Paint.Style.FILL);

        squareFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        squareFillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(board != null) {
            drawCurrentPath(canvas);
            drawCompletedSquares(canvas);
            drawDots(canvas);
            drawCompletedLines(canvas);
            drawFocusCircle(canvas);
        }
    }

    private void drawFocusCircle(Canvas canvas) {
        if(board.isLineDrawingStarted()){
            Point currentPoint = board.lineDrawing.getCurrentPoint();
            canvas.drawCircle(currentPoint.x, currentPoint.y, board.dotRadius * 3, focusPaint);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                board.startDrawingLineFrom(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(board.isLineDrawingStarted()) {
                    board.updateLineDrawingPosition(x, y);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(board.isLineDrawingStarted()) {
                    board.resetLineDrawing();
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void drawDots(Canvas canvas) {
        for(int row = 0; row < board.numberOfDotRows; row++){
            for(int column = 0; column < board.numberOfDotColumns; column++){
                Dot dot = board.dots[row][column];
                canvas.drawCircle(dot.x, dot.y, board.dotRadius, dotsPaint);
            }
        }
    }

    private void drawCompletedLines(Canvas canvas) {
        canvas.drawPath(board.completedLinesPath, linePaint);
    }

    private void drawCompletedSquares(Canvas canvas) {
        for(int row = 0; row < board.numberOfDotRows - 1; row++){
            for(int column = 0; column < board.numberOfDotColumns - 1; column++){
                Square square = board.squares[row][column];
                if(square != null){
                    Dot dot = board.dots[row][column];
                    squareFillPaint.setColor(square.getColor());
                    canvas.drawRect(dot.x, dot.y, dot.x + board.lineSize, dot.y + board.lineSize, squareFillPaint);
                }
            }
        }
    }

    private void drawCurrentPath(Canvas canvas) {
        if(!board.lineDrawing.isStarted()) return;
        canvas.drawPath(getCurrentPath(), linePaint);
    }

    private Path getCurrentPath() {
        Path currentPath = new Path();
        currentPath.moveTo(board.lineDrawing.getStartingDot().x, board.lineDrawing.getStartingDot().y);
        currentPath.lineTo(board.lineDrawing.getCurrentPoint().x, board.lineDrawing.getCurrentPoint().y);
        currentPath.setFillType(Path.FillType.WINDING);
        return currentPath;
    }

    public void initializeBoard(Board board) {
        this.board = board;
        linePaint.setStrokeWidth(board.lineThickness);
        invalidate();
    }
}
