package com.ei10391048.project.modelo;


import java.util.Objects;

public class Location {



    private String name;
    private boolean isActive=true;


    private ApiFacade apiManager;

    private Coordinates coordinates;

    public Location() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return isActive == location.isActive && Objects.equals(name, location.name) && Objects.equals(coordinates, location.coordinates);
    }


    public ApiFacade getApiManager() {
        return apiManager;
    }

    public void setApiManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }
}
