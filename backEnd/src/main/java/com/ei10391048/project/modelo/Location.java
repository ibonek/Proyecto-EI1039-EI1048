package com.ei10391048.project.modelo;

import javax.persistence.*;

public class Location {

    private String name;
    private boolean isActive=true;

    private Coordinates coordinates;
    public Location(String name) {
        this.name = name;
    }

    public Location(Coordinates coordinates) {
        this.coordinates=coordinates;
    }

    public Location() {

    }
    public Location(double lat, double lon) {
        this.coordinates = new Coordinates(lat,lon);

    }

    public Location(String teruel, double lat, double lon) {
        this.name = teruel;
        this.coordinates = new Coordinates(lat,lon);
    }

    public String getName() {
        return name;
    }

    public boolean getIsActive() {
        return isActive;
    }



    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", isActive="+ isActive+
                '}';
    }
}
