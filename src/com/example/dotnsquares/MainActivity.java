package com.example.dotnsquares;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;
import com.example.dotnsquares.domain.Board;
import com.example.dotnsquares.domain.Game;
import com.example.dotnsquares.domain.Player;
import com.example.dotnsquares.domain.ScoreEntry;

import java.util.ArrayList;

public class MainActivity extends Activity implements Game.PlayerChangedEventListener, Game.ScoreChangedEventListener {
    private final String GAME = "game";
    private BoardView boardView;
    private Game game;
    private final TextView[] playerNameViews = new TextView[2];
    private final TextView[] playerScoreViews = new TextView[2];

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        int screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        boardView = (BoardView) findViewById(R.id.board);
        playerNameViews[0] = (TextView) findViewById(R.id.player1Name);
        playerScoreViews[0] = (TextView) findViewById(R.id.player1Score);
        playerNameViews[1] = (TextView) findViewById(R.id.player2Name);
        playerScoreViews[1] = (TextView) findViewById(R.id.player2Score);
        initialize(new Game(new Board(5, screenWidth)));
    }

    private void initialize(Game game) {
        this.game = game;
        boardView.initializeBoard(game.getBoard());
        game.addPlayerChangedEventListener(this);
        game.addScoreChangedEventListener(this);
        ArrayList<ScoreEntry> scoreEntries = game.getScoreEntries();
        for (int index=0; index < scoreEntries.size(); index++){
            ScoreEntry scoreEntry = scoreEntries.get(index);
            updatePlayerName(index, scoreEntry.getPlayer());
            updateScore(index, scoreEntry.getScore());
        }
        highlightPlayer(game.getCurrentPlayerIndex());
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
        playerNameView.setPaintFlags(playerNameView.getPaintFlags() ^ Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(GAME, game);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initialize((Game) savedInstanceState.getSerializable(GAME));
    }

    @Override
    public void onPlayerChange(Game.PlayerChangedEvent event) {
        removeHighlightForPlayer(event.getPreviousPlayerIndex());
        highlightPlayer(event.getCurrentPlayerIndex());
    }

    @Override
    public void onScoreChange(Game.ScoreChangedEvent event) {
        updateScore(event.getCurrentPlayerIndex(), event.getCurrentPlayerScore());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
