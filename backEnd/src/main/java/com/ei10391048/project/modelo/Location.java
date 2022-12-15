package com.ei10391048.project.modelo;

import com.ei10391048.project.modelo.information.APIInformation;

import java.util.List;


public class Location {


    private String name;


    private APIManager apiManager;

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


    public APIManager getApiManager() {
        return apiManager;
    }

    public List<List<APIInformation>> getInfo(){
        return apiManager.getInfo(name);

    }

    public void setApiManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }
}
