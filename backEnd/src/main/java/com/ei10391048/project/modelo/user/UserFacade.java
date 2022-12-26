package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.InformationLocationManager;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;

public interface UserFacade {
    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);

    LocationManager getLocationManager();

    InformationLocationManager getInformationLocationManager();

    List<Location> getMyLocations();

    void changeAPIState(int order) throws NotExistingAPIException;

    List<List<List<APIInformation>>> getAllActivatedInfo();

    void addLocation(String name) throws NotSavedException, IncorrectLocationException;

    void addLocation(Coordinates coords) throws NotSavedException, IncorrectLocationException;

    void deleteLocation(String name) throws IncorrectLocationException;

    int getNumberOfLocations();

    List<String> getActiveLocationsName();
}
