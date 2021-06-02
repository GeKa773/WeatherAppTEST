package com.gekaradchenko.testforwork.weatherapptest.model;

import android.widget.ImageView;

public class LocationForecast {
    private int id;
    private String location;
    private int imageViewToday;
    private String forecastToday;
    private int imageViewTomorrow;
    private String forecastTomorrow;
    private double lat;
    private double lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public LocationForecast(int id, String location, int imageViewToday, String forecastToday, int imageViewTomorrow, String forecastTomorrow, double lat, double lon) {
        this.id = id;
        this.location = location;
        this.imageViewToday = imageViewToday;
        this.forecastToday = forecastToday;
        this.imageViewTomorrow = imageViewTomorrow;
        this.forecastTomorrow = forecastTomorrow;
        this.lat = lat;
        this.lon = lon;
    }
}
