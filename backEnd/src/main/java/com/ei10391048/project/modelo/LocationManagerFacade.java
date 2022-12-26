package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;
import com.ei10391048.project.exception.NotSavedException;
import com.ei10391048.project.fireBase.CRUDFireBase;

import java.util.List;

public interface LocationManagerFacade {
    Location addLocation(String name) throws IncorrectLocationException, NotSavedException;

    Location addLocation(Coordinates coords) throws IncorrectLocationException, NotSavedException;

    int getNumberOfLocations();

    void clearLocations();

    void setLocations(List<Location> locations);

    List<Location> getLocations();

    List<String> getActiveLocationsName();

    Location getLocation(String name) throws IncorrectLocationException;

    void deleteLocation(String name) throws IncorrectLocationException;

    List<Location> getActiveLocations();

}
