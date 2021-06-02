package com.gekaradchenko.testforwork.weatherapptest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gekaradchenko.testforwork.weatherapptest.model.LocationForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;

import java.util.ArrayList;

public class AdapterLocationWeather extends RecyclerView.Adapter<AdapterLocationWeather.AdapterLocationWeatherViewHolder> {
    private ArrayList<LocationForecast> locationForecasts = new ArrayList<>();

    public ArrayList<LocationForecast> getLocationForecasts() {
        return locationForecasts;
    }

    public void setLocationForecasts(ArrayList<LocationForecast> locationForecasts) {
        this.locationForecasts = locationForecasts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterLocationWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_recycler_view_item, parent, false);
        AdapterLocationWeatherViewHolder adapterLocationWeatherViewHolder = new AdapterLocationWeatherViewHolder(view);
        return adapterLocationWeatherViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLocationWeatherViewHolder holder, int position) {
        LocationForecast locationForecast = locationForecasts.get(position);
        holder.locationTextViewLocation.setText(locationForecast.getLocation());
        holder.todayForecastTextViewLocation.setText(locationForecast.getForecastToday());
        holder.tomorrowForecastTextViewLocation.setText(locationForecast.getForecastTomorrow());

        holder.todayWeatherImageViewLocation.setImageResource(locationForecast.getImageViewToday());
        holder.tomorrowWeatherImageViewLocation.setImageResource(locationForecast.getImageViewTomorrow());


    }

    @Override
    public int getItemCount() {
        return locationForecasts.size();
    }

    public class AdapterLocationWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView locationTextViewLocation, todayForecastTextViewLocation, tomorrowForecastTextViewLocation;

        private ImageView todayWeatherImageViewLocation, tomorrowWeatherImageViewLocation;

        public AdapterLocationWeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            locationTextViewLocation = itemView.findViewById(R.id.locationTextViewLocation);
            todayForecastTextViewLocation = itemView.findViewById(R.id.todayForecastTextViewLocation);
            tomorrowForecastTextViewLocation = itemView.findViewById(R.id.tomorrowForecastTextViewLocation);

            todayWeatherImageViewLocation = itemView.findViewById(R.id.todayWeatherImageViewLocation);
            tomorrowWeatherImageViewLocation = itemView.findViewById(R.id.tomorrowWeatherImageViewLocation);

        }
    }
}
