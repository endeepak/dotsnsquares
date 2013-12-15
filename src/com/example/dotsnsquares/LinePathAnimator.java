package com.example.dotsnsquares;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import com.example.dotsnsquares.domain.Board;
import com.example.dotsnsquares.domain.Line;
import com.example.dotsnsquares.domain.LinePath;

public class LinePathAnimator implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private final BoardView boardView;
    private final Board board;
    private LineAnimationCompletedListener lineAnimationCompletedListener;
    private final LinePath linePath;

    public LinePathAnimator(BoardView boardView, Board board, Line line, LineAnimationCompletedListener lineAnimationCompletedListener) {
        this.boardView = boardView;
        this.board = board;
        this.lineAnimationCompletedListener = lineAnimationCompletedListener;
        this.linePath = board.getLinePath(line);
    }

    public void animate(int animationTime) {
        getValueAnimator(animationTime).start();
    }

    private ValueAnimator getValueAnimator(int animationTime) {
        PropertyValuesHolder x = PropertyValuesHolder.ofInt("x", linePath.getStartingPoint().x, linePath.getEndPoint().x);
        PropertyValuesHolder y = PropertyValuesHolder.ofInt("y", linePath.getStartingPoint().y, linePath.getEndPoint().y);
        PropertyValuesHolder[] values = {x, y};
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(values);
        valueAnimator.setDuration(animationTime);
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
        return valueAnimator;
    }

    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        int x = (Integer)animation.getAnimatedValue("x");
        int y = (Integer)animation.getAnimatedValue("y");
        board.startDrawingLineFrom(linePath.getStartingPoint().x, linePath.getStartingPoint().y);
        board.updateLineDrawingPosition(x, y);
        boardView.invalidate();
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        lineAnimationCompletedListener.onLineAnimationEnded();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    public static interface LineAnimationCompletedListener {
        void onLineAnimationEnded();
    }
}
