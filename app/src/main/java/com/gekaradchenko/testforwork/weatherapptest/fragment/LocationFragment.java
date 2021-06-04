package com.gekaradchenko.testforwork.weatherapptest.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gekaradchenko.testforwork.weatherapptest.adapter.AdapterLocationWeather;
import com.gekaradchenko.testforwork.weatherapptest.AddLocationActivity;
import com.gekaradchenko.testforwork.weatherapptest.model.LocationForecast;
import com.gekaradchenko.testforwork.weatherapptest.R;
import com.gekaradchenko.testforwork.weatherapptest.adapter.RecyclerViewItemClickListener;
import com.gekaradchenko.testforwork.weatherapptest.viewmodel.LocationWeatherFragmentViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {
    private Button locationButton;
    private RecyclerView recyclerView;
    private AdapterLocationWeather adapter;
    private ArrayList<LocationForecast> forecastArrayList;

    private SharedPreferences.Editor editor;

    private LocationWeatherFragmentViewModel  locationWeatherFragmentViewModel;

    private boolean isPermissionGranter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationWeatherFragmentViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(LocationWeatherFragmentViewModel.class);

        editor = this.getActivity().getSharedPreferences(getString(R.string.shared_location), Context.MODE_PRIVATE).edit();

        locationButton = view.findViewById(R.id.locationButton);
        forecastArrayList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.locationRecyclerview);

        adapter = new AdapterLocationWeather();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        getAllLocation();


        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCheckPermission();
                if (isPermissionGranter) {
                    startActivity(new Intent(getContext(), AddLocationActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Google Play service not available", Toast.LENGTH_SHORT).show();

                    }

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(getContext(), recyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                editor.putFloat(getString(R.string.lat), (float) forecastArrayList.get(position).getLat());
                editor.putFloat(getString(R.string.lon), (float) forecastArrayList.get(position).getLon());
                editor.apply();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_addLocationFragment_to_todayWeatherFragment);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                LocationForecast locationForecast = forecastArrayList.get(viewHolder.getAdapterPosition());
                int idDelete = locationForecast.getId();

                locationWeatherFragmentViewModel.deleteLocation(idDelete);
//                forecastArrayList = new ArrayList<>();
//                getAllLocation();

            }
        }).attachToRecyclerView(recyclerView);
    }

    private void getAllLocation() {


        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();

        locationWeatherFragmentViewModel.getLocationWeather().observe(lifecycleOwner,
                new Observer<List<LocationForecast>>() {
                    @Override
                    public void onChanged(List<LocationForecast> locationForecasts) {
                        forecastArrayList = (ArrayList<LocationForecast>) locationForecasts;
                        adapter.setLocationForecasts(forecastArrayList);

                    }
                });
    }


    private void myCheckPermission() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                isPermissionGranter = true;
                Toast.makeText(getContext(), "Permission Granter", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
}