package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

import java.util.List;

public class LocationManager {
    private List<Location> locations;

    public LocationManager(GeoCodingService geoCodingService) {
    }
    public static LocationManager getInstance(){
        return null;
    }

    public void addByName(String name) throws IncorrectLocationException {
    }

    public int getNumberOfLocations() {
        return -1;
    }

    public void addByCoordinates(Location location) throws IncorrectLocationException {
    }
}
