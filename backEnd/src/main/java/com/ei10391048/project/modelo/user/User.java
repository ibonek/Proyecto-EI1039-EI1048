package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public class User implements UserFacade{

    private String email;

    private String password;

    private LocationManager locationManager;

    private InformationLocationManager informationLocationManager;

    public User(){
        locationManager = new LocationManager();
        informationLocationManager = new InformationLocationManager();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public InformationLocationManager getInformationLocationManager() {
        return informationLocationManager;
    }

    public void setInformationLocationManager(InformationLocationManager informationLocationManagerFacade) {
        this.informationLocationManager = informationLocationManagerFacade;
    }

    public List<Location> getMyLocations(){
        return locationManager.getLocations();
    }

    @Override
    public void changeAPIState(int order) throws NotExistingAPIException {
        informationLocationManager.changeApiState(order,this);
    }

    @Override
    public List<List<List<APIInformation>>> getAllActivatedInfo() {
        return informationLocationManager.getAllActivatedInfo(this);
    }

    @Override
    public void addLocation(String name) throws NotSavedException, IncorrectLocationException {
        locationManager.addLocation(name);
    }

    @Override
    public void addLocation(Coordinates coords) throws NotSavedException, IncorrectLocationException {
        locationManager.addLocation(coords);
    }

    @Override
    public void deleteLocation(String name) throws IncorrectLocationException {
        locationManager.deleteLocation(name);
    }

    @Override
    public int getNumberOfLocations() {
        return locationManager.getLocations().size();
    }

    @Override
    public List<String> getActiveLocationsName() {
        return locationManager.getActiveLocationsName();
    }

    @Override
    public Location getLocation(String name) throws IncorrectLocationException {
        return locationManager.getLocation(name);
    }

    @Override
    public List<API> getApis() {
        return informationLocationManager.getApis();
    }
}
