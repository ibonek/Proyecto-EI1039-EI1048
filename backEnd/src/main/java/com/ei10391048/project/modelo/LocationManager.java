package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.IncorectAliasException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.addAll;

public class LocationManager {
    private List<Location> locations;
    private CRUDFireBase crudFireBase;

    private static LocationManager locationManager;

    private LocationApiInterface locationApi;
    private List<API> apiList;

    private LocationManager() {

        this.locations = new LinkedList<>();
        this.locationApi = new GeoCodService();
        this.crudFireBase = new CRUDFireBase();
        this.apiList = new LinkedList<>();

        apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());

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

    public List<Location> getLocations() {
        return locations;
    }

    public List<API> getApis() { return null; }

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

    public LocationApiInterface getLocationApi() {
        return locationApi;
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

    public void setLocationApi(LocationApiInterface locationApi) {
        this.locationApi = locationApi;
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

    public void changeActiveState(String location) throws IncorrectLocationException {
        Location loc;
        try {
            loc = getLocation(location);
        } catch (IncorrectLocationException e) {
            throw new IncorrectLocationException();
        }
        loc.setActive(!loc.getIsActive());
    }

    public void setAlias(String name, String s) throws IncorectAliasException, IncorrectLocationException {
        if (s.isEmpty()) throw new IncorectAliasException();
        Location loc;
        loc = getLocation(name);
        loc.setAlias(s);
    }

}

