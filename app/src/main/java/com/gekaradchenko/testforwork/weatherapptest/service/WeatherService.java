package com.gekaradchenko.testforwork.weatherapptest.service;

import com.gekaradchenko.testforwork.weatherapptest.retrofitmodel.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("onecall")
    Call<Example> getWeather(@Query("lat") double lat,
                             @Query("lon") double lon,
                             @Query("exclude") String exclude,
                             @Query("appid") String appid);
}
