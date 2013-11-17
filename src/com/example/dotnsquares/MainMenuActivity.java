package com.example.dotnsquares;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.example.dotnsquares.domain.BoardSize;
import com.example.dotnsquares.domain.GameOptions;

import java.util.ArrayList;

public class MainMenuActivity extends Activity {
    public static final String GAME_OPTIONS = "game_options";
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

    public void exit(View view) {
        finish();
    }
}