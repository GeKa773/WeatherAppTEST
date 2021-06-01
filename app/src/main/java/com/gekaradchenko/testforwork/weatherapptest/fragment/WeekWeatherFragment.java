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

import com.gekaradchenko.testforwork.weatherapptest.AdapterLocationWeather;
import com.gekaradchenko.testforwork.weatherapptest.AdapterWeatherNow;
import com.gekaradchenko.testforwork.weatherapptest.Location;
import com.gekaradchenko.testforwork.weatherapptest.LocationDatabase;
import com.gekaradchenko.testforwork.weatherapptest.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.RecyclerViewItemClickListener;
import com.gekaradchenko.testforwork.weatherapptest.model.Example;
import com.gekaradchenko.testforwork.weatherapptest.model.Hourly;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;

import java.util.ArrayList;

import retrofit2.Call;

public class WeekWeatherFragment extends Fragment {
    private ConstraintLayout weekWeatherFragment;
    private RecyclerView recyclerView;
    private AdapterWeatherNow adapter;
    private ArrayList<NowForecast> forecasts;

    private ArrayList<Hourly> hourlyArrayList;
//    private String exclude = "daily";
    public static final String KEY = "92d56959b946a47f9ad21b1c5c911179";
    private double lat = 50.4547;
    private double lon = 30.5238;

    private ArrayList<Location> locations;
    private LocationDatabase locationDatabase;


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

        locationDatabase = Room.databaseBuilder(getContext(),
                LocationDatabase.class, "locationDB").build();
        adapter = new AdapterWeatherNow();
        forecasts = new ArrayList<>();
        locations = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        getAllLocationOnDB();

        // adapter.setArrayList(forecasts);


        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
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

    private void getAllLocationOnDB() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                locations = (ArrayList<Location>) locationDatabase.getLocationDao().getAllLocations();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getLocationWeather();
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

//    private Object getLocationWeather() {
//        WeatherService weatherService = RetrofitInstance.getService();
//        if (locations.size() > 0) {
//            for (int i = 0; i <locations.size(); i ++){
//
//            Call<Example> call = weatherService.getWeather(
//                    locations.get(i).getLat(),
//                    locations.get(i).getLon(),
//                    getString(R.string.exclude),
//                    getString(R.string.KEY));
//            }
//
//        }
//
//    }

    private void navigationGo(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_weekWeatherFragment_to_todayWeatherFragment);
    }
}