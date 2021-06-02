package com.gekaradchenko.testforwork.weatherapptest.units;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.gekaradchenko.testforwork.weatherapptest.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Unit {

    public static void isNightMode(boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static int isWeatherIcon(int id, int timeZone, int hour) {

        Date date = new Date();
        long localTime = date.getTime();
        long localOffset = date.getTimezoneOffset() * 60000;
        long utc = localTime + localOffset;
        long x = utc + (1000 * timeZone);
        Date timeZoneDate = new Date(x);

        Calendar c = Calendar.getInstance();
        c.setTime(timeZoneDate);
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        timeOfDay += hour;
        if (timeOfDay > 24) {
            timeOfDay -= 24;
            if (timeOfDay > 24) {
                timeOfDay -= 24;
            }
        }

        if (timeOfDay >= 6 && timeOfDay < 20) {

            if (id < 300) return R.drawable.rain_with_thunderstorm;
            else if (id < 504) return R.drawable.rain;
            else if (id < 600) return R.drawable.rain_with_snow;
            else if (id < 701) return R.drawable.snow;
            else if (id < 800) return R.drawable.sun_and_clouds;
            else if (id == 800) return R.drawable.sun;
            else return R.drawable.clounds;

        } else {
            if (id < 300) return R.drawable.night_rain;
            else if (id < 504) return R.drawable.night_rain;
            else if (id < 600) return R.drawable.night_snow;
            else if (id < 701) return R.drawable.night_snow;
            else if (id < 800) return R.drawable.night_clouds;
            else if (id == 800) return R.drawable.moon;
            else return R.drawable.night_clouds;
        }


    }

    public static double getLatShared(Context context) {
        SharedPreferences sharedLat = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return (double) (sharedLat.getFloat("lat", 50.4547f));
    }

    public static double getLonShared(Context context) {
        SharedPreferences sharedLon = context.getSharedPreferences("location", Context.MODE_PRIVATE);
        return (double) (sharedLon.getFloat("lon", 30.5238f));
    }
}
