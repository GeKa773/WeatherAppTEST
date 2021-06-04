package com.gekaradchenko.testforwork.weatherapptest.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.model.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.retrofitmodel.Example;
import com.gekaradchenko.testforwork.weatherapptest.retrofitmodel.Hourly;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;
import com.gekaradchenko.testforwork.weatherapptest.units.Unit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeekWeatherRepository {
    private Application application;
    private double lat, lon;
    private ArrayList<NowForecast> forecasts = new ArrayList<>();
    private ArrayList<Hourly> hourlyArrayList = new ArrayList<>();
    private MutableLiveData<List<NowForecast>> mutableLiveData =
            new MutableLiveData<>();

    public WeekWeatherRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<List<NowForecast>> getMutableLiveData() {
        lat = Unit.getLatShared(application.getApplicationContext());
        lon = Unit.getLonShared(application.getApplicationContext());
        WeatherService weatherService = RetrofitInstance.getService();
        Call<Example> call = weatherService.getWeather(
                lat,
                lon,
                application.getApplicationContext().getString(R.string.exclude),
                application.getApplicationContext().getString(R.string.KEY));
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                if (example != null && example.getHourly() != null) {
                    hourlyArrayList = (ArrayList<Hourly>) example.getHourly();

//                    weekPlaceTextView.setText(example.getTimezone());
//                    weekCoordinateTextView.setText(example.getLat() + ", " + example.getLon());

                    for (int i = 0; i < hourlyArrayList.size(); i++) {
                        int temp = (int) (hourlyArrayList.get(i).getTemp() - 273.15);
                        int humid = hourlyArrayList.get(i).getHumidity();
                        Double windSpeed = hourlyArrayList.get(i).getWindSpeed();

                        String tempNow;
                        if (temp > 0) tempNow = "+" + temp;
                        else tempNow = temp + "";
                        String date= "";

                        forecasts.add(new NowForecast(
                                Unit.isWeatherIcon(hourlyArrayList.get(i).getWeather().get(0).getId(), example.getTimezoneOffset(), i),
                                date,
                                tempNow + " / " + humid + "% / " + windSpeed ,
                                example.getTimezone(),
                                example.getLat() + ", " + example.getLon(),
                                i));
                    }
                    mutableLiveData.setValue(forecasts);

                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }


}
