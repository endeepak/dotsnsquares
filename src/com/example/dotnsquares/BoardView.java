package com.example.dotnsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.dotnsquares.domain.Board;
import com.example.dotnsquares.domain.Dot;

public class BoardView extends View implements View.OnTouchListener{
    private final Paint squareFillPaint;
    private Board board;
    private final Paint linePaint;
    private final Paint dotsPaint;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);

        board = new Board();

        dotsPaint = new Paint();
        dotsPaint.setColor(Color.BLUE);
        dotsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint();
        linePaint.setColor(Color.BLUE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(board.lineThickness);

        squareFillPaint = new Paint();
        squareFillPaint.setColor(Color.GREEN);
        squareFillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurrentPath(canvas);
        drawCompletedSquares(canvas);
        drawDots(canvas);
        drawCompletedLines(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                board.startDrawingLineFrom(x, y);
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
                String c = board.squares[row][column];
                Dot dot = board.dots[row][column];
                if(c != null)
                    canvas.drawRect(dot.x, dot.y, dot.x + board.lineSize, dot.y + board.lineSize, squareFillPaint);
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        invalidate();
    }
}
