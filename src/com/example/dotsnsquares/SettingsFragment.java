package com.example.dotsnsquares;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.example.dotsnsquares.domain.GameOptions;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.settings);
        bindPreferences();
        configureBotDrawingSpeed();
    }

    private void configureBotDrawingSpeed() {
        ListPreference botDrawingSpeed = (ListPreference) findPreference(getResources().getString(R.string.bot_drawing_speed_preference_key));
        botDrawingSpeed.setEntries(GameOptions.BotDrawingSpeed.getNameValues());
        botDrawingSpeed.setEntryValues(GameOptions.BotDrawingSpeed.getNameValues());
    }

    private void bindPreferences() {
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        for (String key : sharedPreferences.getAll().keySet()) {
            setTitle(key);
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        setTitle(key);
    }

    private void setTitle(String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference editTextPreference = (EditTextPreference) pref;
            pref.setTitle(editTextPreference.getText());
        }
        else if (pref instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) pref;
            pref.setTitle(listPreference.getValue());
        }
    }

}
