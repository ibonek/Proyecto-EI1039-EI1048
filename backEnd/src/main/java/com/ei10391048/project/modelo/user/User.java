package com.ei10391048.project.modelo.user;

import com.ei10391048.project.modelo.InformationLocationManager;
import com.ei10391048.project.modelo.InformationLocationManagerFacade;
import com.ei10391048.project.modelo.LocationManager;
import com.ei10391048.project.modelo.LocationManagerFacade;

public class User {

    private String email;

    private String password;

    private LocationManagerFacade locationManager;

    private InformationLocationManagerFacade informationLocationManager;

    public User(){
        locationManager = new LocationManager();
        informationLocationManager = new InformationLocationManager();
        informationLocationManager.setLocationManager(locationManager);
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

    public LocationManagerFacade getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManagerFacade locationManager) {
        this.locationManager = locationManager;
    }

    public InformationLocationManagerFacade getInformationLocationManager() {
        return informationLocationManager;
    }

    public void setInformationLocationManager(InformationLocationManagerFacade informationLocationManagerFacade) {
        this.informationLocationManager = informationLocationManagerFacade;
    }
}
