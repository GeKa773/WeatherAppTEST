package com.gekaradchenko.testforwork.weatherapptest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM location_table")
    List<Location> getAllLocations();

    @Query("SELECT * FROM location_table WHERE id==:locationId")
    Location getLocation(int locationId);

    @Delete
    void deleteLocation(Location location);
}
