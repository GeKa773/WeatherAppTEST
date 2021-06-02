package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.adapter.AdapterWeatherNow;
import com.gekaradchenko.testforwork.weatherapptest.model.Location;
import com.gekaradchenko.testforwork.weatherapptest.data.LocationDatabase;
import com.gekaradchenko.testforwork.weatherapptest.model.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.adapter.RecyclerViewItemClickListener;
import com.gekaradchenko.testforwork.weatherapptest.units.Unit;
import com.gekaradchenko.testforwork.weatherapptest.model.Example;
import com.gekaradchenko.testforwork.weatherapptest.model.Hourly;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekWeatherFragment extends Fragment {
    private ConstraintLayout weekWeatherFragment;
    private RecyclerView recyclerView;
    private AdapterWeatherNow adapter;
    private double lat, lon;
    private ArrayList<NowForecast> forecasts;
    private ArrayList<Hourly> hourlyArrayList;
    private ArrayList<Location> locations;
    private LocationDatabase locationDatabase;
    private TextView weekPlaceTextView, weekCoordinateTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weekWeatherFragment = view.findViewById(R.id.weekWeatherFragment);
        recyclerView = view.findViewById(R.id.weekRecyclerView);

        weekPlaceTextView = view.findViewById(R.id.weekPlaceTextView);
        weekCoordinateTextView = view.findViewById(R.id.weekCoordinateTextView);
        locationDatabase = Room.databaseBuilder(getContext(),
                LocationDatabase.class, "locationDB").build();

        adapter = new AdapterWeatherNow();
        forecasts = new ArrayList<>();
        locations = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        lat = Unit.getLatShared(getContext());
        lon = Unit.getLonShared(getContext());
        getWeatherWeek();


        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(),
                recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                navigationGo(view);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        weekWeatherFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationGo(view);
            }
        });

    }

    private void getWeatherWeek() {
        WeatherService weatherService = RetrofitInstance.getService();
        Call<Example> call = weatherService.getWeather(lat, lon, getString(R.string.exclude), getString(R.string.KEY));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                if (example != null && example.getHourly() != null) {
                    hourlyArrayList = (ArrayList<Hourly>) example.getHourly();

                    weekPlaceTextView.setText(example.getTimezone());
                    weekCoordinateTextView.setText(example.getLat() + ", " + example.getLon());

                    for (int i = 0; i < hourlyArrayList.size(); i++) {
                        int temp = (int) (hourlyArrayList.get(i).getTemp() - 273.15);
                        int humid = hourlyArrayList.get(i).getHumidity();
                        Double windSpeed = hourlyArrayList.get(i).getWindSpeed();

                        String tempNow;
                        if (temp > 0) tempNow = "+" + temp;
                        else tempNow = temp + "";
                        String s = tempNow + " / " + humid + "% / " + windSpeed + getString(R.string.m_c);
                        String date;
                        if (i == 0) {
                            date = getString(R.string.now);
                        } else if (i == 1) {
                            date = "+1 " + getString(R.string.hour);
                        } else date = "+" + i + " " + getString(R.string.hours);

                        forecasts.add(new NowForecast(
                                Unit.isWeatherIcon(hourlyArrayList.get(i).getWeather().get(0).getId(), example.getTimezoneOffset(), i), date, s));
                    }
                    adapter.setArrayList(forecasts);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }


    private void navigationGo(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_weekWeatherFragment_to_todayWeatherFragment);
    }
}