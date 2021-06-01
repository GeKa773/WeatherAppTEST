package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gekaradchenko.testforwork.weatherapptest.AdapterLocationWeather;
import com.gekaradchenko.testforwork.weatherapptest.AddLocationActivity;
import com.gekaradchenko.testforwork.weatherapptest.Location;
import com.gekaradchenko.testforwork.weatherapptest.LocationDatabase;
import com.gekaradchenko.testforwork.weatherapptest.LocationForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.Unit;
import com.gekaradchenko.testforwork.weatherapptest.model.Example;
import com.gekaradchenko.testforwork.weatherapptest.model.Hourly;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;

import java.util.ArrayList;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {
    private Button locationButton;
    private RecyclerView recyclerView;
    private AdapterLocationWeather adapter;
    private ArrayList<LocationForecast> forecastArrayList;
    private ArrayList<Location> locations;
    private ArrayList<Hourly> hourlyArrayList;

    private LocationDatabase locationDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationDatabase = Room.databaseBuilder(getContext(),
                LocationDatabase.class, "locationDB").build();

        locationButton = view.findViewById(R.id.locationButton);
        forecastArrayList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.locationRecyclerview);

        adapter = new AdapterLocationWeather();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getAllLocationOnDB();


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddLocationActivity.class));
            }
        });
    }

    private void getAllLocationOnDB() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                locations = (ArrayList<Location>) locationDatabase.getLocationDao().getAllLocations();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getLocationWeather();
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void getLocationWeather() {
        WeatherService weatherService = RetrofitInstance.getService();
        if (locations.size() > 0) {
            System.out.println("Location size = " + locations.size());
            for (int i = 0; i < locations.size(); i++) {

                Call<Example> call = weatherService.getWeather(
                        locations.get(i).getLat(),
                        locations.get(i).getLon(),
                        getString(R.string.exclude),
                        getString(R.string.KEY));

                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        Example example = response.body();
                        if (example != null && example.getHourly() != null) {
                            hourlyArrayList = (ArrayList<Hourly>) example.getHourly();
                            String locationName = example.getTimezone();

                            int tempToday = (int) (hourlyArrayList.get(0).getTemp() - 273.15);
                            int humidityToday = hourlyArrayList.get(0).getHumidity();

                            int tempTomorrow = (int) (hourlyArrayList.get(23).getTemp() - 273.15);
                            int humidityTomorrow = hourlyArrayList.get(23).getHumidity();

                            String forecastToday = getTempString(tempToday) + "/" + humidityToday + "% " + getString(R.string.today);
                            String forecastTomorrow = getString(R.string.tomorrow) + " " + getTempString(tempTomorrow) + "/" + humidityTomorrow + "%";
                            forecastArrayList.add(new LocationForecast(
                                    locationName,
                                    Unit.isWeatherIcon(hourlyArrayList.get(0).getWeather().get(0).getId()),
                                    forecastToday,
                                    Unit.isWeatherIcon(hourlyArrayList.get(23).getWeather().get(0).getId()),
                                    forecastTomorrow));
                            adapter.setLocationForecasts(forecastArrayList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });

            }

        }
    }

    private String getTempString(int temp) {
        if (temp > 0) {
            return "+" + temp;
        } else return "" + temp;
    }
}