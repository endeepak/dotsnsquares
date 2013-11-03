package com.example.dotnsquares;

import android.app.Activity;
import android.os.Bundle;
import com.example.dotnsquares.domain.Board;

public class MainActivity extends Activity {
    private final String BOARD = "board";
    private BoardView boardView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);
        boardView = (BoardView) findViewById(R.id.board);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(BOARD, boardView.getBoard());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boardView.setBoard((Board)savedInstanceState.getSerializable(BOARD));
    }
}
