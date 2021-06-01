package com.gekaradchenko.testforwork.weatherapptest;

public class NowForecast {
    private int ImageView;
    private String dateString;
    private String forecastString;

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

    public NowForecast(int imageView, String dateString, String forecastString) {
        ImageView = imageView;
        this.dateString = dateString;
        this.forecastString = forecastString;
    }
}
