package com.example.dotsnsquares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.dotsnsquares.domain.GameOptions;

public class GameOptionsActivity extends Activity {
    private GameOptions gameOptions;
    private EditText player1NameText;
    private EditText player2NameText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_options);
        gameOptions = (GameOptions) getIntent().getSerializableExtra(MainMenuActivity.GAME_OPTIONS);
        player1NameText = (EditText)findViewById(R.id.player1NameText);
        player2NameText = (EditText)findViewById(R.id.player2NameText);
        player1NameText.setText(gameOptions.getPlayer1Name());
        player2NameText.setText(gameOptions.getPlayer2Name());
    }

    public void done(View view) {
        Intent data = new Intent();
        data.putExtra(MainMenuActivity.GAME_OPTIONS, gameOptions);
        gameOptions.setPlayer1Name(getTextOrDefault(player1NameText, GameOptions.DEFAULT_PLAYER1_NAME));
        gameOptions.setPlayer2Name(getTextOrDefault(player2NameText, GameOptions.DEFAULT_PLAYER2_NAME));
        setResult(MainMenuActivity.GAME_OPTIONS_OK, data);
        finish();
    }

    private String getTextOrDefault(EditText player1NameText, String defaultText) {
        String textValue = player1NameText.getText().toString();
        return textValue.isEmpty() ? defaultText : textValue;
    }
}