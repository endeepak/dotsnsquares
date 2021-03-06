package com.endeepak.dotsnsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.endeepak.dotsnsquares.domain.Board;
import com.endeepak.dotsnsquares.domain.Dot;
import com.endeepak.dotsnsquares.domain.GameOptions;
import com.endeepak.dotsnsquares.domain.SquareOwner;

public class BoardView extends View {
    private final Paint squareFillPaint;
    private final Paint focusPaint;
    private Board board;
    private final Paint linePaint;
    private final Paint dotsPaint;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        GameOptions gameOptions = GameOptions.fromPreferences(PreferenceManager.getDefaultSharedPreferences(context), context.getResources());

        dotsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotsPaint.setColor(gameOptions.getForegroundColor());
        dotsPaint.setStyle(Paint.Style.FILL);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(gameOptions.getForegroundColor());
        linePaint.setStyle(Paint.Style.STROKE);

        focusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        focusPaint.setColor(gameOptions.getForegroundColor());
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
                SquareOwner squareOwner = board.squareOwners[row][column];
                if(squareOwner != null){
                    Dot dot = board.dots[row][column];
                    squareFillPaint.setColor(squareOwner.getColor());
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
