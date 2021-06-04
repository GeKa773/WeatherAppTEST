package com.gekaradchenko.testforwork.weatherapptest.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.model.TodayForecast;
import com.gekaradchenko.testforwork.weatherapptest.retrofitmodel.Current;
import com.gekaradchenko.testforwork.weatherapptest.retrofitmodel.Example;
import com.gekaradchenko.testforwork.weatherapptest.service.RetrofitInstance;
import com.gekaradchenko.testforwork.weatherapptest.service.WeatherService;
import com.gekaradchenko.testforwork.weatherapptest.units.Unit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayWeatherRepository {
    private Application application;
    private double lat, lon;

    private Current current;
    private Date date;
    private SimpleDateFormat formatDate, formatTime;
    private Calendar calendar;
    private TodayForecast todayForecast;
    private MutableLiveData<TodayForecast> mutableLiveData =
            new MutableLiveData<>();

    private String todayDate;
    private String nowTime;


    public TodayWeatherRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<TodayForecast> getMutableLiveData() {


        lat = Unit.getLatShared(application.getApplicationContext());
        lon = Unit.getLonShared(application.getApplicationContext());
        date = new Date();
        formatDate = new SimpleDateFormat("dd.MM.yyyy");
        formatTime = new SimpleDateFormat("HH:mm");
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.get(Calendar.DAY_OF_WEEK);
        todayDate = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)) + ", " + formatDate.format(date);
        nowTime = formatTime.format(date);

        WeatherService weatherService = RetrofitInstance.getService();
        Call<Example> call = weatherService.getWeather(lat, lon,
                application.getApplicationContext().getString(R.string.exclude),
                application.getApplicationContext().getString(R.string.KEY));

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                if (example != null && example.getCurrent() != null) {
                    current = example.getCurrent();
                    String tempNow = "";
                    int temp = (int) (current.getTemp() - 273.15);
                    if (temp > 0) tempNow = "+" + temp;
                    else tempNow = "" + temp;

                    todayForecast = new TodayForecast(
                            todayDate,
                            example.getTimezone(),
                            example.getLat() + ", " + example.getLon(),
                            tempNow,
                            Unit.isWeatherIcon(current.getWeather().get(0).getId(), example.getTimezoneOffset(), 0),
                            current.getHumidity() + "%",
                            " " + current.getWindSpeed(),
                            nowTime
                    );
                    mutableLiveData.setValue(todayForecast);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


        return mutableLiveData;
    }


    private String getDayOfWeek(int i) {
        --i;
        if (i < 0) i = 7;
        if (i > 7) i = 1;
        if (i == 1) return application.getApplicationContext().getString(R.string.monday);
        else if (i == 2) return application.getApplicationContext().getString(R.string.thursday);
        else if (i == 3) return application.getApplicationContext().getString(R.string.wednesday);
        else if (i == 4) return application.getApplicationContext().getString(R.string.thursday);
        else if (i == 5) return application.getApplicationContext().getString(R.string.friday);
        else if (i == 6) return application.getApplicationContext().getString(R.string.saturday);
        else if (i == 7) return application.getApplicationContext().getString(R.string.sunday);
        else return "Day";
    }
}
