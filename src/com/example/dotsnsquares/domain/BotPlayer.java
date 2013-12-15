package com.example.dotsnsquares.domain;

import com.example.dotsnsquares.BoardView;
import com.example.dotsnsquares.LinePathAnimator;
import com.example.dotsnsquares.bot.LineSelectionStrategy;

public class BotPlayer implements Player {
    private final LineSelectionStrategy lineSelectionStrategy;
    private final Board board;
    transient private final BoardView boardView;
    private String name;
    private int color;

    public BotPlayer(String name, int color, LineSelectionStrategy lineSelectionStrategy, Board board, BoardView boardView) {
        this.name = name;
        this.color = color;
        this.lineSelectionStrategy = lineSelectionStrategy;
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
    public void play(final String token, final MoveDecidedEventListener moveDecidedEventListener, BoardState boardState) {
        final Line line = lineSelectionStrategy.getLine(boardState);
        final BotPlayer botPlayer = this;
        new LinePathAnimator(boardView, board, line, new LinePathAnimator.LineAnimationCompletedListener() {
            @Override
            public void onLineAnimationEnded() {
                moveDecidedEventListener.onMoveDecided(new MoveDecidedEvent(token, botPlayer, line));
            }
        }).animate();
    }
}
