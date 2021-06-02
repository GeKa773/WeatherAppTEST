package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gekaradchenko.testforwork.weatherapptest.adapter.AdapterLocationWeather;
import com.gekaradchenko.testforwork.weatherapptest.AddLocationActivity;
import com.gekaradchenko.testforwork.weatherapptest.model.Location;
import com.gekaradchenko.testforwork.weatherapptest.data.LocationDatabase;
import com.gekaradchenko.testforwork.weatherapptest.model.LocationForMassive;
import com.gekaradchenko.testforwork.weatherapptest.model.LocationForecast;
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

public class LocationFragment extends Fragment {
    private Button locationButton;
    private RecyclerView recyclerView;
    private AdapterLocationWeather adapter;
    private ArrayList<LocationForecast> forecastArrayList;
    private ArrayList<Location> locations;
    private ArrayList<LocationForMassive> locationMass;
    private ArrayList<Hourly> hourlyArrayList;

    private LocationDatabase locationDatabase;

    private SharedPreferences.Editor editor;


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

        editor = this.getActivity().getSharedPreferences(getString(R.string.shared_location), Context.MODE_PRIVATE).edit();

        locationButton = view.findViewById(R.id.locationButton);
        forecastArrayList = new ArrayList<>();
        locationMass = new ArrayList<>();
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

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editor.putFloat(getString(R.string.lat), (float) forecastArrayList.get(position).getLat());
                editor.putFloat(getString(R.string.lon), (float) forecastArrayList.get(position).getLon());
                editor.apply();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_addLocationFragment_to_todayWeatherFragment);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                LocationForecast locationForecast = forecastArrayList.get(viewHolder.getAdapterPosition());
                int idDelete = locationForecast.getId();
                for (int i = 0; i < locations.size(); i++) {
                    if (locations.get(i).getId() == idDelete) {
                        deleteLocation(locations.get(i));

                    }
                }
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void deleteLocation(Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                locationDatabase.getLocationDao().deleteLocation(location);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        forecastArrayList = new ArrayList<>();
                        getAllLocationOnDB();
                    }
                });
            }
        }).start();
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
            for (int i = 0; i < locations.size(); i++) {
                int id = locations.get(i).getId();


                Call<Example> call = weatherService.getWeather(
                        locations.get(i).getLat(),
                        locations.get(i).getLon(),
                        getString(R.string.exclude),
                        getString(R.string.KEY));
                //locationMass.add(new LocationForMassive((float) locations.get(i).getLat(), (float) locations.get(i).getLon()));
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
                                    id,
                                    locationName,
                                    Unit.isWeatherIcon(hourlyArrayList.get(0).getWeather().get(0).getId(),example.getTimezoneOffset(),0),
                                    forecastToday,
                                    Unit.isWeatherIcon(hourlyArrayList.get(23).getWeather().get(0).getId(),example.getTimezoneOffset(),0),
                                    forecastTomorrow,
                                    example.getLat(),
                                    example.getLon()));

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