package com.gekaradchenko.testforwork.weatherapptest.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gekaradchenko.testforwork.weatherapptest.model.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDao getLocationDao();
}
