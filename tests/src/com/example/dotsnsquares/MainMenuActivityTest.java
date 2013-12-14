package com.example.dotsnsquares;

import android.os.Parcel;
import android.test.ActivityInstrumentationTestCase2;
import com.example.dotsnsquares.domain.Board;
import com.example.dotsnsquares.domain.Game;
import com.example.dotsnsquares.domain.HumanPlayer;
import com.example.dotsnsquares.domain.Player;
import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.dotsnsquares.MainMenuActivityTest \
 * com.example.dotsnsquares.tests/android.test.InstrumentationTestRunner
 */
public class MainMenuActivityTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

    public MainMenuActivityTest() {
        super("com.example.dotsnsquares", MainMenuActivity.class);
    }

    public void testSerializingGame() throws Exception {
        Board board = new Board(1, 1);
        HumanPlayer humanPlayer = new HumanPlayer("", 1, board);
        new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(new Game(board, Arrays.<Player>asList(humanPlayer)));
    }
}
