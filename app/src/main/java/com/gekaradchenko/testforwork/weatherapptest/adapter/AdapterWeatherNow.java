package com.gekaradchenko.testforwork.weatherapptest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gekaradchenko.testforwork.weatherapptest.model.NowForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;

import java.util.ArrayList;

public class AdapterWeatherNow extends RecyclerView.Adapter<AdapterWeatherNow.AdapterWeatherNowViewHolder> {
    private ArrayList<NowForecast> arrayList = new ArrayList<>();



    public ArrayList<NowForecast> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<NowForecast> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterWeatherNowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.week_weather_recycler_view_item, parent, false);

        AdapterWeatherNowViewHolder adapterWeatherNowViewHolder = new AdapterWeatherNowViewHolder(view);
        return adapterWeatherNowViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWeatherNowViewHolder holder, int position) {
        NowForecast nowForecast = arrayList.get(position);
        holder.imageView.setImageResource(nowForecast.getImageView());
        holder.dateString.setText(nowForecast.getDateString());
        holder.forecastString.setText(nowForecast.getForecastString());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AdapterWeatherNowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView dateString, forecastString;



        public AdapterWeatherNowViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.weekImageViewItemRecyclerView);
            dateString = itemView.findViewById(R.id.dateTextViewItemRecyclerView);
            forecastString = itemView.findViewById(R.id.forecastTextViewItemRecyclerView);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(context, );
        }
    }
}
