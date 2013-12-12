package com.example.dotsnsquares.domain;

import com.example.dotsnsquares.bot.LineSelectionStrategy;

public class BotPlayer implements Player {
    private final LineSelectionStrategy lineSelectionStrategy;
    private String name;
    private int color;

    public BotPlayer(String name, int color, LineSelectionStrategy lineSelectionStrategy) {
        this.name = name;
        this.color = color;
        this.lineSelectionStrategy = lineSelectionStrategy;
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
    public void play(String token, MoveDecidedEventListener moveDecidedEventListener, BoardState boardState) {
        moveDecidedEventListener.onMoveDecided(new MoveDecidedEvent(token, this, lineSelectionStrategy.getLine(boardState)));
    }
}
