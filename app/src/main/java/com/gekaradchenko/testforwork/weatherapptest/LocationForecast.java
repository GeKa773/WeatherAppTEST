package com.gekaradchenko.testforwork.weatherapptest;

import android.widget.ImageView;

public class LocationForecast {
    private String location;
    private int imageViewToday;
    private String forecastToday;
    private int imageViewTomorrow;
    private String forecastTomorrow;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getImageViewToday() {
        return imageViewToday;
    }

    public void setImageViewToday(int imageViewToday) {
        this.imageViewToday = imageViewToday;
    }

    public String getForecastToday() {
        return forecastToday;
    }

    public void setForecastToday(String forecastToday) {
        this.forecastToday = forecastToday;
    }

    public int getImageViewTomorrow() {
        return imageViewTomorrow;
    }

    public void setImageViewTomorrow(int imageViewTomorrow) {
        this.imageViewTomorrow = imageViewTomorrow;
    }

    public String getForecastTomorrow() {
        return forecastTomorrow;
    }

    public void setForecastTomorrow(String forecastTomorrow) {
        this.forecastTomorrow = forecastTomorrow;
    }

    public LocationForecast(String location, int imageViewToday, String forecastToday, int imageViewTomorrow, String forecastTomorrow) {
        this.location = location;
        this.imageViewToday = imageViewToday;
        this.forecastToday = forecastToday;
        this.imageViewTomorrow = imageViewTomorrow;
        this.forecastTomorrow = forecastTomorrow;
    }
}
