package com.example.dotsnsquares.domain;

import android.graphics.Color;

import java.io.Serializable;

public class GameOptions implements Serializable {
    public static final String DEFAULT_PLAYER1_NAME = "Player 1";
    public static final String DEFAULT_PLAYER2_NAME = "Player 2";
    public static final int DEFAULT_PLAYER1_COLOR = Color.parseColor("#D7E6B1");
    public static final int DEFAULT_PLAYER2_COLOR = Color.parseColor("#0AC9B0");
    private BoardSize boardSize;
    private String player1Name = DEFAULT_PLAYER1_NAME;
    private String player2Name = DEFAULT_PLAYER2_NAME;
    private int player1Color = DEFAULT_PLAYER1_COLOR;
    private int player2Color = DEFAULT_PLAYER2_COLOR;
    private Opponent opponent = Opponent.EasyBot;

    public GameOptions(BoardSize boardSize) {
        this.boardSize = boardSize;
    }

    public void setBoardSize(BoardSize boardSize) {
        this.boardSize = boardSize;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public Player getPlayer1(Board board) {
        return new HumanPlayer(getPlayer1Name(), player1Color, board);
    }

    public Player getPlayer2(Board board) {
        if(opponent == Opponent.EasyBot)
            return new BotPlayer("EasyBot", player2Color);
        else
            return new HumanPlayer(getPlayer2Name(), player2Color, board);
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public enum Opponent {
        HumanOnSameDevice("Human"),
        EasyBot("Bot (easy)");
        private String name;

        Opponent(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
