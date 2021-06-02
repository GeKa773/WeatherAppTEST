package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.Location;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.Unit;
import com.gekaradchenko.testforwork.weatherapptest.model.Current;
import com.gekaradchenko.testforwork.weatherapptest.model.Example;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodayWeatherFragment extends Fragment {
    private ConstraintLayout todayWeatherFragment;
    private double lat, lon;
    private SharedPreferences sharedPreferences;
    private ArrayList<Location> locations;
    private Current current;

    private TextView nowPlaceTextView,
            nowCoordinateTextView,
            nowHumidityTextView,
            nowWindSpeedTextView,
            nowTempTextView,
            todayDateTextView,
            nowTimeTextView;

    private ImageView nowWeatherImageView;

    private Date date;
    private SimpleDateFormat formatDate, formatTime;
    private Calendar calendar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todayWeatherFragment = view.findViewById(R.id.todayWeatherFragment);
        nowPlaceTextView = view.findViewById(R.id.nowPlaceTextView);
        nowCoordinateTextView = view.findViewById(R.id.nowCoordinateTextView);
        nowHumidityTextView = view.findViewById(R.id.nowHumidityTextView);
        nowWindSpeedTextView = view.findViewById(R.id.nowWindSpeedTextView);
        nowTempTextView = view.findViewById(R.id.nowTempTextView);
        todayDateTextView = view.findViewById(R.id.todayDateTextView);
        nowTimeTextView = view.findViewById(R.id.nowTimeTextView);
        nowWeatherImageView = view.findViewById(R.id.nowWeatherImageView);
        date = new Date();
        formatDate = new SimpleDateFormat("dd.MM.yyyy");
        formatTime = new SimpleDateFormat("HH:mm");
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.DAY_OF_WEEK);
        todayDateTextView.setText(getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + ", " + formatDate.format(date));
        nowTimeTextView.setText(formatTime.format(date));



        lat = Unit.getLatShared(getContext());
        lon = Unit.getLonShared(getContext());
        nowCoordinateTextView.setText(lat + ", " + lon);
        getWeatherNow();


        todayWeatherFragment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_todayWeatherFragment_to_weekWeatherFragment);
            }
        });
    }

    private String getDayOfWeek(int i) {
        --i;
        if (i < 0) i = 7;
        if (i > 7) i = 1;
        if (i == 1) return getString(R.string.monday);
        else if (i == 2) return getString(R.string.thursday);
        else if (i == 3) return getString(R.string.wednesday);
        else if (i == 4) return getString(R.string.thursday);
        else if (i == 5) return getString(R.string.friday);
        else if (i == 6) return getString(R.string.saturday);
        else if (i == 7) return getString(R.string.sunday);
        else return "Day";
    }

    private void getWeatherNow() {
        WeatherService weatherService = RetrofitInstance.getService();
        Call<Example> call = weatherService.getWeather(lat, lon, getString(R.string.exclude), getString(R.string.KEY));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                if (example != null && example.getCurrent() != null) {
                    current = example.getCurrent();

                    String locationName = example.getTimezone();
                    nowPlaceTextView.setText(locationName);

                    int temp = (int) (current.getTemp() - 273.15);
                    if (temp > 0) nowTempTextView.setText("+" + temp);
                    else nowTempTextView.setText("" + temp);
                    nowWeatherImageView.setImageResource(Unit.isWeatherIcon(current.getWeather().get(0).getId()));

                    int humidity = current.getHumidity();
                    nowHumidityTextView.setText(getString(R.string.humidity) + " " + humidity + "%");

                    Double windSpeed = current.getWindSpeed();
                    nowWindSpeedTextView.setText(getString(R.string.wind_speed) + " " + windSpeed + getString(R.string.m_c));

                } else System.out.println("CATn sssssssssssssssssssssssqqqqqqqqqqqqqqqqqqqqqqqqq");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }


}