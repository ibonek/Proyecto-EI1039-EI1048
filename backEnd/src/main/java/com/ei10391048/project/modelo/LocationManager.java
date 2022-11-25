package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

import java.util.List;

public class LocationManager {

    private List<Location> locations;
    private static LocationManager locationManager;

    public static LocationManager getInstance(){
        if (locationManager == null)
            locationManager = new LocationManager();
        return locationManager;
    }

    public static void setInstance(LocationApiInterface geoCodingService){


    }

    public void addByName(String name) throws IncorrectLocationException {

    }

    public int getNumberOfLocations() {
        return -1;
    }
}
