package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

import java.util.ArrayList;
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

    public void addLocation() throws IncorrectLocationException {
        locations.add(locationApi.findLocation());
    }

    public void addActiveLocation() {
    }

    public List<Location> getLocations(){
        return locations;
    }

    public int getNumberOfLocations() {
        return locations.size();
    }

    public LocationApiInterface getLocationApi() {
        return locationApi;
    }

    public List<String> getLocationsName(){
        List<String> list = new LinkedList<>();
        for (Location location: locations){
            list.add(location.getName());
        }
        return list;
    }

    public List<String> getActiveLocationsName() {
        return null;
    }



    public void setLocationApi(LocationApiInterface locationApi) {
        this.locationApi = locationApi;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void clearLocations(){
        locations.clear();
    }




}
