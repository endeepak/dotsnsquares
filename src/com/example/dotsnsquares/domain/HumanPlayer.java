package com.example.dotsnsquares.domain;

public class HumanPlayer implements Player {
    private String name;
    private final int color;
    private Board board;

    public HumanPlayer(String name, int color, Board board) {
        this.name = name;
        this.color = color;
        this.board = board;
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
        final Player player = this;
        board.addLineDrawingListener(new Board.LineDrawnEventListener() {
            @Override
            public void onLineDrawn(Board.LineDrawnEvent event) {
                board.removeLineDrawingListener(this);
                moveDecidedEventListener.onMoveDecided(new MoveDecidedEvent(token, player, event.getLine()));
            }
        });
    }

}
