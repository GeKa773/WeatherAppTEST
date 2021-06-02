package com.gekaradchenko.testforwork.weatherapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import com.gekaradchenko.testforwork.weatherapptest.units.Unit;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences nightSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nightSharedPreferences = getSharedPreferences(getString(R.string.shared_night), MODE_PRIVATE);
        Unit.isNightMode(nightSharedPreferences.getBoolean(getString(R.string.shared_night_mode), false));
        loadLocal();

    }

    private void setLocate(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        this.getResources().updateConfiguration(configuration, this.getResources().getDisplayMetrics());
    }

    private void loadLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_language), Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(getString(R.string.shared_language), "en");
        setLocate(language);
    }
}