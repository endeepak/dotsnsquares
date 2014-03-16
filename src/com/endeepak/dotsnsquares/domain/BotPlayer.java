package com.endeepak.dotsnsquares.domain;

import com.endeepak.dotsnsquares.BoardView;
import com.endeepak.dotsnsquares.LinePathAnimator;
import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;

public class BotPlayer implements Player {
    private final LineSelectionStrategy lineSelectionStrategy;
    private final Board board;
    transient private final BoardView boardView;
    private BotDrawingSpeed botDrawingSpeed;
    private String name;
    private int color;

    public BotPlayer(String name, int color, LineSelectionStrategy lineSelectionStrategy, Board board, BoardView boardView, BotDrawingSpeed botDrawingSpeed) {
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
    public void play(BoardState boardState) {
        Line line = lineSelectionStrategy.getLine(boardState);
        new LinePathAnimator(boardView, board, line).animate(botDrawingSpeed.getAnimationTime());
    }

    @Override
    public void stop() {
    }
}
