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
    private final List<GameOptions.Opponent> opponents = Arrays.asList(GameOptions.Opponent.values());
    private GameOptions gameOptions;
    private Spinner opponentsSinner;
    private Spinner boardSizeSpinner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        gameOptions = GameOptions.fromPreferences(getPreferences(), getResources());
        opponentsSinner = (Spinner)findViewById(R.id.opponent_options);
        boardSizeSpinner = (Spinner)findViewById(R.id.board_size_options);
        configureBoardSizeOptions();
        configureOpponentOptions();
        initViewFromGameOptions();
    }

    private void initViewFromGameOptions() {
        opponentsSinner.setSelection(opponents.indexOf(gameOptions.getOpponent()));
        boardSizeSpinner.setSelection(boardSizes.indexOf(gameOptions.getBoardSize()));
    }

    private void configureOpponentOptions() {
        ArrayAdapter adapter = new ArrayAdapter<GameOptions.Opponent>(this, R.layout.opponent_selected_menu_option, android.R.id.text1, opponents);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        opponentsSinner.setAdapter(adapter);
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
        ArrayAdapter adapter = new ArrayAdapter<BoardSize>(this, R.layout.menu_option, boardSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        boardSizeSpinner.setAdapter(adapter);
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
    protected void onResume() {
        super.onResume();
        gameOptions = GameOptions.fromPreferences(getPreferences(), getResources());
        initViewFromGameOptions();
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