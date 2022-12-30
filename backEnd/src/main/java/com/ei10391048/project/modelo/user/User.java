package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public class User implements UserFacade{

    private String email;

    private String password;

    private LocationManager locationManager;

    private InformationLocationManager informationLocationManager;

    private CRUDFireBase crudFireBase;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(String email) {
        this.email = email;
        crudFireBase = new CRUDFireBase();
        locationManager = new LocationManager();
        try {
            locationManager.setLocations(crudFireBase.getUserLocations(email));
        } catch (IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
        informationLocationManager = new InformationLocationManager();
        try {
            informationLocationManager.setApiList(crudFireBase.getUserAPIS(email));
        } catch (NotExistingAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public User() {
        locationManager = new LocationManager();
        informationLocationManager = new InformationLocationManager();
        crudFireBase = new CRUDFireBase();
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
        try {
            crudFireBase.changeAPIStatus(email, informationLocationManager.getApiList().get(order));
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
        informationLocationManager.changeApiState(order,this);
    }

    @Override
    public List<List<List<APIInformation>>> getAllActivatedInfo() {
        return informationLocationManager.getAllActivatedInfo(this);
    }

    @Override
    public void addUserLocation(String name) throws NotSavedException, IncorrectLocationException {
        Location location=locationManager.addUserLocation(name);
        crudFireBase.addUserLocation(location, email);
    }

    @Override
    public void addUserLocation(Coordinates coords) throws NotSavedException, IncorrectLocationException {
        Location location=locationManager.addUserLocation(coords);
        crudFireBase.addUserLocation(location, email);
    }

    @Override
    public void deleteLocation(String name) throws IncorrectLocationException {
        crudFireBase.deleteUserLocation(email, name);
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
