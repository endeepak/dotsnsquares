package com.endeepak.dotsnsquares;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.endeepak.dotsnsquares.domain.BoardSize;
import com.endeepak.dotsnsquares.domain.GameOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenuActivity extends Activity {
    public static final int PREFERENCES = 1;
    private final ArrayList<BoardSize> boardSizes = BoardSize.fromSizes(3, 4, 5, 6);
    private GameOptions gameOptions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameOptions = GameOptions.fromPreferences(getPreferences(), getResources());
        setContentView(R.layout.main_menu);
        configureBoardSizeOptions();
        configureOpponentOptions();
    }

    private void configureOpponentOptions() {
        Spinner opponentsSinner = (Spinner)findViewById(R.id.opponent_options);
        final List<GameOptions.Opponent> opponents = Arrays.asList(GameOptions.Opponent.values());
        ArrayAdapter adapter = new ArrayAdapter<GameOptions.Opponent>(this, R.layout.opponent_selected_menu_option, android.R.id.text1, opponents);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        opponentsSinner.setAdapter(adapter);
        opponentsSinner.setSelection(opponents.indexOf(gameOptions.getOpponent()));
        opponentsSinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameOptions.setOpponent(opponents.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void configureBoardSizeOptions() {
        Spinner boardSizeSpinner = (Spinner)findViewById(R.id.board_size_options);
        ArrayAdapter adapter = new ArrayAdapter<BoardSize>(this, R.layout.menu_option, boardSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        boardSizeSpinner.setAdapter(adapter);
        boardSizeSpinner.setSelection(boardSizes.indexOf(gameOptions.getBoardSize()));
        boardSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameOptions.setBoardSize(boardSizes.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void play(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        gameOptions.saveToPreferences(getPreferences(), getResources());
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        gameOptions.saveToPreferences(getPreferences(), getResources());
        startActivityForResult(intent, PREFERENCES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PREFERENCES)  {
            gameOptions = GameOptions.fromPreferences(getPreferences(), getResources());
        }
    }

    public void exit(View view) {
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameOptions.saveToPreferences(getPreferences(), getResources());
    }

    private SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onBackPressed() {
        ConfirmationDialog.show(this, getString(R.string.confirm_exit), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainMenuActivity.this.finish();
            }
        });
    }
}