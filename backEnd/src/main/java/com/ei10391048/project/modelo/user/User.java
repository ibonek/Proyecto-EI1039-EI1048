package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.*;
import com.ei10391048.project.fireBase.CRUDFireBase;
import com.ei10391048.project.modelo.*;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class User implements UserFacade{

    private String email;

    private final LocationManager locationManager;

    private final InformationLocationManager informationLocationManager;

    private final CRUDFireBase crudFireBase;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }

    public User() {
        locationManager = new LocationManager();
        informationLocationManager = new InformationLocationManager();
        crudFireBase = CRUDFireBase.getInstance();
    }

    public User(String email) {
        this.email = email;
        locationManager = new LocationManager();
        informationLocationManager = new InformationLocationManager();
        crudFireBase = CRUDFireBase.getInstance();
        try {
            locationManager.setLocations(crudFireBase.getUserLocations(email));
            informationLocationManager.setApiList(crudFireBase.getUserAPIS(email));
        } catch (IncorrectLocationException | NotExistingAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public InformationLocationManager getInformationLocationManager() {
        return informationLocationManager;
    }

    public List<Location> getUserLocations(){
        return locationManager.getUserLocations();
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
    public void addUserLocation(String name) throws NotSavedException, IncorrectLocationException, ExecutionException, InterruptedException {
        try {
            Location location=locationManager.findLocation(name);
            location.getApiManager().copyApiListState(informationLocationManager.getApiList());
            crudFireBase.addUserLocation(location, email);
            locationManager.addUserLocation(location);
        } catch (AlreadyExistentLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addUserLocation(Coordinates coords) throws NotSavedException, IncorrectLocationException, AlreadyExistentLocationException {
        try {
            Location location=locationManager.findLocation(coords);
            location.getApiManager().copyApiListState(informationLocationManager.getApiList());
            crudFireBase.addUserLocation(location, email);
            locationManager.addUserLocation(location);
        } catch (AlreadyExistentLocationException e) {
            throw new AlreadyExistentLocationException();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLocation(String name) throws IncorrectLocationException {
        crudFireBase.deleteUserLocation(email, name);
        locationManager.deleteLocation(name);
    }

    @Override
    public List<API> getApis() {
        return informationLocationManager.getApis();
    }

    @Override
    public void signIn() {
        try {
            locationManager.setLocations(crudFireBase.getUserLocations(email));
            informationLocationManager.setApiList(crudFireBase.getUserAPIS(email));
        } catch (IncorrectLocationException | NotExistingAPIException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void changeLocationAPIState(String locationName, int order) throws IncorrectLocationException, NotExistingAPIException {
        try {
            crudFireBase.changeUserLocationAPIStatus(email,locationName,informationLocationManager.getApiList().get(order));
            locationManager.changeAPIState(locationName,order);
        } catch (NotSavedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeUserLocationState(String email, Location loc) {
        try {
            crudFireBase.changeUserLocationStatus(email,loc);
            locationManager.changeLocationState(loc.getName());
        } catch (NotSavedException | IncorrectLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeUserLocationAlias(String email, String locationName, String alias) {
        try {
            crudFireBase.changeUserLocationAlias(email,locationName,alias);
            locationManager.changeLocationAlias(locationName,alias);
        } catch (IncorrectLocationException | IncorrectAliasException e) {
            throw new RuntimeException(e);
        }
    }
}
