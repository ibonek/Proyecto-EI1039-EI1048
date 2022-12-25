package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocationManager implements LocationManagerFacade{
    private List<Location> locations;
    private CRUDFireBase crudFireBase;

    private static InformationLocationManagerFacade informationLocationManager;

    private LocationApiInterface locationApi;
    private static LocationManagerFacade locationManager;

    private LocationManager() {
        this.locations = new LinkedList<>();
        this.crudFireBase = new CRUDFireBase();
        this.locationApi = new GeoCodService();

    }

    public InformationLocationManagerFacade getActivationManager() {
        return InformationLocationManager.getInstance();
    }

    public List<Location> getActiveLocations(){
        List<Location> activeList = new LinkedList<>();
        for (Location location: this.getLocations()){
            if (location.getIsActive())
                activeList.add(location);
        }
        return activeList;
    }

    @Override
    public void setLocationApi(LocationApiInterface locationApi) {
        this.locationApi = locationApi;
    }

    @Override
    public LocationApiInterface getLocationApi() {
        return locationApi;
    }

    public static LocationManagerFacade getInstance() {
        if (locationManager == null) {
            locationManager = new LocationManager();
        }
        return locationManager;

    }


    public List<Location> getLocations() {
        return locations;
    }


    public Location getLocation(String name) throws IncorrectLocationException {
        for (Location loc : locations) {
            if (loc.getName().equals(name)) {
                return loc;
            }
        }
        throw new IncorrectLocationException();
    }


    public int getNumberOfLocations() {
        return locations.size();
    }



    public List<String> getLocationsName() {
        List<String> aux = new LinkedList<>();
        for (Location location : locations) {
            aux.add(location.getName());
        }
        return aux;
    }

    public List<String> getActiveLocationsName() {
        List<String> aux = new LinkedList<>();
        for (Location location : locations) {
            if (location.getIsActive()) {
                aux.add(location.getName());
            }
        }
        return aux;
    }

    public List<String> getLocationsAlias() {
        List<String> aux = new ArrayList<>();
        for (Location location : locations) {
            aux.add(location.getAlias());
        }
        return aux;
    }

    public void clearLocations() {
        this.locations.clear();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void setCrudFireBase(CRUDFireBase crudFireBase) {
        this.crudFireBase = crudFireBase;
    }


    public void setAlias(String name, String s) throws IncorrectAliasException, IncorrectLocationException {
        if (s.isEmpty()) throw new IncorrectAliasException();
        Location loc;
        loc = getLocation(name);
        loc.setAlias(s);
    }


    public void deleteLocation(String name) throws IncorrectLocationException{
        Location location = getLocation(name);
        if (!locations.remove(location))
            throw new IncorrectLocationException();
    }



    @Override
    public void addLocation(String name) throws IncorrectLocationException, NotSavedException {
        GeoCodService geoCodSrv = new GeoCodService();
        geoCodSrv.setSearch(new ByName(name));
        Location location = geoCodSrv.findLocation();
        location.setApiManager(new APIManager());
        locations.add(location);
        crudFireBase.addLocation(location);
    }

    @Override
    public void addLocation(Coordinates coords) throws IncorrectLocationException, NotSavedException {
        GeoCodService geoCodSrv = new GeoCodService();
        geoCodSrv.setSearch(new ByCoordinates(coords));
        Location location = geoCodSrv.findLocation();
        location.setApiManager(new APIManager());
        locations.add(location);
        crudFireBase.addLocation(location);
    }
}

