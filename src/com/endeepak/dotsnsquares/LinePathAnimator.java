package com.endeepak.dotsnsquares;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Point;
import com.endeepak.dotsnsquares.domain.Board;
import com.endeepak.dotsnsquares.domain.Line;

public class LinePathAnimator implements ValueAnimator.AnimatorUpdateListener {
    private final BoardView boardView;
    private final Board board;
    private Point startingPoint;
    private Point endPoint;

    public LinePathAnimator(BoardView boardView, Board board, Line line) {
        this.boardView = boardView;
        this.board = board;
        startingPoint = board.getDot(line.getStartingDotPosition()).getPoint();
        endPoint = board.getDot(line.getEndDotPosition()).getPoint();
    }

    public void animate(int animationTime) {
        board.startDrawingLineFrom(startingPoint.x, startingPoint.y);
        getValueAnimator(animationTime).start();
    }

    private ValueAnimator getValueAnimator(int animationTime) {
        PropertyValuesHolder x = PropertyValuesHolder.ofInt("x", startingPoint.x, endPoint.x);
        PropertyValuesHolder y = PropertyValuesHolder.ofInt("y", startingPoint.y, endPoint.y);
        PropertyValuesHolder[] values = {x, y};
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(values);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(this);
        return valueAnimator;
    }

    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        board.updateLineDrawingPosition((Integer)animation.getAnimatedValue("x"), (Integer)animation.getAnimatedValue("y"), true);
        boardView.invalidate();
    }
}
