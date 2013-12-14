package com.example.dotsnsquares.domain;

import android.content.SharedPreferences;
import android.graphics.Color;
import com.example.dotsnsquares.bot.BruteForceLineSelectionStrategy;
import com.example.dotsnsquares.bot.NextStepMaximiserLineSelectionStrategy;

import java.io.Serializable;

public class GameOptions implements Serializable {
    private BoardSize boardSize = new BoardSize(Defaults.BOARD_SIZE);
    private String player1Name = Defaults.PLAYER1_NAME;
    private String player2Name = Defaults.PLAYER2_NAME;
    private int player1Color = Defaults.PLAYER1_COLOR;
    private int player2Color = Defaults.PLAYER2_COLOR;
    private Opponent opponent = Defaults.OPPONENT;

    public GameOptions() {
    }

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
            return new BotPlayer("EasyBot", player2Color, new BruteForceLineSelectionStrategy());
        if(opponent == Opponent.NormalBot)
            return new BotPlayer("NormalBot", player2Color, new NextStepMaximiserLineSelectionStrategy());
        else
            return new HumanPlayer(getPlayer2Name(), player2Color, board);
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public void saveToPreferences(SharedPreferences preferences) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(PreferenceNames.PLAYER_1_NAME, player1Name);
        edit.putString(PreferenceNames.PLAYER_2_NAME, player2Name);
        edit.putInt(PreferenceNames.PLAYER_1_COLOR, player1Color);
        edit.putInt(PreferenceNames.PLAYER_2_COLOR, player2Color);
        edit.putInt(PreferenceNames.BOARD_SIZE, boardSize.getSize());
        edit.putString(PreferenceNames.OPPONENT, opponent.name());
        edit.commit();
    }

    public static GameOptions fromPreferences(SharedPreferences preferences){
        GameOptions gameOptions = new GameOptions();
        gameOptions.setPlayer1Name(preferences.getString(PreferenceNames.PLAYER_1_NAME, Defaults.PLAYER1_NAME));
        gameOptions.setPlayer2Name(preferences.getString(PreferenceNames.PLAYER_2_NAME, Defaults.PLAYER2_NAME));
        gameOptions.setPlayer1Color(preferences.getInt(PreferenceNames.PLAYER_1_COLOR, Defaults.PLAYER1_COLOR));
        gameOptions.setPlayer2Color(preferences.getInt(PreferenceNames.PLAYER_2_COLOR, Defaults.PLAYER2_COLOR));
        gameOptions.setBoardSize(new BoardSize(preferences.getInt(PreferenceNames.BOARD_SIZE, Defaults.BOARD_SIZE)));
        gameOptions.setOpponent(Opponent.valueOf(preferences.getString(PreferenceNames.OPPONENT, Defaults.OPPONENT.name())));
        return gameOptions;
    }

    public void setPlayer1Color(int color) {
        player1Color = color;
    }

    public void setPlayer2Color(int color) {
        player2Color = color;
    }

    public static class PreferenceNames {
        public static final String PLAYER_1_NAME = "player1Name";
        public static final String PLAYER_2_NAME = "player2Name";
        public static final String PLAYER_1_COLOR = "player1Color";
        public static final String PLAYER_2_COLOR = "player2Color";
        public static final String BOARD_SIZE = "boardSize";
        public static final String OPPONENT = "opponent";
    }

    public static class Defaults {
        public static final String PLAYER1_NAME = "Player 1";
        public static final String PLAYER2_NAME = "Player 2";
        public static final int PLAYER1_COLOR = Color.parseColor("#D7E6B1");
        public static final int PLAYER2_COLOR = Color.parseColor("#0AC9B0");
        public static final int BOARD_SIZE = 3;
        private static final Opponent OPPONENT = Opponent.NormalBot;
    }

    public enum Opponent {
        HumanOnSameDevice("Human"),
        EasyBot("Bot (easy)"),
        NormalBot("Bot (normal)");

        private String title;

        Opponent(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
