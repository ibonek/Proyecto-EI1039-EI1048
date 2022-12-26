package com.ei10391048.project.modelo;


import com.ei10391048.project.fireBase.CRUDFireBase;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;

import java.util.LinkedList;
import java.util.List;

public class LocationManager implements LocationManagerFacade{
    private List<Location> locations;
    private final CRUDFireBase crudFireBase;
    private static LocationManagerFacade locationManager;

    private LocationManager() {
        this.locations = new LinkedList<>();
        this.crudFireBase = new CRUDFireBase();
        InformationLocationManager.getInstance();
        try {
            this.locations = crudFireBase.getLocations();
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Location> getActiveLocations(){
        List<Location> activeList = new LinkedList<>();
        for (Location location: this.getLocations()){
            if (location.getIsActive())
                activeList.add(location);
        }
        return activeList;
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
            if (loc.getAlias().equals(name)) {
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
        crudFireBase.deleteLocations();
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


    public void deleteLocation(String name) throws IncorrectLocationException{
        Location location = getLocation(name);
        if (!locations.remove(location))
            throw new IncorrectLocationException();
        crudFireBase.deleteLocation(location);
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

    @Override
    public Location addLocation(String name) throws IncorrectLocationException, NotSavedException {
        LocationApiInterface geoCod = generateApiInterface(name);
        Location location = geoCod.findLocation();

        location.setApiManager(new APIManager());
        locations.add(location);
        crudFireBase.addLocation(location);
        return location;
    }

    @Override
    public Location addLocation(Coordinates coords) throws IncorrectLocationException, NotSavedException {
        LocationApiInterface geoCod = generateApiInterface(coords);
        Location location = geoCod.findLocation();
        location.setApiManager(new APIManager());
        locations.add(location);
        crudFireBase.addLocation(location);
        return location;
    }

}

