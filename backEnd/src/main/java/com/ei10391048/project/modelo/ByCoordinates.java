package com.ei10391048.project.modelo;

import com.ei10391048.project.exception.IncorrectLocationException;

public class ByCoordinates implements SearchInterface{
    private Coordinates coordinates;

    @Override
    public Location search() throws IncorrectLocationException {
        if (coordinates.getLon() > 180.0 || coordinates.getLon()<-180.0 || coordinates.getLat()>180.0 || coordinates.getLat()<-180.0) {
            Location location = new Location(coordinates.getLat(), coordinates.getLon());
            return location;
        }
        throw new IncorrectLocationException();
    }

    public ByCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
