package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.adapter.AdapterWeatherNow;
import com.gekaradchenko.testforwork.weatherapptest.model.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.adapter.RecyclerViewItemClickListener;
import com.gekaradchenko.testforwork.weatherapptest.viewmodel.WeekWeatherFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class WeekWeatherFragment extends Fragment {
    private ConstraintLayout weekWeatherFragment;
    private RecyclerView recyclerView;
    private AdapterWeatherNow adapter;

    private ArrayList<NowForecast> forecasts;

    private WeekWeatherFragmentViewModel weekWeatherFragmentViewModel;

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

        weekWeatherFragmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(WeekWeatherFragmentViewModel.class);


        adapter = new AdapterWeatherNow();
        forecasts = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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

        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        weekWeatherFragmentViewModel.getWeekWeather().observe(lifecycleOwner,
                new Observer<List<NowForecast>>() {
                    @Override
                    public void onChanged(List<NowForecast> nowForecasts) {

                        forecasts = (ArrayList<NowForecast>) nowForecasts;
                        initViewWeekFragment();
                    }
                });

    }

    private void initViewWeekFragment() {
        if (forecasts.size() > 0) {

            weekPlaceTextView.setText(forecasts.get(0).getTimeZone());
            weekCoordinateTextView.setText(forecasts.get(0).getCoordinate());
            for (int j = 0; j < forecasts.size(); j++) {
                if (forecasts.get(j).getCount() == 0) {
                    forecasts.get(j).setDateString(getString(R.string.now));
                } else if (forecasts.get(j).getCount() == 1) {
                    forecasts.get(j).setDateString("+1 " + getString(R.string.hour));
                } else {
                    forecasts.get(j).setDateString("+" + j + " " + getString(R.string.hours));
                }
                forecasts.get(j).setForecastString(forecasts.get(j).getForecastString() + getString(R.string.m_c));
            }
            adapter.setArrayList(forecasts);

        }
    }


    private void navigationGo(View view) {
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_weekWeatherFragment_to_todayWeatherFragment);
    }
}