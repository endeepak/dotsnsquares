package com.example.dotsnsquares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.dotsnsquares.domain.BoardSize;
import com.example.dotsnsquares.domain.GameOptions;

import java.util.ArrayList;

public class MainMenuActivity extends Activity {
    public static final String GAME_OPTIONS = "game_options";
    public static final int GAME_OPTIONS_OK = 1;
    private final ArrayList<BoardSize> boardSizes = BoardSize.fromSizes(3, 4, 5, 6);
    private GameOptions gameOptions = new GameOptions(boardSizes.get(2));

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
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
        intent.putExtra(GAME_OPTIONS, gameOptions);
        startActivity(intent);
    }

    public void captureOptions(View view) {
        Intent intent = new Intent(this, GameOptionsActivity.class);
        intent.putExtra(GAME_OPTIONS, gameOptions);
        startActivityForResult(intent, GAME_OPTIONS_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GAME_OPTIONS_OK) {
            gameOptions = (GameOptions) data.getSerializableExtra(MainMenuActivity.GAME_OPTIONS);
        }
    }

    public void exit(View view) {
        finish();
    }
}