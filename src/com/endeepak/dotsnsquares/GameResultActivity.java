package com.endeepak.dotsnsquares;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GameResultActivity extends Activity {
    public static final int PLAY_AGAIN = 1;
    public static final int SHOW_MENU = 2;
    public final static String RESULT_TEXT_ID = "RESULT_TEXT_ID";
    public static final String PLAY_AGAIN_TEXT_ID = "PLAY_AGAIN_TEXT_ID";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_result);
        TextView resultMessageTextView = (TextView)findViewById(R.id.resultMessageTextView);
        resultMessageTextView.setText(getIntent().getIntExtra(RESULT_TEXT_ID, R.string.app_name));
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setText(getIntent().getIntExtra(PLAY_AGAIN_TEXT_ID, R.string.play_again));
    }

    public void playAgain(View view) {
        setResult(PLAY_AGAIN);
        finish();
    }

    public void showMenu(View view) {
        setResult(SHOW_MENU);
        finish();
    }
}