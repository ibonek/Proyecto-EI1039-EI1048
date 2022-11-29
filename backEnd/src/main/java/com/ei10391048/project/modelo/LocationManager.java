package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

import java.util.LinkedList;
import java.util.List;

public class LocationManager {
    private List<Location> locations;

    private static  LocationManager locationManager;

    private LocationApiInterface locationApi;

    private LocationManager() {
        this.locations = new LinkedList<>();;
        this.locationApi = new GeoCodService();
    }

    public static LocationManager getInstance(){
        if (locationManager == null){
            locationManager = new LocationManager();
        }
        return locationManager;

    }

    public void addByName(String name) throws IncorrectLocationException {
    }

    public int getNumberOfLocations() {
        return locations.size();
    }

    public void addByCoordinates(Coordinates coordinates) throws IncorrectLocationException {
    }
}
