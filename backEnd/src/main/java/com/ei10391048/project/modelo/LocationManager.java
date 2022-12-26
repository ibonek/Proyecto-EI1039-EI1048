package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.IncorectAliasException;
import com.ei10391048.project.exception.NonExistingAPIException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.api.*;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LocationManager {
    private List<Location> locations;
    private CRUDFireBase crudFireBase;

    private static LocationManager locationManager;

    private LocationApiInterface locationApi;
    private final List<API> apiList;

    private LocationManager() {
        this.locationApi = new GeoCodService();
        this.crudFireBase = new CRUDFireBase();
        try {
            this.locations = crudFireBase.getLocations();
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }

        this.apiList = new LinkedList<>();

        apiList.add(APIsNames.WEATHER.getOrder(),new OpenWeather());
        apiList.add(APIsNames.EVENTS.getOrder(),new TicketMaster());
        apiList.add(APIsNames.NEWS.getOrder(),new NewsAPI());
    }

    public List<Location> getActiveLocations(){
        List<Location> activeList = new LinkedList<>();
        for (Location location: this.getLocations()){
            if (location.getIsActive())
                activeList.add(location);
        }
        return activeList;
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

    public List<API> getApis() { return apiList; }

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

    public List<List<List<APIInformation>>> getAllActivatedInfo() throws IndexOutOfBoundsException{
        List<List<List<APIInformation>>> list = new LinkedList<>();
        for (Location location: getActiveLocations()){
            List<List<APIInformation>> listAux = new LinkedList<>();
            ApiFacade manager = location.getApiManager();
            manager.generateInfo(location.getName());

            for (int i=0;i<apiList.size();i++){
                API api = apiList.get(i);
                if (!api.getIsActive()){
                    listAux.add(new LinkedList<>());
                }else {
                    listAux.add(manager.getInformation(i));
                }
            }
            list.add(listAux);

        }
        return list;
    }

    public void deleteLocation(String name) throws IncorrectLocationException{
        Location location = getLocation(name);
        if (!locations.remove(location))
            throw new IncorrectLocationException();
        crudFireBase.deleteLocation(location);
    }

    public void changeApiState(int order) throws NonExistingAPIException {
        if (order < 0 || order >= apiList.size())
            throw new NonExistingAPIException();
        API api=apiList.get(order);
        api.setActive(false);
    }

}

