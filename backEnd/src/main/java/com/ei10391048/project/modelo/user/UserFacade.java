package com.ei10391048.project.modelo.user;

import com.ei10391048.project.exception.AlreadyExistentLocationException;
import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotExistingAPIException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.modelo.Coordinates;
import com.ei10391048.project.modelo.InformationLocationManager;
import com.ei10391048.project.modelo.Location;
import com.ei10391048.project.modelo.LocationManager;
import com.ei10391048.project.modelo.api.API;
import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserFacade {
    String getEmail();
    void setEmail(String email);

    LocationManager getLocationManager();

    InformationLocationManager getInformationLocationManager();

    List<Location> getUserLocations();

    void changeAPIState(int order) throws NotExistingAPIException;

    List<List<List<APIInformation>>> getAllActivatedInfo();

    void addUserLocation(String name) throws NotSavedException, IncorrectLocationException, AlreadyExistentLocationException, ExecutionException, InterruptedException;

    void addUserLocation(Coordinates coords) throws NotSavedException, IncorrectLocationException, AlreadyExistentLocationException;

    void deleteLocation(String name) throws IncorrectLocationException;

    int getNumberOfLocations();

    List<String> getActiveLocationsName();

    Location getLocation(String name) throws IncorrectLocationException;

    List<API> getApis();

    void signIn();
}
