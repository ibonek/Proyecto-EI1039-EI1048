package com.ei10391048.project.modelo;


import com.ei10391048.project.exception.IncorrectAliasException;
import com.ei10391048.project.modelo.api.API;

import java.util.Objects;

public class Location {



    private String name;
    private String alias;
    private boolean isActive;
    private ApiFacade apiManager;
    private Coordinates coordinates;

    public Location() {
        apiManager = new APIManager();
        isActive=true;
    }

    public Location(String name, double lat, double lon) {
        this.name = name;
        this.alias = name;
        this.coordinates = new Coordinates(lat,lon);
        apiManager = new APIManager();
        isActive=true;
    }

    public String getName() {
        return name;
    }

    public String getAlias() { return alias; }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setName(String name) {
        this.name = name;
        this.alias = name;
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
                ", name='" + name +
                ", alias='"+ alias +
                ", coordinates=" + coordinates +
                ", isActive="+ isActive+
                '}';
    }

    public void setAlias(String alias) throws IncorrectAliasException {
        if (alias == null || alias.length()==0)
            throw new IncorrectAliasException();
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return isActive == location.isActive && Objects.equals(name, location.name) && Objects.equals(alias, location.alias) && Objects.equals(coordinates, location.coordinates);
    }


    public ApiFacade getApiManager() {
        return apiManager;
    }

    public void setApiManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }

    public void changeAPIState(int order){
        API api = apiManager.getApiList().get(order);
        api.setActive(!api.getIsActive());
    }
}
