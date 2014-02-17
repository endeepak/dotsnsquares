package com.endeepak.dotsnsquares;

import android.test.ActivityInstrumentationTestCase2;
import com.endeepak.dotsnsquares.domain.Board;
import com.endeepak.dotsnsquares.domain.Game;
import com.endeepak.dotsnsquares.domain.HumanPlayer;
import com.endeepak.dotsnsquares.domain.Player;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.endeepak.dotsnsquares.MainMenuActivityTest \
 * com.endeepak.dotsnsquares.tests/android.test.InstrumentationTestRunner
 */
public class MainMenuActivityTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

    public MainMenuActivityTest() {
        super("com.endeepak.dotsnsquares", MainMenuActivity.class);
    }

    public void testSerializingGame() throws Exception {
        Board board = new Board(1, 1);
        HumanPlayer humanPlayer = new HumanPlayer("", 1, board);
        new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(new Game(board, Arrays.<Player>asList(humanPlayer)));
    }
}
