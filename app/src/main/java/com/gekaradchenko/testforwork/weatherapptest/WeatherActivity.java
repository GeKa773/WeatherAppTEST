package com.gekaradchenko.testforwork.weatherapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WeatherActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        view = new View(getApplicationContext());


//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//                MenuItem location, home, setting;
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                location = findViewById(R.id.addLocationFragment);
//                home = findViewById(R.id.todayWeatherFragment);
//                setting = findViewById(R.id.settingFragment);
//                Log.d("SSS", "LOCATION is selected");
//                switch (item.getItemId()) {
//                    case R.id.addLocationFragment: {
//                        location.setIcon(R.drawable.ic_sharp_settings_applications_24);
//                        home.setIcon(R.drawable.ic_outline_home_24);
//                        setting.setIcon(R.drawable.ic_sharp_settings_applications_24);
//                        break;
//                    }
//                    case R.id.todayWeatherFragment: {
//                        location.setIcon(R.drawable.ic_outline_list_alt_24);
//                        home.setIcon(R.drawable.ic_sharp_settings_applications_24);
//                        setting.setIcon(R.drawable.ic_sharp_settings_applications_24);
//                        break;
//                    }
//                    case R.id.settingFragment: {
//                        location.setIcon(R.drawable.ic_outline_list_alt_24);
//                        home.setIcon(R.drawable.ic_outline_home_24);
//                        setting.setIcon(R.drawable.ic_sharp_settings_applications_24);
//                        break;
//                    }
//                }
//                return false;
//            }
//        });

        navController = Navigation.findNavController(this, R.id.weatherFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }


}