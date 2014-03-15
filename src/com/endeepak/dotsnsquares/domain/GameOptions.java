package com.endeepak.dotsnsquares.domain;

import android.content.SharedPreferences;
import android.content.res.Resources;
import com.endeepak.dotsnsquares.BoardView;
import com.endeepak.dotsnsquares.R;
import com.endeepak.dotsnsquares.bot.LineSelectionStrategy;
import com.endeepak.dotsnsquares.bot.LineSelectionStrategyFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOptions implements Serializable {
    private BoardSize boardSize;
    private String player1Name;
    private String player2Name;
    private int player1Color;
    private int player2Color;
    private Opponent opponent;
    private BotDrawingSpeed botDrawingSpeed;
    private int foregroundColor;

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

    public List<Player> getPlayers(Board board, BoardView boardView) {
        return Arrays.asList(getPlayer1(board, boardView), getPlayer2(board, boardView));
    }

    private Player getPlayer1(Board board, BoardView boardView) {
        return new HumanPlayer(getPlayer1Name(), player1Color, board, boardView);
    }

    private Player getPlayer2(Board board, BoardView boardView) {
        if(opponent == Opponent.EasyBot)
            return getBotPlayer(opponent.name(), LineSelectionStrategyFactory.easy(), board, boardView);
        if(opponent == Opponent.NormalBot)
            return getBotPlayer(opponent.name(), LineSelectionStrategyFactory.normal(), board, boardView);
        if(opponent == Opponent.HardBot)
            return getBotPlayer(opponent.name(), LineSelectionStrategyFactory.hard(), board, boardView);
        if(opponent == Opponent.ProBot)
            return getBotPlayer(opponent.name(), LineSelectionStrategyFactory.pro(), board, boardView);
        else
            return new HumanPlayer(getPlayer2Name(), player2Color, board, boardView);
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
        edit.putInt(resources.getString(R.string.foreground_color_preference_key), foregroundColor);
        edit.putString(resources.getString(R.string.player1_name_preference_key), player1Name);
        edit.putString(resources.getString(R.string.player2_name_preference_key), player2Name);
        edit.putInt(resources.getString(R.string.player1_color_preference_key), player1Color);
        edit.putInt(resources.getString(R.string.player2_color_preference_key), player2Color);
        edit.putInt(resources.getString(R.string.board_size_preference_key), boardSize.getSize());
        edit.putString(resources.getString(R.string.opponent_preference_key), opponent.name());
        edit.putString(resources.getString(R.string.bot_drawing_speed_preference_key), botDrawingSpeed.name());
        edit.commit();
    }

    public static GameOptions fromPreferences(SharedPreferences preferences, Resources resources) {
        GameOptions gameOptions = new GameOptions();
        gameOptions.setForegroundColor(preferences.getInt(resources.getString(R.string.foreground_color_preference_key), resources.getColor(R.color.foreground_default)));
        gameOptions.setPlayer1Name(preferences.getString(resources.getString(R.string.player1_name_preference_key), Defaults.PLAYER1_NAME));
        gameOptions.setPlayer2Name(preferences.getString(resources.getString(R.string.player2_name_preference_key), Defaults.PLAYER2_NAME));
        gameOptions.setPlayer1Color(preferences.getInt(resources.getString(R.string.player1_color_preference_key), resources.getColor(R.color.player_1_default)));
        gameOptions.setPlayer2Color(preferences.getInt(resources.getString(R.string.player2_color_preference_key), resources.getColor(R.color.player_2_default)));
        gameOptions.setBoardSize(new BoardSize(preferences.getInt(resources.getString(R.string.board_size_preference_key), Defaults.BOARD_SIZE)));
        gameOptions.setOpponent(Opponent.valueOf(preferences.getString(resources.getString(R.string.opponent_preference_key), Defaults.OPPONENT.name())));
        gameOptions.setBotDrawingSpeed(BotDrawingSpeed.valueOf(preferences.getString(resources.getString(R.string.bot_drawing_speed_preference_key), Defaults.BOT_DRAWING_SPEED.name())));
        return gameOptions;
    }

    public static void initPreferences(SharedPreferences preferences, Resources resources){
        GameOptions gameOptions = GameOptions.fromPreferences(preferences, resources);
        gameOptions.saveToPreferences(preferences, resources);
    }

    public static void resetPreferences(SharedPreferences preferences, Resources resources){
        preferences.edit().clear().commit();
        initPreferences(preferences, resources);
    }

    public void setPlayer1Color(int color) {
        player1Color = color;
    }

    public void setPlayer2Color(int color) {
        player2Color = color;
    }

    public void setBotDrawingSpeed(BotDrawingSpeed botDrawingSpeed) {
        this.botDrawingSpeed = botDrawingSpeed;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public static class Defaults {
        public static final String PLAYER1_NAME = "Player 1";
        public static final String PLAYER2_NAME = "Player 2";
        public static final int BOARD_SIZE = 3;
        public static final Opponent OPPONENT = Opponent.NormalBot;
        public static final BotDrawingSpeed BOT_DRAWING_SPEED = BotDrawingSpeed.Normal;
    }

    public enum Opponent {
        HumanOnSameDevice("human", false),
        EasyBot("bot (easy)", true),
        NormalBot("bot (normal)", true),
        HardBot("bot (hard)", true),
        ProBot("bot (pro)", true);

        private String title;
        private boolean bot;

        Opponent(String title, boolean isBot) {
            this.title = title;
            bot = isBot;
        }

        @Override
        public String toString() {
            return title;
        }

        public boolean isBot() {
            return bot;
        }
    }

    public enum BotDrawingSpeed {
        Slow(2000),
        Normal(1000),
        Fast(500),
        Bolt(1);

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
