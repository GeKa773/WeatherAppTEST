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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.model.TodayForecast;
import com.gekaradchenko.testforwork.weatherapptest.viewmodel.TodayWeatherFragmentViewModel;


public class TodayWeatherFragment extends Fragment {
    private ConstraintLayout todayWeatherFragment;

    private TextView
            todayDateTextView,
            nowPlaceTextView,
            nowCoordinateTextView,
            nowTempTextView,
            nowHumidityTextView,
            nowWindSpeedTextView,
            nowTimeTextView;
    private ImageView nowWeatherImageView;

    private TodayWeatherFragmentViewModel todayWeatherFragmentViewModel;
    private TodayForecast forecast;


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

        todayWeatherFragmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(TodayWeatherFragmentViewModel.class);

        getWeatherNow();


        todayWeatherFragment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_todayWeatherFragment_to_weekWeatherFragment);
            }
        });
    }


    private void getWeatherNow() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        todayWeatherFragmentViewModel.getTodayWeather().observe(lifecycleOwner,
                new Observer<TodayForecast>() {
                    @Override
                    public void onChanged(TodayForecast todayForecast) {
                        forecast = todayForecast;
                        initViewTodayFragment();
                    }
                });
    }

    private void initViewTodayFragment() {

        if (forecast!= null){
            todayDateTextView.setText(forecast.getDate());
            nowPlaceTextView.setText(forecast.getLocalName());
            nowCoordinateTextView.setText(forecast.getLocations());
            nowTempTextView.setText(forecast.getTemp());
            nowWeatherImageView.setImageResource(forecast.getNowWeatherImageView());
            nowHumidityTextView.setText(getString(R.string.humidity) + forecast.getHumidity());
            nowWindSpeedTextView.setText(getString(R.string.wind_speed) + forecast.getWindSpeed() + getString(R.string.m_c));
            nowTimeTextView.setText(forecast.getTime());
        }

    }


}