package com.ei10391048.project.modelo;

import com.ei10391048.project.fireBase.CRUDFireBase;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;

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

    public List<Location> getLocations(){
        return locations;
    }
    public int getNumberOfLocations() {
        return locations.size();
    }


    public LocationApiInterface getLocationApi() {
        return locationApi;
    }

    public void setLocationApi(LocationApiInterface locationApi) {
        this.locationApi = locationApi;
    }

}
