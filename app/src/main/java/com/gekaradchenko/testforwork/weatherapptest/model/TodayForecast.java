package com.gekaradchenko.testforwork.weatherapptest.model;

public class TodayForecast {
    private String date;
    private String localName;
    private String locations;
    private String temp;
    private int nowWeatherImageView;
    private String humidity;
    private String windSpeed;
    private String time;

    public TodayForecast(String date, String localName, String locations, String temp,
                         int nowWeatherImageView, String humidity, String windSpeed, String time) {
        this.date = date;
        this.localName = localName;
        this.locations = locations;
        this.temp = temp;
        this.nowWeatherImageView = nowWeatherImageView;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getNowWeatherImageView() {
        return nowWeatherImageView;
    }

    public void setNowWeatherImageView(int nowWeatherImageView) {
        this.nowWeatherImageView = nowWeatherImageView;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
