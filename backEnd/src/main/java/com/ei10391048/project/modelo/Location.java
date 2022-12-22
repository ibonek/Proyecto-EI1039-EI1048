package com.ei10391048.project.modelo;


public class Location {


    private String name;


    private ApiFacade apiManager;

    private Coordinates coordinates;

    public Location() {

    }

    public String getName() {
        return name;
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
                '}';
    }


    public ApiFacade getApiManager() {
        return apiManager;
    }

    public void setApiManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }
}
