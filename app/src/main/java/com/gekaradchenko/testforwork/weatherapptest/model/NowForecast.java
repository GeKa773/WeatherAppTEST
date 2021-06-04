package com.gekaradchenko.testforwork.weatherapptest.model;

public class NowForecast {
    private int ImageView;
    private String dateString;
    private String forecastString;
    private String timeZone;
    private String coordinate;
    // for translation text
    private int count;

    public NowForecast(int imageView, String dateString, String forecastString, String timeZone, String coordinate, int count) {
        ImageView = imageView;
        this.dateString = dateString;
        this.forecastString = forecastString;
        this.timeZone = timeZone;
        this.coordinate = coordinate;
        this.count = count;
    }

    public int getImageView() {
        return ImageView;
    }

    public void setImageView(int imageView) {
        ImageView = imageView;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getForecastString() {
        return forecastString;
    }

    public void setForecastString(String forecastString) {
        this.forecastString = forecastString;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
