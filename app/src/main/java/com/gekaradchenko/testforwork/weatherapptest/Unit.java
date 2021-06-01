package com.gekaradchenko.testforwork.weatherapptest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Locale;

public class Unit {

    public static void isNightMode(boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static int isWeatherIcon(int id) {
        if (id < 300) return R.drawable.rain_with_thunderstorm;
        else if (id < 504) return R.drawable.rain;
        else if (id < 600) return R.drawable.rain_with_snow;
        else if (id < 701) return R.drawable.snow;
        else if (id < 800) return R.drawable.sun_and_clouds;
        else if (id == 800) return R.drawable.sun;
        else return R.drawable.clounds;


    }
}
