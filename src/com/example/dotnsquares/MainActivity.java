package com.example.dotnsquares;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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
    private int screenWidth;
    private ImageButton restartButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        boardView = (BoardView) findViewById(R.id.board);
        playerNameViews[0] = (TextView) findViewById(R.id.player1Name);
        playerScoreViews[0] = (TextView) findViewById(R.id.player1Score);
        playerNameViews[1] = (TextView) findViewById(R.id.player2Name);
        playerScoreViews[1] = (TextView) findViewById(R.id.player2Score);
        restartButton = (ImageButton) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });
        startNewGame();
    }

    private void restartGame() {
        if(game.isOver()) {
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
        startGame(new Game(new Board(5, screenWidth)));
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
            Toast toast = Toast.makeText(this, "Game over!!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        ConfirmationDialog.show(this, getString(R.string.confirm_exit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        });
    }

}
