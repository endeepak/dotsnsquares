package com.example.dotsnsquares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.dotsnsquares.domain.GameOptions;

import java.util.Arrays;
import java.util.List;

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
        configureBotSpeedOptions();
    }

    public void done(View view) {
        Intent data = new Intent();
        data.putExtra(MainMenuActivity.GAME_OPTIONS, gameOptions);
        gameOptions.setPlayer1Name(getTextOrDefault(player1NameText, gameOptions.getPlayer1Name()));
        gameOptions.setPlayer2Name(getTextOrDefault(player2NameText, gameOptions.getPlayer2Name()));
        setResult(MainMenuActivity.GAME_OPTIONS_OK, data);
        finish();
    }

    private void configureBotSpeedOptions() {
        Spinner opponentsSinner = (Spinner)findViewById(R.id.bot_speed_options);
        final List<GameOptions.BotDrawingSpeed> botDrawingSpeeds = Arrays.asList(GameOptions.BotDrawingSpeed.values());
        ArrayAdapter adapter = new ArrayAdapter<GameOptions.BotDrawingSpeed>(this, R.layout.bot_speed_selected_menu_option, android.R.id.text1, botDrawingSpeeds);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        opponentsSinner.setAdapter(adapter);
        opponentsSinner.setSelection(botDrawingSpeeds.indexOf(gameOptions.getBotDrawingSpeed()));
        opponentsSinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameOptions.setBotDrawingSpeed(botDrawingSpeeds.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private String getTextOrDefault(EditText player1NameText, String defaultText) {
        String textValue = player1NameText.getText().toString();
        return textValue.isEmpty() ? defaultText : textValue;
    }
}