package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.modelo.api.APIsNames;

import java.util.LinkedList;
import java.util.List;

public class LocationManager{
    private List<Location> locations;
    public LocationManager() {
        locations = new LinkedList<>();
    }

    public List<Location> getActiveLocations(){
        List<Location> activeList = new LinkedList<>();
        for (Location location: this.getUserLocations()){
            if (location.getIsActive())
                activeList.add(location);
        }
        return activeList;
    }

    public List<Location> getUserLocations() {
        return locations;
    }


    public Location getLocation(String name) throws IncorrectLocationException {
        for (Location loc : locations) {
            if (loc.getName().equals(name) ||loc.getAlias().equals(name)) {
                return loc;
            }
        }
        throw new IncorrectLocationException();
    }


    public int getNumberOfLocations() {
        return locations.size();
    }


    public List<String> getActiveLocationsName() {
        List<String> aux = new LinkedList<>();
        for (Location location : locations) {
            if (location.getIsActive()) {
                aux.add(location.getAlias());
            }
        }
        return aux;
    }


    public void clearLocations() {
        this.locations.clear();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


    public void deleteLocation(String name) throws IncorrectLocationException{
        Location location = getLocation(name);
        if (!locations.remove(location))
            throw new IncorrectLocationException();
    }

    public LocationApiInterface generateApiInterface(String name){
        LocationApiInterface geoCod = new GeoCodService();
        geoCod.setSearch(new ByName(name));
        return geoCod;
    }

    public LocationApiInterface generateApiInterface(Coordinates coordinates){
        LocationApiInterface geoCod = new GeoCodService();
        geoCod.setSearch(new ByCoordinates(coordinates));
        return geoCod;
    }

    public Location findLocation(String name) throws IncorrectLocationException {
        LocationApiInterface geoCod = generateApiInterface(name);
        return geoCod.findLocation();
    }

    public Location findLocation(Coordinates coordinates) throws IncorrectLocationException {
        LocationApiInterface geoCod = generateApiInterface(coordinates);
        return geoCod.findLocation();
    }

    public Location addUserLocation(Location location) throws IncorrectLocationException {
        if (location==null||location.getAlias()==null||location.getAlias().equals(""))
            throw new IncorrectLocationException();
        locations.add(location);
        return location;
    }

    public void changeAPIState(String name, int order) throws IncorrectLocationException, NotExistingAPIException {
        if (order <0 ||order >= APIsNames.values().length){
            throw new NotExistingAPIException();
        }
        Location location = getLocation(name);
        location.changeAPIState(order);
    }

    public void changeLocationState(String name) throws IncorrectLocationException {
        Location location = getLocation(name);
        location.setActive(!location.getIsActive());
    }

    public void changeLocationAlias(String locationName, String alias) throws IncorrectLocationException, IncorrectAliasException {
        Location location = getLocation(locationName);
        location.setAlias(alias);
    }
}

