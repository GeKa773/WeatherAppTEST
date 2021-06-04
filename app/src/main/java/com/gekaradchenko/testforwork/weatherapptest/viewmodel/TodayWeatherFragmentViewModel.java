package com.gekaradchenko.testforwork.weatherapptest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gekaradchenko.testforwork.weatherapptest.model.TodayForecast;
import com.gekaradchenko.testforwork.weatherapptest.repository.TodayWeatherRepository;

public class TodayWeatherFragmentViewModel extends AndroidViewModel {
    private TodayWeatherRepository todayWeatherRepository;
    public TodayWeatherFragmentViewModel(@NonNull Application application) {
        super(application);
        todayWeatherRepository = new TodayWeatherRepository(application);
    }
    public LiveData<TodayForecast> getTodayWeather(){
        return todayWeatherRepository.getMutableLiveData();
    }
}
