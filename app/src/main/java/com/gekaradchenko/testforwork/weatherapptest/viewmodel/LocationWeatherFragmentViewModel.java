package com.gekaradchenko.testforwork.weatherapptest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gekaradchenko.testforwork.weatherapptest.model.Location;
import com.gekaradchenko.testforwork.weatherapptest.model.LocationForecast;
import com.gekaradchenko.testforwork.weatherapptest.repository.LocationWeatherRepository;

import java.util.List;

public class LocationWeatherFragmentViewModel extends AndroidViewModel {
    private LocationWeatherRepository locationWeatherRepository;

    public LocationWeatherFragmentViewModel(@NonNull Application application) {
        super(application);
        locationWeatherRepository = new LocationWeatherRepository(application);
    }

    public LiveData<List<LocationForecast>> getLocationWeather() {
        return locationWeatherRepository.getMutableLiveData();
    }

    public void deleteLocation(int id) {
        locationWeatherRepository.deleteLocationFromDatabase(id);
    }

    public void insertLocation(Location location) {
        locationWeatherRepository.insertLocationOnDatabase(location);
    }
}
