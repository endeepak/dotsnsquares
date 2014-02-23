package com.endeepak.dotsnsquares.domain;

import android.view.MotionEvent;
import android.view.View;
import com.endeepak.dotsnsquares.BoardView;

public class HumanPlayer  implements View.OnTouchListener, Player {
    private String name;
    private final int color;
    private Board board;
    transient private BoardView boardView;

    public HumanPlayer(String name, int color, Board board, BoardView boardView) {
        this.name = name;
        this.color = color;
        this.board = board;
        this.boardView = boardView;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void play(BoardState boardState) {
        boardView.setOnTouchListener(this);
    }

    @Override
    public void stop() {
        boardView.setOnTouchListener(null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                board.startDrawingLineFrom(x, y);
                boardView.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                board.updateLineDrawingPosition(x, y, false);
                boardView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if(board.isLineDrawingStarted()) {
                    board.resetLineDrawing();
                    boardView.invalidate();
                }
                break;
        }
        return true;
    }
}
