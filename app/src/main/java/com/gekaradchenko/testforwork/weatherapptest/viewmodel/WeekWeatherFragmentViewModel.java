package com.gekaradchenko.testforwork.weatherapptest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gekaradchenko.testforwork.weatherapptest.model.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.repository.WeekWeatherRepository;

import java.util.List;

public class WeekWeatherFragmentViewModel extends AndroidViewModel {

    private WeekWeatherRepository weekWeatherRepository;
    public WeekWeatherFragmentViewModel(@NonNull Application application) {
        super(application);
        weekWeatherRepository = new WeekWeatherRepository(application);
    }
    public LiveData<List<NowForecast>> getWeekWeather(){
        return weekWeatherRepository.getMutableLiveData();
    }
}
