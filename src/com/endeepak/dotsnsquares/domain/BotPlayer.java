package com.endeepak.dotsnsquares.domain;

import com.endeepak.dotsnsquares.BoardView;
import com.endeepak.dotsnsquares.LinePathAnimator;
import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;

public class BotPlayer implements Player {
    private final LineSelectionStrategy lineSelectionStrategy;
    private final Board board;
    transient private final BoardView boardView;
    private GameOptions.BotDrawingSpeed botDrawingSpeed;
    private String name;
    private int color;

    public BotPlayer(String name, int color, LineSelectionStrategy lineSelectionStrategy, Board board, BoardView boardView, GameOptions.BotDrawingSpeed botDrawingSpeed) {
        this.name = name;
        this.color = color;
        this.lineSelectionStrategy = lineSelectionStrategy;
        this.board = board;
        this.boardView = boardView;
        this.botDrawingSpeed = botDrawingSpeed;
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
        }).animate(botDrawingSpeed.getAnimationTime());
    }
}
