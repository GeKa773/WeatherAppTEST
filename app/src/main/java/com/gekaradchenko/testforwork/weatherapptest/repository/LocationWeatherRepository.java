package com.gekaradchenko.testforwork.weatherapptest.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.data.LocationDatabase;
import com.gekaradchenko.testforwork.weatherapptest.model.Location;
import com.gekaradchenko.testforwork.weatherapptest.model.LocationForecast;
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

public class LocationWeatherRepository {
    private Application application;

    private ArrayList<LocationForecast> forecastArrayList;
    private ArrayList<Location> locations;

    private ArrayList<Hourly> hourlyArrayList;

    private LocationDatabase locationDatabase;

    public LocationWeatherRepository(Application application) {
        this.application = application;
    }


    private MutableLiveData<List<LocationForecast>> mutableLiveData =
            new MutableLiveData<>();




    public MutableLiveData<List<LocationForecast>> getMutableLiveData() {
        locationDatabase = Room.databaseBuilder(application.getApplicationContext(),
                LocationDatabase.class, "locationDB").build();

        new GetLocationAsyncTask().execute();

        return mutableLiveData;
    }
    public void deleteLocationFromDatabase(int id){
        //new DeleteLocationDbAsyncTask(id).execute();
        deleteLocalDB(id);
    }
    public void insertLocationOnDatabase(Location location){
        insertLocalDB(location);
    }

    private void insertLocalDB(Location location) {
        locationDatabase = Room.databaseBuilder(application.getApplicationContext(),
                LocationDatabase.class, "locationDB").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                locationDatabase.getLocationDao().insertLocation(location);
            }
        }).start();
    }

    private void deleteLocalDB(int id) {
        locationDatabase = Room.databaseBuilder(application.getApplicationContext(),
                LocationDatabase.class, "locationDB").build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < locations.size(); i++) {
                    if (locations.get(i).getId() == id) {
                        locationDatabase.getLocationDao().deleteLocation(locations.get(i));

                    }
                }
            }
        }).start();
    }


    private class GetLocationAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            locations = (ArrayList<Location>) locationDatabase.getLocationDao().getAllLocations();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            forecastArrayList = new ArrayList<>();

            WeatherService weatherService = RetrofitInstance.getService();
            if (locations.size() > 0) {
                for (int i = 0; i < locations.size(); i++) {
                    int id = locations.get(i).getId();

                    Call<Example> call = weatherService.getWeather(
                            locations.get(i).getLat(),
                            locations.get(i).getLon(),
                            application.getApplicationContext().getString(R.string.exclude),
                            application.getApplicationContext().getString(R.string.KEY));
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

                                String forecastToday = getTempString(tempToday) + "/" + humidityToday + "% " + application.getApplicationContext().getString(R.string.today);
                                String forecastTomorrow = application.getApplicationContext().getString(R.string.tomorrow) + " " + getTempString(tempTomorrow) + "/" + humidityTomorrow + "%";
                                forecastArrayList.add(new LocationForecast(
                                        id,
                                        locationName,
                                        Unit.isWeatherIcon(hourlyArrayList.get(0).getWeather().get(0).getId(), example.getTimezoneOffset(), 0),
                                        forecastToday,
                                        Unit.isWeatherIcon(hourlyArrayList.get(23).getWeather().get(0).getId(), example.getTimezoneOffset(), 0),
                                        forecastTomorrow,
                                        example.getLat(),
                                        example.getLon()));
                                mutableLiveData.setValue(forecastArrayList);
                            }
                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {

                        }
                    });
                }
            }
        }
    }

    private String getTempString(int temp) {
        if (temp > 0) {
            return "+" + temp;
        } else return "" + temp;
    }
}