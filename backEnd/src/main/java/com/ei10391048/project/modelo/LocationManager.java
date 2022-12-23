package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.AlreadyActiveLocation;
import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;

import java.util.LinkedList;
import java.util.List;

public class LocationManager {
    private List<Location> locations;
    private CRUDFireBase crudFireBase;

    private static LocationManager locationManager;

    private LocationApiInterface locationApi;

    private LocationManager() {
        this.locations = new LinkedList<>();
        this.locationApi = new GeoCodService();
        this.crudFireBase = new CRUDFireBase();
    }

    public static LocationManager getInstance() {
        if (locationManager == null) {
            locationManager = new LocationManager();
        }
        return locationManager;

    }

    public void addLocation() throws IncorrectLocationException, NotSavedException {
        Location location = locationApi.findLocation();
        location.setApiManager(new APIManager());
        locations.add(location);
        crudFireBase.addLocation(location);
    }

    public List<Location> getLocations () {
        return locations;
    }


        public Location getLocation(String name){
            for(Location loc : locations){
                if (loc.getName().equals(name)){
                   return loc;
                }
            }
        return null;
        }


    public int getNumberOfLocations () {
        return locations.size();
    }


    public LocationApiInterface getLocationApi () {
        return locationApi;
    }

    public List<String> getLocationsName () {
        List<String> aux = new LinkedList<>();
        for (Location location : locations) {
            aux.add(location.getName());
        }
        return aux;
    }

    public void activateLocation(String name) throws AlreadyActiveLocation, NotSavedException {
        for (Location location: locations) {
            if (location.getName().equals(name)){
                if (location.getIsActive()) {
                    throw new AlreadyActiveLocation();
                }
                location.setActive(true);
                try {
                    crudFireBase.activateLocation(location);
                } catch (NotSavedException e) {
                    throw new NotSavedException();
                }
            }
        }
    }

    public List<String> getActiveLocationsName () {
        List<String> aux = new LinkedList<>();
        for (Location location : locations) {
            if (location.getIsActive()) {
                aux.add(location.getName());
            }
        }
        return aux;
    }

    public void setLocationApi(LocationApiInterface locationApi){
        this.locationApi = locationApi;
    }

    public void clearLocations() {
        this.locations.clear();
    }

    public void setLocations(List < Location > locations) {
        this.locations = locations;
    }

    public void setCrudFireBase(CRUDFireBase crudFireBase) {
        this.crudFireBase = crudFireBase;
    }

    public void changeActiveState(String location) {
        Location loc = getLocation(location);
        loc.setActive(!loc.getIsActive());
    }
}

