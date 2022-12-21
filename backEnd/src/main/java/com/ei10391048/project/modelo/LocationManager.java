package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.AlreadyActiveLocation;
import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocationManager {
    private List<Location> locations;
    private CRUDFireBase crudFireBase;

    private static  LocationManager locationManager;

    private LocationApiInterface locationApi;

    private LocationManager() {
        this.locations = new LinkedList<>();;
        this.locationApi = new GeoCodService();
        this.crudFireBase = new CRUDFireBase();
    }

    public static LocationManager getInstance(){
        if (locationManager == null){
            locationManager = new LocationManager();
        }
        return locationManager;

    }

    public void addLocation() throws IncorrectLocationException, NotSavedException {
        Location location = locationApi.findLocation();
        locations.add(location);
        crudFireBase.addLocation(location);
    }

    public void activeLocation(String name) throws AlreadyActiveLocation {
        for (Location location: locations) {
            if (location.getName().equals(name)){
                if (location.getIsActive()) {
                    throw new AlreadyActiveLocation();
                }
                location.setActive(true);
            }
        }
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
        List<String> aux = new LinkedList<>();
        for (Location location: locations){
            aux.add(location.getName());
        }
        return aux;
    }

    public List<String> getActiveLocationsName() {
        List<String> aux = new LinkedList<>();
        for (Location location: locations){
            if (location.getIsActive()){
                aux.add(location.getName());
            }
        }
        return aux;
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
