package com.example.dotsnsquares.domain;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import com.example.dotsnsquares.BoardView;
import com.example.dotsnsquares.R;
import com.example.dotsnsquares.bot.BruteForceLineSelectionStrategy;
import com.example.dotsnsquares.bot.LineSelectionStrategy;
import com.example.dotsnsquares.bot.NextStepMaximiserLineSelectionStrategy;

import java.io.Serializable;
import java.util.ArrayList;

public class GameOptions implements Serializable {
    private BoardSize boardSize = new BoardSize(Defaults.BOARD_SIZE);
    private String player1Name = Defaults.PLAYER1_NAME;
    private String player2Name = Defaults.PLAYER2_NAME;
    private int player1Color = Defaults.PLAYER1_COLOR;
    private int player2Color = Defaults.PLAYER2_COLOR;
    private Opponent opponent = Defaults.OPPONENT;
    private BotDrawingSpeed botDrawingSpeed = Defaults.BOT_DRAWING_SPEED;

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

    public Player getPlayer2(Board board, BoardView boardView) {
        if(opponent == Opponent.EasyBot)
            return getBotPlayer("EasyBot", new BruteForceLineSelectionStrategy(), board, boardView);
        if(opponent == Opponent.NormalBot)
            return getBotPlayer("NormalBot", new NextStepMaximiserLineSelectionStrategy(), board, boardView);
        else
            return new HumanPlayer(getPlayer2Name(), player2Color, board);
    }

    private BotPlayer getBotPlayer(String name, LineSelectionStrategy lineSelectionStrategy, Board board, BoardView boardView) {
        return new BotPlayer(name, player2Color, lineSelectionStrategy, board, boardView, botDrawingSpeed);
    }

    public Opponent getOpponent() {
        return opponent;
    }

    public void setOpponent(Opponent opponent) {
        this.opponent = opponent;
    }

    public void saveToPreferences(SharedPreferences preferences, Resources resources) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(resources.getString(R.string.player1_name_preference_key), player1Name);
        edit.putString(resources.getString(R.string.player2_name_preference_key), player2Name);
        edit.putInt(resources.getString(R.string.player1_color_preference_key), player1Color);
        edit.putInt(resources.getString(R.string.player2_color_preference_key), player2Color);
        edit.putInt(resources.getString(R.string.board_size_preference_key), boardSize.getSize());
        edit.putString(resources.getString(R.string.opponent_preference_key), opponent.name());
        edit.putString(resources.getString(R.string.bot_drawing_speed_preference_key), botDrawingSpeed.name());
        edit.commit();
    }

    public static GameOptions fromPreferences(SharedPreferences preferences, Resources resources){
        GameOptions gameOptions = new GameOptions();
        gameOptions.setPlayer1Name(preferences.getString(resources.getString(R.string.player1_name_preference_key), Defaults.PLAYER1_NAME));
        gameOptions.setPlayer2Name(preferences.getString(resources.getString(R.string.player2_name_preference_key), Defaults.PLAYER2_NAME));
        gameOptions.setPlayer1Color(preferences.getInt(resources.getString(R.string.player1_color_preference_key), Defaults.PLAYER1_COLOR));
        gameOptions.setPlayer2Color(preferences.getInt(resources.getString(R.string.player2_color_preference_key), Defaults.PLAYER2_COLOR));
        gameOptions.setBoardSize(new BoardSize(preferences.getInt(resources.getString(R.string.board_size_preference_key), Defaults.BOARD_SIZE)));
        gameOptions.setOpponent(Opponent.valueOf(preferences.getString(resources.getString(R.string.opponent_preference_key), Defaults.OPPONENT.name())));
        gameOptions.setBotDrawingSpeed(BotDrawingSpeed.valueOf(preferences.getString(resources.getString(R.string.bot_drawing_speed_preference_key), Defaults.BOT_DRAWING_SPEED.name())));
        return gameOptions;
    }

    public void setPlayer1Color(int color) {
        player1Color = color;
    }

    public void setPlayer2Color(int color) {
        player2Color = color;
    }

    public BotDrawingSpeed getBotDrawingSpeed() {
        return botDrawingSpeed;
    }

    public void setBotDrawingSpeed(BotDrawingSpeed botDrawingSpeed) {
        this.botDrawingSpeed = botDrawingSpeed;
    }

    public static class Defaults {
        public static final String PLAYER1_NAME = "Player 1";
        public static final String PLAYER2_NAME = "Player 2";
        public static final int PLAYER1_COLOR = Color.parseColor("#D7E6B1");
        public static final int PLAYER2_COLOR = Color.parseColor("#0AC9B0");
        public static final int BOARD_SIZE = 3;
        public static final Opponent OPPONENT = Opponent.NormalBot;
        public static final BotDrawingSpeed BOT_DRAWING_SPEED = BotDrawingSpeed.Normal;
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

    public enum BotDrawingSpeed {
        Slow(2000),
        Normal(1000),
        Fast(500),
        Bolt(0);

        private int animationTime;

        BotDrawingSpeed(int animationTime) {
            this.animationTime = animationTime;
        }

        public int getAnimationTime() {
            return animationTime;
        }

        public static String[] getNameValues() {
            GameOptions.BotDrawingSpeed[] botDrawingSpeeds = GameOptions.BotDrawingSpeed.values();
            ArrayList<String> values = new ArrayList<String>();
            for (GameOptions.BotDrawingSpeed botDrawingSpeed : botDrawingSpeeds) {
                values.add(botDrawingSpeed.name());
            }
            return values.toArray(new String[values.size()]);
        }
    }
}
