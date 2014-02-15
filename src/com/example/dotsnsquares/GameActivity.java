package com.example.dotsnsquares;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import com.example.dotsnsquares.domain.Board;
import com.example.dotsnsquares.domain.Game;
import com.example.dotsnsquares.domain.GameOptions;
import com.example.dotsnsquares.domain.Player;
import com.example.dotsnsquares.domain.ScoreEntry;
import com.example.dotsnsquares.domain.ScoreState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends Activity implements Game.PlayerChangedEventListener, Game.ScoreChangedEventListener {
    private static final int GAME_RESULT = 1;
    private final String GAME = "game";
    private BoardView boardView;
    private Game game;
    private final TextView[] playerNameViews = new TextView[2];
    private final TextView[] playerScoreViews = new TextView[2];
    private int screenWidth;
    private GameOptions gameOptions;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        gameOptions = GameOptions.fromPreferences(PreferenceManager.getDefaultSharedPreferences(this), getResources());
        screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        boardView = (BoardView) findViewById(R.id.board);
        playerNameViews[0] = (TextView) findViewById(R.id.player1Name);
        playerScoreViews[0] = (TextView) findViewById(R.id.player1Score);
        playerNameViews[1] = (TextView) findViewById(R.id.player2Name);
        playerScoreViews[1] = (TextView) findViewById(R.id.player2Score);
        startNewGame();
    }

    public void restartGame(View view) {
        if(game.canBeInterrupted()) {
            startNewGame();
        } else {
            ConfirmationDialog.show(this, getString(R.string.confirm_restart), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startNewGame();
                }
            });
        }
    }

    private void startNewGame() {
        Board board = new Board(gameOptions.getBoardSize().getSize(), screenWidth);
        List<Player> players = Arrays.asList(gameOptions.getPlayer1(board), gameOptions.getPlayer2(board, boardView));
        Game game = new Game(board, players);
        startGame(game);
    }

    private void startGame(Game game) {
        this.game = game;
        boardView.initializeBoard(game.getBoard());
        game.addPlayerChangedEventListener(this);
        game.addScoreChangedEventListener(this);
        ArrayList<ScoreEntry> scoreEntries = game.getScoreEntries();
        for (int index=0; index < scoreEntries.size(); index++){
            ScoreEntry scoreEntry = scoreEntries.get(index);
            updatePlayerName(index, scoreEntry.getPlayer());
            updateScore(index, scoreEntry.getScore());
            removeHighlightForPlayer(index);
        }
        highlightPlayer(game.getCurrentPlayerIndex());
        game.start();
    }

    private void updateScore(int index, int score) {
        playerScoreViews[index].setText(String.valueOf(score));
    }

    private void updatePlayerName(int playerIndex, Player player) {
        playerNameViews[playerIndex].setText(player.getName());
        playerNameViews[playerIndex].setTextColor(player.getColor());
    }

    private void highlightPlayer(int currentPlayerIndex) {
        TextView playerNameView = playerNameViews[currentPlayerIndex];
        playerNameView.setPaintFlags(playerNameView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void removeHighlightForPlayer(int currentPlayerIndex) {
        TextView playerNameView = playerNameViews[currentPlayerIndex];
        int paintFlags = playerNameView.getPaintFlags();
        if((paintFlags & Paint.UNDERLINE_TEXT_FLAG) != 0){
            playerNameView.setPaintFlags(paintFlags ^ Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(GAME, game);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startGame((Game) savedInstanceState.getSerializable(GAME));
    }

    @Override
    public void onPlayerChange(Game.PlayerChangedEvent event) {
        removeHighlightForPlayer(event.getPreviousPlayerIndex());
        highlightPlayer(event.getCurrentPlayerIndex());
    }

    @Override
    public void onScoreChange(Game.ScoreChangedEvent event) {
        updateScore(event.getCurrentPlayerIndex(), event.getCurrentPlayerScore());
        if(game.isOver()) {
            ScoreState scoreState = game.getScoreState();
            int resultTextId = scoreState == ScoreState.HostLeading ? R.string.you_won : (scoreState == ScoreState.GuestLeading ?  R.string.you_lost : R.string.match_tied);
            int playAgainTextId = scoreState == ScoreState.HostLeading ? R.string.play_again : R.string.try_again;
            Intent intent = new Intent(this, GameResultActivity.class);
            intent.putExtra(GameResultActivity.RESULT_TEXT_ID, resultTextId);
            intent.putExtra(GameResultActivity.PLAY_AGAIN_TEXT_ID, playAgainTextId);
            startActivityForResult(intent, GAME_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GAME_RESULT) {
            if(resultCode == GameResultActivity.PLAY_AGAIN)  {
                startNewGame();
            }
            else if(resultCode == GameResultActivity.SHOW_MENU) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(game.canBeInterrupted()) {
            super.onBackPressed();
        } else {
            ConfirmationDialog.show(this, getString(R.string.confirm_end_game), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    GameActivity.this.finish();
                }
            });
        }

    }
}
