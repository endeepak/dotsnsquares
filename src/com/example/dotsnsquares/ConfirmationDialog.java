package com.example.dotsnsquares;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmationDialog {
    public static void show(Context context, String message, DialogInterface.OnClickListener yesButtonListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", yesButtonListener)
                .setNegativeButton("No", null)
                .show();
    }
}