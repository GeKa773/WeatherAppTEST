package com.gekaradchenko.testforwork.weatherapptest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "location_table")
public class Location {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private double lat;
    private double lon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Location(int id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
