package com.gb.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SwitchCompat themeSelector;
    public final static String KEY = "SETTINGS_KEY";
    public final static int THEME_SELECTOR_KEY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences prefs = getSharedPreferences("safe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        themeSelector = findViewById(R.id.theme_selector);
        themeSelector.setChecked(prefs.getBoolean("value", false));
        Intent data = new Intent();
        themeSelector.setOnCheckedChangeListener((compoundButton, b) -> {
            data.putExtra(KEY, b);
            if (b) {
                Toast.makeText(SettingsActivity.this, R.string.toast_theme_dark, Toast.LENGTH_SHORT).show();
                editor.putBoolean("value", true);
            } else {
                Toast.makeText(SettingsActivity.this, R.string.toast_theme_light, Toast.LENGTH_SHORT).show();
                editor.putBoolean("value", false);
            }
            editor.apply();
            setResult(Activity.RESULT_OK, data);
        });


    }
}